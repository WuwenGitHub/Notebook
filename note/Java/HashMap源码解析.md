
<h1>一、HashMap存储结构</h1>
     采用<b>数组</b>+<b>链表</b>+<b>红黑树</b>的方式来存储和组织数据
<h1> 二、HashMap类的put方法</h1>
<b>方法名</b>
<pre><code>public V put(K key, V value) {...}</code></pre>
<b>方法体</b>
<pre><code>/**
 * Associates the specified value with the specified key in this map.
 * If the map previously contained a mapping for the key, the old
 * value is replaced.
 *
 * @param key key with which the specified value is to be associated
 * @param value value to be associated with the specified key
 * @return the previous value associated with <tt>key</tt>, or
 *         <tt>null</tt> if there was no mapping for <tt>key</tt>.
 *         (A <tt>null</tt> return can also indicate that the map
 *         previously associated <tt>null</tt> with <tt>key</tt>.)
 */
 public V put(K key, V value) {
     return putVal(hash(key), key, value, false, true);
 }</code></pre>
<b>方法名</b><br />
<code>final V putVal(int hash, K key, V value, boolean onlyIfAbsent, boolean evict) {...}</code><br />
<b>方法体</b>  
<pre><code>/**
 * Implements Map.put and related methods
 *
 * @param hash hash for key
 * @param key the key
 * @param value the value to put
 * @param onlyIfAbsent if true, don't change existing value
 * @param evict if false, the table is in creation mode.
 * @return previous value, or null if none
 */
 final V putVal(int hash, K key, V value, boolean onlyIfAbsent,
                boolean evict) {
     Node<K,V>[] tab; Node<K,V> p; int n, i;
     //判断哈希桶是否为<tt>null</tt>
     if ((tab = table) == null || (n = tab.length) == 0)
         //重新初始化<tt>table</tt>
         n = (tab = resize()).length;
     //计算插入位置
     //null 存于该节点
     //存在 1.节hash值、key值均相同 替换
     //     2.该节点为TreeNode 表明为红黑树 连接为TreeNode节点
     //     3.按链表方式进行连接
     if ((p = tab[i = (n - 1) & hash]) == null)
         tab[i] = newNode(hash, key, value, null);
     else {
         Node<K,V> e; K k;
         if (p.hash == hash &&
             ((k = p.key) == key || (key != null && key.equals(k))))
             e = p;
         else if (p instanceof TreeNode)
             //插入一TreeNode节点
             e = ((TreeNode<K,V>)p).putTreeVal(this, tab, hash, key, value);
         else {
             for (int binCount = 0; ; ++binCount) {
                 if ((e = p.next) == null) {
                     p.next = newNode(hash, key, value, null);
                     //链表长度超过了8 进行树化 
                     if (binCount >= TREEIFY_THRESHOLD - 1) // -1 for 1st
                         treeifyBin(tab, hash);
                     break;
                 }
                 //如果下一个节点e 不为null 并且这个链表中的节点就是你要找的节点 终止循环
                 if (e.hash == hash &&
                     ((k = e.key) == key || (key != null && key.equals(k))))
                     break;
                 p = e;
             }
         }
         //
         if (e != null) { // existing mapping for key
             V oldValue = e.value;
             if (!onlyIfAbsent || oldValue == null)
                 e.value = value;
             afterNodeAccess(e);
             return oldValue;
         }
     }
     ++modCount;
     if (++size > threshold)
         resize();
     afterNodeInsertion(evict);
     return null;
 }</code></pre>
<b>方法名</b><br />
<pre><code>final TreeNode<K,V> putTreeVal(HashMap<K,V> map, Node<K,V>[] tab,int h, K k, V v) {...}</code></pre>
<b>方法体</b>
<pre><code>/**
 * Tree version of putVal.
 *
 * 当存在hash碰撞的时候，且元素数量大于8个时候，就会以红黑树的方式将这些元素组织起来
 * map 当前节点所在的HashMap对象
 * tab 当前HashMap对象的元素数组
 * h   指定key的hash值
 * k   指定key
 * v   指定key上要写入的值
 * 返回：指定key所匹配到的节点对象，针对这个对象去修改V（返回空说明创建了一个新节点）
 */
final TreeNode<K,V> putTreeVal(HashMap<K,V> map, Node<K,V>[] tab,
                               int h, K k, V v) {
    Class<?> kc = null; // 定义k的Class对象
    boolean searched = false;// 标识是否已经遍历过一次树，未必是从根节点遍历的，但是遍历路径上一定已经包含了后续需要比对的所有节点。
    TreeNode<K,V> root = (parent != null) ? root() : this;// 父节点不为空那么查找根节点，为空那么自身就是根节点
    for (TreeNode<K,V> p = root;;) {// 从根节点开始遍历，没有终止条件，只能从内部退出
        int dir, ph; K pk;// 声明方向、当前节点hash值、当前节点的键对象
        if ((ph = p.hash) > h)// 如果当前节点hash 大于 指定key的hash值
            dir = -1;// 要添加的元素应该放置在当前节点的左侧
        else if (ph < h) // 如果当前节点hash 小于 指定key的hash值
            dir = 1;// 要添加的元素应该放置在当前节点的右侧
        else if ((pk = p.key) == k || (k != null && k.equals(pk))) // 如果当前节点的键对象 和 指定key对象相同
            return p; // 那么就返回当前节点对象，在外层方法会对v进行写入
        // 走到这一步说明 当前节点的hash值  和 指定key的hash值  是相等的，但是equals不等
        else if ((kc == null &&
                  (kc = comparableClassFor(k)) == null) ||
                 (dir = compareComparables(kc, k, pk)) == 0) {
            // 走到这里说明：指定key没有实现comparable接口 或者 实现comparable接口并且和当前节点的键对象比较之后相等（仅限第一次循环）
            /*
             * searched 标识是否已经对比过当前节点的左右子节点了
             * 如果还没有遍历过，那么就递归遍历对比，看是否能够得到那个键对象equals相等的的节点
             * 如果得到了键的equals相等的的节点就返回
             * 如果还是没有键的equals相等的节点，那说明应该创建一个新节点了
             */
            if (!searched) { // 如果还没有比对过当前节点的所有子节点
                TreeNode<K,V> q, ch; // 定义要返回的节点、和子节点
                searched = true; // 标识已经遍历过一次了
                /*
                 * 红黑树也是二叉树，所以只要沿着左右两侧遍历寻找就可以了
                 * 这是个短路运算，如果先从左侧就已经找到了，右侧就不需要遍历了
                 * find 方法内部还会有递归调用。参见：find方法解析
                 */
                if (((ch = p.left) != null &&
                     (q = ch.find(h, k, kc)) != null) ||
                    ((ch = p.right) != null &&
                     (q = ch.find(h, k, kc)) != null))
                    return q; // 找到了指定key键对应的
            }
            // 走到这里就说明，遍历了所有子节点也没有找到和当前键equals相等的节点
            dir = tieBreakOrder(k, pk);
        }
	&nbsp;
        TreeNode<K,V> xp = p; // 定义xp指向当前节点
        /*
         * 如果dir小于等于0，那么看当前节点的左节点是否为空，如果为空，就可以把要添加的元素作为当前节点的左节点，如果不为空，还需要下一轮继续比较
         * 如果dir大于等于0，那么看当前节点的右节点是否为空，如果为空，就可以把要添加的元素作为当前节点的右节点，如果不为空，还需要下一轮继续比较
         * 如果以上两条当中有一个子节点不为空，这个if中还做了一件事，那就是把p已经指向了对应的不为空的子节点，开始下一轮的比较
         */
        if ((p = (dir <= 0) ? p.left : p.right) == null) {
	       // 如果恰好要添加的方向上的子节点为空，此时节点p已经指向了这个空的子节点
            Node<K,V> xpn = xp.next; // 获取当前节点的next节点
            TreeNode<K,V> x = map.newTreeNode(h, k, v, xpn); // 创建一个新的树节点
            if (dir <= 0)
                 xp.left = x;  // 左孩子指向到这个新的树节点
            else
                xp.right = x; // 右孩子指向到这个新的树节点
            xp.next = x; // 链表中的next节点指向到这个新的树节点
            x.parent = x.prev = xp; // 这个新的树节点的父节点、前节点均设置为 当前的树节点
            if (xpn != null) // 如果原来的next节点不为空
                ((TreeNode<K,V>)xpn).prev = x; // 那么原来的next节点的前节点指向到新的树节点
            moveRootToFront(tab, balanceInsertion(root, x)); // 重新平衡，以及新的根节点置顶
            return null; // 返回空，意味着产生了一个新节点
        }
    }
}
</code></pre>
<b>方法名</b>
