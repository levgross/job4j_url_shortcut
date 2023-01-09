package ru.job4j.shortcut.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.job4j.shortcut.model.Shortcut;
import ru.job4j.shortcut.model.Site;
import ru.job4j.shortcut.repository.ShortcutRepository;

import java.util.List;
import java.util.Optional;

/**
 * Сервис обработки шорткатов.
 * @see Shortcut
 * @author Lev Grossevich
 * @version 1.0
 */
@Service
@AllArgsConstructor
public class SimpleShortcutService implements ShortcutService {
    /**
     * Хранилище шорткатов.
     * @see ShortcutRepository
     */
    private final ShortcutRepository repository;

    /**
     * Если shortcut в параметре метода не содержит идентификатор id,
     * выполняет сохранение шортката с проинициализированным id.
     * Если shortcut в параметре метода содержит идентификатор id,
     * выполняет обновление шортката с таким id.
     * Действие может не произойти, если сохраняется/обновляется шорткат, у которого URL
     * совпадает с URL другого зарегистрированного шортката.
     * @param shortcut Объект шорткат.
     * @return shortcut c id.
     */
    @Override
    public Shortcut save(Shortcut shortcut) {
        return repository.save(shortcut);
    }

    /**
     * Возвращает список всех шорткатов, которые зарегистрированы указанным сайтом-пользователем.
     * @param site Сайт-пользователь.
     * @return Список шорткатов указанного сайта-пользователя.
     */
    @Override
    public List<Shortcut> findAllBySite(Site site) {
        return repository.findAllBySite(site);
    }

    /**
     * Возвращает Optional объекта шорткат по URL,
     * если такой шорткат зарегистрирован в хранилище.
     * Иначе возвращает Optional.empty().
     * @param url URL шортката.
     * @return Optional.of(shortcut) при успешном поиске, иначе Optional.empty()
     */
    @Override
    public Optional<Shortcut> findByUrl(String url) {
        return repository.findByUrl(url);
    }

    /**
     * Возвращает Optional объекта шорткат по уникальному коду,
     * если такой шорткат зарегистрирован в хранилище.
     * Иначе возвращает Optional.empty().
     * @param code уникальный код, хранящийся в объекте шортката.
     * @return Optional.of(shortcut) при успешном поиске, иначе Optional.empty()
     */
    @Override
    public Optional<Shortcut> findByCode(String code) {
        return repository.findByCode(code);
    }

    /**
     * Производит в хранилище инкремент счетчика шортката по id шортката.
     * @param shortcutId Id шортката.
     */
    @Override
    public void incrementCount(int shortcutId) {
        repository.incrementCount(shortcutId);
    }
}
