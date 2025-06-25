package kode.dto.Book_dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;

public record BookResponse(Long id,
                           String title,
                           Long year,
                           @Positive(message = "Год издания должен быть положительным")
                           int genre,
                           @NotBlank(message = "Жанр обязателен") String author
) {}