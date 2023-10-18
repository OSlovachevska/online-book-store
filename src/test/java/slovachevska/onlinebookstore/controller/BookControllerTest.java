package slovachevska.onlinebookstore.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Set;
import javax.sql.DataSource;
import lombok.SneakyThrows;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.MediaType;
import org.springframework.jdbc.datasource.init.ScriptUtils;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.testcontainers.shaded.org.apache.commons.lang3.builder.EqualsBuilder;
import slovachevska.onlinebookstore.dto.book.BookResponseDto;
import slovachevska.onlinebookstore.dto.book.CreateBookRequestDto;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class BookControllerTest {

    protected static MockMvc mockMvc;

    private static final String ADMIN = "ADMIN";

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeAll
    static void beforeAll(@Autowired DataSource dataSource,
                          @Autowired WebApplicationContext applicationContext) throws SQLException {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(applicationContext)
                .apply(SecurityMockMvcConfigurers.springSecurity())
                .build();
        teardown(dataSource);
        try (Connection connection = dataSource.getConnection()) {
            connection.setAutoCommit(true);
            ScriptUtils.executeSqlScript(connection,
                    new ClassPathResource("database/books/remove-book-from-books-table.sql"));
        }
    }

    @AfterAll
    static void afterAll(@Autowired DataSource dataSource) {
        teardown(dataSource);
    }

    @SneakyThrows
    static void teardown(DataSource dataSource) {
        try (Connection connection = dataSource.getConnection()) {
            connection.setAutoCommit(true);
            ScriptUtils.executeSqlScript(connection,
                    new ClassPathResource("database/books/remove-book-from-books-table.sql"));
        }
    }

    @Test
    @WithMockUser(roles = {ADMIN})
    @Sql(scripts = "classpath:database/category/add-categories-in-table.sql",
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @DisplayName("Create a new book")
    public void createBook_ValidRequestDto_Success() throws Exception {
        CreateBookRequestDto requestDto = getBookRequestDto();
        BookResponseDto responseDto = getBookResponseDto();

        String jsonRequest = objectMapper.writeValueAsString(requestDto);

        MvcResult result = mockMvc.perform(
                        post("/books")
                                .content(jsonRequest)
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andReturn();
        BookResponseDto actual = objectMapper.readValue(
                result.getResponse().getContentAsString(), BookResponseDto.class);

        assertNotNull(actual);

        EqualsBuilder.reflectionEquals(responseDto, actual, "id");
    }

    @Test
    @WithMockUser
    @Sql(scripts = "classpath:database/books/add-book-to-books-table.sql",
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @DisplayName("Get a book by id")
    public void getBookById_ValidRequestDto_ReturnBookById() throws Exception {
        MvcResult result = mockMvc.perform(
                            get("/books/1")
                                    .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andReturn();

        BookResponseDto actual = objectMapper.readValue(
                    result.getResponse().getContentAsString(), BookResponseDto.class);

        assertNotNull(actual);
        assertEquals(1L, actual.getId());
        assertEquals("Title1", actual.getTitle());
    }

    private CreateBookRequestDto getBookRequestDto() {
        return new CreateBookRequestDto()
                .setAuthor("Author")
                .setIsbn("0123456789")
                .setTitle("Title")
                .setPrice(BigDecimal.valueOf(29.99))
                .setDescription("New book")
                .setCoverImage("image.jpg")
                .setCategoryIds(Set.of(1L,2L)
                );
    }

    private BookResponseDto getBookResponseDto() {
        return new BookResponseDto()
                .setId(1L)
                .setAuthor("Author")
                .setIsbn("0123456789")
                .setTitle("Title")
                .setPrice(BigDecimal.valueOf(29.99))
                .setDescription("New book")
                .setCoverImage("image.jpg")
                .setCategoryIds(Set.of(1L,2L)
                );
    }
}
