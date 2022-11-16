package Method;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import Entity.Seller;
import Method.SysFunction;
import org.junit.jupiter.api.Test;

public class SellerLoginTest {
    SysFunction sf = new SysFunction();

    @Test
    public void testNoSeller(){
        Seller c = new Seller("1","2");
        sf.sellers.add(c);
        assertNull(sf.sellerLogin(null,"1"));
        assertNull(sf.sellerLogin(" ","1"));
        assertNull(sf.sellerLogin("1",null));
        assertNull(sf.sellerLogin("1"," "));

    }

    @Test
    public void testNoSuchSeller(){
        Seller c = new Seller("1","2");
        sf.sellers.add(c);
        Seller b = new Seller("2","2");
        sf.sellers.add(b);
        assertNull(sf.sellerLogin("5","2"));
    }

    @Test
    public void testNormal(){
        Seller c = new Seller("1","2");
        sf.sellers.add(c);
        assertNotNull(sf.sellerLogin("1","2"));
    }
}
