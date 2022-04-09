import com.sun.xml.internal.ws.policy.privateutil.PolicyUtils;

import java.io.IOException;

public class ThreadTest {
    private static Object object = new Object();
    private static volatile char name = 'A';

    public static void main(String[] args) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                synchronized (object){
                    try {
                        for (int i = 0; i < 5; i++) {
                            while (name != 'A'){
                                object.wait();
                            }
                            System.out.print("A");
                            name = 'B';
                            object.notifyAll();
                        }
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

            }
        }).start();
        new Thread(new Runnable() {
            @Override
            public synchronized void run() {
                synchronized (object){
                    try {
                        for (int i = 0; i < 5; i++) {
                            while (name != 'B'){
                                object.wait();
                            }
                            System.out.print("B");
                            name = 'C';
                            object.notifyAll();
                        }
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
        new Thread(new Runnable() {
            @Override
            public synchronized void run() {
                synchronized (object){
                    try {
                        for (int i = 0; i < 5; i++) {
                            while (name != 'C'){
                                object.wait();
                            }
                            System.out.print("C");
                            name = 'A';
                            object.notifyAll();
                        }
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }
}
