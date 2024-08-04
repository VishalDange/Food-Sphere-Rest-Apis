package com.foodsphere.Controller;

import com.foodsphere.model.CartItem;
import com.foodsphere.model.Order;
import com.foodsphere.model.User;
import com.foodsphere.request.AddCartItemRequest;
import com.foodsphere.request.OrderRequest;
import com.foodsphere.service.OrderService;
import com.foodsphere.service.UserService;
import org.aspectj.weaver.ast.Or;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private UserService userService;

    @PostMapping("/order")
    public ResponseEntity<Order> createOrder(@RequestBody OrderRequest request, @RequestHeader("Authorization") String jwt) throws Exception {

        User user=userService.findUserByJwtToken(jwt);
        Order order=orderService.createOrder(request,user);

        return new ResponseEntity<>(order, HttpStatus.CREATED);

    }

    @GetMapping("/order/user")
    public ResponseEntity<List<Order>> getOrderHistory(@RequestHeader("Authorization") String jwt) throws Exception {

        User user=userService.findUserByJwtToken(jwt);
        List<Order> order=orderService.getUsersOrder(user.getId());

        return new ResponseEntity<>(order, HttpStatus.OK);

    }

}
