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
<h1>Spring MVC工作流程</h1>
<img src="https://github.com/WuwenGitHub/Notebook/blob/master/pics/SpringMVC%E8%AF%B7%E6%B1%82%E5%A4%84%E7%90%86%E8%BF%87%E7%A8%8B.png">
<img src="https://github.com/WuwenGitHub/Notebook/blob/master/pics/SpringMVC%E5%B7%A5%E4%BD%9C%E6%B5%81%E7%A8%8B%E5%9B%BE2.png">
<img src="https://github.com/WuwenGitHub/Notebook/blob/master/pics/SpringMVC%E5%B7%A5%E4%BD%9C%E6%B5%81%E7%A8%8B%E5%9B%BE3.png">
<h5>工作流程描述</h5>
<ul>
	<ol>用户向服务器发送请求，请求被Spring前端控制DispatcherServlet捕获</ol>
	<ol>DispatcherServlet对请求URL进行解析，得到请求资源标识符(URI)。然后根据该URI，调用HandlerMapping获得该Handler配置的所有相关的对象(包括Handler对象以及Handler对象对应的拦截器)，最后以HandlerExecutionChain对象的形式返回</ol>
	<ol>DispatcherServlet根据获得的Handler，选择一个合适的HandlerAdapter(注:如果成功获得HandlerAdapter后，此时开始执行拦截器的preHandler(...)方法)</ol>
	<ol>提取Request中的模型数据，填充Handler入参，开始执行Handler(Controller)</ol>
	在填充Handler的入参过程中，根据配置，Spring将帮你做一些额外的工作:
	<ul>
		<li>HttpMessageConveter：将请求消息(如json、xml等数据)转换成一个对象，将对象转换为指定的响应信息</li>
		<li>数据转换：对请求消息进行数据转换。如String转换成Integer、Double等</li>
		<li>数据根式化：对请求消息进行数据格式化。如将字符串转换成格式化数字或格式化日期等</li>
		<li>数据验证：验证数据的有效性(长度、格式等)，验证结果存储到BindingResult或Error中</li>
	</ul>
	<ol>Handler执行完成后，向DispatcherServlet返回一个ModelAndView对象</ol>
	<ol>根据返回的ModelAndView，选择一个适合的ViewResolver(必须是已经注册到Spring容器中的ViewResolver)返回给DispatcherServlet</ol>
	<ol>ViewResolver结合Model和View，来渲染视图</ol>
	<ol>将渲染结果返回给客户端</ol>
</ul>
