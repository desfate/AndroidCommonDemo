package com.defate.mac.androidcommondemo.kotilins.constructor_clazz;

//java 的构造函数 就比较easy
public class JavaClazz {

    public JavaClazz() {
        System.out.println("main");
    }

    JavaClazz(String name){
        System.out.println(name);
    }

    JavaClazz(String name1, String name2){
        System.out.println(name1 + name2);
    }

    /*********   直接转换后 变成三个次级构造方法了
    constructor() {
        println("main")
    }

    internal constructor(name: String) {
        println(name)
    }

    internal constructor(name1: String, name2: String) {
        println(name1 + name2)
    }

     *********/



}
