package side.boardservice.web.auth;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import side.boardservice.domain.user.User;
import side.boardservice.domain.user.UserRepository;

// POST "/login" 요청이 오면 UserDetailsService 타입으로 IoC되어 있는 loadUserByUsername 함수 실행
@Slf4j
@Service
public class PrincipalDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        User userEntity = userRepository.findByUserEmailId(username);

        if(userEntity != null) {
            return new PrincipalDetails(userEntity);
        }

        return null;
    }
}
