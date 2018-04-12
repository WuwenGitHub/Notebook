# 一、Java编译与执行

  * 将源文件编译成字节码文件 --> java命令
  * 解释执行字节码文件  --> Javac命令
  * 命令寻找  --> path变量路径

# 二、Java Serialization 与 Transient
   1. Serialization
       * 把对象的状态存储到硬盘上去，等需要的时候就可以再把它读出来使用
  2. Transient
      * 对特定的对象数据在serialization时不进行存储
      * 底层的java虚拟机来说，该类型的变量不是一个类的永久性的状态
      * [例子1](./LoggingInfo.java)
