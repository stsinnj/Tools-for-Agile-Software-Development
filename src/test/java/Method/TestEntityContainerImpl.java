package Method;
import Entity.Cashier;



import Entity.Cashier;

import Entity.Seller;
import Entity.User;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

public class TestEntityContainerImpl {
    static {
        EntityContainerImpl.sellerFile = "src/test/resources/Seller.csv";
    }
    private EntityContainer entityContainer;

    @Before
    public void beforeInit() {
        entityContainer = new EntityContainerImpl();
    }

    @Test
    public void testReadUserCSV() {
    }


    @Test
    public void testRead() {
        ArrayList<User> list = entityContainer.readUserCSV();
        Assert.assertTrue(list.size() > 0);


        ArrayList<Cashier> cashiers = entityContainer.readCashierCSV();
        Assert.assertTrue(cashiers.size() > 0);
    }


    @Test
    public void testSellerRead() {
        ArrayList<Seller> sellerList = entityContainer.readSellerCSV();
        Assert.assertEquals(1, sellerList.size());
    }
}

