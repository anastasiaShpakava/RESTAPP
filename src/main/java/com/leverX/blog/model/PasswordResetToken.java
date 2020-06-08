package com.leverX.blog.model;



import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class PasswordResetToken { // При срабатывании сброса пароля будет создан токен,
    // и пользователю будет отправлена ​​специальная ссылка, содержащая этот токен.
    private static final int EXPIRATION = 60 * 24;  //токен и ссылка будут действит. в теч. 24 часа

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String token;

    @OneToOne(targetEntity = User.class, fetch = FetchType.EAGER)
    @JoinColumn(nullable = false, name = "user_id")
    private User user;

    private Date expiryDate; //дата истечения действия ссылки

    public PasswordResetToken(String token, User user) { //?
    }
}
