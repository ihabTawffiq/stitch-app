package io.stitch.stitch.repos;

import io.stitch.stitch.constants.OrderStatus;
import io.stitch.stitch.entity.Order;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;


@Repository
public interface OrderRepository extends MongoRepository<Order, Long> {
    List<Order> findAllByStatus(OrderStatus status);
    List<Order> findAllByCreateAt(LocalDate createAt);
    List<Order> findAllByPhoneNumber(String phoneNumber);
}
