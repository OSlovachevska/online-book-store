package slovachevska.onlinebookstore.service.order;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import slovachevska.onlinebookstore.dto.order.OrderItemResponseDto;
import slovachevska.onlinebookstore.dto.order.OrderResponseDto;
import slovachevska.onlinebookstore.dto.order.ShippingAddressRequestDto;
import slovachevska.onlinebookstore.dto.order.UpdateStatusRequestDto;
import slovachevska.onlinebookstore.mapper.OrderItemMapper;
import slovachevska.onlinebookstore.mapper.OrderMapper;
import slovachevska.onlinebookstore.model.CartItem;
import slovachevska.onlinebookstore.model.Order;
import slovachevska.onlinebookstore.model.OrderItem;
import slovachevska.onlinebookstore.model.OrderStatus;
import slovachevska.onlinebookstore.model.ShoppingCart;
import slovachevska.onlinebookstore.model.User;
import slovachevska.onlinebookstore.repository.cart.CartItemRepository;
import slovachevska.onlinebookstore.repository.cart.ShoppingCartRepository;
import slovachevska.onlinebookstore.repository.order.OrderItemRepository;
import slovachevska.onlinebookstore.repository.order.OrderRepository;

@RequiredArgsConstructor
@Service
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;

    private final OrderMapper orderMapper;

    private final OrderItemRepository orderItemRepository;

    private final OrderItemMapper orderItemMapper;

    private final ShoppingCartRepository shoppingCartRepository;

    private final CartItemRepository cartItemRepository;

    @Override
    public OrderResponseDto createOrder(String username,
                                        ShippingAddressRequestDto shippingAddressRequestDto) {

        ShoppingCart shoppingCart = shoppingCartRepository.findShoppingCartByUserEmail(username);
        if (shoppingCart.getCartItems().isEmpty()) {
            throw new RuntimeException("Your shopping cart is empty");
        }

        Order order = getOrderFromDb(shoppingCart.getUser(), shippingAddressRequestDto);
        Set<OrderItem> orderItems = new HashSet<>();
        double total = 0;

        for (CartItem cartItem : shoppingCart.getCartItems()) {
            OrderItem orderItem = getOrderItemFromDb(cartItem, order);
            orderItems.add(orderItem);
            total += orderItem.getPrice().doubleValue() * orderItem.getQuantity();
            cartItemRepository.delete(cartItem);
        }
        shoppingCart.setCartItems(Collections.emptySet());
        shoppingCartRepository.save(shoppingCart);

        order.setTotal(BigDecimal.valueOf(total));
        order.setOrderItems(orderItems);
        return orderMapper.toDto(orderRepository.save(order));
    }

    @Override
    public Set<OrderResponseDto> getAllOrdersByUsername(String username) {
        return orderRepository.findAllByUserEmail(username).stream()
                .map(orderMapper::toDto)
                .collect(Collectors.toSet());
    }

    @Override
    public OrderResponseDto updateOrderStatus(Long orderid,
                                              UpdateStatusRequestDto updateStatusRequestDto) {
        Order order = orderRepository.getReferenceById(orderid);
        order.setStatus(OrderStatus.valueOf(updateStatusRequestDto.getStatus()));
        return orderMapper.toDto(orderRepository.save(order));
    }

    @Override
    public Set<OrderItemResponseDto> getOrderItemsByOrderId(Long orderid) {
        return orderItemRepository.findAllByOrderId(orderid).stream()
                .map(orderItemMapper::toDto)
                .collect(Collectors.toSet());
    }

    @Override
    public OrderItemResponseDto getOrderItemById(Long orderid) {
        return orderItemMapper.toDto(orderItemRepository.getReferenceById(orderid));
    }

    @Override
    public void doesUserHasThisOrder(String username, Long orderid) {
        if (!orderRepository.getReferenceById(orderid).getUser().getEmail().equals(username)) {
            throw new RuntimeException("You don`t have sush an order!");

        }
    }

    private Order getOrderFromDb(User user, ShippingAddressRequestDto requestDto) {
        Order order = new Order();
        order.setUser(user);
        order.setStatus(OrderStatus.PENDING);
        order.setOrderDate(LocalDateTime.now());
        order.setShippingAddress(requestDto.getShippingAddress());
        order.setTotal(BigDecimal.ZERO);
        return orderRepository.save(order);
    }

    private OrderItem getOrderItemFromDb(CartItem cartItem, Order order) {
        OrderItem orderItem = new OrderItem();
        orderItem.setBook(cartItem.getBook());
        orderItem.setQuantity(cartItem.getQuantity());
        orderItem.setPrice(cartItem.getBook().getPrice());
        orderItem.setOrder(order);
        return orderItemRepository.save(orderItem);
    }

}
