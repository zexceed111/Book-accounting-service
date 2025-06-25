package kode.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "authors")
public class Author {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "first_name")
    @NotBlank(message = "Имя автора не может быть пустым")
    private String firstName;

    @Column(name = "last_name")
    @NotBlank(message = "Фамилия автора не может быть пустой")
    private String lastName;

    @Positive(message = "Год рождения должен быть положительным")
    @Column(name = "birth_year")
    private Integer birthYear;
}