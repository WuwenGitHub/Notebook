<h1>@Transactional</h1>
<h4>常用参数说明</h4>
<table>
	<tr>
		<th>参数名</th>
		<th>功能描述</th>
	</tr>
	<tr>
		<td>readOnly</td>
		<td>用于设置当前事务是否为只读事务，true表示只读，false表示可读写，默认值为false </td>
	</tr>
	<tr>
		<td>rollbackFor</td>
		<td>用于设置需要进行回滚的异常类数组，当方法中抛出指定异常数组中的异常时，则进行事务回滚</td>
	</tr>
	<tr>
		<td>rollbankForClassName</td>
		<td>用于设置需要进行回滚的异常类名称数组，当方法中抛出指定异常名称数组中的异常时，则进行事务回滚</td>
	</tr>
	<tr>
		<td>noRollbackFor</td>
		<td>用于设置不需要进行回滚的异常类数组，当方法中抛出指定异常数组中的异常时，不进行事务回滚</td>
	</tr>
	<tr>
		<td>noRollbackForClassName</td>
		<td>用于设置不需要进行回滚的异常类名称数组，当方法中抛出指定异常名称数组中的异常时，不进行事务回滚</td>
	</tr>
	<tr>
		<td>propagation</td>
		<td>设置事务的传播行为</td>
	</tr>
	<tr>
		<td>isolation</td>
		<td>用于设置底层数据库的事务隔离级别</td>
	</tr>
	<tr>
		<td>timeout</td>
		<td>用于设置事务的超时秒数，默认为-1，永不超时</td>
	</tr>
</table>
<h4>注意事项：</h4>
<ol>
	<li>只能被应用到pubic方法上，对于其他非public方法，会被忽略，将不会抛出任何异常</li>
	<li>默认情况下，只有来自外部的方法调用才会被AOP代理捕获</li>
	<li>默认情况下，只有未检查异常会导致事务回滚，在遇到检查型异常时不会回滚</li>
</ol>
<h4>函数之间互相调用</h4>
<h6>同一个类中函数互相调用</h6>
<p>同一个类AClass中，有两个函数aFunction、aInnerFunction。aFunction调用aInnerFunction，且aFunction会被外部调用</p>
<ol>
	<li>情况一</li>
	<p>aFunction添加了@Transaction注解，aInnerFunction没有添加，aFunction抛异常</p>
```java
public class AClass {
	@Transactional(rollbackFor = Exception.class)
    	public void aFunction() {
		//todo: 数据库操作A(增，删，该)
		// 调用内部没有添加@Transactional注解的函数
        	aInnerFunction();
	}
	private void aInnerFunction() {
		//todo: 操作数据B(做了增，删，改 操作)
        	throw new RuntimeException("函数执行有异常!");
    	}
}
```
</ol>
