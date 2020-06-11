package com.leverX.blog.controller;

import com.leverX.blog.exception.UserNotFoundException;
import com.leverX.blog.util.GenericResponse;
import com.leverX.blog.model.dto.PasswordDTO;
import com.leverX.blog.model.User;
import com.leverX.blog.service.UserService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.MessageSource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Locale;
import java.util.UUID;

/**
 * @author Shpakova A.
 */

@RestController
public class UserController {

    private final UserService userService;
    private final MessageSource messages;
    private final JavaMailSender mailSender;

    public UserController(UserService userService, @Qualifier("messageSource") MessageSource messages, JavaMailSender mailSender) {
        this.userService = userService;
        this.messages = messages;
        this.mailSender = mailSender;
    }

    @PostMapping(value = "/auth/resetPassword")
    @ResponseBody
    public GenericResponse resetPassword(HttpServletRequest request,
                                         @RequestParam("email") String userEmail) throws UserNotFoundException {
        User user = userService.findByEmail(userEmail);
        if (user == null) {
            throw new UserNotFoundException();
        }
        String token = UUID.randomUUID().toString();
        userService.createPasswordResetTokenForUser(token, user);
        mailSender.send(constructResetTokenEmail(request, request.getLocale(), token, user));
        return new GenericResponse(
                messages.getMessage("message.resetPasswordEmail", null,
                        request.getLocale()));
    }

    /**
     * Method: used to send an email with the reset token.
     *
     * @return message with link of token
     */
    private SimpleMailMessage constructResetTokenEmail(HttpServletRequest contextPath, Locale locale, String token, User user) {
        String url = contextPath + "/user/changePassword?id=" +
                user.getId() + "&token=" + token;
        String message = messages.getMessage("message.resetPassword",
                null, locale);
        return constructEmail("Reset Password", message + " " + url, user);
    }

    private SimpleMailMessage constructEmail(String subject, String body, User user) {
        SimpleMailMessage email = new SimpleMailMessage();
        email.setSubject(subject);
        email.setText(body);
        email.setTo(user.getEmail());
        return email;
    }

    @GetMapping(value = "/auth/changePassword")
    public String showChangePasswordPage(Locale locale, Model model,
                                         @RequestParam("id") long id, @RequestParam("token") String token) {
        String result = userService.validatePasswordResetToken(id, token);
        if (result != null) {
            model.addAttribute("message",
                    messages.getMessage("auth.message." + result, null, locale));
            return "redirect:/login?lang=" + locale.getLanguage();
        }
        return "redirect:/updatePassword.html?lang=" + locale.getLanguage();
    }

    @PostMapping(value = "/auth/savePassword")
    @ResponseBody
    public GenericResponse savePassword(Locale locale, @Valid PasswordDTO password) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        userService.changeUserPassword(user, password.getNewPassword());
        return new GenericResponse(
                messages.getMessage("message.resetPasswordSuc", null, locale));
    }

}
