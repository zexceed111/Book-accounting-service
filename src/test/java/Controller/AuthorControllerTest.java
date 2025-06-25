package Controller;

import kode.dto.Author_dto.AuthorResponse;
import kode.controller.AuthorController;
import kode.service.AuthorService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.isNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(AuthorController.class)
class AuthorControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AuthorService authorService;

    @Test
    void getAuthors_returnsPagedList() throws Exception {
        List<AuthorResponse> authors = List.of(
                new AuthorResponse(1L, "George", "Orwell", 1903),
                new AuthorResponse(2L, "Joanne", "Rowling", 1965)
        );
        Mockito.when(authorService.findAll(
                isNull(),
                isNull(),
                eq(PageRequest.of(0, 10))
        )).thenReturn(new PageImpl<>(authors));

        mockMvc.perform(get("/authors?page=0&size=10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].firstName").value("George"))
                .andExpect(jsonPath("$[1].lastName").value("Rowling"));
    }
}