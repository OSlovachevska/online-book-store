package slovachevska.onlinebookstore.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.sql.Connection;
import java.sql.SQLException;
import javax.sql.DataSource;
import lombok.SneakyThrows;
import org.junit.jupiter.api.AfterEach;
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
import slovachevska.onlinebookstore.dto.category.CategoryRequestDto;
import slovachevska.onlinebookstore.dto.category.CategoryResponseDto;
import slovachevska.onlinebookstore.model.Category;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)

class CategoryControllerTest {

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

    @AfterEach
    void afterEach(@Autowired DataSource dataSource) {
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
    @DisplayName("Create a new category")
    public void createCategory_ValidRequestDto_Success() throws Exception {
        CategoryRequestDto requestDto = getCategoryRequestDto();
        CategoryResponseDto responseDto = getCategoryResponseDto();

        String jsonRequest = objectMapper.writeValueAsString(requestDto);

        MvcResult result = mockMvc.perform(
                        post("/categories")
                                .content(jsonRequest)
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andReturn();
        CategoryResponseDto actual = objectMapper.readValue(
                result.getResponse().getContentAsString(), CategoryResponseDto.class);

        assertNotNull(actual);

        EqualsBuilder.reflectionEquals(responseDto, actual, "id");
    }

    @Test
    @WithMockUser
    @Sql(scripts = "classpath:database/category/add-categories-in-table.sql",
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @DisplayName("Get a category by id")
    public void getCategoryById_ValidRequestDto_ReturnCategoryById() throws Exception {
        MvcResult result = mockMvc.perform(
                        get("/categories/1")
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        CategoryResponseDto actual = objectMapper.readValue(
                result.getResponse().getContentAsString(), CategoryResponseDto.class);

        assertNotNull(actual);
        assertEquals(1L, actual.getId());
        assertEquals("poem", actual.getName());
    }

    @Test
    @WithMockUser(roles = ADMIN)
    @DisplayName("Delete category by id")
    @Sql(scripts = "classpath:database/category/add-categories-in-table.sql",
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    public void deleteCategory_ValidId_CategoryMustNotBeInDb() throws Exception {
        mockMvc.perform(delete("/categories/3")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent())
                .andReturn();
    }

    private Category getCategory() {
        return new Category()
                .setName("Category")
                .setDescription("Description");
    }

    private CategoryResponseDto getCategoryResponseDto() {
        return new CategoryResponseDto()
                .setId(1L)
                .setName("Category")
                .setDescription("Description");

    }

    private CategoryRequestDto getCategoryRequestDto() {
        return new CategoryRequestDto()
                .setName("Category")
                .setDescription("Description");
    }

}
