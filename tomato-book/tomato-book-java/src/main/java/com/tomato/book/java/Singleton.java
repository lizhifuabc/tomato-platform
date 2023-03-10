package com.tomato.book.java;

/**
 * 单例模式
 *
 * @author lizhifu
 * @since 2023/3/7
 */
public class Singleton{
    private static Singleton singleton;
    private Singleton(){}
    public static Singleton getInstance(){
        if(singleton == null){
            synchronized(Singleton.class){
                if(singleton == null){
                    singleton = new Singleton();
                }
            }
        }
        return null;
    }
}