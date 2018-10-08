<h1>自动装箱、拆箱</h1>
<pre><code>
	Integer i = 1;  // 1 自动装箱
	int j = i;          // 2 自动拆箱
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
其中IntegerCache代表Integer缓存。
该段源码表明，对范围在IntegerCache.low~IntegerCache.high之间的数，将直接返回缓存中值，进行比较时也将会返回<b>true</b>；而在此范围以外的数值，则直接返回的是一个新的Integer对象，除非使用equals()方法，否则都将返回false

<h1>缓存策略</h1>

<h1>toString()方法</h1>