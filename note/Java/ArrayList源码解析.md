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
4.  初始容量大小及扩容
   1. 初始容量
   * 默认初始容量、指定初始容量大小、根据传入集合确定
   2. 扩容