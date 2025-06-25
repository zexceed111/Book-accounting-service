package kode.controller;

import kode.dto.Author_dto.AuthorRequest;
import kode.dto.Author_dto.AuthorResponse;
import kode.service.AuthorService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/authors")
@RequiredArgsConstructor
public class AuthorController {
    private final AuthorService authorService;

    @PostMapping
    public AuthorResponse addAuthor(@RequestBody @Valid AuthorRequest request) {
        return authorService.create(request);
    }

    @GetMapping
    public Page<AuthorResponse> getAuthors(@RequestParam(required = false) String firstName, @RequestParam(required = false) String lastName, Pageable pageable) {
        return authorService.findAll(firstName, lastName, pageable);
    }

    @GetMapping("/{id}")
    public AuthorResponse getAuthor(@PathVariable Long id) {
        return authorService.findById(id);
    }
}