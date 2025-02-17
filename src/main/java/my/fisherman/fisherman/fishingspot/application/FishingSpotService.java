package my.fisherman.fisherman.fishingspot.application;

import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import my.fisherman.fisherman.fishingspot.application.command.FishingSpotCommand;
import my.fisherman.fisherman.fishingspot.application.dto.FishingSpotInfo;
import my.fisherman.fisherman.fishingspot.domain.FishingSpot;
import my.fisherman.fisherman.fishingspot.repository.FishingSpotRepository;
import my.fisherman.fisherman.security.util.SecurityUtil;
import my.fisherman.fisherman.smelt.domain.Letter;
import my.fisherman.fisherman.smelt.domain.Question;
import my.fisherman.fisherman.smelt.domain.Quiz;
import my.fisherman.fisherman.smelt.domain.QuizType;
import my.fisherman.fisherman.smelt.domain.Smelt;
import my.fisherman.fisherman.smelt.repository.QuestionRepository;
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
    private final SmeltRepository smelRepository;
    private final UserRepository userRepository;
    private final QuestionRepository questionRepository;

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
    
    public FishingSpotInfo.DetailSmelt sendSmeltTo(FishingSpotCommand.SendSmelt command) {
        // TODO: 사용자 ID를 가져오지 못하는 예외 처리
        Long userId = SecurityUtil.getCurrentUserId().orElseThrow();
        
        //TODO: NotFound 예외 처리
        User user = userRepository.findById(userId).orElseThrow();
        Smelt smelt = smelRepository.findById(command.smeltId()).orElseThrow();
        FishingSpot fishingSpot = fishingSpotRepository.findById(command.fishingSpotId()).orElseThrow();

        Letter letter = Letter.of(command.letterTitle(), command.letterContent(), command.senderName());
        Quiz quiz = null;
        List<Question> questions = null;
        if (command.existQuiz()) {
            QuizType quizType = QuizType.valueOf(command.quiztype());
            quiz = Quiz.of(command.quizTitle(), quizType);

            questions = new ArrayList<>();
            for (int i = 0; i < command.questions().size(); i++) {
                questions.add(Question.of(command.questions().get(i), i == command.answerIndex(), quiz));
            }
        }
        
        smelt.send(user, fishingSpot, letter, quiz);

        smelRepository.save(smelt);
        questionRepository.saveAll(questions);

        // TODO: 질문 조회
        return FishingSpotInfo.DetailSmelt.of(smelt, questions);
    }
}
