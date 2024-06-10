import java.util.Random;

/**
 * @author linqi
 * @version 1.0.0
 * @description 购票系统
 */

public class TicketSystemDemo {
    /**
     * 总共票数
     */
    private static final int TOTAL_TICKETS = 1000;
    /**
     * 剩余票数
     */
    private static int remainingTICKETS = TOTAL_TICKETS;
    /**
     * 锁对象,用于同步
     */
    private static final Object lock = new Object();

    public static void main(String[] args) {
        // 创建线程，并且启动
        for (int i = 0; i < 10; i++) {
            new Thread(new TicketSeller(i)).start();
        }

    }

    private static class TicketSeller implements Runnable {
        private int windowNumber;

        public TicketSeller(int windowNumber) {
            this.windowNumber = windowNumber;
        }

        @Override
        public void run() {
            while (true) {
                synchronized (lock) {
                    if (remainingTICKETS > 0) {
                        try {
                            Thread.sleep(100);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        buyTicket();
                    } else {
                        break;
                    }
                    // 模拟购票后的其他操作，增加随机性
                    try {
                        Thread.sleep((long) (Math.random() * 1000));
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        }


        private void buyTicket() {
            int number = new Random().nextInt(10);
            if(remainingTICKETS   >= number && number > 0){
                remainingTICKETS = remainingTICKETS - number;
                System.out.println("从窗口 G1000" + windowNumber + " 购买了 " + number + " 张票, 还剩 " + remainingTICKETS + " 张票");
            }
        }
    }
}
