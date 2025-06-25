package kode.dto.Book_dto;

public record BookResponse(Long id, String title, Long year, @jakarta.validation.constraints.Positive(message = "Год издания должен быть положительным") int genre, @jakarta.validation.constraints.NotBlank(message = "Жанр обязателен") String author) {}