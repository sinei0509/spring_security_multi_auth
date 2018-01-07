package jp.sinei0509.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import jp.sinei0509.service.AdminUserDetailService;

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

  @Autowired
  private AdminUserDetailService adminUserdetail;

  @Override
  protected void configure(AuthenticationManagerBuilder auth) throws Exception {
    auth.userDetailsService(adminUserdetail);
  }

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http.authorizeRequests()
        .antMatchers("/**").permitAll()
        .antMatchers("/admin/**").hasAnyRole("ADMIN")
        .and()
        .formLogin()
        .loginPage("/admin/login")
        .usernameParameter("user")
        .passwordParameter("password")
        .loginProcessingUrl("/admin/loginapi")
        .defaultSuccessUrl("/admin/hello")
        .failureUrl("/admin/login?error")
        .permitAll()
        .and()
        .logout()
        .logoutRequestMatcher(new AntPathRequestMatcher("/admin/logout"))
        .permitAll();
    ;
  }
}
