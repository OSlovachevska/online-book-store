package slovachevska.onlinebookstore.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.jdbc.Sql;
import slovachevska.onlinebookstore.model.ShoppingCart;
import slovachevska.onlinebookstore.repository.cart.ShoppingCartRepository;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class ShoppingCartRepositoryTest {

    @Autowired
    private ShoppingCartRepository shoppingCartRepository;

    @Test
    @DisplayName("Find shopping cart by user email")
    @Sql(scripts = {
            "classpath:database/add-users-and-shopping-carts-to-tables.sql"
    }, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = {
            "classpath:database/remove-users-and-shopping-carts-from-tables.sql"
    }, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void findShoppingCartByUserEmail_ReturnValidRequestDto() {

        ShoppingCart shoppingCart = shoppingCartRepository
                .findShoppingCartByUserEmail("user123@gmail.com");
        assertNotNull(shoppingCart);
        assertEquals(1L, shoppingCart.getId());
        assertEquals(1L, shoppingCart.getUser().getId());

    }

}
