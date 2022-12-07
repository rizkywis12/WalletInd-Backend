package net.mujoriwi.walletind.service.serviceimpl;

import net.mujoriwi.walletind.model.entity.User;
import net.mujoriwi.walletind.model.entity.UserRole;
import net.mujoriwi.walletind.repository.UserRepository;
import net.mujoriwi.walletind.repository.UserRoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
public class UserDetailsServiceImpl implements UserDetailsService {
  @Autowired
  private UserRepository userRepository;

  @Autowired
  private UserRoleRepository userRoleRepository;

  // pada saat login input username bisa disesuaikan
  @Override
  public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
    // TODO Auto-generated method stub
    Optional<User> userOpt = userRepository.findByEmail(email);
    if (userOpt.isEmpty()) {
      throw new UsernameNotFoundException("User not found");
    }

    User user = userOpt.get();
    UserRole userRole = userRoleRepository.findByUser(user);
    String role = String.valueOf(userRole.getRole().getRoleName());

    return UserDetailsImpl.build(user, role);
  }

}
