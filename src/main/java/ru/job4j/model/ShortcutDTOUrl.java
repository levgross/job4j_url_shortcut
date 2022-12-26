package ru.job4j.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO-модель данных шортката.
 * @author Lev Grossevich
 * @version 1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ShortcutDTOUrl {
    /**
     * Прямая URL-ссылка.
     */
    private String url;
}