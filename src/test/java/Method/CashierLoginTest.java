package Method;

import Entity.Cashier;
import Method.SysFunction;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class CashierLoginTest {
    SysFunction sf = new SysFunction();

    @Test
    public void testNoCashier(){
        Cashier c = new Cashier("1","2");
        sf.cashiers.add(c);
        assertFalse(sf.CashierLogin(null,"1"));
        assertFalse(sf.CashierLogin(" ","1"));
        assertFalse(sf.CashierLogin("1",null));
        assertFalse(sf.CashierLogin("1"," "));

    }

    @Test
    public void testNoSuchCashier(){
        Cashier c = new Cashier("1","2");
        sf.cashiers.add(c);
        Cashier b = new Cashier("2","2");
        sf.cashiers.add(b);
        assertFalse(sf.CashierLogin("5","2"));
    }

    @Test
    public void testNormal(){
        Cashier c = new Cashier("1","2");
        sf.cashiers.add(c);
        assertTrue(sf.CashierLogin("1","2"));
    }
}
