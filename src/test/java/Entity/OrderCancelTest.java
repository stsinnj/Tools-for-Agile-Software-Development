package Entity;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import org.junit.Assert;
import org.junit.Test;

public class OrderCancelTest {
    @Test
    public void testCartCancel() {
        OrderCancel.CANCEL_ORDER_FILE = "src/test/resources/cancelOrder.csv";
        File file = new File("src/test/resources/cancelOrder.csv");
        if (file.exists()) {
            file.delete();
        }

        OrderCancel.addOrderCancel("test", "2022-10-12 12:30:00", "abcd");
        try {
            String str = Files.readString(file.toPath());
            StringBuilder sb = new StringBuilder("Date and Time,User Name,Reason\n");
            sb.append("2022-10-12 12:30:00,abcd,test\n");
            Assert.assertEquals(sb.toString(), str);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
