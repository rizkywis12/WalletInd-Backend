package net.mujoriwi.walletind.service.serviceimpl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.transaction.Transactional;

import net.mujoriwi.walletind.config.jwt.JwtUtil;
import net.mujoriwi.walletind.model.dto.request.ChangePasswordDto;
import net.mujoriwi.walletind.model.entity.*;
import net.mujoriwi.walletind.repository.DetailUserRepository;
import net.mujoriwi.walletind.repository.RoleRepository;
import net.mujoriwi.walletind.repository.UserRoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import net.mujoriwi.walletind.model.dto.request.ForgotPasswordDto;
import net.mujoriwi.walletind.model.dto.request.LoginDto;
import net.mujoriwi.walletind.model.dto.request.RegisterDto;
import net.mujoriwi.walletind.model.dto.response.ResponseData;
import net.mujoriwi.walletind.repository.UserRepository;
import net.mujoriwi.walletind.service.service.UserService;
import net.mujoriwi.walletind.validator.UserValidator;

@Service
@Transactional
public class UserServiceImpl implements UserService {


    @Autowired
    private DetailUserRepository detailUserRepository;
    @Autowired
    private UserRoleRepository userRoleRepository;
    @Autowired
    private RoleRepository  roleRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtil jwtUtil;

    private ResponseData<Object> responseData;
    private User user;
    private List<User> users;
    private DetailUser detailUser;

    @Autowired
    private UserValidator userValidator;

    private Map<Object, Object> data;



    @Override
    public ResponseData<Object> register(RegisterDto request) throws Exception {
        Optional<User> userOpt = userRepository.findByEmail(request.getEmail());
        Optional<User> emailOpt = userRepository.findByEmail(request.getEmail());
        userValidator.validateEmailExist(emailOpt);
        userValidator.validateUserFound(userOpt);

        Optional<User> userNameOpt = userRepository.findByUserName(request.getUserName());
        userValidator.validateUserNameExist(userNameOpt);

        user = new User(request.getEmail(), request.getPassword());
        user.setUserName(request.getUserName());
        userRepository.save(user);
        UserRole userRole = new UserRole();
        Role role = new Role();
        if (request.getRole() == null) {
            role = roleRepository.findByRoleName(ERole.USER);
        } else if (ERole.SUPER_ADMIN.name().equalsIgnoreCase(request.getRole())) {
            role = roleRepository.findByRoleName(ERole.SUPER_ADMIN);
        } else if (ERole.SUPER_USER.name().equalsIgnoreCase(request.getRole())) {
            role = roleRepository.findByRoleName(ERole.SUPER_USER);
        }
        userRole.setRole(role);
        userRole.setUser(user);
        userRoleRepository.save(userRole);

        data = new HashMap<>();
        data.put("email", user.getEmail());
        data.put("password", user.getPassword());
        data.put("username", user.getUserName());
        data.put("role", role);
        responseData = new ResponseData<Object>(HttpStatus.CREATED.value(), "Success register!", data);
        return responseData;
    }

    @Override
    public ResponseData<Object> login(LoginDto request) throws Exception {
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                request.getEmail(), request.getPassword());
        Authentication authentication = authenticationManager.authenticate(authenticationToken);

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwtToken = jwtUtil.generateJwtToken(authentication);
        UserDetails userDetails = (UserDetailsImpl) authentication.getPrincipal();

        data = new HashMap<>();
        data.put("token", jwtToken);
        data.put("email", userDetails.getUsername());
        data.put("password", userDetails.getPassword());

        responseData = new ResponseData<Object>(HttpStatus.OK.value(), "Success Login", data);

        return responseData;
    }

    @Override
    public ResponseData<Object> forgotPassword(ForgotPasswordDto request) throws Exception {
        Optional<User> userOpt = userRepository.findByEmail(request.getEmail());

        userValidator.validateUserNotFound(userOpt);

        user = userOpt.get();

        userValidator.validateForgotPassword(user.getPassword(), request.getPassword());

        userValidator.validateConfirmPassword(request.getPassword(), request.getConfirmPassword());

        user.setPassword(request.getPassword());

        responseData = new ResponseData<Object>(HttpStatus.OK.value(), "Successfully update your password!", data);

        return responseData;
    }

    @Override
    public ResponseData<Object> changePassword(long id, ChangePasswordDto request) throws Exception {
        Optional<User> userOpt = userRepository.findById(id);
        userValidator.validateUserNotFound(userOpt);
        user = userOpt.get();
        userValidator.validateCurrentPassword(user.getPassword(), request.getCurrentPassword());
        userValidator.validateConfirmPassword(request.getNewPassword(), request.getNewConfirmPassword());

        user.setPassword(request.getNewPassword());


        responseData = new ResponseData<Object>(HttpStatus.OK.value(), "Successfully Change your password!", data);

        return responseData;
    }

    @Override
    public ResponseData<Object> getAll(long id) {
        users = userRepository.findId(id);
        responseData = new ResponseData<Object>(HttpStatus.OK.value(), "success", users);
        return responseData;
    }

    @Override
    public ResponseData<Object> getBalance(long id) throws Exception {
        Optional<User> userOpt = userRepository.findById(id);

        userValidator.validateUserNotFound(userOpt);
        user = userOpt.get();

        responseData = new ResponseData<Object>(HttpStatus.OK.value(), "Success!", user.getBalance());

        return responseData;
    }
}
