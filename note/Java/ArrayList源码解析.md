# ArrayList特性
1.  结构树
![ArrayList结构树](https://github.com/WuwenGitHub/Notebook/blob/master/pics/ArrayList%E7%BB%93%E6%9E%84%E6%A0%91.png)
2. 存储结构
```
    /**
     * The array buffer into which the elements of the ArrayList are stored.
     * The capacity of the ArrayList is the length of this array buffer. Any
     * empty ArrayList with elementData == DEFAULTCAPACITY_EMPTY_ELEMENTDATA
     * will be expanded to DEFAULT_CAPACITY when the first element is added.
     */
    transient Object[] elementData; // non-private to simplify nested class access
```
3. 各方法时间复杂度
   1. O(1)
   * size()、isEmpty()、get()、set()、iterator()、listIterator()
   2. 扩容
   * add()
   3. Linear Time
    * other operations
4. 初始容量大小及扩容
   1. 初始容量
      1. 默认初始容量
      ```
      private static final int DEFAULT_CAPACITY = 10;
      ...
      /**
      * Constructs an empty list with an initial capacity of ten.
      */
      public ArrayList() {
              this.elementData = DEFAULTCAPACITY_EMPTY_ELEMENTDATA;
      }
      ```
      2. 指定初始容量大小
      ```
      /**
       * Constructs an empty list with the specified initial capacity.
       *
       * @param  initialCapacity  the initial capacity of the list
       * @throws IllegalArgumentException if the specified initial capacity
       *         is negative
       */
      public ArrayList(int initialCapacity) {
          if (initialCapacity > 0) {
              this.elementData = new Object[initialCapacity];
          } else if (initialCapacity == 0) {
              this.elementData = EMPTY_ELEMENTDATA;
          } else {
              throw new IllegalArgumentException("Illegal Capacity: "+
                                                 initialCapacity);
          }
      }
      ```
      3. 根据传入集合确定
      ```
      /**
       * Constructs a list containing the elements of the specified
       * collection, in the order they are returned by the collection's
       * iterator.
       *
       * @param c the collection whose elements are to be placed into this list
       * @throws NullPointerException if the specified collection is null
       */
      public ArrayList(Collection<? extends E> c) {
          elementData = c.toArray();
          if ((size = elementData.length) != 0) {
              // c.toArray might (incorrectly) not return Object[] (see 6260652)
              if (elementData.getClass() != Object[].class)
                  elementData = Arrays.copyOf(elementData, size, Object[].class);
          } else {
              // replace with empty array.
              this.elementData = EMPTY_ELEMENTDATA;
          }
      }
      ```
   2. 扩容
   ```
   /**
     * The maximum size of array to allocate.
     * Some VMs reserve some header words in an array.
     * Attempts to allocate larger arrays may result in
     * OutOfMemoryError: Requested array size exceeds VM limit
     */
    private static final int MAX_ARRAY_SIZE = Integer.MAX_VALUE - 8;

   /**
     * Increases the capacity to ensure that it can hold at least the
     * number of elements specified by the minimum capacity argument.
     *
     * @param minCapacity the desired minimum capacity
     */
    private void grow(int minCapacity) {
        // overflow-conscious code
        int oldCapacity = elementData.length;
        int newCapacity = oldCapacity + (oldCapacity >> 1);
        if (newCapacity - minCapacity < 0)
            newCapacity = minCapacity;
        if (newCapacity - MAX_ARRAY_SIZE > 0)
            newCapacity = hugeCapacity(minCapacity);
        // minCapacity is usually close to size, so this is a win:
        elementData = Arrays.copyOf(elementData, newCapacity);
    }

    private static int hugeCapacity(int minCapacity) {
        if (minCapacity < 0) // overflow
            throw new OutOfMemoryError();
        return (minCapacity > MAX_ARRAY_SIZE) ?
            Integer.MAX_VALUE :
            MAX_ARRAY_SIZE;
    }
   ```
5. Synchronized
   1. UnSynchronized
   多线程下保证同步方法
      1. 通过同步一些自然封装到列表的类来完成
      ```
      This is typically accomplished by synchronizing on 
      some object that naturally encapsulates the list.
      ```
      2. 用Collection.synchronize方法“包装”该List
      ```
      If no such object exists, the list should be "wrapped" using the
      {@link Collections#synchronizedList Collections.synchronizedList}
      method.  This is best done at creation time, to prevent accidental
      unsynchronized access to the list:<pre>
      	List list = Collections.synchronizedList(new ArrayList(...));</pre>
      ```
   2. fail-fast
