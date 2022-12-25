package ru.job4j.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.job4j.model.Shortcut;
import ru.job4j.model.Site;
import ru.job4j.repository.ShortcutRepository;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class ShortcutService {
    private final ShortcutRepository repository;

    public Shortcut save(Shortcut shortcut) {
        return repository.save(shortcut);
    }

    public boolean delete(Shortcut shortcut) {
        if (repository.existsById(shortcut.getId())) {
            repository.delete(shortcut);
            return true;
        }
        return false;
    }

    public List<Shortcut> findAll() {
        return (List<Shortcut>) repository.findAll();
    }

    public List<Shortcut> findAllBySite(Site site) {
        return repository.findAllBySite(site);
    }

    public Optional<Shortcut> findByUrl(String url) {
        return repository.findByUrl(url);
    }

    public Optional<Shortcut> findByCode(String code) {
        return repository.findByCode(code);
    }

    public void incrementCount(int shortcutId) {
        repository.incrementCount(shortcutId);
    }
}
