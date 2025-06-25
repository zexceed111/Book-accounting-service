package kode.service;

import kode.dto.Author_dto.AuthorRequest;
import kode.dto.Author_dto.AuthorResponse;
import kode.exception.ResourceNotFoundException;
import kode.model.Author;
import kode.repository.AuthorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
@RequiredArgsConstructor
public class AuthorService {
    private final AuthorRepository authorRepository;

    public AuthorResponse create(AuthorRequest req) {
        Author author = new Author();
        author.setFirstName(req.firstName());
        author.setLastName(req.lastName());
        author.setBirthYear(req.birthYear());
        return map(authorRepository.save(author));
    }

    public Page<AuthorResponse> findAll(String firstName, String lastName, Pageable pageable) {
        if (StringUtils.hasText(firstName)) {
            return authorRepository.findByFirstNameContainingIgnoreCase(firstName, pageable).map(this::map);
        }
        if (StringUtils.hasText(lastName)) {
            return authorRepository.findByLastNameContainingIgnoreCase(lastName, pageable).map(this::map);
        }
        return authorRepository.findAll(pageable).map(this::map);
    }

    public AuthorResponse findById(Long id) {
        return map(authorRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Автор не найден: " + id)));
    }

    private AuthorResponse map(Author a) {
        return new AuthorResponse(a.getId(), a.getFirstName(), a.getLastName(), a.getBirthYear());
    }
}
