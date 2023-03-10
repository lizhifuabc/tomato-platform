package com.tomato.book.java;

/**
 * happens-before原则
 *
 * @author lizhifu
 * @since 2023/3/7
 */
public class HappenBeforeMain {
    private static int a = 0;
    private static boolean flag = false;

    public void method1(){
        a = 1;
        flag = true;
    };

    public void method2(){
        if (flag) {
            a *= 1;
        }
        if (a == 0) {
            System.out.println("happen before a->" + a);
        }
    };

    public static void main(String[] args) throws InterruptedException {
        for (int i = 0; i < 10; i++) {
            HappenBeforeMain happenBeforeMain = new HappenBeforeMain();
            Thread thread1 = new Thread(() -> {
                happenBeforeMain.method1();
            });
            Thread thread2 = new Thread(() -> {
                happenBeforeMain.method2();
            });
            thread1.start();
            thread2.start();
            thread1.join();
            thread2.join();
        }
    }
}
