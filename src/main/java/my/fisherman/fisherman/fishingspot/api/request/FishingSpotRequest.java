package my.fisherman.fisherman.fishingspot.api.request;

public class FishingSpotRequest {

    public record Send(
            Long smeltId,
            String title,
            String content,
            String senderName
    ) {}
}
