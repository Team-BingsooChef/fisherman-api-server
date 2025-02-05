package my.fisherman.fisherman.fishingspot.application;

import java.util.List;
import lombok.RequiredArgsConstructor;
import my.fisherman.fisherman.fishingspot.application.dto.FishingSpotInfo;
import my.fisherman.fisherman.fishingspot.repository.FishingSpotRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FishingSpotService {

    private final FishingSpotRepository fishingSpotRepository;

    public List<FishingSpotInfo.Simple> searchFishingSpot(String keyword) {

        var fishingSpots = fishingSpotRepository.searchByKeyword(keyword);

        return fishingSpots.stream()
            .map(FishingSpotInfo.Simple::from)
            .toList();
    }
}
