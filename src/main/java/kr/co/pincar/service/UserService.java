package kr.co.pincar.service;

import kr.co.pincar.exeception.BaseException;
import kr.co.pincar.exeception.BaseResponseCode;
import kr.co.pincar.jpa.dto.user.UserReq;
import kr.co.pincar.jpa.dto.user.JoinRes;
import kr.co.pincar.jpa.dto.user.LoginRes;
import kr.co.pincar.jpa.entity.User;
import kr.co.pincar.jpa.repository.UserRepository;
import kr.co.pincar.utils.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;


@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;

    public boolean existsAdmin(String email) {
        return userRepository.existsByEmailAndStatus(email, "enter");
    }

    // 회원가입
    @Transactional
    public JoinRes createAdmin(UserReq req) {

        User user;

        try {
            user = userRepository.save(User.builder()
                    .email(req.getEmail())
                    .phone(req.getPhone())
                    .password(passwordEncoder.encode(req.getPassword()))
                    .type(req.getType())
                    .jandi_id(req.getJandi_id())
                    .build());
        } catch (Exception e) {
            throw new BaseException(BaseResponseCode.FAILED_TO_SAVE_ADMIN);
        }

        return new JoinRes(user.getId(), user.getEmail());
    }

    // 로그인
    public LoginRes login(String email) {
        User user = readAdmin(email);
        String token = jwtTokenProvider.createToken(email);

        return new LoginRes(user.getId(), token);
    }

    public User readAdmin(String email) {
        User user = userRepository.findByEmailAndStatus(email, "enter")
                .orElseThrow(() -> new BaseException(BaseResponseCode.FAILED_TO_FIND_ADMIN));

        return user;
    }
}
