跟随博客学习MVVM的base demo如何搭建
博客: https://www.jianshu.com/p/153ba1adf4cc
Google Sample: https://github.com/android/architecture-samples/tree/todo-mvvm-databinding


1. 在module:app 的build.gradle下添加

android {
    // ...
    dataBinding {
        enabled = true
    }
}

2.