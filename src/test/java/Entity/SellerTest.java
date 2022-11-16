package Entity;

import org.junit.Assert;
import org.junit.Test;

public class SellerTest {
    @Test
    public void testSeller() {
        Seller s = new Seller("a", "1");
        Assert.assertEquals("a", s.getSeller_username());
        Assert.assertEquals("1", s.getSeller_password());

        Assert.assertFalse(s.setAdmin_username(null));
        Assert.assertFalse(s.setAdmin_password(null));

        Assert.assertTrue(s.setAdmin_username("b"));
        Assert.assertTrue(s.setAdmin_password("2"));

        Assert.assertEquals("b", s.getSeller_username());
        Assert.assertEquals("2", s.getSeller_password());
    }
}
