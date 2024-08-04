package com.foodsphere.service.impl;

import com.foodsphere.model.Cart;
import com.foodsphere.model.CartItem;
import com.foodsphere.model.Food;
import com.foodsphere.model.User;
import com.foodsphere.repository.CartItemRepository;
import com.foodsphere.repository.CartRepository;
import com.foodsphere.repository.FoodRepository;
import com.foodsphere.request.AddCartItemRequest;
import com.foodsphere.service.CartService;
import com.foodsphere.service.FoodService;
import com.foodsphere.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CartServiceImpl implements CartService {

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private CartItemRepository cartItemRepository;

    @Autowired
    private FoodRepository foodRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private FoodService foodService;

    @Override
    public CartItem addItemToCart(AddCartItemRequest request, String jwt) throws Exception {

        User user=userService.findUserByJwtToken(jwt);

        Food food=foodService.findFoodById(request.getFoodId());

        Cart cart=cartRepository.findByCustomerId(user.getId());

        for(CartItem cartItem:cart.getItem()){
            if(cartItem.getFood().equals(food)){
                int newQuantity=cartItem.getQuantity()+request.getQuantity();

                return updateCartItemQuantity(cartItem.getId(),newQuantity);
            }
        }

        CartItem newCartItem=new CartItem();
        newCartItem.setFood(food);
        newCartItem.setQuantity(request.getQuantity());
        newCartItem.setCart(cart);
        newCartItem.setIngredients(request.getIngredients());
        newCartItem.setTotalPrice(request.getQuantity()*food.getPrice());

        CartItem savedCardItem=cartItemRepository.save(newCartItem);

        cart.getItem().add(savedCardItem);
        return savedCardItem;
    }

    @Override
    public CartItem updateCartItemQuantity(Long cartItemId, int quantity) throws Exception {
        Optional<CartItem> cartItem=cartItemRepository.findById(cartItemId);
        if(cartItem.isEmpty()){
            throw new Exception("cart item not Found");
        }

        CartItem item=cartItem.get();
        item.setQuantity(quantity);
        item.setTotalPrice(item.getFood().getPrice()*quantity);
        return cartItemRepository.save(item);
    }

    @Override
    public Cart removeItemFromCart(Long cartItemId, String jwt) throws Exception {
        User user=userService.findUserByJwtToken(jwt);

        Cart cart=cartRepository.findByCustomerId(user.getId());

        Optional<CartItem> cartItemOptional=cartItemRepository.findById(cartItemId);
        if(cartItemOptional.isEmpty()){
            throw new Exception("cart item not Found");
        }

        CartItem item=cartItemOptional.get();

        cart.getItem().remove(item);
        return cartRepository.save(cart);
    }

    @Override
    public Long calculateCartTotals(Cart cart) throws Exception {

        Long total=0L;

        for(CartItem cartItem:cart.getItem()){
            total+=cartItem.getFood().getPrice()*cartItem.getQuantity();
        }
        return total;
    }

    @Override
    public Cart findCartById(Long id) throws Exception {
        Optional<Cart> cart=cartRepository.findById(id);
        if(cart.isEmpty()){
            throw new Exception("Cart not found with id :"+id);
        }
        return cart.get();
    }

    @Override
    public Cart findCartByUserId(Long userId) throws Exception {

//        User user=userService.findUserByJwtToken(userId);
        Cart cart=cartRepository.findByCustomerId(userId);
        cart.setTotal(calculateCartTotals(cart));
        return cart;
    }

    @Override
    public Cart clearCart(Long userId) throws Exception {

//        User user=userService.findUserByJwtToken(jwt);

        Cart cart=findCartByUserId(userId);
        cart.getItem().clear();
        return cartRepository.save(cart);
    }
}
