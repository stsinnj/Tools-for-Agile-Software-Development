package Entity;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class CashierTest {
    Cashier cashier = new Cashier("Cashier1","123456");

    @Test
    public void testCashierName(){
        assertEquals("Cashier1",cashier.Cashier_username);
        assertNotEquals("Cashier2",cashier.Cashier_username);
    }

    @Test
    public void testCashierPassword(){
        assertEquals("123456",cashier.Cashier_password);
    }

    @Test
    public void testGetCashierName(){
        assertEquals("Cashier1",cashier.getCashier_username());
        assertNotEquals("Cashier2",cashier.getCashier_username());
    }

    @Test
    public void testGetAdminPassword(){
        assertEquals("123456",cashier.getCashier_password());
        assertNotEquals("1234567",cashier.getCashier_password());
    }
}
