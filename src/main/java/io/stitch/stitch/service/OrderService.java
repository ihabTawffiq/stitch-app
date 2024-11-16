package io.stitch.stitch.service;

import io.stitch.stitch.constants.OrderStatus;
import io.stitch.stitch.dto.requets.OrderRequest;
import io.stitch.stitch.dto.response.OrderResponse;
import io.stitch.stitch.entity.Machine;
import io.stitch.stitch.entity.Order;
import io.stitch.stitch.entity.OrderItem;
import io.stitch.stitch.repos.MachineRepository;
import io.stitch.stitch.repos.OrderRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final PrimarySequenceService primarySequenceService;
    private final MachineRepository machineRepository;


    public OrderService(OrderRepository orderRepository, PrimarySequenceService primarySequenceService, MachineRepository machineRepository) {
        this.orderRepository = orderRepository;
        this.primarySequenceService = primarySequenceService;
        this.machineRepository = machineRepository;
    }

    public Long createOrder(OrderRequest orderRequest) {
        return orderRepository.save(mapRequestToEntity(orderRequest)).getId();
    }

    public List<OrderResponse> getOrdersByStatus(OrderStatus orderStatus){
        List<Order> orders = orderRepository.findAllByStatus(orderStatus);
        return orders.parallelStream()
                .map(this::mapEntityToResponse)
                .toList();
    }

    public List<OrderResponse> getAllOrders(final Integer offset,final Integer limit){
        List<Order> orders = new ArrayList<>();
        if(offset.equals(-1)){
            orders = orderRepository.findAll(Sort.by("id"));
        }else {
            orders = orderRepository.findAll(PageRequest.of(offset, limit)).getContent();
        }
        return orders.parallelStream()
                .map(this::mapEntityToResponse)
                .toList();
    }

    public List<OrderResponse> getAllOrdersByCreatedDate(final LocalDate createdDate){
        List<Order> orders = orderRepository.findAllByCreateAt(createdDate);
        return orders.parallelStream()
                .map(this::mapEntityToResponse)
                .toList();
    }

    public List<OrderResponse> getAllOrdersByPhoneNumber(final String phoneNumber){
        List<Order> orders = orderRepository.findAllByPhoneNumber(phoneNumber);
        return orders.parallelStream()
                .map(this::mapEntityToResponse)
                .toList();
    }

    public OrderResponse getOrderById(Long id){
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Order not found"));
        return mapEntityToResponse(order);
    }
    public Long updateOrderStatus(final Long orderId ,final OrderStatus orderStatus){
        Optional<Order> order = orderRepository.findById(orderId);
        if(order.isPresent()){
            order.get().setStatus(orderStatus);
            orderRepository.save(order.get());
        }
        return orderId;
    }

    private Order mapRequestToEntity(final OrderRequest orderRequest) {
        if (orderRequest == null) throw new IllegalArgumentException("orderRequest cannot be null");
        if (orderRequest.getMachines() == null || orderRequest.getMachines().isEmpty())
            throw new IllegalArgumentException("orderRequest machines cannot be null or empty");

        Map<Long, Long> machineIdCounts = orderRequest.getMachines().stream()
                .collect(Collectors.groupingBy(id -> id, Collectors.counting()));

        List<Machine> uniqueMachines = machineRepository.findByIdIn(new ArrayList<>(machineIdCounts.keySet()));

        List<OrderItem> orderItems = uniqueMachines.stream()
                .map(machine -> {
                    OrderItem orderItem = new OrderItem();
                    orderItem.setMachine(machine);
                    orderItem.setQuantity(machineIdCounts.get(machine.getId()).intValue());
                    return orderItem;
                })
                .collect(Collectors.toList());

        double totalPrice = orderItems.stream()
                .mapToDouble(item -> item.getMachine().getFinalPrice() * item.getQuantity())
                .sum();

        Order order = new Order();
        order.setCreateAt(LocalDate.now());
        order.setMachines(orderItems);
        order.setPrice(totalPrice);
        order.setId(primarySequenceService.getNextValue());
        order.setAddress(orderRequest.getAddress());
        order.setFullName(orderRequest.getFullName());
        order.setStatus(OrderStatus.ORDERED);
        order.setPhoneNumber(orderRequest.getPhoneNumber());

        return order;
    }


    private OrderResponse mapEntityToResponse(final Order order) {
        if (order == null) throw new IllegalArgumentException("order cannot be null");

        List<String> machineNames = order.getMachines().stream()
                .map(item -> item.getMachine().getBrand().getName() + " " + item.getMachine().getModel() +
                        " (x" + item.getQuantity() + ")")
                .toList();

        OrderResponse orderResponse = new OrderResponse();
        orderResponse.setCreateDate(order.getCreateAt());
        orderResponse.setOrderId(order.getId());
        orderResponse.setPrice(order.getPrice());
        orderResponse.setFullName(order.getFullName());
        orderResponse.setAddress(order.getAddress());
        orderResponse.setPhoneNumber(order.getPhoneNumber());
        orderResponse.setMachines(machineNames);
        orderResponse.setStatus(order.getStatus());

        return orderResponse;
    }

}
