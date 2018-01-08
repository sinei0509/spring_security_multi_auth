package jp.sinei0509.service;

import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailServiceImpl implements UserDetailsService {

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

    if (username.equals("user")) {
      return new User(username, "userpass", AuthorityUtils.createAuthorityList("ROLE_USER"));
    }

    throw new UsernameNotFoundException("not found : " + username);
  }

}
