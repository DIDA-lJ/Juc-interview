/**
 * @author linqi
 * @version 1.0.0
 * @description
 */

public class AlternatePrintDemo {

    private static final Object lock = new Object();
    /**
     * 0 表示打印字母，1 表示数字
     */
    private static int state = 0;
    private static int round = 0;

    public static void main(String[] args) {
        Thread printLetters = new Thread(() ->{
            for(int i = 0; i < 40;i++){
                synchronized (lock){
                    while(state != 0){
                        try {
                            lock.wait();
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                    }

                    if(round < 10){
                        char letter = (char)('a' + (i % 4));
                        System.out.print(letter);
                        state = 1;
                        lock.notifyAll();
                    }
                }
            }
        });

        Thread printNumbers = new Thread(() ->{
            for(int i = 0; i < 40 ;i++){
                synchronized (lock){
                    while(state != 1){
                        try {
                            lock.wait();
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                    }

                    if(round < 10){
                        int number = (i % 4 )+ 1;
                        System.out.print(number);

                        if( (i + 1) % 4 == 0){
                            round++;
                        }

                        state = 0;
                        lock.notifyAll();
                    }
                }
            }
        });

        printNumbers.start();
        printLetters.start();
    }

}
