package Entity;

import Controller.MainController;
import Controller.UserController;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

public class CartManagerTest {

    private CartManager cartManager;

    @Before
    public void beforeInit() {
        this.cartManager = CartManager.getInstance();
    }

    @Test
    public void testAddCart() {
        Assert.assertTrue(cartManager.addCartItem("user", "1001", 1));
        Assert.assertTrue(cartManager.addCartItem("user", "1001", 1));
        Assert.assertFalse(cartManager.addCartItem("user", "4434634", 10));
    }

    @Test
    public void testGetCart() {
        Assert.assertTrue(cartManager.getCart("user", "1001").getQuantity() > 0);
        Assert.assertTrue(cartManager.getCartByUser("user").size() > 0);
    }

    @Test
    public void testPrintCarts() {
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        MainController mc = new MainController();
        mc.loginUser = new User("user", "");
        new UserController(mc).viewsCartProducts();

        Assert.assertTrue(outContent.toString().length() > 0);
    }

}