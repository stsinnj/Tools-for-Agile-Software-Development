package Method;

import Entity.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class UserLoginRegisterTest {
    SysFunction sf = new SysFunction();

    @Test
    public void testLoginFailed(){
        sf.users.add(new User("Alex","123"));
        assertFalse(sf.UserLogin("","123"));
        assertFalse(sf.UserLogin("",""));
        assertFalse(sf.UserLogin("Alex",""));
    }


    @Test
    public void testLoginNormal(){
        sf.users.add(new User("Alex","123"));
        assertTrue(sf.UserLogin("Alex","123"));
    }

    @Test
    public void testWrongInputArgs(){
        assertFalse(sf.UserRegister(null,null));
        assertFalse(sf.UserRegister("New    Name","123"));
        assertFalse(sf.UserRegister("NewName","1  23"));
    }

    @Test
    public void testAlreadyExistUserName(){
        sf.users.add(new User("Ed","123"));
        assertFalse(sf.UserRegister("Ed","123"));
    }
    @Test
    public void testNormal(){
        sf.users.add(new User("Ed","123"));
        sf.users.add(new User("Alex","123456"));
        assertTrue(sf.UserRegister("abcccc","1123456"));
    }

    @Test
    public void testGetUserByUserName(){
        User us = new User("ALex","123");
        Assertions.assertEquals("ALex",us.getUser_username());
        Assertions.assertNotEquals("Ale", us.getUser_username());


    }

}
