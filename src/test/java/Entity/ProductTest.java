package Entity;

import Entity.Product.Category;
import org.junit.Assert;
import org.junit.Test;

public class ProductTest {
    @Test
    public void testProduct() {
        Product product = new Product("1234", Category.Candies, "test", 10, 3);
        Assert.assertEquals(10, product.getRemain());
        Assert.assertEquals(3, product.getPrice());
        Assert.assertEquals("test", product.getProduct());
        Assert.assertEquals(Category.Candies, product.getCategory());
        Assert.assertEquals("1234", product.getId());
        Assert.assertEquals(0, product.getTotalSold());

        product.setPrice(1);
        product.setRemain(100);
        product.setProduct("aaa");

        Assert.assertEquals(100, product.getRemain());
        Assert.assertEquals(1, product.getPrice());
        Assert.assertEquals("aaa", product.getProduct());
    }
}
