<h1>Spring MVC概述</h1>
<ul>
	<li>框架类型</li>
	一种基于<b>注解</b>的web框架<br>
	<li>实现</li>
	以请求为驱动，围绕<b>Servlet</b>设计，将请求分发给控制器(一般不直接处理请求，而是将其委托给Spring上下文中的其他<b>bean</b>，通过Spring的<b>依赖注入</b>功能，将这些bean注入到控制器中)，然后通过模型对象、分派器来展现请求结果视图<br>
	<li>主要组成</li>
	DispatcherServlet+处理器(控制器)+视图解析器+视图
	<li>核心</li>
	<ul>
		<li><b>处理器映射</b></li>
		选择使用哪个处理器来处理请求
		<li><b>视图解析器</b></li>
		选择结果应该如何渲染
	</ul>
</ul>
<h1>Spring MVC运行原理</h1>
<img src="https://github.com/WuwenGitHub/Notebook/blob/master/pics/SpringMVC%E8%AF%B7%E6%B1%82%E5%A4%84%E7%90%86%E8%BF%87%E7%A8%8B.png">
<img src="https://github.com/WuwenGitHub/Notebook/blob/master/pics/SpringMVC%E5%B7%A5%E4%BD%9C%E6%B5%81%E7%A8%8B%E5%9B%BE2.png">
<img src="https://github.com/WuwenGitHub/Notebook/blob/master/pics/SpringMVC%E5%B7%A5%E4%BD%9C%E6%B5%81%E7%A8%8B%E5%9B%BE3.png">
