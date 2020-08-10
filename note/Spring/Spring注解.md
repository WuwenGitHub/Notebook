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
	<pre>
	aFunction添加了@Transaction注解，aInnerFunction没有添加，aFunction抛异常
		<code>
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
		</code>
	结果：两个函数的操作数据都会回滚
	</pre>

	<li>情况二</li>
	<pre>
	两个函数都添加了@Transactional注解。aInnerFunction抛异常。
		<code>
public class AClass {

    @Transactional(rollbackFor = Exception.class)
    public void aFunction() {
        //todo: 数据库操作A(增，删，该)
        aInnerFunction(); // 调用内部没有添加@Transactional注解的函数
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class)
    private void aInnerFunction() {
        //todo: 操作数据B(做了增，删，改 操作)
        throw new RuntimeException("函数执行有异常!");
    }

}
		</code>
	结果：同第一种情况一样，两个函数对数据库操作都会回滚。因为同一个类中函数相互调用的时候，内部函数添加@Transactional注解无效。@Transactional注解只有外部调用才有效。
	</pre>

	<li>情况三</li>
	<pre>
	aFunction不添加注解，aInnerFunction添加注解。aInnerFunction抛异常。
		<code>
public class AClass {

    public void aFunction() {
        //todo: 数据库操作A(增，删，该)
        aInnerFunction(); // 调用内部没有添加@Transactional注解的函数
    }

    @Transactional(rollbackFor = Exception.class)
    protected void aInnerFunction() {
        //todo: 操作数据B(做了增，删，改 操作)
        throw new RuntimeException("函数执行有异常!");
    }

}
		</code>
	结果：两个函数对数据库的操作都不会回滚。因为内部函数@Transactional注解添加和没添加一样。
	</pre>

	<li>情况四</li>
	<pre>
	aFunction添加了@Transactional注解，aInnerFunction函数没有添加。aInnerFunction抛异常，不过在aFunction里面把异常抓出来了。
		<code>
public class AClass {

    @Transactional(rollbackFor = Exception.class)
    public void aFunction() {
        //todo: 数据库操作A(增，删，该)
        try {
            aInnerFunction(); // 调用内部没有添加@Transactional注解的函数
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void aInnerFunction() {
        //todo: 操作数据B(做了增，删，改 操作)
        throw new RuntimeException("函数执行有异常!");
    }

}
		</code>
	结果：两个函数里面的数据库操作都成功。事务回滚的动作发生在当有@Transactional注解函数有对应异常抛出时才会回滚。(当然了要看你添加的@Transactional注解有没有效)。
	</pre>
</ol>
<h6>不同类中函数相互调用</h6>
<p>两个类AClass、BClass。AClass类有aFunction、BClass类有bFunction。AClass类aFunction调用BClass类bFunction。最终会在外部调用AClass类的aFunction。</p>
<ol>
	<li>情况一</li>
	<pre>
	aFunction添加注解，bFunction不添加注解。bFunction抛异常。
		<code>
@Service()
public class AClass {

    private BClass bClass;

    @Autowired
    public void setbClass(BClass bClass) {
        this.bClass = bClass;
    }

    @Transactional(rollbackFor = Exception.class)
    public void aFunction() {
        //todo: 数据库操作A(增，删，该)
        bClass.bFunction();
    }

}

@Service()
public class BClass {

    public void bFunction() {
        //todo: 数据库操作A(增，删，该)
        throw new RuntimeException("函数执行有异常!");
    }
}
		</code>
	结果：两个函数对数据库的操作都回滚了。
	</pre>

	<li>情况二</li>
	<pre>
	aFunction、bFunction两个函数都添加注解，bFunction抛异常。
		<code>
@Service()
public class AClass {

    private BClass bClass;

    @Autowired
    public void setbClass(BClass bClass) {
        this.bClass = bClass;
    }

    @Transactional(rollbackFor = Exception.class)
    public void aFunction() {
        //todo: 数据库操作A(增，删，该)
        bClass.bFunction();
    }

}

@Service()
public class BClass {

    @Transactional(rollbackFor = Exception.class)
    public void bFunction() {
        //todo: 数据库操作A(增，删，该)
        throw new RuntimeException("函数执行有异常!");
    }
}
		</code>
	结果：两个函数对数据库的操作都回滚了。两个函数里面用的还是同一个事务。这种情况下，你可以认为事务rollback了两次。两个函数都有异常。
	</pre>

	<li>情况三</li>
	<pre>
	aFunction、bFunction两个函数都添加注解，bFunction抛异常。aFunction抓出异常。
		<code>
@Service()
public class AClass {

    private BClass bClass;

    @Autowired
    public void setbClass(BClass bClass) {
        this.bClass = bClass;
    }

    @Transactional(rollbackFor = Exception.class)
    public void aFunction() {
        //todo: 数据库操作A(增，删，该)
        try {
            bClass.bFunction();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}

@Service()
public class BClass {

    @Transactional(rollbackFor = Exception.class)
    public void bFunction() {
        //todo: 数据库操作A(增，删，该)
        throw new RuntimeException("函数执行有异常!");
    }
}
		</code>
	结果：两个函数数据库操作都没成功。而且还抛异常了。org.springframework.transaction.UnexpectedRollbackException: Transaction rolled back because it has been marked as rollback-only。看打印出来的解释也很好理解把。咱们也可以这么理解，两个函数用的是同一个事务。bFunction函数抛了异常，调了事务的rollback函数。事务被标记了只能rollback了。程序继续执行，aFunction函数里面把异常给抓出来了，这个时候aFunction函数没有抛出异常，既然你没有异常那事务就需要提交，会调事务的commit函数。而之前已经标记了事务只能rollback-only(以为是同一个事务)。直接就抛异常了，不让调了。
	</pre>

	<li>情况四</li>
	<pre>
	@Service()
public class AClass {

    private BClass bClass;

    @Autowired
    public void setbClass(BClass bClass) {
        this.bClass = bClass;
    }

    @Transactional(rollbackFor = Exception.class)
    public void aFunction() {
        //todo: 数据库操作A(增，删，该)
        try {
            bClass.bFunction();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}

@Service()
public class BClass {

    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class)
    public void bFunction() {
        //todo: 数据库操作A(增，删，该)
        throw new RuntimeException("函数执行有异常!");
    }
}
		</code>
	bFunction函数里面的操作回滚了，aFunction里面的操作成功了。有了前面情况2的理解。这种情况也很好解释。两个函数不是同一个事务了。
	</pre>
</ol>
