package Controller;


import Entity.OrderCancel;
import Entity.User;
import java.io.IOException;
import java.nio.file.Files;
import java.util.concurrent.TimeUnit;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.File;

public class TestUserController {


    MainController mainController;
    UserController userController;

    @Before
    public void before(){
        mainController = new MainController();
        mainController.loginUser = new User("aaa", "aaa");
        userController = new UserController(mainController);

    }

    @Test
    public void test1() {
        String pathToCsv = "src/main/resources/Cart.csv";
        File file = new File(pathToCsv);
        if(!file.exists()){
            Assert.assertFalse(file.exists());
        }
        MainController mc = new MainController();
        mc.loginUser = new User("abcd", "abcd");
        UserController uc = new UserController(mc);
        uc.cancel_order();

        file = new File(pathToCsv);
        Assert.assertTrue(file.exists());
    }

    @Test
    public void testCartCancel() {
        OrderCancel.CANCEL_ORDER_FILE = "src/main/resources/cancelOrder.csv";
        File file = new File("src/main/resources/resources/cancelOrder.csv");
        if (file.exists()) {
            file.delete();
        }

        OrderCancel.addOrderCancel("test", "2022-10-12 12:30:00", "abcd");
        //try {
            //String str = Files.readString(file.toPath());
            StringBuilder sb = new StringBuilder("Date and Time,User Name,Reason\n");
            sb.append("2022-10-12 12:30:00,abcd,test\n");
            //Assert.assertEquals(sb.toString(), str);
//        } catch (IOException e) {
//            throw new RuntimeException(e);
        //}
    }

    @Test
    public  void testSplitChange(){
        userController.splitChange(100);
        userController.splitChange(1);
        userController.splitChange(11);
        userController.splitChange(7);
        userController.splitChange(0.5);
        userController.splitChange(30);
        userController.splitChange(40);
    }

    @Test
    public void testShowBankCard(){
        userController.showBankCard();
    }

    @Test
    public void testViewsCartProducts(){
        userController.viewsCartProducts();
    }
    @Test
    public void testShowAllOrder(){
        userController.showAllOrder();
    }

    @Test
    public void testView_category(){
        userController.view_category();
    }

    @Test
    public void testGetTime(){
        String s  = userController.getTime();
        System.out.println(s);
        Assert.assertTrue(s!=null );
    }
}
