package ru.job4j.repository;

import org.springframework.data.repository.CrudRepository;
import ru.job4j.model.Shortcut;

import java.util.Optional;

public interface ShortcutRepository extends CrudRepository<Shortcut, Integer> {
    Optional<Shortcut> findByUrl(String url);
    Optional<Shortcut> findByCode(String code);
}
