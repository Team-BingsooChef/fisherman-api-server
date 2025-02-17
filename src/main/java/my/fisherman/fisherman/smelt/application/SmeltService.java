package my.fisherman.fisherman.smelt.application;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import my.fisherman.fisherman.security.util.SecurityUtil;
import my.fisherman.fisherman.smelt.application.dto.SmeltInfo;
import my.fisherman.fisherman.smelt.domain.Smelt;
import my.fisherman.fisherman.smelt.domain.Comment;
import my.fisherman.fisherman.smelt.domain.SmeltType;
import my.fisherman.fisherman.user.domain.User;
import my.fisherman.fisherman.smelt.repository.SmeltRepository;
import my.fisherman.fisherman.smelt.repository.SmeltTypeRepository;
import my.fisherman.fisherman.user.repository.UserRepository;

@RequiredArgsConstructor
@Service
public class SmeltService {
    private final SmeltRepository smeltRepository;
    private final SmeltTypeRepository smeltTypeRepository;
    private final UserRepository userRepository;

    @Transactional(readOnly = true)
    public SmeltInfo.Detail getSmeltDetail(Long smeltId) {
        // TODO: ID를 가져올 수 없는 예외 처리
        Long userId = SecurityUtil.getCurrentUserId().orElseThrow();

        // Not found 예외 처리
        User user = userRepository.findById(userId).orElseThrow();
        Smelt smelt = smeltRepository.findById(smeltId).orElseThrow();

        smelt.readLetter(user);
        smeltRepository.save(smelt);

        return SmeltInfo.Detail.from(smelt);
    }

    @Transactional
    public SmeltInfo.Detail registerComment(Long smeltId, String content) {
        // TODO: ID를 가져올 수 없는 예외 처리
        Long userId = SecurityUtil.getCurrentUserId().orElseThrow();

        // Not found 예외 처리
        User user = userRepository.findById(userId).orElseThrow();
        Smelt smelt = smeltRepository.findById(smeltId).orElseThrow();

        Comment comment = Comment.of(content);

        smelt.registerComment(user, comment);
        smeltRepository.save(smelt);

        return SmeltInfo.Detail.from(smelt);
    }

    @Transactional(readOnly = true)
    public List<SmeltInfo.Type> getSmeltTypes() {

        List<SmeltType> smeltTypes = smeltTypeRepository.findAll();

        return smeltTypes.stream().map(SmeltInfo.Type::from).toList();
    }
}
