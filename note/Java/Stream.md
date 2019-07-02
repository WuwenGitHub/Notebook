# Stream的基本语法
   * 使用函数式编程方法
   * 聚合操作
   * 查找、遍历、过滤及常见计算等

### 聚合操作
数据类：  
'''
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
'''
现List中有6个Student对象，而对Sex = "G"的Student的获取  

Iterator迭代|聚合操作
--------------|-----------:
'''
Iterator<Student> iterator = list.iterator();
while(iterator.hasNext()) {
	Student stu = iterator.next();
	if (stu.getSex().equals("G")) {
		System.out.println(stu.toString());
	}
}
'''
