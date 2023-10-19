package slovachevska.onlinebookstore.controller;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.sql.Connection;
import java.sql.SQLException;
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
import slovachevska.onlinebookstore.dto.cart.CartItemCreateRequestDto;
import slovachevska.onlinebookstore.dto.cart.CartItemResponseDto;
import slovachevska.onlinebookstore.dto.cart.ShoppingCartResponseDto;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ShoppingCartControllerTest {

    protected static MockMvc mockMvc;

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
                    new ClassPathResource(
                            "database/remove-cart-items"
                                    + "-books-shopping-carts-users-from-tables.sql"));
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
                    new ClassPathResource(
                            "database/remove-cart-items"
                                    + "-books-shopping-carts-users-from-tables.sql"));
        }
    }

    @Test
    @WithMockUser(username = "user234@email.com")
    @DisplayName("Add a book to shopping cart")
    @Sql(scripts = "classpath:database/add-users-shopping-carts-books-and-cart-items-to-tables.sql",
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    public void addBookToShoppingCart_ValidEmailAndCartItem_ReturnsShoppingCartDto()
            throws Exception {
        CartItemCreateRequestDto cartItemCreateRequestDto = getCartItemCreateRequestDto();
        CartItemResponseDto responseDto = getCartItemResponseDto().setId(2L);
        String jsonRequest = objectMapper.writeValueAsString(cartItemCreateRequestDto);

        MvcResult result = mockMvc.perform(
                        post("/cart")
                                .content(jsonRequest)
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        ShoppingCartResponseDto actual = objectMapper.readValue(
                result.getResponse().getContentAsString(), ShoppingCartResponseDto.class);

        assertNotNull(actual);
        assertTrue(actual.getCartItems().contains(responseDto));
    }

    private CartItemResponseDto getCartItemResponseDto() {
        return new CartItemResponseDto()
                .setId(1L)
                .setBookId(1L)
                .setBookTitle("First Book")
                .setQuantity(3);
    }

    private CartItemCreateRequestDto getCartItemCreateRequestDto() {
        return new CartItemCreateRequestDto()
                .setBookId(1L)
                .setQuantity(3);
    }
}

