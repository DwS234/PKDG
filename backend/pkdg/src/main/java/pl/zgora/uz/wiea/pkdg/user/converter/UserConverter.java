package pl.zgora.uz.wiea.pkdg.user.converter;

import lombok.val;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;
import pl.zgora.uz.wiea.pkdg.auth.model.RegisterRequest;
import pl.zgora.uz.wiea.pkdg.common.Converter;
import pl.zgora.uz.wiea.pkdg.user.entity.UserEntity;
import pl.zgora.uz.wiea.pkdg.user.model.AppUserDetails;
import pl.zgora.uz.wiea.pkdg.user.model.User;

import static java.util.Collections.singleton;

@Component
public class UserConverter implements Converter<UserEntity, User> {

	@Override
	public User convertEntityToModel(final UserEntity userEntity) {
		final User user = new User();
		user.setUsername(userEntity.getUsername());
		user.setEmail(userEntity.getEmail());
		user.setPassword(userEntity.getPassword());
		user.setRole(userEntity.getRole());
		return user;
	}

	@Override
	public UserEntity convertModelToEntity(final User userModel) {
		final UserEntity userEntity = new UserEntity();
		userEntity.setUsername(userModel.getUsername());
		userEntity.setEmail(userModel.getEmail());
		userEntity.setPassword(userModel.getPassword());
		userEntity.setRole(userModel.getRole());
		return userEntity;
	}

	public UserEntity convertRegisterRequestToEntity(final RegisterRequest registerRequest) {
		final UserEntity userEntity = new UserEntity();
		userEntity.setUsername(registerRequest.getUsername());
		userEntity.setPassword(registerRequest.getPassword());
		userEntity.setEmail(registerRequest.getEmail());

		return userEntity;
	}

	public AppUserDetails convertEntityToUserDetails(final UserEntity userEntity) {
		final AppUserDetails appUserDetails = new AppUserDetails();
		appUserDetails.setEmail(userEntity.getEmail());
		appUserDetails.setPassword(userEntity.getPassword());
		appUserDetails.setUsername(userEntity.getUsername());
		val authorities = singleton(new SimpleGrantedAuthority(userEntity.getRole().toString()));
		appUserDetails.setAuthorities(authorities);
		return appUserDetails;
	}
}
