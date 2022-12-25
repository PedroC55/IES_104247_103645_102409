package con_backend.api;

import org.springframework.stereotype.Service;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import con_backend.api.exception.ResourceNotFoundException;

import con_backend.api.repository.UserRepository;
import con_backend.api.model.User;

@Service
public class MyUserDetailsService implements UserDetailsService {

	@Autowired
	private UserRepository userRepository;

	@Override
	public UserDetails loadUserByUsername(String username) {
		User user = userRepository.findByUsername(username);
		System.out.println(user.getPassword());
		if (user == null) {
			return null;
		}
		return new MyUserPrincipal(user);
	}
}
