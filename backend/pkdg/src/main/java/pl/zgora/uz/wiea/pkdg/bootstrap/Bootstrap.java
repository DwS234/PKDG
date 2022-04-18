package pl.zgora.uz.wiea.pkdg.bootstrap;

import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import pl.zgora.uz.wiea.pkdg.user.entity.UserEntity;
import pl.zgora.uz.wiea.pkdg.user.repository.UserRepository;

import javax.transaction.Transactional;

import static pl.zgora.uz.wiea.pkdg.user.model.UserRole.ADMIN;

@Component
@RequiredArgsConstructor
public class Bootstrap {

    private static final String ADMIN_USERNAME = "admin";

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    @Transactional
    @EventListener(ApplicationReadyEvent.class)
    public void createInitialData() {
        if (userRepository.findByUsername(ADMIN_USERNAME) != null) {
            return;
        }

        val userEntity = new UserEntity();
        userEntity.setUsername(ADMIN_USERNAME);
        userEntity.setPassword(passwordEncoder.encode("admin"));
        userEntity.setEmail("admin@gmail.com");
        userEntity.setRole(ADMIN);
        userRepository.save(userEntity);
    }
}
