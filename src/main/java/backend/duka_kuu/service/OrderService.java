package backend.duka_kuu.service;

import backend.duka_kuu.domain.AppUser;
import backend.duka_kuu.domain.Order;
import backend.duka_kuu.model.OrderDTO;
import backend.duka_kuu.model.OrderLineItemsDTO;
import backend.duka_kuu.repos.AppUserRepo;
import backend.duka_kuu.repos.OrderLineItemsRepository;
import backend.duka_kuu.repos.OrderRepository;
import java.util.List;
import java.util.stream.Collectors;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;


@Service @Slf4j
public class OrderService {

    private final OrderRepository orderRepository;
    //private final AppUserRepo appUserRepository;
    private final OrderLineItemsRepository orderLineItemsRepo;

    private List<OrderLineItemsDTO> orderLineItemsDTOList;

//    public OrderService(final OrderRepository orderRepository,
//            final AppUserRepo appUserRepository) {
//        this.orderRepository = orderRepository;
//        this.appUserRepository = appUserRepository;
//    }
    public OrderService(final OrderRepository orderRepository, final OrderLineItemsRepository orderLineItemsRepo) {
        this.orderRepository = orderRepository;
        this.orderLineItemsRepo = orderLineItemsRepo;
        //this.appUserRepository = appUserRepository;
    }

    public List<OrderDTO> findAll() {
        return orderRepository.findAll(Sort.by("id"))
                .stream()
                .map(order -> mapToDTO(order, new OrderDTO()))
                .collect(Collectors.toList());
    }

    public OrderDTO get(final Long id) {
        return orderRepository.findById(id)
                .map(order -> mapToDTO(order, new OrderDTO()))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    public Long placeOrder(final OrderDTO orderDTO) {
        log.info("saving user order");
        final Order order = new Order();
        mapToEntity(orderDTO, order);
        return orderRepository.save(order).getId();
    }

    public void update(final Long id, final OrderDTO orderDTO) {
        final Order order = orderRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        mapToEntity(orderDTO, order);
        orderRepository.save(order);
    }

    public void delete(final Long id) {
        orderRepository.deleteById(id);
    }

    private OrderDTO mapToDTO(final Order order, final OrderDTO orderDTO) {
        orderDTO.setId(order.getId());
        orderDTO.setOrderNumber(order.getOrderNumber());
        //orderDTO.setUser(order.getAppUser() == null ? null : order.getAppUser().getId());
        return orderDTO;
    }

    private Order mapToEntity(final OrderDTO orderDTO, final Order order) {
        order.setOrderNumber(orderDTO.getOrderNumber());
//        final AppUser user = orderDTO.getUser() == null ? null : appUserRepository.findById(orderDTO.getUser())
//                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "customer not found"));
//        order.setAppUser(user);
        return order;
    }
}
