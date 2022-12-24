package ru.job4j.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import ru.job4j.model.Site;
import ru.job4j.service.SiteService;
import org.json.JSONObject;

import java.util.List;

import static org.apache.commons.lang.RandomStringUtils.randomAlphanumeric;

@RestController
@RequestMapping("/site")
@AllArgsConstructor
public class SiteController {
    private final static int LOGIN_LENGTH = 10;
    private final static int PASSWORD_LENGTH = 10;

    private final SiteService service;
    private final BCryptPasswordEncoder encoder;

    @PostMapping("/registration")
    public ResponseEntity<String> register(@RequestBody Site site) {
        JSONObject response = new JSONObject();
        if (service.findByUrl(site.getUrl()).isPresent()) {
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

    @GetMapping("/")
    public List<Site> findAll() {
        return service.findAll();
    }

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
