<h1>存储引擎类别</h1>
MyISAM  InnoDB
<h1>简单对比</h1>
<table>
	<tr>
		<th></th>
		<th>MyISAM</th>
		<th>InnoDB</th>
	</tr>
	<tr>
		<td><b>构成上区别</b></td>
		<td><pre>
			每个MyISAM在磁盘上存储为三个文件。第一个文件的名字以表的名字开始，扩展名指出文件类型
			.frm文件：存储表定义
			.MYD(MYData)文件：数据文件
			.MYI(MYIndex)文件：索引文件
		</pre></td>
		<td>基于磁盘的资源是InnoDB表空间数据文件和它的日志文件，InnoDB表的大小只受限于操作系统文件的大小，一般为2GB</td>
	</tr>
	<tr>
		<td><b>事务处理上</b></td>
		<td>MyISAM类型的表强调的是<b>性能</b>，其执行度比InnoDB类型<b>更快</b>，但是<em>不提供事务支持</em></td>
		<td>InnoDB<em>支持事务、外部键等高级数据库功能</em></td>
	</tr>
	<tr>
		<td><pre>
		SELECT
		UPDATE
		INSERT
		DELETE</pre></td>
		<td>如果执行大量的SELECT操作，MyISAM是更好的选择</td>
		<td><ol>
			<li>如果你的数据执行大量的<b>INSERT</b>或<b>UPDATE</b>，出于性能方面的考虑，应该使用InnoDB表</li>
			<li><b>DELETE FROM TABLE</b>时，InnoDB不会重新建立表，而是一行一行的删除</li>
			<li><b>LOAD TABLE FROM</b>操作对InnoDB是不起作用的，解决方法是首先把InnoDB表改成MyISAM表，导入数据后再改成InnoDB表，但是对于使用的额外的InnoDB特性(例如外键)的表不适用</li>
		</ol></td>
	</tr>
</table>