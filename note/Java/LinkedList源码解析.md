# LinkedList特性

1. 结构树
![LinkedList结构树](https://github.com/WuwenGitHub/Notebook/blob/master/pics/LinkedList%E7%BB%93%E6%9E%84%E6%A0%91.png "LinkedList结构树")

2. 存储/数据结构
     双向链表
     ```
     /**
     * Pointer to first node.
     * Invariant: (first == null && last == null) ||
     *            (first.prev == null && first.item != null)
     */
    transient Node<E> first;

    /**
     * Pointer to last node.
     * Invariant: (first == null && last == null) ||
     *            (last.next == null && last.item != null)
     */
    transient Node<E> last;
    ....
    private static class Node<E> {
        E item;
        Node<E> next;
        Node<E> prev;

        Node(Node<E> prev, E element, Node<E> next) {
            this.item = element;
            this.next = next;
            this.prev = prev;
        }
    }
     ```
3. 构造方法
   1. 无参构造方法
   构造一个空的LinkedList
   ```
   /**
     * Constructs an empty list.
     */
    public LinkedList() {
    }
   ```
   2. 指定集合构造器
   将一个集合作为参数传入，并将集合中的所有元素取出，重构成双向链表
   ```
   /**
     * Constructs a list containing the elements of the specified
     * collection, in the order they are returned by the collection's
     * iterator.
     *
     * @param  c the collection whose elements are to be placed into this list
     * @throws NullPointerException if the specified collection is null
     */
    public LinkedList(Collection<? extends E> c) {
        this();
        addAll(c);
    }
   ```

3. 各方法时间复杂度

4. 初始容量大小及扩容

5. Synchronized
