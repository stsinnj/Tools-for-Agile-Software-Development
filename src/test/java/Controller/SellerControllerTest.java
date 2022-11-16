package Controller;

import Method.FrontEndInteraction;
import org.junit.Assert;
import org.junit.Test;

public class SellerControllerTest {
    @Test
    public void testCheck() {
        SellerController sc = new SellerController(new MainController());
        Assert.assertFalse(sc.checkName(null));
        Assert.assertFalse(sc.checkName(" "));
        Assert.assertFalse(sc.checkName("9921`"));
        Assert.assertTrue(sc.checkName("abc"));

        Assert.assertFalse(sc.checkId(" "));
        Assert.assertFalse(sc.checkId("-1"));
        Assert.assertTrue(sc.checkId("1"));
    }


    private class TestFe extends FrontEndInteraction {
        public int id;

        public TestFe(int id) {
            this.id = id;
        }

        @Override
        public int displayMenu(String header, String[] options, String prompt) {
            return id;
        }

        @Override
        public int getInt(String prompt) {
            return id;
        }

        @Override
        public String getString(String prompt) {
            if (prompt.equals("Please enter id for new product (Must be a number greater than 0): ")) {
                return "1234";
            }
            return "abc";
        }

        @Override
        public String getString(String prompt, boolean emptyAllowed) {
            if (prompt.equals("Please enter name for product, input empty means no change(cannot be number): ")) {
                return "newP";
            }
            return "abc";
        }
    }
}
