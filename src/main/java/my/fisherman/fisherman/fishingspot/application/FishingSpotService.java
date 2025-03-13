package my.fisherman.fisherman.fishingspot.application;

import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import my.fisherman.fisherman.fishingspot.application.command.FishingSpotCommand;
import my.fisherman.fisherman.fishingspot.application.dto.FishingSpotInfo;
import my.fisherman.fisherman.fishingspot.domain.FishingSpot;
import my.fisherman.fisherman.fishingspot.repository.FishingSpotRepository;
import my.fisherman.fisherman.global.exception.FishermanException;
import my.fisherman.fisherman.global.exception.code.FishingSpotErrorCode;
import my.fisherman.fisherman.inventory.domain.Inventory;
import my.fisherman.fisherman.inventory.repository.InventoryRepository;
import my.fisherman.fisherman.security.util.SecurityUtil;
import my.fisherman.fisherman.smelt.domain.Letter;
import my.fisherman.fisherman.smelt.domain.Question;
import my.fisherman.fisherman.smelt.domain.Quiz;
import my.fisherman.fisherman.smelt.domain.QuizType;
import my.fisherman.fisherman.smelt.domain.Smelt;
import my.fisherman.fisherman.smelt.repository.LetterRepository;
import my.fisherman.fisherman.smelt.repository.QuestionRepository;
import my.fisherman.fisherman.smelt.repository.QuizRepository;
import my.fisherman.fisherman.smelt.repository.SmeltRepository;
import my.fisherman.fisherman.user.domain.User;
import my.fisherman.fisherman.user.repository.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@RequiredArgsConstructor
public class FishingSpotService {

    private final FishingSpotRepository fishingSpotRepository;
    private final InventoryRepository inventoryRepository;
    private final SmeltRepository smeltRepository;
    private final LetterRepository letterRepository;
    private final UserRepository userRepository;
    private final QuizRepository quizRepository;
    private final QuestionRepository questionRepository;

    @Transactional(readOnly = true)
    public FishingSpotInfo.Simple getMyFishingSpot() {
        // TODO: 사용자 ID를 가져오지 못하는 예외 처리
        Long userId = SecurityUtil.getCurrentUserId().orElseThrow();

        User user = userRepository.findById(userId)
            .orElseThrow(
                () -> new FishermanException(FishingSpotErrorCode.NOT_FOUND, "현재 사용자를 찾을 수 없습니다."));
        FishingSpot fishingSpot = fishingSpotRepository.findByFisherman(user)
            .orElseThrow(() -> new FishermanException(FishingSpotErrorCode.NOT_FOUND,
                "현재 사용자의 낚시터를 찾을 수 없습니다."));

        return FishingSpotInfo.Simple.from(fishingSpot);
    }

    @Transactional(readOnly = true)
    public List<FishingSpotInfo.Simple> searchFishingSpot(String keyword) {

        List<FishingSpot> fishingSpots = fishingSpotRepository.searchByKeyword(keyword);

        return fishingSpots.stream()
            .map(FishingSpotInfo.Simple::from)
            .toList();
    }

    @Transactional(readOnly = true)
    public FishingSpotInfo.SmeltPage getSmelts(Long fishingSpotId, Pageable pageable) {
        FishingSpot fishingSpot = fishingSpotRepository.findById(fishingSpotId)
            .orElseThrow(() -> new FishermanException(FishingSpotErrorCode.NOT_FOUND,
                "낚시터 %d을/를 찾을 수 없습니다.".formatted(fishingSpotId)));

        Page<Smelt> smeltPage = smeltRepository.findAllByFishingSpot(fishingSpot, pageable);

        return FishingSpotInfo.SmeltPage.of(fishingSpot, smeltPage);
    }

    @Transactional
    public FishingSpotInfo.DetailSmelt sendSmeltTo(FishingSpotCommand.SendSmelt command) {
        // TODO: 사용자 ID를 가져오지 못하는 예외 처리
        Long userId = SecurityUtil.getCurrentUserId().orElseThrow();

        User user = userRepository.findById(userId)
            .orElseThrow(
                () -> new FishermanException(FishingSpotErrorCode.NOT_FOUND, "현재 사용자를 찾을 수 없습니다."));
        Smelt smelt = smeltRepository.findById(command.smeltId())
            .orElseThrow(() -> new FishermanException(FishingSpotErrorCode.NOT_FOUND,
                "빙어 %d을/를 찾을 수 없습니다.".formatted(command.smeltId())));
        FishingSpot fishingSpot = fishingSpotRepository.findById(command.fishingSpotId())
            .orElseThrow(() -> new FishermanException(FishingSpotErrorCode.NOT_FOUND,
                "낚시터 %d을/를 찾을 수 없습니다.".formatted(command.fishingSpotId())));

        Letter letter = Letter.of(command.letterTitle(), command.letterContent(),
            command.senderName());
        Quiz quiz = null;
        List<Question> questions = null;
        if (command.existQuiz()) {
            QuizType quizType = QuizType.getQuizType(command.quiztype());
            quiz = Quiz.of(command.quizTitle(), quizType);

            questions = new ArrayList<>();
            for (int i = 0; i < command.questions().size(); i++) {
                questions.add(
                    Question.of(command.questions().get(i), i == command.answerIndex(), quiz));
            }
        }

        smelt.send(user, fishingSpot, letter, quiz);

        quizRepository.save(quiz);
        letterRepository.save(letter);
        smeltRepository.save(smelt);
        questionRepository.saveAll(questions);

        Inventory senderInventory = smelt.getInventory();
        senderInventory.increaseCoin(5L);
        Inventory recieverInventory = inventoryRepository.findByUser(
                smelt.getFishingSpot().getFisherman())
            .orElseThrow(() -> new FishermanException(FishingSpotErrorCode.NOT_FOUND,
                "낚시꾼의 인벤토리를 찾을 수 없습니다."));
        recieverInventory.increaseCoin(3L);

        inventoryRepository.save(senderInventory);
        inventoryRepository.save(recieverInventory);

        return FishingSpotInfo.DetailSmelt.of(smelt, questions);
    }

    @Transactional
    public void updatePublic(FishingSpotCommand.UpdatePublic command) {
        FishingSpot fishingSpot = fishingSpotRepository.findById(command.fishingSpotId())
            .orElseThrow(() -> new FishermanException(FishingSpotErrorCode.NOT_FOUND,
                "낚시터 %d을/를 찾을 수 없습니다.".formatted(command.fishingSpotId())));
        Long userId = SecurityUtil.getCurrentUserId().orElseThrow();
        fishingSpot.updatePublic(userId, command.isPublic());
    }
}
