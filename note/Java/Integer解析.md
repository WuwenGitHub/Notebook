<h1>自动装箱、拆箱</h1>
<pre><code>
	Integer i = 1;  // 1 自动装箱
	int j = i;      // 2 自动拆箱
</code></pre>

代码1、2处在编译时会分别进行自动装箱和拆箱过程，其中：

自动装箱代码：<code>Integer i = 5</code>操作在编译阶段会转为执行<code>Integer i = Integer.valueOf(1)</code>

自动拆箱代码：<code>int j = i</code>操作在编译阶段会转为执行<code>int j = Integer.intValue(i)</code>

同时针对自动装箱的Integer.valueOf()方法，Integer的源码为：
<pre><code>
	public static Integer valueOf(int i) {
            if (i >= IntegerCache.low && i <= IntegerCache.high)
                return IntegerCache.cache[i + (-IntegerCache.low)];
            return new Integer(i);
        }
</code></pre>
其中IntegerCache代表Integer缓存。</br>
该段源码表明，对范围在IntegerCache.low～IntegerCache.high之间的数，将直接返回缓存中值，进行比较时也将会返回<b>true</b>；而在此范围以外的数值，则直接返回的是一个新的Integer对象，除非使用equals()方法，否则都将返回false

<h1>缓存策略</h1>
实现方式：内部高速缓存</br>
源码表示：
<pre><code>
	private static class IntegerCache {
            static final int low = -128;
            static final int high;
            static final Integer cache[];
	    <br />
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
		<br />
            	cache = new Integer[(high - low) + 1];
            	int j = low;
            	for(int k = 0; k < cache.length; k++)
                    cache[k] = new Integer(j++);
		<br />
            	// range [-128, 127] must be interned (JLS7 5.1.7)
            	assert IntegerCache.high >= 127;
            }
	    <br />
            private IntegerCache() {}
   	}
</code></pre>
此段代码将会于Integer类加载时执行，通常情况下，默认缓存从Integer.low=-128～Integer.high=127的数，但同时可以使用JVM的启动参数来设置缓存上限(<code>-XX:AutoBoxCacheMax=size</code>)，即 java.lang.Integer.IntegerCache.high的值。

<h3>疑问：</h3>为什么仅缓存从-128开始的值(byte型的取值范围即为-128～127)

<h1>toString()方法</h1>
toString()源码:
<pre><code>
	public static String toString(int i) {
            if (i == Integer.MIN_VALUE)
            	return "-2147483648";
            int size = (i < 0) ? stringSize(-i) + 1 : stringSize(i);
            char[] buf = new char[size];
            getChars(i, size, buf);
            return new String(buf, true);
    	}
</code></pre>

<h4>解析：</h4>
就将数值转化为String类型输出来说，正负数差异仅仅在于符号位，或者说是否需要在数字最前边添加负号的差异。去掉此差异后，可采用相同的方式进行处理

<h5>片段一：</h5>
<pre><code>
	if (i == Integer.MIN_VALUE)
            return "-2147483648";
</code></pre>
由于int类型的数据的取值范围为-2147483648～2147483647，正负对应来看，负数较正数多了一个,所以有了代码片段一；且若将负数全部转换为正数来进行处理，此时会造成越界

<h5>片段二：</h5>
<pre><code>
	int size = (i < 0) ? stringSize(-i) + 1 : stringSize(i);
        char[] buf = new char[size];
</code></pre>
在除掉多余的数字后，剩下的值仅存在正负号的差异
由于负数较正数在转化为String类型时 的差异表现在多了一个负号

该段代码的主要目的是提取出整数i的位数，并创建一个字符数组。而提取方法采用<code>stringSize</code>
<pre><code>
    final static int [] sizeTable = { 9, 99, 999, 9999, 99999, 999999, 9999999,
                                      99999999, 999999999, Integer.MAX_VALUE };

    // Requires positive x
    static int stringSize(int x) {
        for (int i=0; ; i++)
            if (x <= sizeTable[i])
                return i+1;
    }
</code></pre>

<h5>片段三：</h5>
<pre><code>
    getChars(i, size, buf);
</code></pre>
该段代码主要是提取出整数的每一位的值，具体实现方式如下：
<pre><code>
    static void getChars(int i, int index, char[] buf) {
        int q, r;
        int charPos = index;
        char sign = 0;

        if (i < 0) {
            sign = '-';
            i = -i;
        }

        // Generate two digits per iteration
        while (i >= 65536) {
            q = i / 100;
        // really: r = i - (q * 100);
            r = i - ((q << 6) + (q << 5) + (q << 2));
            i = q;
            buf [--charPos] = DigitOnes[r];
            buf [--charPos] = DigitTens[r];
        }

        // Fall thru to fast mode for smaller numbers
        // assert(i <= 65536, i);
        for (;;) {
            q = (i * 52429) >>> (16+3);
            r = i - ((q << 3) + (q << 1));  // r = i-(q*10) ...
            buf [--charPos] = digits [r];
            i = q;
            if (i == 0) break;
        }
        if (sign != 0) {
            buf [--charPos] = sign;
        }
    }
</code></pre>
<b>两点疑惑：</b>
1.为什么在getChars方法中，将整型数字写入到字符数组的过程中为什么按照数字65536分成了两部分呢?这个65535是怎么来的?
片段一：
<pre><code>
    // Generate two digits per iteration
        while (i >= 65536) {
            q = i / 100;
        // really: r = i - (q * 100);
            r = i - ((q << 6) + (q << 5) + (q << 2));
            i = q;
            buf [--charPos] = DigitOnes[r];
            buf [--charPos] = DigitTens[r];
        }
</code></pre>
片段二：
<pre><code>
    // Fall thru to fast mode for smaller numbers
        // assert(i <= 65536, i);
        for (;;) {
            q = (i * 52429) >>> (16+3);
            r = i - ((q << 3) + (q << 1));  // r = i-(q*10) ...
            buf [--charPos] = digits [r];
            i = q;
            if (i == 0) break;
        }
</code></pre>
2.在上面两段代码的部分二中，在对i进行除十操作的过程中为什么选择先乘以52429在向右移位19位。其中52429和19是怎么来的?

<b>解答：</b>
注意：
<pre>
移位的效率比直接乘除的效率要高
乘法的效率比除法的效率要高
</pre>
代码解读：

<h5>片段四：</h5>
<pre><code>
    return new String(buf, true);
</code></pre>