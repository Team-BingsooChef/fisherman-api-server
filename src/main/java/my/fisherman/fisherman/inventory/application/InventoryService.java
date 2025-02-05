package my.fisherman.fisherman.inventory.application;

import java.util.List;

import org.springframework.stereotype.Service;

import io.netty.util.internal.ThreadLocalRandom;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import my.fisherman.fisherman.inventory.application.dto.InventoryInfo;
import my.fisherman.fisherman.inventory.domain.Inventory;
import my.fisherman.fisherman.inventory.repository.InventoryRepository;
import my.fisherman.fisherman.smelt.domain.Smelt;
import my.fisherman.fisherman.smelt.domain.SmeltType;
import my.fisherman.fisherman.smelt.repository.SmeltRepository;
import my.fisherman.fisherman.smelt.repository.SmeltTypeRepository;

@RequiredArgsConstructor
@Service
public class InventoryService {
    private final InventoryRepository inventoryRepository;
    private final SmeltRepository smeltRepository;
    private final SmeltTypeRepository smeltTypeRepository;

    @Transactional
    public InventoryInfo.SmeltInfo drawSmelt(Long inventoryId, Long userId) {
        // TODO: NotFound 예외 처리
        Inventory inventory = inventoryRepository.findById(inventoryId)
                .orElseThrow();

        if (!inventory.isReadableBy(userId)) {
            // TODO: 예외 처리
        }

        int randomNumber = ThreadLocalRandom.current().nextInt(101);
        SmeltType smeltType = drawSmeltType(randomNumber);
        Smelt smelt = Smelt.of(inventory, smeltType);

        Smelt savedSmelt = smeltRepository.save(smelt);

        return InventoryInfo.SmeltInfo.from(savedSmelt);
    }

    private SmeltType drawSmeltType(int randomNumber) {
        List<SmeltType> smeltTypes = smeltTypeRepository.findAllOrderByCumulativeProbability();
        for (SmeltType type : smeltTypes) {
            if (type.getCumulativeProbability() >= randomNumber) {
                return type;
            }
        }

        return smeltTypes.get(smeltTypes.size() - 1);
    }
}
