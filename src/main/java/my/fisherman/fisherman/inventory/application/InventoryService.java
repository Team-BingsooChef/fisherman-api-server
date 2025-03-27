package my.fisherman.fisherman.inventory.application;

import lombok.RequiredArgsConstructor;
import my.fisherman.fisherman.global.exception.FishermanException;
import my.fisherman.fisherman.global.exception.code.InventoryErrorCode;
import my.fisherman.fisherman.global.exception.code.UserErrorCode;
import my.fisherman.fisherman.inventory.application.dto.InventoryInfo;
import my.fisherman.fisherman.inventory.domain.Inventory;
import my.fisherman.fisherman.inventory.repository.InventoryRepository;
import my.fisherman.fisherman.security.util.SecurityUtil;
import my.fisherman.fisherman.smelt.domain.Smelt;
import my.fisherman.fisherman.smelt.domain.SmeltStatus;
import my.fisherman.fisherman.smelt.domain.SmeltType;
import my.fisherman.fisherman.smelt.repository.SmeltRepository;
import my.fisherman.fisherman.smelt.repository.SmeltTypeRepository;
import my.fisherman.fisherman.smelt.repository.dto.SmeltTypeCount;
import my.fisherman.fisherman.user.domain.User;
import my.fisherman.fisherman.user.repository.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

@RequiredArgsConstructor
@Service
public class InventoryService {
    private final InventoryRepository inventoryRepository;
    private final SmeltRepository smeltRepository;
    private final SmeltTypeRepository smeltTypeRepository;
    private final UserRepository userRepository;

    @Transactional(readOnly = true)
    public InventoryInfo.Simple getMyInventory() {
        Long userId = SecurityUtil.getCurrentUserId()
            .orElseThrow(() -> new FishermanException(UserErrorCode.FORBIDDEN));

        User user = userRepository.findById(userId)
            .orElseThrow(() -> new FishermanException(InventoryErrorCode.NOT_FOUND, "현재 사용자를 찾을 수 없습니다."));
        Inventory inventory = inventoryRepository.findByUser(user)
            .orElseThrow(() -> new FishermanException(InventoryErrorCode.NOT_FOUND, "현재 사용자의 인벤토리를 찾을 수 없습니다."));

        return InventoryInfo.Simple.from(inventory);
    }

    @Transactional
    public InventoryInfo.SmeltInfo drawSmelt(Long inventoryId) {
        Long userId = SecurityUtil.getCurrentUserId()
            .orElseThrow(() -> new FishermanException(UserErrorCode.FORBIDDEN));

        User user = userRepository.findById(userId)
            .orElseThrow(() -> new FishermanException(InventoryErrorCode.NOT_FOUND, "현재 사용자를 찾을 수 없습니다."));
        Inventory inventory = inventoryRepository.findById(inventoryId)
            .orElseThrow(() -> new FishermanException(InventoryErrorCode.NOT_FOUND, "인벤토리 %d을/를 찾을 수 없습니다.".formatted(inventoryId)));

        inventory.checkReadable(user);

        inventory.decreaseCoin(5L);
        SmeltType smeltType = drawSmeltType();
        Smelt smelt = Smelt.of(inventory, smeltType);

        inventoryRepository.save(inventory);
        Smelt savedSmelt = smeltRepository.save(smelt);

        return InventoryInfo.SmeltInfo.from(savedSmelt);
    }

    @Transactional(readOnly = true)
    public InventoryInfo.SentSmeltPage searchSentSmelt(Long inventoryId, Pageable pageable) {
        Long userId = SecurityUtil.getCurrentUserId()
            .orElseThrow(() -> new FishermanException(UserErrorCode.FORBIDDEN));

        User user = userRepository.findById(userId)
            .orElseThrow(() -> new FishermanException(InventoryErrorCode.NOT_FOUND, "현재 사용자를 찾을 수 없습니다."));
        Inventory inventory = inventoryRepository.findById(inventoryId)
            .orElseThrow(() -> new FishermanException(InventoryErrorCode.NOT_FOUND, "인벤토리 %d을/를 찾을 수 없습니다.".formatted(inventoryId)));

        inventory.checkReadable(user);

        Page<Smelt> smeltPage = smeltRepository.findAllByAndInventoryIsAndFishingSpotIsNotNull(inventory, pageable);

        return InventoryInfo.SentSmeltPage.of(smeltPage);
    }

    public List<InventoryInfo.Statistic> getDrewStatistics(Long inventoryId) {
        Long userId = SecurityUtil.getCurrentUserId()
            .orElseThrow(() -> new FishermanException(UserErrorCode.FORBIDDEN));

        User user = userRepository.findById(userId)
            .orElseThrow(() -> new FishermanException(InventoryErrorCode.NOT_FOUND, "현재 사용자를 찾을 수 없습니다."));
        Inventory inventory = inventoryRepository.findById(inventoryId)
            .orElseThrow(() -> new FishermanException(InventoryErrorCode.NOT_FOUND, "인벤토리 %d을/를 찾을 수 없습니다.".formatted(inventoryId)));

        inventory.checkReadable(user);

        List<SmeltTypeCount> counts = smeltRepository.countAllByInventoryAndStatusGroupByType(inventory, SmeltStatus.DREW);

        return counts.stream().map(InventoryInfo.Statistic::from).toList();
    }

    private SmeltType drawSmeltType() {
        int randomNumber = ThreadLocalRandom.current().nextInt(101);

        List<SmeltType> smeltTypes = smeltTypeRepository.findAllByOrderByNameAsc();

        int sumOfProbability = 0;
        for (SmeltType type : smeltTypes) {
            sumOfProbability += type.getProbability();
            if (sumOfProbability >= randomNumber) {
                return type;
            }
        }

        return smeltTypes.get(smeltTypes.size() - 1);
    }
}
