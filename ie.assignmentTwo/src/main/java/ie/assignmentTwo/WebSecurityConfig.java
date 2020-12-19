package ie.assignmentTwo;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	 @Override
	 protected void configure(HttpSecurity http) throws Exception {
	  http.authorizeRequests()
	      .antMatchers("/css/**", "/", "/directors", "/films", "/directorsfilms/{directorName}", "/edit", "/h2/**").permitAll()
	      .antMatchers("/adddirector", "/addfilm", "/editfilm").hasAnyRole("ADMIN","USER")
	      .antMatchers("/deletefilm", "/deletedirectorandfilms").hasRole("ADMIN")
	      .anyRequest().authenticated()
	      .and()
	      .formLogin()    // authenticate by login form
	      .and()
	      .httpBasic()   // and by basic http request
	      .and()
	      .logout().logoutRequestMatcher(new AntPathRequestMatcher("/logout")).logoutSuccessUrl("/edit");
	  
      http.csrf().disable();
      http.headers().frameOptions().disable();
	 }
	 
	 @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    // Create our own instance of Spring's user details but creating some UserDetails objects and 
    // creating an in-memory user details manager from the. 
    @Bean
    @Override
    protected UserDetailsService userDetailsService() 
    {
        String encodedPassword = passwordEncoder().encode("password");  
        UserDetails user1 = User.withUsername("user").password(encodedPassword).roles("USER").build();
        UserDetails user2 = User.withUsername("admin").password(encodedPassword).roles("ADMIN").build();
        
        return new InMemoryUserDetailsManager(user1, user2);
    }
}