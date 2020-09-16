package com.guitu18.study.proxy.guitu;
import java.lang.reflect.Method;
public class $GuituProxy0 implements service.ProductService {
    GuituInvocationHandler guituInvocationHandler;
    public $GuituProxy0(GuituInvocationHandler guituInvocationHandler) { 
        this.guituInvocationHandler = guituInvocationHandler;
    }
    public void addProduct(java.lang.String arg0) {
        try{
            Method method = service.ProductService.class.getMethod("addProduct",new Class[]{java.lang.String.class});
            this.guituInvocationHandler.invoke(this, method, null);
        }catch(Throwable e){
            e.printStackTrace();
        }
    }
}
