package jp.sinei0509.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import jp.sinei0509.service.AdminUserDetailService;

@EnableWebSecurity
//@Order(1)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

  @Autowired
  private AdminUserDetailService adminUserdetail;

  @Override
  protected void configure(AuthenticationManagerBuilder auth) throws Exception {
    auth.userDetailsService(adminUserdetail);
    System.out.println(auth.isConfigured());
  }

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    // 管理画面用のアクセス権とログイン/ログアウトの設定
    http.antMatcher("/**") // /admin以下の設定
        .authorizeRequests()
        .antMatchers("/admin/**")
        .hasRole("ADMIN") // /admin下はADMINロールが必要
        .anyRequest()
        .authenticated()
        .and()
        .httpBasic()
        .disable()
        .formLogin() // 管理画面のログイン設定
          .loginPage("/admin/login")  // ログイン画面のpath
          .usernameParameter("user")  // フォーム認証のparam情報(ユーザ名)
          .passwordParameter("password")  // フォーム認証のparam情報(パスワード)
          .loginProcessingUrl("/admin/loginapi") // フォームのリクエスト送り先のpath
          .defaultSuccessUrl("/admin/hello") // ログイン後に遷移するpath
          .failureUrl("/admin/login?error")  // ログイン失敗した時のURL
          .permitAll() // ログインのpathにはだれでもアクセスできる
        .and()
          .logout() // ログアウトの設定
          // ログアウトのpath
          .logoutRequestMatcher(new AntPathRequestMatcher("/admin/logout"))
          .permitAll(); // ログアウトのpathは誰でもアクセスできる
    ;
  }
}
