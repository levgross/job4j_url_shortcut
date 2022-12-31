package ru.job4j.shortcut.security;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import ru.job4j.shortcut.model.Site;
import ru.job4j.shortcut.repository.SiteRepository;

import java.util.Optional;

import static java.util.Collections.emptyList;

/**
 * Сервис для определения модели, которая используется, как пользователь сервиса,
 * и определения, какое поле используется в качестве логина.
 */
@Service
@AllArgsConstructor
public class SiteDetailsService implements UserDetailsService {
    /**
     * Хранилище сайтов-пользователей.
     * @see SiteRepository
     */
    private final SiteRepository repository;

    /**
     * Выполняет поиск пользователя по логину. Возвращает UserDetail с парой логин-пароль, если поиск успешный.
     * Или возвращает UsernameNotFoundException, если нет.
     * @param username логин сайта-пользователя.
     * @return объект UserDetail с указанными параметрами
     * @throws UsernameNotFoundException - если сайта-пользователя с таким логином нет в хранилище.
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Site> site = repository.findByLogin(username);
        if (site.isEmpty()) {
            throw new UsernameNotFoundException(username);
        }
        return new User(site.get().getLogin(), site.get().getPassword(), emptyList());
    }
}
