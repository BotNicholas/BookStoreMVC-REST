package org.teamwork.spring.bookstoremvcrest.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.teamwork.spring.bookstoremvcrest.model.Author;
import org.teamwork.spring.bookstoremvcrest.model.dto.AuthorDTO;
import org.teamwork.spring.bookstoremvcrest.service.impl.AuthorServiceImpl;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@AutoConfigureMockMvc(addFilters = false)
@SpringBootTest
public class AuthorControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private AuthorServiceImpl authorService;
    @Autowired
    private ObjectMapper objectMapper;
    private Author author1 = new Author("a", "b", "ab", LocalDate.of(2000, 04, 23), "M", "+37379344245", "sport analyst");
    private Author author2 = new Author("c", "d", "cd", LocalDate.of(1999, 07, 22), "M", "+37378227243", "loves camping");
    private AuthorDTO authorDTO1 = new AuthorDTO("a", "ab", "ab", LocalDate.of(2000, 04, 23), "M", "+37379344245", "sport analyst");
    private AuthorDTO authorDTO2 = new AuthorDTO("c", "d", "cd", LocalDate.of(1999, 07, 22), "M", "+37378227243", "loves camping");

    @Test
    @DisplayName("get author by id")
    public void testGetAuthorById() throws Exception {
        Mockito.when(authorService.findByKey(1)).thenReturn(authorDTO1);

        mockMvc.perform(MockMvcRequestBuilders.get("/authors/1"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(authorDTO1)))
                .andDo(MockMvcResultHandlers.print());;
    }

    @Test
    @DisplayName("get all authors")
    public void testGetAuthors() throws Exception {
        List<AuthorDTO> listAuthors = new ArrayList<>(List.of(authorDTO1, authorDTO2));
        Mockito.when(authorService.findAll()).thenReturn(listAuthors);

        mockMvc
                .perform(MockMvcRequestBuilders.get("/authors"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(listAuthors)))
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    @DisplayName("get invalid author id")
    public void testInvalidAuthorId() throws Exception {
        int invalidId = 43;

        Mockito.when(authorService.findByKey(invalidId)).thenReturn(null);

        mockMvc.perform(MockMvcRequestBuilders.get("/authors/" + invalidId))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }
    @Test
    public void testCreateThenReturnAuthor() throws Exception {
        given(authorService.save(ArgumentMatchers.any())).willAnswer(invocation -> invocation.getArgument(0));

        ResultActions response = mockMvc.perform(post("/authors")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(authorDTO1)));

        response.andExpect(MockMvcResultMatchers.status().isCreated())
                .andDo(print());
    }
}

