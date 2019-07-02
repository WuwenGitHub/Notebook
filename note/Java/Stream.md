# Stream的基本语法
   * 使用函数式编程方法
   * 聚合操作
   * 查找、遍历、过滤及常见计算等

### 聚合操作
数据类：  
```
public class Student {
	int no;
	String name;
	String  sex;
	float height;

	public Student(int no, String name, String sex, float height) {
		this.no = no;
		this.name = name;
		this.sex = sex;
		this.height = height;
	}

	*****
}

Student stuA = new Studnet(1, "A", "M", 184);
Student stuB = new Studnet(2, "B", "G", 163);
Student stuC = new Studnet(3, "C", "M", 175);
Student stuD = new Studnet(4, "D", "G", 158);
Student stuE = new Studnet(5, "E", "M", 170);
Student stuF = new Studnet(6, "F", "G", 166);

List<Student> list = new ArrayList<>();
list.add(stuA);
list.add(stuB);
list.add(stuC);
list.add(stuD);
list.add(stuE);
list.add(stuF);
```
对Sex = "G"的Student的获取
1. Iterator迭代/外部迭代  
通过显示调用Iterator对象的hasNext和next方法完成迭代
```
Iterator<Student> iterator = list.iterator();
while(iterator.hasNext()) {
	Student stu = iterator.next();
	if (stu.getSex().equals("G")) {
		System.out.println(stu.toString());
	}
}
```
2. 聚合操作/内部迭代  
通过stream方法创建Stream,然后通过filter方法对源数据进行过滤，最后通过forEach方法进行迭代
常与Lambda表达式一起使用
```
list.stream()
    .filter(student -> student.getSex().equals("G"))
    .forEach(student -> System.out.println(student.toString()));
```
3. 差异
   
   1. Iterator迭代  
   可自行控制对元素的处理以及处理方式，但只能顺序处理
   2. 聚合操作  
   无法控制对元素的迭代（由系统内部实现），迭代不一定有序，可以并行

### Stream
1. 聚合操作3步骤
   
   1.创建Stream
   通过stream()方法，取得集合对象的数据集
   2.Intermediate
   通过一系列中间(Intermediate)方法，对数据集进行过滤、检索等数据集的再次处理
   3.Terminal
   通过最终(terminal)方法完成对数据集中元素的处理

2. Stream的操作分类
   * Intermediate  
   map(mapToInt,flatMap等)、filter、distinct、sorted、peek、skip、parallel、sequential、unordered
   * Terminal  
   forEach、forEachOrdered、toArray、reduce、collect、min、max、count、iterator
   * Short-circuiting  
   anyMatch、allMatch、noneMatch、findFirst、findAny、limit

3. 惰性求值和及早求值方法
   * 惰性求值方法  
   只描述Stream，最终不产生新集合的方法，例如filter
   * 及早求值方法  
   最终会从Stream产生新值的方法，例如count

4. 创建Stream
   
   * Stream接口的静态工厂方法
      * of
      * generator
      * iterate
      * empty
   * Collection接口和数组的默认方法
   * 其他
      * Random.ints()
      * BitSet.stream()
      * Pattern.splitAsStream(java.lang.CharSequence)
      * JarFile.stream()
