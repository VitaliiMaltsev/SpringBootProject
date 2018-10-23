package appPackage.config;

import appPackage.model.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import nz.net.ultraq.thymeleaf.LayoutDialect;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final LoggingAccessDeniedHandler accessDeniedHandler;

    private final UserService userService;

    private final PasswordEncoder passwordEncoder;

    @Autowired
    public SecurityConfig(LoggingAccessDeniedHandler accessDeniedHandler, UserService userService, PasswordEncoder passwordEncoder ){
        this.accessDeniedHandler = accessDeniedHandler;
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    @Bean
    public LayoutDialect layoutDialect() {
        return new LayoutDialect();
    }

    @Bean
    public PasswordEncoder getPasswordEncoder(){
        return new BCryptPasswordEncoder(8);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers(
                        "/",
                        "/js/**",
                        "/css/**",
                        "/img/**",
                        "/uploads/**",
                        "/imgUploads/**",
                        "/registration",
                        "/activate/*",
                        "/global",
                        "/webjars/**").permitAll()

                .antMatchers("/users/profile", "/topics/**")
                .hasAuthority("USER")
                .anyRequest().authenticated()
                .and()

                .formLogin()
                .loginPage("/login")
                .permitAll()
                .and().rememberMe()
                .and()
                .logout()
                .invalidateHttpSession(true)
                .clearAuthentication(true)
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                .logoutSuccessUrl("/login?logout")
                .permitAll()
                .and()
                .exceptionHandling()
                .accessDeniedHandler(accessDeniedHandler);
    }
//    @Override
//    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//        auth
//                .userDetailsService(userService);
////                .jdbcAuthentication().dataSource(dataSource)
////                .passwordEncoder(passwordEncoder);
////                .usersByUsernameQuery(
////                        "select name, password, active from users where name=?")
////                .authoritiesByUsernameQuery(
////                        "select u.name, ur.roles from users u inner join user_roles ur on u.id = ur.user_id where u.name =?");
////                .withUser("user").password("{noop}password").roles("USER")
////                .and()
////                .withUser("manager").password("{noop}password").roles("MANAGER");
//    }
//    @Bean
//    @Override
//    public UserDetailsService userDetailsService() {
//        UserDetails user =
//                User.withDefaultPasswordEncoder()
//                        .username("u")
//                        .password("123456")
//                        .roles("USER")
//                        .build();
//
//        return new InMemoryUserDetailsManager(user);
    }
