package com.jls;

import com.jls.MyProxy;
import com.jls.service.ProductService;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author jiangpeng
 * @date 2020/4/1318:08
 */
public class MyInfvocationdHandler implements InvocationHandler {
    private ProductService target;

    public Object getInstance(ProductService target) throws IllegalAccessException {
        try {
            this.target = target;
            return MyProxy.newProxyInstance(new MyClassLoader(), target.getClass().getInterfaces(), this);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String currentDate  = simpleDateFormat.format(new Date());
        System.out.println("日期【"+currentDate + "】添加了一款产品");

        return method.invoke(this.target,args);
    }
}
