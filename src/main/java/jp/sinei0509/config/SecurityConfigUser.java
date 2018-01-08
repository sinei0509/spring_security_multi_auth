package jp.sinei0509.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import jp.sinei0509.service.UserDetailServiceImpl;

@EnableWebSecurity
@Order(2)
public class SecurityConfigUser extends WebSecurityConfigurerAdapter {

  @Autowired
  private UserDetailServiceImpl userdetail;

  @Override
  protected void configure(AuthenticationManagerBuilder auth) throws Exception {
    auth.userDetailsService(userdetail);
  }

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    // ユーザ画面(管理画面以外の設定)
    http.antMatcher("/**") // /直下の設定(@orderの優先順位に従うので/admin以外の設定)
        .authorizeRequests()
        .antMatchers("/**").hasAnyRole("USER") // USER権限だけアクセス可能
        .anyRequest()
        .authenticated()
        .and()
          .formLogin() // ユーザ画面のログイン設定
          .loginPage("/login")  // ログイン画面のpath
          .usernameParameter("user")  // フォーム認証のparam情報(ユーザ名)
          .passwordParameter("password")  // フォーム認証のparam情報(パスワード)
          .loginProcessingUrl("/loginapi") // フォームのリクエスト送り先のpath
          .defaultSuccessUrl("/hello") // ログイン後に遷移するpath
          .failureUrl("/login?error")  // ログイン失敗した時のURL
          .permitAll() // ログインのpathにはだれでもアクセスできる
        .and()
          .logout() // ログアウトの設定
          // ログアウトのpath
          .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
          .permitAll(); // ログアウトのpathは誰でもアクセスできる
    ;
  }
}
