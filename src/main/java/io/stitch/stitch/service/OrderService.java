package io.stitch.stitch.service;

import io.stitch.stitch.constants.OrderStatus;
import io.stitch.stitch.dto.requets.OrderRequest;
import io.stitch.stitch.dto.response.OrderResponse;
import io.stitch.stitch.entity.*;
import io.stitch.stitch.repos.MachineRepository;
import io.stitch.stitch.repos.OrderRepository;
import io.stitch.stitch.repos.SpearPartRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final PrimarySequenceService primarySequenceService;
    private final MachineRepository machineRepository;
    private final SpearPartRepository spearPartRepository;


    public OrderService(OrderRepository orderRepository, PrimarySequenceService primarySequenceService, MachineRepository machineRepository, SpearPartRepository spearPartRepository) {
        this.orderRepository = orderRepository;
        this.primarySequenceService = primarySequenceService;
        this.machineRepository = machineRepository;
        this.spearPartRepository = spearPartRepository;
    }

    public Long createOrder(OrderRequest orderRequest) {
        return orderRepository.save(mapRequestToEntity(orderRequest)).getId();
    }

    public List<OrderResponse> getOrdersByStatus(OrderStatus orderStatus) {
        List<Order> orders = orderRepository.findAllByStatus(orderStatus);
        return orders.parallelStream()
                .map(this::mapEntityToResponse)
                .toList();
    }

    public List<OrderResponse> getAllOrders(final Integer offset, final Integer limit) {
        List<Order> orders = new ArrayList<>();
        if (offset.equals(-1)) {
            orders = orderRepository.findAll(Sort.by("id"));
        } else {
            orders = orderRepository.findAll(PageRequest.of(offset, limit)).getContent();
        }
        return orders.parallelStream()
                .map(this::mapEntityToResponse)
                .toList();
    }

    public List<OrderResponse> getAllOrdersByCreatedDate(final LocalDate createdDate) {
        List<Order> orders = orderRepository.findAllByCreateAt(createdDate);
        return orders.parallelStream()
                .map(this::mapEntityToResponse)
                .toList();
    }

    public List<OrderResponse> getAllOrdersByPhoneNumber(final String phoneNumber) {
        List<Order> orders = orderRepository.findAllByPhoneNumber(phoneNumber);
        return orders.parallelStream()
                .map(this::mapEntityToResponse)
                .toList();
    }

    public OrderResponse getOrderById(Long id) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Order not found"));
        return mapEntityToResponse(order);
    }

    public Long updateOrderStatus(final Long orderId, final OrderStatus orderStatus) {
        Optional<Order> order = orderRepository.findById(orderId);
        if (order.isPresent()) {
            order.get().setStatus(orderStatus);
            orderRepository.save(order.get());
        }
        return orderId;
    }

    private Order mapRequestToEntity(final OrderRequest orderRequest) {
        if (orderRequest == null) throw new IllegalArgumentException("orderRequest cannot be null");
        double totalPrice = 0;
        Order order = new Order();
        if (Objects.nonNull(orderRequest.getMachines())) {
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
                    .toList();
            totalPrice += orderItems.stream()
                    .mapToDouble(item -> item.getMachine().getFinalPrice() * item.getQuantity())
                    .sum();
            order.setMachines(orderItems);
        }

        if (Objects.nonNull(orderRequest.getSpearParts())) {
            Map<Long, Long> spearPartsIdCounts = orderRequest.getSpearParts().stream()
                    .collect(Collectors.groupingBy(id -> id, Collectors.counting()));

            List<SpearPart> uniqueSpearParts = spearPartRepository.findByIdIn(new ArrayList<>(spearPartsIdCounts.keySet()));

            List<SpearPartOrderItem> spearPartOrderItems = uniqueSpearParts.stream()
                    .map(spearPart -> {
                        SpearPartOrderItem spearPartOrderItem = new SpearPartOrderItem();
                        spearPartOrderItem.setSpearPart(spearPart);
                        spearPartOrderItem.setQuantity(spearPartsIdCounts.get(spearPart.getId()).intValue());
                        return spearPartOrderItem;
                    })
                    .toList();
            totalPrice += spearPartOrderItems.stream()
                    .mapToDouble(item -> item.getSpearPart().getPrice() * item.getQuantity())
                    .sum();
            order.setSpearParts(spearPartOrderItems);
        }

        order.setCreateAt(LocalDate.now());

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
        OrderResponse orderResponse = new OrderResponse();
        if (Objects.nonNull(order.getMachines())) {
            List<String> machineNames = order.getMachines().stream()
                    .map(item -> item.getMachine().getBrand().getName() + " " + item.getMachine().getModel() +
                            " (x" + item.getQuantity() + ")")
                    .toList();
            orderResponse.setMachines(machineNames);
        }

        if (Objects.nonNull(order.getSpearParts())) {
            List<String> spearPartNames = order.getSpearParts().stream()
                    .map(item -> item.getSpearPart().getName() + " " +
                            " (x" + item.getQuantity() + ")")
                    .toList();
            orderResponse.setSpearParts(spearPartNames);

        }

        orderResponse.setCreateDate(order.getCreateAt());
        orderResponse.setOrderId(order.getId());
        orderResponse.setPrice(order.getPrice());
        orderResponse.setFullName(order.getFullName());
        orderResponse.setAddress(order.getAddress());
        orderResponse.setPhoneNumber(order.getPhoneNumber());
        orderResponse.setStatus(order.getStatus());

        return orderResponse;
    }

}
