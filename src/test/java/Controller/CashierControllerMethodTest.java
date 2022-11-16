package Controller;

import Entity.Cashier;
import Entity.Seller;
import Entity.User;
import Method.EntityContainerImpl;
import Method.FrontEndInteraction;
import Method.SysFunction;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class CashierControllerMethodTest {
    MainController mc = new MainController();
    SysFunction sf = new SysFunction();
    EntityContainerImpl en = new EntityContainerImpl();


    CashierController cc = new CashierController();


    @Test
    public void ModifyMoneyTest() {
        cc.sf.Start();

        assertTrue(cc.modify_money("10", "+", 1));
        assertTrue(cc.modify_money("2", "+", 1));
        assertTrue(cc.modify_money("1", "+", 1));
        assertTrue(cc.modify_money("20", "+", 1));
        assertTrue(cc.modify_money("5", "+", 1));
        assertTrue(cc.modify_money("2", "-", 1));
        assertTrue(cc.modify_money("1", "-", 1));
        assertTrue(cc.modify_money("20", "-", 1));
        assertTrue(cc.modify_money("5", "-", 1));
        assertTrue(cc.modify_money("10", "-", 1));
        assertFalse(cc.modify_money("asdasd","+",2));
        assertFalse(cc.modify_money("10","asda",2));
        assertFalse(cc.modify_money("10","-",10));
    }


}
