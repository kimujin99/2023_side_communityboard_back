package side.boardservice.web.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import side.boardservice.domain.user.User;
import side.boardservice.domain.user.UserDto;
import side.boardservice.domain.user.UserRepository;

import java.security.Principal;

@Slf4j
@Service
public class UserService {

    @Autowired
    UserRepository userRepository;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public User getUserByUserCode(Long userCode) {
        return userRepository.findById(userCode).get();
    }

    public void saveUser(UserDto.Request dto) {
        dto.setUserPassword(bCryptPasswordEncoder.encode(dto.getUserPassword()));

        userRepository.save(dto.toEntity());
    }

    public void editUser(User user, UserDto.Request dto) {

        if(dto.getUserProfile() != null) {
            user.updateUser(dto.getUserNickname(), dto.getUserProfile());
        } else {
            user.updateUserNickname(dto.getUserNickname());
        }

        userRepository.save(user);
    }

    public Boolean emailDuplicateCheck(UserDto.Request dto) {
        User user = userRepository.findByUserEmailId(dto.getUserEmailId());

        if(user == null) {
            return true;
        }

        return false;
    }

    public UserDto.Response getUserInfo(Principal principal) {
        return new UserDto.Response(userRepository.findByUserEmailId(principal.getName()));
    }

    public UserDto.MyPageResponse getMyPageUserInfo(Principal principal) {
        return new UserDto.MyPageResponse(userRepository.findByUserEmailId(principal.getName()));
    }
}
