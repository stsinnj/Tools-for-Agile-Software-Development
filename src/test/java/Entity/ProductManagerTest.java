package Entity;

import Entity.Product.Category;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.PrintStream;
import java.util.List;
import java.util.Map;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class ProductManagerTest {

    @Before
    public void setUp() throws Exception {
        ProductManager.getInstance().loadProductFromFile("src/main/resources/Product.csv");
    }

    @Test
    public void testLoadProduct() {
        Map<Category, List<Product>> productMap = ProductManager.getInstance().getProductMap();
        Assert.assertEquals(4, productMap.size());
        Assert.assertEquals(5, productMap.get(Category.Drinks).size());
        Assert.assertEquals(4, productMap.get(Category.Chocolates).size());
        Assert.assertEquals(4, productMap.get(Category.Chips).size());
        Assert.assertEquals(3, productMap.get(Category.Candies).size());

        Product product = productMap.get(Category.Drinks).get(0);

        Assert.assertEquals("1001", product.getId());
        Assert.assertEquals("Mineral Water", product.getProduct());
        Assert.assertEquals(Category.Drinks, product.getCategory());
        Assert.assertEquals(3, product.getPrice());
        Assert.assertEquals(7, product.getRemain());

        product.addSold(3);
        Assert.assertEquals(3, product.getTotalSold());
        Assert.assertEquals(4, product.getRemain());

        product.addRemain(3);
        Assert.assertEquals(7, product.getRemain());

    }

    @Test
    public void testShowProduct() {
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        ProductManager.getInstance().showCategoryAndProducts();

        Assert.assertTrue(outContent.toString().length() > 0);
    }

    @Test
    public void testDuplicateCheck() {
        ProductManager pm = ProductManager.getInstance();
        Assert.assertTrue(pm.isNameOrIdDuplicate(Category.Drinks, "a", "1001"));
        Assert.assertTrue(pm.isNameOrIdDuplicate(Category.Drinks, "Sprite", "1033"));
    }

    @Test
    public void testAddProduct() throws Exception {
        ProductManager pm = ProductManager.getInstance();
        Product product = new Product("2001", Category.Drinks, "test", 10, 1);
        Assert.assertEquals(pm.getProductMap().get(Category.Drinks).size(), 5);
        ProductManager.getInstance().addProduct(product);
        Assert.assertEquals(pm.getProductMap().get(Category.Drinks).size(), 6);
    }

    @Test
    public void testSoldReport() {
        ProductManager pm = ProductManager.getInstance();
        File soldFile = new File(ProductManager.SALE_FILE);
        if (soldFile.exists()) {
            soldFile.delete();
        }

        pm.saveSaleToFile();
        Assert.assertTrue(soldFile.exists());
    }
}
