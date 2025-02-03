package my.fisherman.fisherman.user.application;

import lombok.RequiredArgsConstructor;
import my.fisherman.fisherman.auth.repository.AuthenticationRepository;
import my.fisherman.fisherman.user.application.command.UserCommand;
import my.fisherman.fisherman.user.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final AuthenticationRepository authenticationRepository;

    @Transactional
    public void signUp(UserCommand.SignUp command) {
        var email = command.email();

        //TODO: 예외 세분화 필요
        var authResult = authenticationRepository.find(email)
                .orElseThrow();
        if (authResult.notVerified()) {
            throw new IllegalArgumentException("이메일 인증이 필요합니다.");
        }

        userRepository.findUserByEmailForSignUp(email)
                .ifPresent(user -> {
                    throw new IllegalArgumentException("이미 가입된 이메일입니다.");
                });

        //TODO: 비밀번호 암호화 추가 로직
        var user = command.toEntity();
        userRepository.save(user);
    }
}
