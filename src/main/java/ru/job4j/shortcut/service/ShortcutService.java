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
public class ShortcutService {
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
    public Shortcut save(Shortcut shortcut) {
        return repository.save(shortcut);
    }

    /**
     * Удаляет из хранилища шорткат, если он там зарегистрирован, и возвращает true.
     * Если шорткат с таким id в хранилище не зарегистрирован, возвращает false.
     * @param shortcut Объект шорткат.
     * @return true, если шорткат удален, или false, если такого шортката не было.
     */
    public boolean delete(Shortcut shortcut) {
        if (repository.existsById(shortcut.getId())) {
            repository.delete(shortcut);
            return true;
        }
        return false;
    }

    /**
     * Возвращает из хранилища список всех шорткатов.
     * @return список всех шорткатов.
     */
    public List<Shortcut> findAll() {
        return (List<Shortcut>) repository.findAll();
    }

    /**
     * Возвращает список всех шорткатов, которые зарегистрированы указанным сайтом-пользователем.
     * @param site Сайт-пользователь.
     * @return Список шорткатов указанного сайта-пользователя.
     */
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
    public Optional<Shortcut> findByCode(String code) {
        return repository.findByCode(code);
    }

    /**
     * Производит в хранилище инкремент счетчика шортката по id шортката.
     * @param shortcutId Id шортката.
     */
    public void incrementCount(int shortcutId) {
        repository.incrementCount(shortcutId);
    }
}
