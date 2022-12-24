package ru.job4j.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.job4j.model.Site;
import ru.job4j.repository.SiteRepository;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class SiteService {
    private final SiteRepository repository;

    public Site save(Site site) {
        return repository.save(site);
    }

    public boolean delete(Site site) {
        if (repository.existsById(site.getId())) {
            return false;
        }
        repository.delete(site);
        return true;
    }

    public List<Site> findAll() {
        return  repository.findAll();
    }

    public Optional<Site> findById(int id) {
        return repository.findById(id);
    }

    public Optional<Site> findByUrl(String url) {
        return repository.findByUrl(url);
    }
}
