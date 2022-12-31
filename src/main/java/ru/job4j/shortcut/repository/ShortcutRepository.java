package ru.job4j.shortcut.repository;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;
import ru.job4j.shortcut.model.Shortcut;
import ru.job4j.shortcut.model.Site;

import java.util.Optional;
import java.util.List;

/**
 * Хранилище шорткатов.
 * @see Shortcut
 * @author Lev Grossevich
 * @version 1.0
 */
public interface ShortcutRepository extends CrudRepository<Shortcut, Integer> {
    /**
     * Возвращает Optional объекта шортката по URL,
     * если шорткат с таким URL зарегистрирован в хранилище.
     * Иначе возвращает Optional.empty().
     * @param url URL шортката.
     * @return Optional.of(shortcut) при успешном поиске, иначе Optional.empty()
     */
    Optional<Shortcut> findByUrl(String url);
    /**
     * Возвращает Optional объекта шортката по уникальному коду,
     * если шорткат с таким кодом зарегистрирован в хранилище.
     * Иначе возвращает Optional.empty().
     * @param code уникальный код, хранящийся в объекте шортката.
     * @return Optional.of(shortcut) при успешном поиске, иначе Optional.empty()
     */
    Optional<Shortcut> findByCode(String code);

    /**
     * Возвращает список всех шорткатов, которые зарегистрированы указанным сайтом-пользователем.
     * @param site Сайт-пользователь.
     * @return Список шорткатов указанного сайта-пользователя.
     */
    List<Shortcut> findAllBySite(Site site);

    /**
     * Производит в хранилище инкремент счетчика шортката по id шортката.
     * @param shortcutId Id шортката.
     */
    @Modifying
    @Transactional
    @Query("update Shortcut s set s.count = s.count + 1 where s.id = :shortcutId")
    void incrementCount(int shortcutId);
}
