# String特性
1. 存储结构
```java
    /** The value is used for character storage. */
    private final char value[];
```
2. 长度限制
   未测试出，仅出现内存溢出异常，但由于诸多方法的参数为`int`类型，所以在使用时极有可能抛出越界异常
   * 编译期
   * 运行期
3. 构造方法
   * 基于int数组
   ```java
      public String(int[] codePoints, int offset, int count{...}
   ```
      * Unicode限制([使用Unicode codespace标准 3.4-D9](http://www.unicode.org/versions/Unicode12.1.0/ch03.pdf#G2212))
         * BMP -- \u0000~\uFFFF -- 一个字符
	 * 其他Unicode码 -- 0x00FFFF~0X10FFFF -- 两个字符
	 * 非法参数 -- 其他范围
   * 基于StringBuffer、StringBuilder
   ```java
   public String(StringBuffer buffer) {
           synchronized(buffer) {
               this.value = Arrays.copyOf(buffer.getValue(), buffer.length());
           }
       }
   ```
   ```java
   public String(StringBuilder builder) {
           this.value = Arrays.copyOf(builder.getValue(), builder.length());
      }
   ```
      * 区别 -- 是否用synchronized代码块对copy方法进行包裹
      * 相同 -- Arrays.copyOf() --浅拷贝
   * compareTo()方法
      * 实质是比较Unicode的大小
