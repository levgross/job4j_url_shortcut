package ru.job4j.repository;

import org.springframework.data.repository.CrudRepository;
import ru.job4j.model.Site;

import java.util.List;
import java.util.Optional;

public interface SiteRepository extends CrudRepository<Site, Integer> {
    List<Site> findAll();
    Optional<Site> findByUrl(String url);
    Site findByLogin(String login);
}
