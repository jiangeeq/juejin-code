package com.jls.service;

/**
 * @author jiangpeng
 * @date 2020/4/1318:14
 */
public class ProductServiceImpl implements ProductService{
    @Override
    public void addProduct(String productName) {
        System.out.println("正在添加"+productName);
    }
}
