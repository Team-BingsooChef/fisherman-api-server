package my.fisherman.fisherman.inventory.application;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import my.fisherman.fisherman.inventory.application.dto.InventoryInfo;
import my.fisherman.fisherman.inventory.domain.Inventory;
import my.fisherman.fisherman.inventory.repository.InventoryRepository;
import my.fisherman.fisherman.smelt.domain.Smelt;
import my.fisherman.fisherman.smelt.domain.SmeltType;
import my.fisherman.fisherman.smelt.repository.SmeltRepository;
import my.fisherman.fisherman.smelt.repository.SmeltTypeRepository;
import my.fisherman.fisherman.user.domain.User;
import my.fisherman.fisherman.user.repository.UserRepository;
import org.springframework.stereotype.Service;

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

    private SmeltType drawSmeltType() {
        int randomNumber = ThreadLocalRandom.current().nextInt(101);

        List<SmeltType> smeltTypes = smeltTypeRepository.findAllOrderByCumulativeProbability();
        for (SmeltType type : smeltTypes) {
            if (type.getCumulativeProbability() >= randomNumber) {
                return type;
            }
        }

        return smeltTypes.get(smeltTypes.size() - 1);
    }
}
