package ru.job4j.security;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import ru.job4j.model.Site;
import ru.job4j.repository.SiteRepository;

import static java.util.Collections.emptyList;

@Service
@AllArgsConstructor
public class SiteDetailsService implements UserDetailsService {
    private final SiteRepository repository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Site site = repository.findByLogin(username);
        if (site == null) {
            throw new UsernameNotFoundException(username);
        }
        return new User(site.getLogin(), site.getPassword(), emptyList());
    }
}
