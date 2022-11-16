package Entity;

import static Controller.UserController.getTime;
import static Controller.UserController.mc;

public class ThreadTest extends Thread {
    private long timeout;
    private boolean isCanceled = false;
    public boolean isTimeout = false;
    private TimerOutException timeoutException;


    public ThreadTest(long timeout, TimerOutException timeoutErr) {
        super.run();
        this.timeout = timeout;
        this.timeoutException = timeoutErr;

        this.setDaemon(true);
    }

    public void cancel() {
        isCanceled = true;
    }

    public void run() {
        try {
            isTimeout = false;
            Thread.sleep(timeout);
            if (!isCanceled) {
                Thread.interrupted();
                System.out.println("Timeout");
                System.out.println("Type any key to continue");
                OrderCancel.addOrderCancel("Timeout", getTime(), mc.loginUser.User_username);
                isTimeout = true;
            } else {
                Thread.interrupted();
            }
        } catch (InterruptedException e) {
            Thread.interrupted();
            System.out.println("InterruptedException");
        }
    }
}
