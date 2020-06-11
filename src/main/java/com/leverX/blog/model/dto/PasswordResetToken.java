package com.leverX.blog.model.dto;


import com.leverX.blog.model.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


import javax.persistence.*;
import java.util.Date;

/**
 * This class is use  for resetting the user’s password (When a password reset
 * is triggered – a token will be created and a special link containing this
 * token will be emailed to the use)
 *
 * @author Shpakova A.
 */

@Entity
@Data
public class PasswordResetToken {

    private static final int EXPIRATION = 60 * 24;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String token;

    @OneToOne(targetEntity = User.class, fetch = FetchType.EAGER)
    @JoinColumn(nullable = false, name = "user_id")
    private User user;

    private Date expiryDate; //дата истечения действия ссылки

    public PasswordResetToken(String token, User user) {
    }
}
