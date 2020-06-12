package com.leverX.blog.controller;

import com.leverX.blog.model.User;
import com.leverX.blog.model.dto.AuthRequest;
import com.leverX.blog.model.dto.AuthResponse;
import com.leverX.blog.model.dto.RegistrationRequest;
import com.leverX.blog.security.JwtProvider;
import com.leverX.blog.service.UserService;
import com.leverX.blog.util.GenericResponse;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.MessageSource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Locale;

/**
 * @author Shpakova A.
 */
@RestController
public class AuthController {

    private final UserService userService;
    private final JwtProvider jwtProvider;
    private final MessageSource messages;
    private final JavaMailSender mailSender;

    public AuthController(UserService userService, JwtProvider jwtProvider, @Qualifier("messageSource") MessageSource messages, JavaMailSender mailSender) {
        this.userService = userService;
        this.jwtProvider = jwtProvider;
        this.messages = messages;
        this.mailSender = mailSender;
    }

    @PostMapping("/register")
    public GenericResponse registerUser(@RequestBody @Valid RegistrationRequest registrationRequest, HttpServletRequest request) {
        User u = new User();
        u.setPassword(registrationRequest.getPassword());
        u.setLogin(registrationRequest.getLogin());
        userService.save(u);
        mailSender.send(constructResetTokenEmail(request, request.getLocale(), u));
        return new GenericResponse(
                messages.getMessage("message.register", null,
                        request.getLocale()));
    }

    @PostMapping("/auth")
    public AuthResponse auth(@RequestBody AuthRequest request) {
        User user = userService.findByLoginAndPassword(request.getLogin(), request.getPassword());
        String token = jwtProvider.generateToken(user.getLogin());
        return new AuthResponse(token);
    }


    ////

    /**
     * Method: used to send an email with the link about registration.
     *
     * @return message with link about registration
     */
    private SimpleMailMessage constructResetTokenEmail(HttpServletRequest contextPath, Locale locale, User user) {
        String url = contextPath + "/user/registration?id=" +
                user.getId();
        String message = messages.getMessage("message.registration",
                null, locale);
        return constructEmail("Registration: ", message + " " + url, user);
    }

    private SimpleMailMessage constructEmail(String subject, String body, User user) {
        SimpleMailMessage email = new SimpleMailMessage();
        email.setSubject(subject);
        email.setText(body);
        email.setTo(user.getEmail());
        return email;
    }

}
