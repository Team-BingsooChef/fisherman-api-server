package my.fisherman.fisherman.smelt.application;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import my.fisherman.fisherman.global.exception.FishermanException;
import my.fisherman.fisherman.global.exception.code.SmeltErrorCode;
import my.fisherman.fisherman.global.exception.code.UserErrorCode;
import my.fisherman.fisherman.security.util.SecurityUtil;
import my.fisherman.fisherman.smelt.application.dto.QuizInfo;
import my.fisherman.fisherman.smelt.application.dto.SmeltInfo;
import my.fisherman.fisherman.smelt.domain.Comment;
import my.fisherman.fisherman.smelt.domain.Question;
import my.fisherman.fisherman.smelt.domain.Quiz;
import my.fisherman.fisherman.smelt.domain.Smelt;
import my.fisherman.fisherman.smelt.domain.SmeltType;
import my.fisherman.fisherman.smelt.repository.CommentRepository;
import my.fisherman.fisherman.smelt.repository.QuestionRepository;
import my.fisherman.fisherman.smelt.repository.SmeltRepository;
import my.fisherman.fisherman.smelt.repository.SmeltTypeRepository;
import my.fisherman.fisherman.user.domain.User;
import my.fisherman.fisherman.user.repository.UserRepository;

@RequiredArgsConstructor
@Service
public class SmeltService {
    private final SmeltRepository smeltRepository;
    private final SmeltTypeRepository smeltTypeRepository;
    private final QuestionRepository questionRepository;
    private final UserRepository userRepository;
    private final CommentRepository commentRepository;

    @Transactional
    public SmeltInfo.Detail getSmeltDetail(Long smeltId) {
        Long userId = SecurityUtil.getCurrentUserId()
            .orElseThrow(() -> new FishermanException(UserErrorCode.FORBIDDEN));

        User user = userRepository.findById(userId)
            .orElseThrow(() -> new FishermanException(SmeltErrorCode.NOT_FOUND, "현재 사용자를 찾을 수 없습니다."));
        Smelt smelt = smeltRepository.findById(smeltId)
            .orElseThrow(() -> new FishermanException(SmeltErrorCode.NOT_FOUND, "빙어 %d을/를 찾을 수 없습니다.".formatted(smeltId)));

        smelt.readLetter(user);
        smeltRepository.save(smelt);

        return SmeltInfo.Detail.from(smelt);
    }

    @Transactional(readOnly = true)
    public QuizInfo.Detail getQuiz(Long smeltId) {
        Long userId = SecurityUtil.getCurrentUserId()
            .orElseThrow(() -> new FishermanException(UserErrorCode.FORBIDDEN));

        User user = userRepository.findById(userId)
            .orElseThrow(() -> new FishermanException(SmeltErrorCode.NOT_FOUND, "현재 사용자를 찾을 수 없습니다."));
        Smelt smelt = smeltRepository.findById(smeltId)
            .orElseThrow(() -> new FishermanException(SmeltErrorCode.NOT_FOUND, "빙어 %d을/를 찾을 수 없습니다.".formatted(smeltId)));
        Quiz quiz = smelt.getQuiz();
        if (quiz == null) {
            throw new FishermanException(SmeltErrorCode.NOT_FOUND, "빙어 %d의 퀴즈를 찾을 수 없습니다".formatted(smeltId));
        }

        smelt.checkReadableQuiz(user);

        List<Question> questions = questionRepository.findAllByQuiz(quiz);

        return QuizInfo.Detail.of(quiz, questions);
    }

    @Transactional
    public QuizInfo.Simple solve(Long smeltId, Long questionId) {
        Long userId = SecurityUtil.getCurrentUserId()
            .orElseThrow(() -> new FishermanException(UserErrorCode.FORBIDDEN));

        User user = userRepository.findById(userId)
            .orElseThrow(() -> new FishermanException(SmeltErrorCode.NOT_FOUND, "현재 사용자를 찾을 수 없습니다."));
        Smelt smelt = smeltRepository.findById(smeltId)
            .orElseThrow(() -> new FishermanException(SmeltErrorCode.NOT_FOUND, "빙어 %d을/를 찾을 수 없습니다.".formatted(smeltId)));
        Question question = questionRepository.findById(questionId)
            .orElseThrow(() -> new FishermanException(SmeltErrorCode.NOT_FOUND, "선지 %d을/를 찾을 수 없습니다.".formatted(questionId)));
        if (smelt.getQuiz() == null) {
            throw new FishermanException(SmeltErrorCode.NOT_FOUND, "빙어 %d의 퀴즈를 찾을 수 없습니다".formatted(smeltId));
        }

        smelt.trySolve(user, question);

        return QuizInfo.Simple.from(smelt.getQuiz());
    }

    @Transactional
    public SmeltInfo.Detail registerComment(Long smeltId, String content) {
        Long userId = SecurityUtil.getCurrentUserId()
            .orElseThrow(() -> new FishermanException(UserErrorCode.FORBIDDEN));

        User user = userRepository.findById(userId)
            .orElseThrow(() -> new FishermanException(SmeltErrorCode.NOT_FOUND, "현재 사용자를 찾을 수 없습니다."));
        Smelt smelt = smeltRepository.findById(smeltId)
            .orElseThrow(() -> new FishermanException(SmeltErrorCode.NOT_FOUND, "빙어 %d을/를 찾을 수 없습니다.".formatted(smeltId)));

        Comment comment = Comment.of(content);

        smelt.registerComment(user, comment);

        commentRepository.save(comment);
        smeltRepository.save(smelt);

        return SmeltInfo.Detail.from(smelt);
    }

    @Transactional(readOnly = true)
    public List<SmeltInfo.Type> getSmeltTypes() {
        List<SmeltType> smeltTypes = smeltTypeRepository.findAll();

        return smeltTypes.stream().map(SmeltInfo.Type::from).toList();
    }
}
