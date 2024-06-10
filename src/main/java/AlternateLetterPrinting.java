/**
 * @author linqi
 * @version 1.0.0
 * @description
 */

public class AlternateLetterPrinting {
    private static final Object lock = new Object();
    private static char currentLetter = 'A';
    private static boolean printUpperCase = true;

    public static void main(String[] args) {
        Thread upperCasePrinter = new Thread(()-> printLetter(true));
        Thread lowerCasePrinter = new Thread(()-> printLetter(false));

        upperCasePrinter.start();
        lowerCasePrinter.start();
    }

    private static void printLetter(boolean isUpperCaseThread) {
        while (currentLetter <= 'Z'){
            synchronized (lock){
                while (printUpperCase != isUpperCaseThread) {
                    try {
                        lock.wait();
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
                if (currentLetter > 'Z') {
                    break;
                }
                if (isUpperCaseThread) {
                    System.out.print( currentLetter);
                } else {
                    System.out.print(Character.toLowerCase( currentLetter));
                }

                printUpperCase = !printUpperCase;
                currentLetter++;

                lock.notifyAll();
            }
        }
    }
}
