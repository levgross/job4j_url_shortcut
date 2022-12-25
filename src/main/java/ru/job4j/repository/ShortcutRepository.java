package ru.job4j.repository;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;
import ru.job4j.model.Shortcut;
import ru.job4j.model.Site;

import java.util.Optional;
import java.util.List;

public interface ShortcutRepository extends CrudRepository<Shortcut, Integer> {
    Optional<Shortcut> findByUrl(String url);
    Optional<Shortcut> findByCode(String code);
    List<Shortcut> findAllBySite(Site site);
    @Modifying
    @Transactional
    @Query("update Shortcut s set s.count = s.count + 1 where s.id = :shortcutId")
    void incrementCount(int shortcutId);
}
