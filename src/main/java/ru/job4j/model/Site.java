package ru.job4j.model;

import lombok.*;
import lombok.EqualsAndHashCode.Include;


import javax.persistence.*;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Site {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Include
    private int id;
    private String site;
    private String login;
    private String password;
}
