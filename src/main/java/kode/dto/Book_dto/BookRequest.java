package kode.dto.Book_dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record BookRequest(
        @NotBlank(message = "Название книги не может быть пустым") String title,
        @NotNull(message = "ID автора обязателен") Long authorId,
        @Positive(message = "Год издания должен быть положительным") int year,
        @NotBlank(message = "Жанр обязателен") String genre
) {}
