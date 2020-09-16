package com.jsl;
import java.lang.reflect.Method;
public class $MyProxy0 implements com.jls.service.ProductService {
    GuituInvocationHandler guituInvocationHandler;
    public $MyProxy0(GuituInvocationHandler guituInvocationHandler) { 
        this.guituInvocationHandler = guituInvocationHandler;
    }
    public void addProduct(java.lang.String arg0) {
        try{
            Method method = com.jls.service.ProductService.class.getMethod("addProduct",new Class[]{java.lang.String.java});
            this.guituInvocationHandler.invoke(this, method, null);
        }catch(Throwable e){
            e.printStackTrace();
        }
    }
}
