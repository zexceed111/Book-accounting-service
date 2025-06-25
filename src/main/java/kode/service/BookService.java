package kode.service;

import kode.dto.Book_dto.BookRequest;
import kode.dto.Book_dto.BookResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface BookService {
    BookResponse create(BookRequest request);
    BookResponse update(Long id, BookRequest request);
    BookResponse getById(Long id);
    Page<BookResponse> findAll(Pageable pageable);
    void delete(Long id);
}