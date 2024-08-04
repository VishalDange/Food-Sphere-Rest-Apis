package com.foodsphere.Controller;

import com.foodsphere.model.Order;
import com.foodsphere.model.User;
import com.foodsphere.request.OrderRequest;
import com.foodsphere.service.OrderService;
import com.foodsphere.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
public class AdminOrderController {
    @Autowired
    private OrderService orderService;

    @Autowired
    private UserService userService;

    @GetMapping("/order/restaurant/{id}")
    public ResponseEntity<List<Order>> getOrderHistory(@PathVariable Long id,@RequestParam(required = false) String orderStatus ,@RequestHeader("Authorization") String jwt) throws Exception {

        User user=userService.findUserByJwtToken(jwt);
        List<Order> order=orderService.getRestaurantsOrder(id,orderStatus);

        return new ResponseEntity<>(order, HttpStatus.OK);

    }

    @PutMapping("/order/{orderId}/{orderStatus}")
    public ResponseEntity<Order> updateOrderStatus(@PathVariable Long id,@PathVariable String orderStatus,@RequestHeader("Authorization") String jwt) throws Exception {

        User user=userService.findUserByJwtToken(jwt);
        Order order=orderService.updateOrder(id,orderStatus);

        return new ResponseEntity<>(order, HttpStatus.OK);

    }
}
