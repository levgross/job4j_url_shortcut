package ru.job4j.shortcut.controller;

import lombok.AllArgsConstructor;
import org.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.job4j.shortcut.model.Shortcut;
import ru.job4j.shortcut.model.ShortcutDTOUrl;
import ru.job4j.shortcut.model.Site;
import ru.job4j.shortcut.model.Statistics;
import ru.job4j.shortcut.service.ShortcutService;
import ru.job4j.shortcut.service.SiteService;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import static org.apache.commons.lang.RandomStringUtils.randomAlphanumeric;

/**
 * Контроллер обработки шорткатов.
 * @see Shortcut
 * @author Lev Grossevich
 * @version 1.0
 */
@RestController
@AllArgsConstructor
public class ShortcutController {
    /**
     * Количество символов уникального кода, который ассоциирован с URL-ссылкой.
     */
    private final static int SHORTCUT_LENGTH = 7;
    /**
     * Сервис обработки шорткатов.
     * @see ShortcutService
     */
    private final ShortcutService service;
    /**
     * Сервис обработки сайтов-пользователей.
     * @see SiteService
     */
    private final SiteService siteService;

    /**
     * Возвращает их хранилища уникальный код, который ассоциирован с предоставленной URL-ссылкой,
     * если URL ранее была зарегистрирована.
     * Если URL-ссылка новая, сохраняет её в новом шоркате и возвращает пользователю код.
     * @param shortcutDTOUrl тело JSON-объекта с URL-ссылкой.
     * @return JSON-объект с уникальным кодом, ассоциированным с URL-ссылкой.
     */
    @PostMapping("/convert")
    public ResponseEntity<String> convert(@RequestBody ShortcutDTOUrl shortcutDTOUrl) {
        JSONObject response = new JSONObject();
        String url = shortcutDTOUrl.getUrl();
        if (url == null) {
            throw new NullPointerException("Url must be not empty!");
        }
        var shortcutOpt = service.findByUrl(url);
        if (shortcutOpt.isPresent()) {
            response.put("code", shortcutOpt.get().getCode());
            return new ResponseEntity<>(
                    response.toString(),
                    HttpStatus.OK
            );
        }
        Shortcut shortcut = new Shortcut();
        shortcut.setUrl(url);
        String code = randomAlphanumeric(SHORTCUT_LENGTH);
        shortcut.setCode(code);
        shortcut.setSite(siteService.findInContext().get());
        service.save(shortcut);
        response.put("code", code);
        return new ResponseEntity<>(
                response.toString(),
                HttpStatus.OK
        );
    }

    /**
     * Переадресовывает пользователя на URL-адрес, ассоциированный с введенным кодом.
     * В шорткате с указанным кодом увеличивает на 1 счетчик вызовов - count.
     * @param code уникальный код, ассоциированный с URL-ссылкой.
     * @return ResponseEntity, который переадресовывает пользователя на URL-адрес, ассоциированный с введенным кодом.
     */
    @GetMapping("/redirect/{code}")
    public ResponseEntity<Void> redirect(@PathVariable String code) {
        var shortcutOpt = service.findByCode(code);
        if (shortcutOpt.isEmpty()) {
            return new ResponseEntity<>(
                HttpStatus.NOT_FOUND
            );
        }
        Shortcut shortcut = shortcutOpt.get();
        service.incrementCount(shortcut.getId());
        return ResponseEntity.status(HttpStatus.FOUND).location(URI.create(shortcut.getUrl())).build();
    }

    /**
     * Возвращает статистику вызовов по каждой URL-ссылке, которую регистрировал данный пользователь.
     * @return JSON-объект с количеством вызовов по каждой url-ссылке, которую регистрировал данный пользователь.
     */
    @GetMapping("/statistic")
    public List<Statistics> statistic() {
        List<Statistics> statisticsList = new ArrayList<>();
        Site site = siteService.findInContext().get();
        List<Shortcut> shortcuts = service.findAllBySite(site);
        shortcuts.forEach((s) ->  statisticsList.add(new Statistics(s.getUrl(), s.getCount())));
        return statisticsList;
    }
}
