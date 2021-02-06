package com.solomka.controllers;

import com.solomka.models.Book;
import com.solomka.models.CreateBookDto;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Arrays;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class BookControllerTest extends BaseControllerTest {
    @Autowired
    private BookController bookController;
    private final String url = "/books";

    @Test
    public void testBookController() {
        Assertions.assertNotNull(this.bookController);
    }

    @Test
    public void createBookTest() throws Exception {
        CreateBookDto createBookDto = new CreateBookDto();
        createBookDto.setName("Cool createBookDto");
        createBookDto.setDescription("Cool description");
        createBookDto.setNumberOfWords(100500);
        createBookDto.setRating(10);
        createBookDto.setYearOfPublication(2020);
        createBookDto.setAuthors(Arrays.asList("author1", "author2"));

        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post(this.url)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(OBJECT_MAPPER.writeValueAsString(createBookDto));

        MvcResult result = this.mockMvc.perform(requestBuilder)
                .andExpect(status().isCreated())
                .andReturn();

        Book book = OBJECT_MAPPER.readValue(
                result.getResponse().getContentAsString(),
                Book.class
        );

        this.mockMvc.perform(get(url+"/" + book.getBookId()))
                .andExpect(status().isOk());
    }

    @Test
    public void getAllSuccessfulTest() throws Exception {
        String expected = "{\"bookId\":\"random_id_value_5\",\"name\":\"7 habits of highly effective\",\"description\":\"\",\"authors\":[\"Stephen R Covey \"],\"yearOfPublication\":1989,\"numberOfWords\":464368,\"rating\":10}";
        this.mockMvc.perform(get(this.url)).andExpect(status().isOk())
                .andExpect(content().string(containsString(expected)));
    }

    @Test
    public void getBookByIdSuccessfulTest() throws Exception {
        String expected = "random_id_value_3";
        this.mockMvc.perform(get(this.url+"/"+expected))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString(expected)));
    }

    @Test
    public void getBookByIdFailedTest() throws Exception {
        String expected = "some id";
        this.mockMvc.perform(get(this.url+"/"+expected))
                .andExpect(status().isNotFound());
    }

    @Test
    public void deleteByIdSuccessfulTest() throws Exception{
        String expected = "random_id_value_1";
        this.mockMvc.perform(delete(this.url+"/"+expected))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString(expected)));
    }

    @Test
    public void deleteByIdFailedTest() throws Exception{
        String expected = "some_id";
        this.mockMvc.perform(delete(this.url+"/"+expected))
                .andExpect(status().isNotFound());
    }
}