package ru.job4j.shortcut.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO-модель данных шортката для представления статистики вызовов шорткатаю.
 * @author Lev Grossevich
 * @version 1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Statistic {
    /**
     * Прямая URL-ссылка.
     */
    private String url;
    /**
     * Общее количество вызовов шортката с указанным url.
     */
    private int total;
}
