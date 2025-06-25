package kode.dto.Author_dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;

public record AuthorRequest(
        @NotBlank(message = "Имя автора не может быть пустым") String firstName,
        @NotBlank(message = "Фамилия автора не может быть пустой") String lastName,
        @Positive(message = "Год рождения должен быть положительным") Integer birthYear
) {}
