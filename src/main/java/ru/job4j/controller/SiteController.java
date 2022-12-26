package ru.job4j.controller;

import lombok.AllArgsConstructor;
import org.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import ru.job4j.model.Site;
import ru.job4j.service.SiteService;

import javax.validation.Valid;
import java.util.List;

import static org.apache.commons.lang.RandomStringUtils.randomAlphanumeric;

/**
 * Контроллер обработки сайтов-пользователей.
 * @see ru.job4j.model.Site
 * @author Lev Grossevich
 * @version 1.0
 */
@RestController
@RequestMapping("/site")
@AllArgsConstructor
public class SiteController {
    /**
     * Количество символов уникального кода, который предоставляется пользователю в качестве логина.
     */
    private final static int LOGIN_LENGTH = 10;
    /**
     * Количество символов уникального кода, который предоставляется пользователю в качестве пароля.
     */
    private final static int PASSWORD_LENGTH = 10;

    /**
     * Сервис обработки сайтов-пользователей.
     * @see ru.job4j.service.SiteService
     */
    private final SiteService service;
    /**
     * Кодировщик паролей.
     */
    private final BCryptPasswordEncoder encoder;

    /**
     * Метод регистрации сайта пользователя в сервисе.
     * Пользователь указывает название сайта.
     * Если сайт с таким названием уже зарегистрирован, возвращает false и сообщение.
     * Если регистрация прошла успешно, возвращает true и сгенерированные логин и пароль.
     * Флаг registration указывает, была ли регистрация выполнена, или сайт уже есть в системе.
     * @param site тело JSON-объекта с названием сайта.
     * @return Если успешно - ResponseEntity с флагом true, сгенерированным логином, сгенерированным паролем.
     * Если название сайт-пользователь с таким названием уже зарегистрирован - ResponseEntity с флагом false,
     * и сообщением.
     * @throws MethodArgumentNotValidException - если поле с названием сайта в параметре не указано или пустое.
     */
    @PostMapping("/registration")

    public ResponseEntity<String> register(@Valid @RequestBody Site site) {
        JSONObject response = new JSONObject();
        if (site.getSite() == null) {
            throw new NullPointerException("Site name must must be not empty!");
        }
        if (service.findBySite(site.getSite()).isPresent()) {
            response.put("registration", false);
            response.put("message", "Site is registered already!");
            return new ResponseEntity<>(
                    response.toString(),
                    HttpStatus.BAD_REQUEST
            );
        }
        String login = randomAlphanumeric(LOGIN_LENGTH);
        site.setLogin(login);
        String password = randomAlphanumeric(PASSWORD_LENGTH);
        String safePassword = encoder.encode(password);
        site.setPassword(safePassword);
        service.save(site);
        response.put("registration", true);
        response.put("login", login);
        response.put("password", password);
        return new ResponseEntity<>(
                response.toString(),
                HttpStatus.OK
        );
    }

    /**
     * Возвращает из хранилища список всех сайтов-пользователей.
     * @return список всех пользователей.
     */
    @GetMapping("/")
    public List<Site> findAll() {
        return service.findAll();
    }

    /**
     * Возвращает из хранилища сайт-пользователь с указанным id.
     * Если такого нет, сообщение об ошибке.
     * @return список всех пользователей.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Site> findById(@PathVariable int id) {
        var site = service.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Site is not found! Check login!"
                ));
        return new ResponseEntity<>(
                site,
                HttpStatus.OK
        );
    }
}
