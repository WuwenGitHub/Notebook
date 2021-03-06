<h1>一、数据类型</h1>
<ul>
	<li>原始数据类型及包装类型</li>
	<li>自动装箱、拆箱</li>
	<li>缓存</li>
	<li>线程安全</li>
	<li>原始数据类型数组及对象数组</li>
</ul>
<h1>一、数据类型</h1>
<h4>原始数据类型及包装类型</h4>
<table>
	<tr>
		<th>原始数据类型</th>
		<th>包装类型</th>
	</tr>
	<tr>
		<td>boolean/1</td>
		<td>Boolean</td>
	</tr>
	<tr>
		<td>byte/8</td>
		<td>Byte</td>
	</tr>
	<tr>
		<td>char/16</td>
		<td>Character</td>
	</tr>
	<tr>
		<td>short/16</td>
		<td>Short</td>
	</tr>
	<tr>
		<td>int/32</td>
		<td>Integer</td>
	</tr>
	<tr>
		<td>float/32</td>
		<td>Float</td>
	</tr>
	<tr>
		<td>long/64</td>
		<td>Long</td>
	</tr>
	<tr>
		<td>double/64</td>
		<td>Double</td>
	</tr>
</table>

<h4>自动装箱、拆箱</h4>
发生于编译阶段，Java平台为我们自动进行了一些转换，保证不同的写法在运行时等价，生成的字节码是一致的。<br \>
装箱操作:Object.valueOf() ==> 将原始数据类型转换为对应的包装类<br \>
拆箱操作:Object.objectValue() ==> 将包装类转化为原始数据类型<br \>
例：
<pre>
<code>
	Integer integer = 1;
	int unboxing = integer++;

	Boolean boolean1 = true;
	boolean unboxing2 = !boolean1;
</code>
</pre>
javac替我们自动把装箱转换为Integer.valueOf()、Boolean.valueOf，<br \>
把拆箱替换为Integer.intValue()、Boolean.booleanValue().<br \>

反编译输出：
<pre>
<code>
	.........
	1: invokestatic  #2                  // Method java/lang/Integer.valueOf:(I)Ljava/lang/Integer;
	.........
	8: invokevirtual #3                  // Method java/lang/Integer.intValue:()I
	11: iconst_1
	12: iadd
	13: invokestatic  #2                  // Method java/lang/Integer.valueOf:(I)Ljava/lang/Integer;
	16: dup
	17: astore_1
	18: astore        4
	20: aload_3
	21: invokevirtual #3                  // Method java/lang/Integer.intValue:()I
	24: istore_2
	25: iconst_1
	26: invokestatic  #4                  // Method java/lang/Boolean.valueOf:(Z)Ljava/lang/Boolean;
	29: astore_3
	30: aload_3
	31: invokevirtual #5                  // Method java/lang/Boolean.booleanValue:()Z
	.........
</code>
</pre>
<h4>包装类型的值缓存</h4>
缓存机制：<br \>
除Float、Double外，其余基本类型都使用了缓存机制<br \>
采用静态数组方式存储：<code>static final Object cache[]</code><br \>
当进行自动装箱时，静态工作方法valueOf将会从缓存中去获取值。<br \>
<pre>
<code>
private static class ObjectCache {
        private ObjectCache(){}

        static final Object cache[] = new Object[size];

        static {
            for(int i = 0; i < cache.length; i++)
                ......
        }
    }
</code>
</pre>

缓存对象
<table>
	<tr>
		<th>包装类型</th>
		<th>缓存对象(基本数据类型值)</th>
	</tr>
	<tr>
		<td>Boolean</td>
		<td>true,false</td>
	</tr>
	<tr>
		<td>Byte</td>
		<td>-128～127</td>
	</tr>
	<tr>
		<td>Character</td>
		<td>'\u0000'～'\u007F'</td>
	</tr>
	<tr>
		<td>Short</td>
		<td>-128～127</td>
	</tr>
	<tr>
		<td>Integer</td>
		<td>-128～127(默认127，可进行改变)</td>
	</tr>
	<tr>
		<td>Float</td>
		<td>无缓存值</td>
	</tr>
	<tr>
		<td>Long</td>
		<td>-128～127</td>
	</tr>
	<tr>
		<td>Float</td>
		<td>无缓存值</td>
	</tr>
</table>
相关缓存代码：<br \>
Boolean缓存:<br \>
<pre>
<code>
    /**
     * The {@code Boolean} object corresponding to the primitive
     * value {@code true}.
     */
    public static final Boolean TRUE = new Boolean(true);

    /**
     * The {@code Boolean} object corresponding to the primitive
     * value {@code false}.
     */
    public static final Boolean FALSE = new Boolean(false);
</code>
</pre>
Integer缓存：<br \>
默认缓存范围为-128～127，但可以在运行时通过修改系统属性来进行设置<br \>
java -Djava.lang.Integer.IntegerCache.high=100 xxx<br \>
或<br \>
java -server -XX:AutoBoxCacheMax=100 xxx<br \>
<pre>
<code>
    /**
     * Cache to support the object identity semantics of autoboxing for values between
     * -128 and 127 (inclusive) as required by JLS.
     *
     * The cache is initialized on first usage.  The size of the cache
     * may be controlled by the {@code -XX:AutoBoxCacheMax=<size>} option.
     * During VM initialization, java.lang.Integer.IntegerCache.high property
     * may be set and saved in the private system properties in the
     * sun.misc.VM class.
     */

    private static class IntegerCache {
        static final int low = -128;
        static final int high;
        static final Integer cache[];

        static {
            // high value may be configured by property
            int h = 127;
            String integerCacheHighPropValue =
                sun.misc.VM.getSavedProperty("java.lang.Integer.IntegerCache.high");
            if (integerCacheHighPropValue != null) {
                try {
                    int i = parseInt(integerCacheHighPropValue);
                    i = Math.max(i, 127);
                    // Maximum array size is Integer.MAX_VALUE
                    h = Math.min(i, Integer.MAX_VALUE - (-low) -1);
                } catch( NumberFormatException nfe) {
                    // If the property cannot be parsed into an int, ignore it.
                }
            }
            high = h;

            cache = new Integer[(high - low) + 1];
            int j = low;
            for(int k = 0; k < cache.length; k++)
                cache[k] = new Integer(j++);

            // range [-128, 127] must be interned (JLS7 5.1.7)
            assert IntegerCache.high >= 127;
        }

        private IntegerCache() {}
    }
</code>
</pre>
<h4>线程安全</h4>
多线程访问基础数据类型及其包装类型变量时，可能会出现安全问题，从而无法保证原子性<br \>
可采用手段:
<ul>
	<li>采用并发手段</li>
	<li>使用java.util.concurrent.atomic下的相关类型替换</li>
</ul>
<h4>数组存储</h4>
<table>
	<tr>
		<th>数组类型</th>
		<th>内存中各元素关系</th>
	</tr>
	<tr>
		<td>原始数据类型数组</td>
		<td>于内存中是一段连续的内存</td>
	</tr>
	<tr>
		<td rowspan="2">相关包装类型数组</td>
		<td>1.存储引用，对象分散地存储在堆的不同位置</td>
	</tr>
	<tr>
		<td>2.在拥有极大灵活性的同时伴随着数据操作的低效，无法充分利用现代CPU缓存机制</td>
	</tr>
</table>
<h1>包装类型源码解析</h1>
