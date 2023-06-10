package com.polarbookshop.catalogservice.web;

import com.polarbookshop.catalogservice.domain.Book;
import com.polarbookshop.catalogservice.domain.BookNotFoundException;
import com.polarbookshop.catalogservice.domain.BookService;
import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(BookController.class)
class BookControllerMvcTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BookService bookService;

    @Test
    void whenGetBookNotExistingThenShouldReturn404() throws Exception {
        String isbn = "73737313940";
        given(bookService.viewBookDetails(isbn)).willThrow(BookNotFoundException.class);
        mockMvc
                .perform(get("/books/" + isbn))
                .andExpect(status().isNotFound());
    }

    @Test
    void whenThen() throws Exception {
        String validIsbn = "12344567890123";
        Book book = Book.of(validIsbn, "title", "author", 1.0, "publisher");

        given(bookService.viewBookDetails(validIsbn)).willReturn(book);
        mockMvc.perform(get("/books/" + validIsbn))
                .andExpectAll(
                        status().is2xxSuccessful(),
                        content().contentType(MediaType.APPLICATION_JSON),
                        content().json("{\"isbn\":\"12344567890123\",\"title\":\"title\",\"author\":\"author\",\"price\":1.0}"));

    }

}

