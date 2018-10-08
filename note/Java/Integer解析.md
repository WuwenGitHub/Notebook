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
就将数值转化为String类型输出来说，正负数差异仅仅在于符号位，或者说是否需要在数字最前边添加负号的差异，处理过程其实都相同

<h5>片段一：</h5>
<pre><code>
	if (i == Integer.MIN_VALUE)
            		return "-2147483648";
</code></pre>
由于int类型的数据的取值范围为-2^31～2^31 - 1，正负对应来看，负数较正数多了一个,所以有了代码片段一；且若将负数全部转换为正数来进行处理，此时会造成越界

<h5>片段二：</h5>
<pre><code>
	int size = (i < 0) ? stringSize(-i) + 1 : stringSize(i);
        char[] buf = new char[size];
</code></pre>
在除掉多余的数字后，剩下的值仅存在正负号的差异
