package ru.job4j.shortcut.service;

import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import ru.job4j.shortcut.model.Site;
import ru.job4j.shortcut.repository.SiteRepository;

import java.util.List;
import java.util.Optional;

/**
 * Сервис обработки сайтов-пользователей.
 * @see Site
 * @author Lev Grossevich
 * @version 1.0
 */
@Service
@AllArgsConstructor
public class SiteService {
    /**
     * Хранилище сайтов-пользователей.
     * @see SiteRepository
     */
    private final SiteRepository repository;

    /**
     * Если site в параметре метода не содержит идентификатор id,
     * выполняет сохранение сайта-пользователя с проинициализированным id.
     * Если site в параметре метода содержит идентификатор id,
     * выполняет обновление сайта-пользователя с таким id.
     * Действие может не произойти, если сохраняется/обновляется сайт-пользователь, у которого название сайта
     * совпадает с названием сайта другого зарегистрированног сайта-пользователя.
     * @param site Объект сайт-пользователь.
     * @return site c id.
     */
    public Site save(Site site) {
        return repository.save(site);
    }

    /**
     * Удаляет из хранилища сайт-пользователь, если он там зарегистрирован, и возвращает true.
     * Если сайт-пользователь с таким id в хранилище не зарегистрирован, возвращает false.
     * @param site Объект сайт-пользователь.
     * @return true, если сайт-пользователь удален, или false, если такого пользователя не было.
     */
    public boolean delete(Site site) {
        if (repository.existsById(site.getId())) {
            return false;
        }
        repository.delete(site);
        return true;
    }

    /**
     * Возвращает из хранилища список всех сайтов-пользователей.
     * @return список всех пользователей.
     */
    public List<Site> findAll() {
        return  repository.findAll();
    }

    /**
     * Возвращает Optional объекта сайт-пользователь по id,
     * если такой сайт-пользователь зарегистрирован в хранилище.
     * Иначе возвращает Optional.empty().
     * @param id идентификатор сайта-пользователя.
     * @return Optional.of(site) при успешном поиске, иначе Optional.empty()
     */
    public Optional<Site> findById(int id) {
        return repository.findById(id);
    }

    /**
     * Возвращает Optional объекта сайт-пользователь по названию сайта,
     * если такой сайт-пользователь зарегистрирован в хранилище.
     * Иначе возвращает Optional.empty().
     * @param site название сайта.
     * @return Optional.of(site) при успешном поиске, иначе Optional.empty()
     */
    public Optional<Site> findBySite(String site) {
        return repository.findBySite(site);
    }

    /**
     * Возвращает Optional объекта сайт-пользователь по логину сайта-пользователя,
     * если сайт-пользователь с таким логином зарегистрирован в хранилище.
     * Иначе возвращает Optional.empty().
     * @param login Логин сайта-пользователя.
     * @return Optional.of(site) при успешном поиске, иначе Optional.empty()
     */
    public Optional<Site> findByLogin(String login) {
        return repository.findByLogin(login);
    }

    /**
     * Возвращает из контекста сервиса Optional текущего объекта сайт-пользователь,
     * если сайт-пользователь с таким логином зарегистрирован в хранилище.
     * Иначе возвращает Optional.empty().
     * @return Optional.of(site) при успешном поиске, иначе Optional.empty()
     */
    public Optional<Site> findInContext() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return findByLogin((String) auth.getPrincipal());
    }
}
