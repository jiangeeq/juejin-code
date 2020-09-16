package com.jls;

import com.jls.service.ProductService;
import com.jls.service.ProductServiceImpl;

/**
 * @author jiangpeng
 * @date 2020/4/1318:13
 */
public class CustomClient {
    public static void main(String[] args) throws IllegalAccessException {
        ProductService productService = new ProductServiceImpl();
        ProductService proxy = (ProductService) new CustomInfvocationdHandler().getInstance(productService);
        proxy.addProduct("apple");
    }
}
