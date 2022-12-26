package ru.job4j.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.EqualsAndHashCode.Include;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;

/**
 * Модель данных сайта-пользователя
 * @author Lev Grossevich
 * @version 1.0
 */
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Site {
    /**
     * Идентификатор сайта-пользователя.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Include
    private int id;
    /**
     * Название сайта-пользователя.
     */
    @NotBlank(message = "Site must be not empty")
    private String site;
    /**
     * Логин сайта-пользователя.
     */
    private String login;
    /**
     * Пароль сайта-пользователя.
     */
    private String password;
}
