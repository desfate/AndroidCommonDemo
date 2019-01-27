package com.defate.mac.androidcommondemo.kotilins.constructor_clazz

//在 Kotlin 中的一个类可以有一个主构造函数以及一个或多个次构造函数。主构造函数是类头的一部分：它跟在类名（与可选的类型参数）后

//主构造函数是类头的一部分 。主构造函数是类头的一部分。主构造函数是类头的一部分  主构造函数是类头的一部分

//如果主构造函数没有任何注解或者可见性修饰符，可以省略这个 constructor 关键字

//主构造函数不能包含任何的代码。初始化的代码可以放到以 init 关键字作为前缀的初始化块（initializer blocks）中。
class KotlinConstructor constructor(name: String){

    //执行顺序 就按这个来

    val startName = "My name $name".also(::println)

    init {
        test()
    }

    val nextName = "My name $name".also(::println)

    init {
        println("Second initializer")
    }

    fun test(){
        println("First initializer")
    }

    // 次级构造函数
    // 可以直接委托或者通过别的次构造函数间接委托。委托到同一个类的另一个构造函数用 this 关键字即可：

    constructor(names: String, names2: String) : this(names) {
       println("次级构造函数" + names + names2);
    }


    /**  //测试代码
     *      var kotlinConstructor = KotlinConstructor("yu jie")  //调用主构造函数
     *
     *      var kotlinConstructor = KotlinConstructor("yu jie","yu jie 2")  //调用副构造函数
     *      kotlinConstructor.test()
     */



}