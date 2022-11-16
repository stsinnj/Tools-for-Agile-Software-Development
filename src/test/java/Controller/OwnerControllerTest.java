package Controller;

import Method.FrontEndInteraction;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Map;
import java.util.Scanner;
import java.util.UUID;

public class OwnerControllerTest {

    private OwnerController ownerController;
    private String[] usersPaths;

    @Before
    public void beforeInit() {
        ownerController = new OwnerController(new MainController());
        usersPaths = new String[]{
                "src/main/resources/Cashier.csv",
                "src/main/resources/User.csv",
                "src/main/resources/Seller.csv",
                "src/main/resources/Owner.csv"
        };
    }

    @Test
    public void testAddUserAndDelUser() {
        String username = UUID.randomUUID().toString();
        StringBuilder str = new StringBuilder();
        for (int i = 0; i < usersPaths.length; i++) {
            for (int j = 0; j < 3; j++) {
                str.append(username).append("\n");
            }
        }

        ByteArrayInputStream stream = new ByteArrayInputStream(str.toString().getBytes());
        System.setIn(stream);
        FrontEndInteraction.s = new Scanner(System.in);
        for (String usersPath : usersPaths) {
            System.out.println(usersPath);
            Map<String, String> map = ownerController.getUserMap(FileUtil.read(usersPath));
            Assert.assertFalse(map.containsKey(username));
            ownerController.addUser(usersPath);
            map = ownerController.getUserMap(FileUtil.read(usersPath));
            Assert.assertTrue(map.containsKey(username));
            ownerController.delUser(usersPath);
        }
    }

    @Test
    public void testPrintAllUser() {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(stream));
        System.setIn(new ByteArrayInputStream("1\n5".getBytes()));
        Assert.assertEquals(0, stream.size());
        FrontEndInteraction.s = new Scanner(System.in);
        ownerController.execPage();

        Assert.assertNotEquals(0, stream.size());
    }

    @Test
    public void testPrintAllCancelOrders() {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(stream));
        System.setIn(new ByteArrayInputStream("4\n5".getBytes()));
        Assert.assertEquals(0, stream.size());
        FrontEndInteraction.s = new Scanner(System.in);
        ownerController.execPage();

        Assert.assertNotEquals(0, stream.size());
    }

}
