package com.leverX.blog.security;

import io.jsonwebtoken.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

/**
 * This class is for manipulating the data that will be placed in the token.
 *
 * @author Shpakova A.
 */

@Component
@Slf4j
public class JwtProvider {
    @Value("$(jwt.secret)") //поле jwtSecret в файле настроект лежит
    private String jwtSecret;

    public String generateToken(String login) {
        Date date = Date.from(LocalDate.now().plusDays(15).atStartOfDay(ZoneId.systemDefault()).toInstant());
        return Jwts.builder() //создаем токен
                .setSubject(login)//потом забираем логин в фильтре ,когда пользователь делает запрос
                .setExpiration(date) //15 дней,сли пройдет 15 дней и токен не обновить — будет выброшено сообщение об ошибке в методе validateToken
                .signWith(SignatureAlgorithm.HS512, jwtSecret)//принимает на вход алгоритм подписи и кодовое слово, которое потом потребуется для расшифровки
                .compact();
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token);
            return true;
        } catch (ExpiredJwtException expEx) {
            log.error("Token expired");
        } catch (UnsupportedJwtException unsEx) {
            log.error("Unsupported jwt");
        } catch (MalformedJwtException mjEx) {
            log.error("Malformed jwt");
        } catch (SignatureException sEx) {
            log.error("Invalid signature");
        } catch (Exception e) {
            log.error("invalid token");
        }
        return false;
    }

    public String getLoginFromToken(String token) { //получить информацию о логине пользователя
        Claims claims = Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody();
        return claims.getSubject();
    }
    //При генерации токена в Subject кладется токен — значит,
    // если токен будет валидный - в нем будет логин.
}
