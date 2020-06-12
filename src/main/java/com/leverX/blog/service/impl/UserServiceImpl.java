package com.leverX.blog.service.impl;

import com.leverX.blog.model.Role;
import com.leverX.blog.model.User;
import com.leverX.blog.model.dto.PasswordResetToken;
import com.leverX.blog.model.dto.RegistrationRequest;
import com.leverX.blog.repository.PasswordResetTokenRepository;
import com.leverX.blog.repository.RoleRepository;
import com.leverX.blog.repository.UserRepository;
import com.leverX.blog.service.UserService;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Calendar;

/**
 * This class implements {@link UserService}
 *
 * @author Shpakova A.
 */

@Service("userService")
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final PasswordResetTokenRepository passwordTokenRepository;


    public UserServiceImpl(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder, PasswordResetTokenRepository passwordTokenRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.passwordTokenRepository = passwordTokenRepository;
    }

    @Override
    @Cacheable ("user")
    public User findByEmail(String email) {
        return userRepository.findByEmailIgnoreCase(email);
    }

    @Override
    @Cacheable("user")  //кэшируем возвращамые данные
    public User findByLogin(String login) {
        return userRepository.findByLoginIgnoreCase(login);
    }

    @Transactional
    @Override
    public User save(User user) {  //для авторизированных пользователей
        Role role = roleRepository.findByLogin("ROLE_USER");
        user.setRole(role);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    public User findByLoginAndPassword(String login, String password) {
        User user = findByLogin(login);
        if (user != null) {
            if (passwordEncoder.matches(password, user.getPassword())) {
                return user;
            }
        }
        return null;
    }

    @Override
    @Cacheable(value = "users", key = "#name")  //если сущность с таким именем уже есть, мы не будем её сохранять
    public User createUser(RegistrationRequest registrationRequest) {
        User newUser = new User();
        newUser.setEmail(registrationRequest.getEmail());
        newUser.setPassword(passwordEncoder.encode(registrationRequest.getPassword()));
        newUser.setLogin(registrationRequest.getLogin());
        newUser.setLastName(registrationRequest.getLastName());
        newUser.setFirstName(registrationRequest.getFirstName());
        newUser.setCreatedAt(LocalDateTime.now());
        return newUser;
    }

    @Override
    public User currentUser() {
      return   (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

    @Override
    public void createPasswordResetTokenForUser(String token, User user) {
    PasswordResetToken myToken = new PasswordResetToken(token, user);
        passwordTokenRepository.save(myToken);
    }


    @Override

        //  если токен действителен, пользователь получит право сменить свой пароль,
        //  предоставив ему CHANGE PASSWORD PRIVILEGE и направив его на страницу для обновления
        //  своего пароля
        public String validatePasswordResetToken(Integer id, String token) {
            PasswordResetToken passToken =
                    passwordTokenRepository.findByToken(token);
            if ((passToken == null) || (passToken.getUser()
                    .getId() != id)) {
                return "invalidToken";
            }

            Calendar cal = Calendar.getInstance();  //проверяет, истекла ли ссылка
            if ((passToken.getExpiryDate()
                    .getTime() - cal.getTime()
                    .getTime()) <= 0) {
                return "expired";
            }

            User user = passToken.getUser();
            Authentication auth = new UsernamePasswordAuthenticationToken(
                    user, null, Arrays.asList(
                    new SimpleGrantedAuthority("CHANGE__PASSWORD__PRIVILEGE")));
            SecurityContextHolder.getContext().setAuthentication(auth);
            return null;
        }

    @Override
    public void changeUserPassword(User user, String password) {
        user.setPassword(passwordEncoder.encode(password));
      userRepository.save(user);
    }
}




