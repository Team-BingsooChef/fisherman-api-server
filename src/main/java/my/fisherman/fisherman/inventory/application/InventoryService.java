package my.fisherman.fisherman.inventory.application;

import lombok.RequiredArgsConstructor;
import my.fisherman.fisherman.smelt.domain.SmeltType;
import my.fisherman.fisherman.smelt.repository.SmeltTypeRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

@RequiredArgsConstructor
@Service
public class InventoryService {
    private final SmeltTypeRepository smeltTypeRepository;

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
