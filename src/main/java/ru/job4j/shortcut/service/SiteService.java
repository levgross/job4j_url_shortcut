package ru.job4j.shortcut.service;

import ru.job4j.shortcut.model.Site;

import java.util.List;
import java.util.Optional;

public interface SiteService {
    Site save(Site site);
    Optional<Site> findById(int id);
    Optional<Site> findBySite(String site);
    Optional<Site> findByLogin(String login);
    Optional<Site> findInContext();
    List<Site> findAll();
}
