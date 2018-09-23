<h1>HashMap与Hashtable区别</h1>
<table>
  <tr>
    <th scope="col">&nbsp;</th>
    <th colspan="2" scope="col">HashMap</th>
    <th colspan="2" scope="col">HashTable</th>
  </tr>
  <tr>
    <th rowspan="4" scope="row">类继承及接口实现</th>
    <td>extends</td>
    <td>java.util.AbstractMap<K,V></td>
    <td>extends</td>
    <td>java.util.Dictionary<K,V></td>
  </tr>
  <tr>
    <td rowspan="3">implements</td>
    <td>java.util.Map<K,V></td>
    <td rowspan="3">implements</td>
    <td>java.util.Map<K,V></td>
  </tr>
  <tr>
    <td>java.lang.Cloneable</td>
    <td>java.lang.Cloneable</td>
  </tr>
  <tr>
    <td>java.io.Serializable</td>
    <td>java.io.Serializable</td>
  </tr>
  <tr>
    <th scope="row">key、value值</th>
    <td colspan="2">均可为null</td>
    <td colspan="2">不能包含为null的key和value</td>
  </tr>
  <tr>
    <th scope="row">线程安全实现</th>
    <td colspan="2">未做任何操作</td>
    <td colspan="2">将所有操作都标记成synchronized(对当前实例的锁)</td>
  </tr>
  <tr>
    <th scope="row">存储结构实现</th>
    <td colspan="2">数组+链表+红黑树</td>
    <td colspan="2">&nbsp;</td>
  </tr>
  <tr>
    <th scope="row">解决hash冲突</th>
    <td colspan="2">链地址法</td>
    <td colspan="2">&nbsp;</td>
  </tr>
  <tr>
    <th rowspan="6" scope="row">相关参数</th>
    <td>DEFAULT_INITIAL_CAPACITY</td>
    <td>1 &lt;&lt; 4  // aka 16</td>
    <td>initialCapacity</td>
    <td>11</td>
  </tr>
  <tr>
    <td>MAXIMUM_CAPACITY</td>
    <td>1 &lt;&lt; 30</td>
    <td>MAX_ARRAY_SIZE</td>
    <td>Integer.MAX_VALUE - 8</td>
  </tr>
  <tr>
    <td>DEFAULT_LOAD_FACTOR</td>
    <td>0.75f</td>
    <td>loadFactor</td>
    <td>0.75f</td>
  </tr>
  <tr>
    <td>TREEIFY_THRESHOLD</td>
    <td>8</td>
    <td colspan="2">&nbsp;</td>
  </tr>
  <tr>
    <td>UNTREEIFY_THRESHOLD</td>
    <td>6</td>
    <td colspan="2">&nbsp;</td>
  </tr>
  <tr>
    <td>MIN_TREEIFY_CAPACITY</td>
    <td>64</td>
    <td colspan="2">&nbsp;</td>
  </tr>
  <tr>
  	<th rowspan="4" scope="row">扩容</th>
    <td colspan="2">newCap = oldCap &lt;&lt; 1</td>
    <td colspan="2">newCapacity = (oldCapacity &lt;&lt; 1) + 1</td>
  </tr>
  <tr>
    <td rowspan="2">条件</td>
    <td>1.(tab = table) == null || (n = tab.length) == 0</td>
    <td rowspan="2">条件</td>
    <td rowspan="2">count &gt;= threshold</td>
  </tr>
  <tr>
    <td>2.tab == null || (n = tab.length) &lt; MIN_TREEIFY_CAPACITY</td>
  </tr>
  <tr>
    <td colspan="2">始终为2^n大小</td>
    <td colspan="2">表大小为素数,使hash结果更加均匀,hash冲突减少</td>
  </tr>
  <tr>
  	<th scope="row">threshold更改</th>
    <td colspan="2">newThr = oldThr &lt;&lt; 1</td>
    <td colspan="2">Math.min(newCapacity * loadFactor, MAX_ARRAY_SIZE + 1)</td>
  </tr>
  <tr>
    <th rowspan="2" scope="row">迭代器</th>
    <td rowspan="2">fail-fast HashIterator迭代</td>
    <td>extends HashIterator</td>
    <td rowspan="2">fast-fail enumerator迭代器</td>
    <td>implements Enumeration&lt;T&gt;</td>
  </tr>
  <tr>
    <td>implements Iterator&lt;K&gt;</td>
    <td>implements Iterator&lt;T&gt;</td>
  </tr>
  <tr>
    <th scope="row">元素次序</th>
    <td colspan="2">不能保证随着时间的推移Map中的元素次序是不变</td>
    <td colspan="2">次序不变</td>
  </tr>
  <tr>
    <th rowspan="2" scope="row">定位</th>
    <td colspan="2">(key == null) ? 0 : (h = key.hashCode()) ^ (h &gt;&gt;&gt; 16)</td>
    <td colspan="2">(key.hashCode() &amp; 0x7FFFFFFF) % tab.length</td>
  </tr>
  <tr>
    <td colspan="2">提高了计算效率(只需要做位运算，高16位异或低16位)</td>
    <td colspan="2">除法运算较耗时</td>
  </tr>
</table>