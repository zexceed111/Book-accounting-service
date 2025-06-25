package service;

import kode.dto.Book_dto.BookRequest;
import kode.exception.ResourceNotFoundException;
import kode.model.Author;
import kode.model.Book;
import kode.repository.AuthorRepository;
import kode.repository.BookRepository;
import kode.service.BookServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

class BookServiceTest {

    @Mock
    private BookRepository bookRepository;

    @Mock
    private AuthorRepository authorRepository;

    @InjectMocks
    private BookServiceImpl bookService;

    private Author author;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        author = new Author(1L, "George", "Orwell", 1903);
    }

    @Test
    void saveBook_success() {
        BookRequest request = new BookRequest("1984", 1L, 1949, "Dystopian");

        when(authorRepository.findById(1L)).thenReturn(Optional.of(author));
        when(bookRepository.save(any(Book.class))).thenAnswer(invocation -> {
            Book b = invocation.getArgument(0);
            b.setId(123L);
            return b;
        });

        var response = bookService.create(request);

        assertThat(response.id()).isEqualTo(123L);
        assertThat(response.title()).isEqualTo("1984");

        ArgumentCaptor<Book> captor = ArgumentCaptor.forClass(Book.class);
        verify(bookRepository).save(captor.capture());
        Book saved = captor.getValue();
        assertThat(saved.getAuthor()).isSameAs(author);
    }

    @Test
    void saveBook_authorNotFound_throwException() {
        BookRequest request = new BookRequest("Unknown", 42L, 2000, "Genre");

        when(authorRepository.findById(42L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> bookService.create(request))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining("Author with id=42 not found");

        verify(bookRepository, never()).save(any());
    }
}
