package com.leverX.blog.controller;

import com.leverX.blog.exception.UserNotFoundException;
import com.leverX.blog.util.GenericResponse;
import com.leverX.blog.model.PasswordResetRequest;
import com.leverX.blog.model.User;
import com.leverX.blog.service.UserService;
import lombok.RequiredArgsConstructor;
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



@RestController
@RequiredArgsConstructor

public class UserController {

    private final UserService userService;
    private final MessageSource messages;
    private final JavaMailSender mailSender;


    @RequestMapping(value = "/user/resetPassword",  //создание нового PasswordResetToken и отправим его по электронной почте пользователю
            method = RequestMethod.POST)
    @ResponseBody
    public GenericResponse resetPassword(HttpServletRequest request,
                                         @RequestParam("email") String userEmail) throws UserNotFoundException {
        User user = userService.findByEmail(userEmail);
        if (user == null) {
            throw new UserNotFoundException();
        }
        String token = UUID.randomUUID().toString();
        userService.createPasswordResetTokenForUser(user, token);
        mailSender.send(constructResetTokenEmail(request, request.getLocale(), token, user));
        return new GenericResponse(
                messages.getMessage("message.resetPasswordEmail", null,
                        request.getLocale()));
    }
    ////// используется для отправки электронного письма с токеном сброса
    private SimpleMailMessage constructResetTokenEmail(
            HttpServletRequest contextPath, Locale locale, String token, User user) {
        String url = contextPath + "/user/changePassword?id=" +
                user.getId() + "&token=" + token;
        String message = messages.getMessage("message.resetPassword",
                null, locale);
        return constructEmail("Reset Password", message + " \r\n" + url, user);
    }

    private SimpleMailMessage constructEmail(String subject, String body,
                                             User user) {
        SimpleMailMessage email = new SimpleMailMessage();
        email.setSubject(subject);
        email.setText(body);
        email.setTo(user.getEmail());
        return email;
    }

    /////
    @RequestMapping(value = "/user/changePassword", method = RequestMethod.GET)
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


    ///// когда отправляется предыдущий почтовый запрос - новый пароль пользователя сохраняется:


    @RequestMapping(value = "/user/savePassword", method = RequestMethod.POST)
    @ResponseBody
    public GenericResponse savePassword(Locale locale,
                                        @Valid PasswordResetRequest password) {
        User user =
                (User) SecurityContextHolder.getContext()
                        .getAuthentication().getPrincipal();

        userService.changeUserPassword(user, password.getNewPassword());
        return new GenericResponse(
                messages.getMessage("message.resetPasswordSuc", null, locale));
    }


}
