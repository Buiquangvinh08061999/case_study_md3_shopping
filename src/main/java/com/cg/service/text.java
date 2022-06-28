package com.cg.service;

import com.cg.model.Product;
import com.cg.model.User;

import java.util.List;

public class text {
    public static void main(String[] args) {
//        UserService userService = new UserService();
//        User u = userService.findById(47);
//        System.out.println(u);


        ProductService productService = new ProductService();
        List<Product> o = productService.findAll();
        for(Product item : o ){
            System.out.println(item);
        }


    }


}
