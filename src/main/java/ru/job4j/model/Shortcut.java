package ru.job4j.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

/**
 * Модель данных шортката.
 * @author Lev Grossevich
 * @version 1.0
 */
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Shortcut {
    /**
     * Идентификатор шортката.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private int id;
    /**
     * Прямая URL-ссылка, ассоциированная с кодом.
     */
    @NotBlank(message = "Site must be not empty")
    private String url;
    /**
     * Уникальный код, ассоциированный с URL-ссылкой.
     */
    private String code;
    /**
     * Сайт-пользователь.
     */
    @ManyToOne
    private Site site;
    /**
     * Счетчик вызовов уникального кода.
     */
    int count;
}
