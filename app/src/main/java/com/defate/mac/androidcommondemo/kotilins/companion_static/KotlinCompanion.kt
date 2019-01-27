package com.defate.mac.androidcommondemo.kotilins.companion_static

// 基于java 和 kotlin 互换的一种学习方式
// 对比java和kotlin的相同和不同点来进行学习
// 我是kotlin
class KotlinCompanion{

    //1.常量  const 关键字用来修饰常量，且只能修饰  val，不能修饰var  引申
    // (var是一个可变变量，val是一个只读变量 这种声明变量的方式相当于java中的final变量)

//    companion object {
//        val TEST1 = 1
//        val TEST2 = 2
//    }

//    companion object StaticParams{  //这个StaticParams名称可以省略  也可以自定义
//        val TEST1 = 1
//        val TEST2 = 2
//    }

//      companion object {
//          const val TEST1 = 1
//          const val TEST2 = 2
//      }


    //2. 静态方法
    companion object StaticParams{  //这个StaticParams名称可以省略  也可以自定义
        val TEST1 = 1
        val TEST2 = 2

        fun test(){
            // 我是静态方法
        }
    }



      fun test(){
          System.out.println(KotlinCompanion.StaticParams.TEST1)
          System.out.println(KotlinCompanion.TEST2)
          KotlinCompanion.StaticParams.test()
      }






}