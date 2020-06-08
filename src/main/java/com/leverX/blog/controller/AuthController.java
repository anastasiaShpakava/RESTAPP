package com.leverX.blog.controller;

import com.leverX.blog.model.User;
import com.leverX.blog.model.AuthRequest;
import com.leverX.blog.model.AuthResponse;
import com.leverX.blog.model.RegistrationRequest;
import com.leverX.blog.security.JwtProvider;
import com.leverX.blog.service.UserService;
import com.leverX.blog.util.GenericResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;
    private final JwtProvider jwtProvider;
    private final MessageSource messages;

    @PostMapping("/register")
    public GenericResponse registerUser(@RequestBody @Valid RegistrationRequest registrationRequest, HttpServletRequest request) {
        User u = new User();
        u.setPassword(registrationRequest.getPassword());
        u.setLogin(registrationRequest.getLogin());
        userService.save(u);
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
}
