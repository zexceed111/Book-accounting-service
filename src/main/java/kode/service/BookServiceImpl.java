package kode.service;

import kode.dto.Book_dto.BookRequest;
import kode.dto.Book_dto.BookResponse;
import kode.exception.ResourceNotFoundException;
import kode.model.Author;
import kode.model.Book;
import kode.repository.AuthorRepository;
import kode.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;

    @Override
    public BookResponse create(BookRequest request) {
        Author author = authorRepository.findById(request.authorId())
                .orElseThrow(() -> new ResourceNotFoundException(Author.class, request.authorId()));
        Book book = new Book(null, request.title(), author, request.year(), request.genre());
        Book saved = bookRepository.save(book);
        return toResponse(saved);
    }

    @Override
    public BookResponse update(Long id, BookRequest request) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(Book.class, id));
        if (request.title() != null) book.setTitle(request.title());
        if (request.genre() != null) book.setGenre(request.genre());
        if (request.authorId() != null) {
            Author author = authorRepository.findById(request.authorId())
                    .orElseThrow(() -> new ResourceNotFoundException(Author.class, request.authorId()));
            book.setAuthor(author);
        }
        return toResponse(bookRepository.save(book));
    }

    @Override
    public BookResponse getById(Long id) {
        return bookRepository.findById(id)
                .map(this::toResponse)
                .orElseThrow(() -> new ResourceNotFoundException(Book.class, id));
    }

    @Override
    public Page<BookResponse> findAll(Pageable pageable) {
        return bookRepository.findAll(pageable).map(this::toResponse);
    }

    @Override
    public void delete(Long id) {
        if (!bookRepository.existsById(id)) {
            throw new ResourceNotFoundException(Book.class, id);
        }
        bookRepository.deleteById(id);
    }

    private BookResponse toResponse(Book book) {
        return new BookResponse(book.getId(), book.getTitle(), book.getAuthor().getId(),
                book.getYear(), book.getGenre());
    }
}
