# 一、概览
  容器主要包括 Collection 和 Map 两种。
  ![集合总体框架](https://github.com/WuwenGitHub/Notebook/blob/master/pics/%E9%9B%86%E5%90%88%E6%80%BB%E4%BD%93%E6%A1%86%E6%9E%B6.png "集合总体框架图")

## Collection
  包含Set、List、Queue
  ![Collection结构体系图1](https://github.com/WuwenGitHub/Notebook/blob/master/pics/Collection%E7%BB%93%E6%9E%84%E4%BD%93%E7%B3%BB%E5%9B%BE.jpg "Collection结构体系图1")
  ![Collection结构体系图2](https://github.com/WuwenGitHub/Notebook/blob/master/pics/Collection%E9%9B%86%E5%90%88%E4%BD%93%E7%B3%BB%E5%9B%BE2.png "Collection结构体系图2")

### Set
   * HashSet  
       1. 使用HashMap存储数据  
           * private transient HashMap<E,Object> map; 
           * private static final Object PRESENT = new Object();
           * public boolean add(E e){ return map.put(e, PRESENT) = null; }
       2. fast-fail机制  
       3. 非同步  
       4. 迭代时间=HashSet中元素个数+HashMap容量
       5. 可存null
   * TreeSet
       1. TreeMap实现
       2. fast-fail机制
       3. 非同步
       4. 有序存储
             自然排序(默认)或指定方式排序(Comparator)  
             ```public TreeSet(){...}  自然排序  
               public TreeSet(Comparator<? super E> comparator){...} 指定方式排序
            ```
   * LinkedHashSet  
        1. 继承HashSet  
        2. 存储结构  
            * 使用LinkedHashMap存储  
            * 双向循环链表  
            * 按插入顺序存储  
        3. fast-fail机制  
        4. 非同步 
### List
   * ArrayList
       1. 继承 **AbstractList** 及 **fast-fail** 机制
        `protected transient int modCount = 0;`  
        iterator() iterator / listIterator(int) listIterator方法
      2. 实现 **RandomAccess** 接口
      3. elementData使用 **transient** 修饰  
        `transient Object[] elementData; // non-private to simplify nested class access`
      4. 非同步  
            多线程情况下的添加、删除操作
      5. 存储元素  
           使用动态数组存储
      >permits all elements(including **null**)

   * Vector
       1. 继承 **AbstractList**
       2. 实现 **RandomAccess** 接口
       3. elementData
          使用动态数组存储  
        `protected Object[] elementData;`  
       4. 方法用 **synchronized** 修饰

   * LinkedList
       1. 实现 **List** 及 **Deque** 接口  
            双向循环链表(Doubly-linked list)  
            permits all elements(including **null**)  
       2. 非同步
       3. fast-fail
       4. 可用作栈、队列和双端队列  
           * public E peek() {...} 
           * public E poll() {...}
           * public void push(E e) {...}
           * public E pop() {...}


### Queue
   * LinkedList
   * PriorityQueue  

## Map
![Map集合框架](https://github.com/WuwenGitHub/Notebook/blob/master/pics/Map%E9%9B%86%E5%90%88%E6%A1%86%E6%9E%B6.png "Map集合框架")
   * HashMap  
        1.非同步, 允许key为null(存储在数组首位,index=0)
        2. fast-fail
        3. 存储结构
	   * 数组+链表+红黑树(按2倍进行扩容)  
                   `transient Node<K,V>[] table;`
	   * 存值遵循LRU(Least Recently Used)算法  
	   * 使用拉链法解决冲突
	   * 数组类型**Node<K,V>**
	   * Node<K,V>结构
	   ```java
	   static class Node<K,V> implements Map.Entry<K,V> {}{
                  final int hash;  
                  final K key;  
                  V value;  
                  Node<K,V> next;
              }
	      ```
	4. entrySet实现?
   * HashTable  
        1. 实现**Map**接口
	>permits **null** values and the **null** key

   * LinkedHashMap

   * TreeMap


