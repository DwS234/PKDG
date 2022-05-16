package pl.zgora.uz.wiea.pkdg.config;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import pl.zgora.uz.wiea.pkdg.exception.UserNotFoundException;
import pl.zgora.uz.wiea.pkdg.user.converter.UserConverter;
import pl.zgora.uz.wiea.pkdg.user.entity.UserEntity;
import pl.zgora.uz.wiea.pkdg.user.repository.UserRepository;

@Component
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

	private final UserRepository userRepository;

	private final UserConverter userConverter;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		final UserEntity userEntity = userRepository.findById(username).orElseThrow(() -> new UserNotFoundException(username));
		return userConverter.convertEntityToUserDetails(userEntity);
	}
}
