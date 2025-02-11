package my.fisherman.fisherman.inventory.application;

import lombok.RequiredArgsConstructor;
import my.fisherman.fisherman.inventory.application.dto.InventoryInfo;
import my.fisherman.fisherman.inventory.domain.Inventory;
import my.fisherman.fisherman.inventory.repository.InventoryRepository;
import my.fisherman.fisherman.smelt.domain.Smelt;
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


    @Transactional
    public InventoryInfo.SmeltInfo drawSmelt(Long userId, Long inventoryId) {
        User user = userRepository.getReferenceById(userId);
        Inventory inventory = inventoryRepository.getReferenceById(inventoryId);

        if (!inventory.isReadableBy(user)) {
            // TODO: 예외 처리
        }

        SmeltType smeltType = drawSmeltType();

        Smelt smelt = Smelt.of(inventory, smeltType);

        Smelt savedSmelt = smeltRepository.save(smelt);

        return InventoryInfo.SmeltInfo.from(savedSmelt);
    }

    @Transactional(readOnly = true)
    public InventoryInfo.SentSmeltPage searchSentSmelt(Long userId, Long inventoryId, Pageable pageable) {
        // TODO: Not found 예외 처리
        User user = userRepository.getReferenceById(userId);
        Inventory inventory = inventoryRepository.getReferenceById(inventoryId);

        if (!inventory.isReadableBy(user)) {
            // TODO: 예외 처리
        }

        Page<Smelt> smeltPage = smeltRepository.findAllByAndInventoryIsAndFishingSpotIsNotNull(inventory, pageable);

        return InventoryInfo.SentSmeltPage.of(smeltPage);
    }

    public List<InventoryInfo.Statistic> getStatistics(Long userId, Long inventoryId) {
        // TODO: Not found 예외 처리
        User user = userRepository.getReferenceById(userId);
        Inventory inventory = inventoryRepository.getReferenceById(inventoryId);

        if (!inventory.isReadableBy(user)) {
            // TODO: 예외 처리
        }

        List<SmeltTypeCount> counts = smeltRepository.countAllByInventoryIsGroupByType(inventory);

        return counts.stream().map(InventoryInfo.Statistic::from).toList();
    }

    private SmeltType drawSmeltType() {
        int randomNumber = ThreadLocalRandom.current().nextInt(101);

        List<SmeltType> smeltTypes = smeltTypeRepository.findAllByOrderByCumulativeProbabilityAsc();
        for (SmeltType type : smeltTypes) {
            if (type.getCumulativeProbability() >= randomNumber) {
                return type;
            }
        }

        return smeltTypes.get(smeltTypes.size() - 1);
    }
}
