package ru.job4j.security;

import com.auth0.jwt.JWT;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import ru.job4j.model.Site;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

import static com.auth0.jwt.algorithms.Algorithm.HMAC512;

@AllArgsConstructor
public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    /**
     * Секретный ключ для генерации токенов.
     */
    public static final String SECRET = "SecretKeyToGenJWTs";
    /**
     * Срок действия токена с момента авторизации в миллисекундах.
     */
    public static final long EXPIRATION_TIME = 864_000_000;
    /**
     * Префикс токена.
     */
    public static final String TOKEN_PREFIX = "Bearer ";
    /**
     * Хэдер токена.
     */
    public static final String HEADER_STRING = "Authorization";
    /**
     * URL для регистрации пользователя.
     */
    public static final String SIGN_UP_URL = "/site/registration";
    /**
     * URL для переадресации с шортката на ассоциированный с ним URL.
     */
    public static final String REDIRECT_URL = "/redirect/**";

    private AuthenticationManager auth;

    @Override
    public Authentication attemptAuthentication(HttpServletRequest req, HttpServletResponse res)
            throws AuthenticationException {
        try {
            Site creds = new ObjectMapper()
                    .readValue(req.getInputStream(), Site.class);

            return auth.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            creds.getLogin(),
                            creds.getPassword(),
                            new ArrayList<>())
            );
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest req,
                                            HttpServletResponse res,
                                            FilterChain chain,
                                            Authentication auth) throws IOException, ServletException {

        String token = JWT.create()
                .withSubject(((User) auth.getPrincipal()).getUsername())
                .withExpiresAt(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .sign(HMAC512(SECRET.getBytes()));
        res.addHeader(HEADER_STRING, TOKEN_PREFIX + token);
    }
}
