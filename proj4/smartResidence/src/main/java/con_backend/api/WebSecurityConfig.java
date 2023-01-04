package con_backend.api;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http
			.authorizeHttpRequests((requests) -> requests
					.antMatchers("/").authenticated()
					.anyRequest().permitAll()
					)
			.formLogin((form) -> form
					.loginPage("/login")
					.permitAll()
				  )
			.logout((logout) -> logout.permitAll());

		return http.build();
	}

        /*
	@Bean
	public UserDetailsService userDetailsService() {
		UserDetails user =
			User.withDefaultPasswordEncoder()
			.username("user")
			.password("password")
			.roles("USER")
			.build();

		return new InMemoryUserDetailsManager(user);
	}
        */
	public PasswordEncoder encoder() {
		return new BCryptPasswordEncoder(11);
	}
}
