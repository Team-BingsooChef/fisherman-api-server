package my.fisherman.fisherman.util;

import my.fisherman.fisherman.fishingspot.domain.FishingSpot;
import my.fisherman.fisherman.inventory.domain.Coin;
import my.fisherman.fisherman.inventory.domain.Inventory;
import my.fisherman.fisherman.smelt.domain.Question;
import my.fisherman.fisherman.user.domain.User;
import my.fisherman.fisherman.smelt.domain.Smelt;
import my.fisherman.fisherman.smelt.domain.SmeltStatus;
import my.fisherman.fisherman.smelt.domain.SmeltType;
import my.fisherman.fisherman.smelt.domain.Letter;
import my.fisherman.fisherman.smelt.domain.Quiz;
import my.fisherman.fisherman.smelt.domain.QuizType;
import my.fisherman.fisherman.smelt.domain.Comment;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import org.springframework.test.util.ReflectionTestUtils;

public class TestFixtureUtil {

    // User 생성
    public static User createUserWith(String email, String password, String nickname) {
        return User.of(email, password, nickname);
    }

    public static User createUserWith(Long id, String email, String password, String nickname) {
        User user = User.of(email, password, nickname);

        ReflectionTestUtils.setField(user, "id", id);

        return user;
    }

    // Inventory 생성
    public static Inventory createInventoryWith(Long id, User user) {
        Inventory inventory = Inventory.of(user);

        ReflectionTestUtils.setField(inventory, "id", id);

        return inventory;
    }

    public static Inventory createInventoryWith(Long id, User user, Long coinAmount) {
        Coin coin = new Coin();

        ReflectionTestUtils.setField(coin, "coin", coinAmount);

        Inventory inventory = Inventory.of(user);

        ReflectionTestUtils.setField(inventory, "id", id);
        ReflectionTestUtils.setField(inventory, "coin", coin);

        return inventory;
    }

    // FishingSpot 생성
    public static FishingSpot createFishingSpotWith(User fisherman) {
        return FishingSpot.of(fisherman);
    }

    // SmeltType 생성
    public static SmeltType createSmeltTypeWith(Long id) throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        Constructor<SmeltType> constructor = SmeltType.class.getDeclaredConstructor();
        constructor.setAccessible(true);
        SmeltType smeltType = constructor.newInstance();

        ReflectionTestUtils.setField(smeltType, "id", id);

        return smeltType;
    }

    // Smelt 생성
    public static Smelt createSmeltWith(Inventory inventory, SmeltType smeltType) {
        Smelt smelt = Smelt.of(inventory, smeltType);

        ReflectionTestUtils.setField(smelt, "status", SmeltStatus.DREW);

        return smelt;
    }

    public static Smelt createSmeltWith(Inventory inventory, SmeltType smeltType, FishingSpot fishingSpot, Letter letter, SmeltStatus status) {
        Smelt smelt = Smelt.of(inventory, smeltType);

        ReflectionTestUtils.setField(smelt, "fishingSpot", fishingSpot);
        ReflectionTestUtils.setField(smelt, "letter", letter);
        ReflectionTestUtils.setField(smelt, "status", status);

        return smelt;
    }

    public static Smelt createSmeltWith(Inventory inventory, SmeltType smeltType, FishingSpot fishingSpot, Letter letter, Quiz quiz, SmeltStatus status) {
        Smelt smelt = Smelt.of(inventory, smeltType);

        ReflectionTestUtils.setField(smelt, "fishingSpot", fishingSpot);
        ReflectionTestUtils.setField(smelt, "letter", letter);
        ReflectionTestUtils.setField(smelt, "quiz", quiz);
        ReflectionTestUtils.setField(smelt, "status", status);

        return smelt;
    }

    // Letter 생성
    public static Letter createLetterWith(Long id, String sender)  {
        Letter letter = Letter.of("letter content", "test sender");

        ReflectionTestUtils.setField(letter, "id", id);

        return letter;
    }

    public static Letter createLetterWith(Long id, String sender, Comment comment)  {
        Letter letter = Letter.of("letter content", "test sender");

        ReflectionTestUtils.setField(letter, "id", id);
        ReflectionTestUtils.setField(letter, "comment", comment);

        return letter;
    }

    // Quiz 생성
    public static Quiz createQuizWith(String title, QuizType quizType) {
        return Quiz.of(title, quizType);
    }

    public static Quiz createQuizWith(boolean isSolved)  {
        Quiz quiz = Quiz.of("quiz title", QuizType.OX);

        ReflectionTestUtils.setField(quiz, "isSolved", isSolved);

        return quiz;
    }

    public static Quiz createQuizWith(Long id, boolean isSolved)  {
        Quiz quiz = Quiz.of("quiz title", QuizType.OX);

        ReflectionTestUtils.setField(quiz, "id", id);
        ReflectionTestUtils.setField(quiz, "isSolved", isSolved);

        return quiz;
    }

    // Question 생성
    public static Question createQuestionWith(String content, boolean isAnswer, Quiz quiz) {
        return Question.of(content, isAnswer, quiz);
    }

    // Comment 생성
    public static Comment createCommentWith(String content) {
        return Comment.of(content);
    }
}
