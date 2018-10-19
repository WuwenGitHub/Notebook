<h1>MyBatis与Hibernate的区别</h1>
<ol>
	<li>Hibernate是全自动的，而MyBatis是半自动的</li>
	<pre>Hibernate：完全可以通过对象关系模型实现对数据库的操作，拥有完整的JavaBean对象与数据库的映射结构来自动生成sql<br>MyBatis：仅有基本的字段映射，对象数据以及对象实际关系任然需要通过手写sql来实现和管理</pre>
	<li>Hibernate数据库移植性远大于MyBatis</li>
	<pre>Hibernate：通过强大的映射结构和hql语言，大大降低了对象与数据库(Oracle、mysql等)的耦合性<br>MyBatis：由于需要手写sql，因此与数据库的耦合性直接取决于程序员写sql的语法，如果不具通用性而用了很多某数据库特性的sql语句的话，移植性也会随之降低很多，成本很高</pre>
	<li>Hibernate拥有完整的日志系统，MyBatis欠缺一些</li>
	<pre>Hibernate：日志系统非常健全，涉及广泛，包括：sql记录、关系异常、优化警告、缓存提示、脏数据警告等<br>MyBatis：除基本功能外，功能薄弱很多</pre>
	<li>MyBatis相比Hibernate需要关心很多细节</li>
	<pre>Hibernate配置要比MyBatis复杂的多，学习成本比MyBatis高。但也正因为MyBatis使用简单，才导致它要比Hibernate关心很多技术细节。MyBatis由于不用考虑很多细节，开发模式上与传统jdbc区别很小，因此很容易上手并开发项目，但忽略细节会导致项目前期bug较多，因而开发出相对稳定的软件很慢，而开发出软件却很快。Hibernate则正好与之相反。但是如果使用Hibernate很熟练的话，实际上开发效率丝毫不差于甚至超越MyBatis。</pre>
	<li>sql优化上，MyBatis比Hibernate方便</li>
	<pre>MyBatis：所有sql都写在xml中，灵活度高，能够直接维护sql<br>Hibernate：sql很多为自动生成，灵活度低，无法直接维护；hql功能没有sql强大，拥有局限性；开发模式与ORM不同，需要转换思维</pre>
</ol>
<table border="1">
	<tr>
		<th colspan="2">MyBatis</th>
		<th colspan="2">Hibernate</th>
	</tr>
	<tr>
		<td width="25%">优点</td>
		<td width="25%">缺点</td>
		<td width="25%">优点</td>
		<td width="25%">缺点</td>
	</tr>
	<tr>
		<td>
			<ul>
				<li>小巧</li>
				<li>方便</li>
				<li>高效</li>
				<li>简单</li>
				<li>直接</li>
				<li>半自动</li>
				<li>sql优化方便，便于维护</li>
			</ul>
		</td>
		<td>
			<ul>
				<li>数据库移植性低</li>
				<li>日志功能不够健全</li>
				<li>稳定性差</li>
				<li>二级缓存机制不佳</li>
			</ul>
		</td>
		<td>
			<ul>
				<li>强大</li>
				<li>方便</li>
				<li>高效</li>
				<li>复杂</li>
				<li>绕弯子</li>
				<li>全自动</li>
				<li>数据库无关性好</li>
				<li>O/R映射能力强</li>
				<li>有更好的二级缓存机制，可以使用第三方缓存</li>
			</ul>
		</td>
		<td>
			<ul>
				<li>无法直接维护sql</li>
				<li>sql灵活度低</li>
			</ul>
		</td>
	</tr>
</table>