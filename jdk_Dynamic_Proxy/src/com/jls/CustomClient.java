import service.ProductService;
import service.ProductServiceImpl;

/**
 * @author jiangpeng
 * @date 2020/4/1318:13
 */
public class CustomClient {
    public static void main(String[] args) throws IllegalAccessException {
        ProductService productService = new ProductServiceImpl();
        ProductService proxy = (ProductService) new MyInvocationHandler().getInstance(productService);
        proxy.addProduct("apple");
    }
}
