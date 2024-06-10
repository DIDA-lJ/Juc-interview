/**
 * @author linqi
 * @version 1.0.0
 * @description
 */

public class AlternatePrintingNumberLetter {
    private static final Object lock = new Object();
    /**
     *  用于计数，确认打印的字母和数字
     */
    private static int count = 1;
    /**
     * 控制标记，用于控制是否打印数字
     */
    private static boolean printNumber = false;

    public static void main(String[] args) {
        // 创建打印数字的线程
        Thread printNumberThread = new Thread(() ->{
           while(count <= 26){
               synchronized (lock){
                   while(!printNumber){
                       try {
                           lock.wait();
                       } catch (InterruptedException e) {
                           throw new RuntimeException(e);
                       }
                   }
                   if(count <= 26){
                       System.out.print(count);
                       count++;
                       printNumber = false;
                       lock.notifyAll();
                   }
               }
           }
        });
        // 创建打印字母的线程
        Thread printLetterThread = new Thread(()->{
            while(count <= 26){
                synchronized (lock){
                    while(printNumber) {
                        try {
                            lock.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    if (count <= 26){
                        char letter = (char)('a' + count - 1);
                        System.out.print(letter);
                        printNumber = true;
                        lock.notify();
                    }
                }
            }
        });
        printNumberThread.start();
        printLetterThread.start();
    }

}
