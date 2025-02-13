package my.fisherman.fisherman.fishingspot.application;

import java.util.List;
import lombok.RequiredArgsConstructor;
import my.fisherman.fisherman.fishingspot.application.dto.FishingSpotInfo;
import my.fisherman.fisherman.fishingspot.domain.FishingSpot;
import my.fisherman.fisherman.fishingspot.repository.FishingSpotRepository;
import my.fisherman.fisherman.smelt.domain.Smelt;
import my.fisherman.fisherman.smelt.repository.SmeltRepository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@RequiredArgsConstructor
public class FishingSpotService {

    private final FishingSpotRepository fishingSpotRepository;
    private final SmeltRepository smelRepository;

    public List<FishingSpotInfo.Simple> searchFishingSpot(String keyword) {

        var fishingSpots = fishingSpotRepository.searchByKeyword(keyword);

        return fishingSpots.stream()
            .map(FishingSpotInfo.Simple::from)
            .toList();
    }

    @Transactional(readOnly = true)
    public FishingSpotInfo.SmeltPage getSmelts(Long fishingSpotId, Pageable pageable) {
        // TODO: NotFound 예외 처리
        FishingSpot fishingSpot = fishingSpotRepository.findById(fishingSpotId)
                                    .orElseThrow();

        Page<Smelt> smeltPage = smelRepository.findAllByFishingSpot(fishingSpot, pageable);

        return FishingSpotInfo.SmeltPage.of(fishingSpot, smeltPage);
    }
}
