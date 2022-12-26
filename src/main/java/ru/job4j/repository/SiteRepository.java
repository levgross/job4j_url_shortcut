package ru.job4j.repository;

import org.springframework.data.repository.CrudRepository;
import ru.job4j.model.Site;

import java.util.List;
import java.util.Optional;

/**
 * Хранилище сайтов-пользователей.
 * @see ru.job4j.model.Site
 * @author Lev Grossevich
 * @version 1.0
 */
public interface SiteRepository extends CrudRepository<Site, Integer> {
    /**
     * Возвращает список всех сайтов-пользователей.
     * @return список всех пользователей.
     */
    List<Site> findAll();

    /**
     * Возвращает Optional объекта сайт-пользователь по названию сайта,
     * если такой сайт-пользователь зарегистрирован в хранилище.
     * Иначе возвращает Optional.empty().
     * @param site название сайта.
     * @return Optional.of(site) при успешном сохранении, иначе Optional.empty()
     */
    Optional<Site> findBySite(String site);

    /**
     * Возвращает Optional объекта сайт-пользователь по логину сайта-пользователя,
     * если сайт-пользователь с таким логином зарегистрирован в хранилище.
     * Иначе возвращает Optional.empty().
     * @param login Логин сайта-пользователя.
     * @return Optional.of(site) при успешном поиске, иначе Optional.empty()
     */
    Optional<Site> findByLogin(String login);
}
