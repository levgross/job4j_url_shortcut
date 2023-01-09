package ru.job4j.shortcut.service;

import ru.job4j.shortcut.model.Shortcut;
import ru.job4j.shortcut.model.Site;

import java.util.List;
import java.util.Optional;

public interface ShortcutService {
    Shortcut save(Shortcut shortcut);
    List<Shortcut> findAllBySite(Site site);
    Optional<Shortcut> findByUrl(String url);
    Optional<Shortcut> findByCode(String code);
    void incrementCount(int shortcutId);
}
