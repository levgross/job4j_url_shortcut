package ru.job4j.controller;

import lombok.AllArgsConstructor;
import org.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.job4j.model.Shortcut;
import ru.job4j.model.ShortcutDTOUrl;
import ru.job4j.service.ShortcutService;
import ru.job4j.service.SiteService;

import java.net.URI;

import static org.apache.commons.lang.RandomStringUtils.randomAlphanumeric;

@RestController
@AllArgsConstructor
public class ShortcutController {
    private final static int SHORTCUT_LENGTH = 7;
    private final ShortcutService service;
    private final SiteService siteService;

    @PostMapping("/convert")
    public ResponseEntity<String> convert(@RequestBody ShortcutDTOUrl shortcutDTOUrl) {
        JSONObject response = new JSONObject();
        String url = shortcutDTOUrl.getUrl();
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

    @GetMapping("/redirect/{code}")
    public ResponseEntity<Void> redirect(@PathVariable String code) {
        var shortcutOpt = service.findByCode(code);
        if (shortcutOpt.isEmpty()) {
            return new ResponseEntity<>(
                HttpStatus.NOT_FOUND
            );
        }
        Shortcut shortcut = shortcutOpt.get();
        return ResponseEntity.status(HttpStatus.FOUND).location(URI.create(shortcut.getUrl())).build();
    }
}
