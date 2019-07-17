# LinkedList特性

1. 结构树
![LinkedList结构树](https://github.com/WuwenGitHub/Notebook/blob/master/pics/LinkedList%E7%BB%93%E6%9E%84%E6%A0%91.png "LinkedList结构树")

2. 存储/数据结构
     双向链表
     ```java
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
   ```java
   /**
     * Constructs an empty list.
     */
    public LinkedList() {
    }
   ```
   2. 指定集合构造器
   将一个集合作为参数传入，并将集合中的所有元素取出，重构成双向链表
   ```java
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
   1. O(1)
      1. getFirst()
      2. getLast()
      3. removeFirst()
      4. removeLast()
      5. addFirst(E e)
      6. addLast(E e)
      7. size()
      8. add(E e)
      9. peek()
      10. element()
      11. poll()
      12. remove()
      13. offer(E e)
      14. offerFirst(E e)
      15. offerLast(E e)
      16. peekFirst()
      17. peekLast()
      18. peekLast()
      19. pollLast()
      20. push(E e)
      21. pop()
      22. spliterator()

   2. O(n)
      1. contains(Object o)
      2. remove(Object o)
      3. addAll(Collection<? extends E> c)
      4. addAll(int index, Collection<? extends E> c)
      5. clear()
      6. get(int index)
      7. set(int index, E element)
      8. add(int index, E element)
      9. remove(int index)
      10. indexOf(Object o)
      11. indexOf(Object o)
      12. lastIndexOf(Object o)
      13. removeFirstOccurrence(Object o)
      14. removeLastOccurrence(Object o)
      15. removeLastOccurrence(Object o)
      16. descendingIterator()
      17. clone()
      18. toArray()
      19. toArray(T[] a)

4. 涉及GC操作方法
   1. removeFirst()
   2. removeLast()
   3. clear()
   4. poll()
   5. remove()
   6. peekLast()
   7. pollLast()
   8. pop()

4. 初始容量大小及扩容
   1. 初始容量
      与构造函数有关
   2. 扩容
       与添加、删除的元素个数有关

5. Synchronized
   多线程下不安全，可采用与ArrayList相同的方式来保证同步
   ```html
   <p><strong>Note that this implementation is not synchronized.</strong>
   If multiple threads access a linked list concurrently, and at least
   one of the threads modifies the list structurally, it <i>must</i> be
   synchronized externally.  (A structural modification is any operation
   that adds or deletes one or more elements; merely setting the value of
   an element is not a structural modification.)  This is typically
   accomplished by synchronizing on some object that naturally
   encapsulates the list.

   If no such object exists, the list should be "wrapped" using the
   {@link Collections#synchronizedList Collections.synchronizedList}
   method.  This is best done at creation time, to prevent accidental
   unsynchronized access to the list:<pre>
      List list = Collections.synchronizedList(new LinkedList(...));</pre>
   ```
