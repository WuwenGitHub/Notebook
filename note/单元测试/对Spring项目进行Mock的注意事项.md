# MockBean
1. 特性
   * 用于对Spring ApplicationContext进行mocks.
   * 若要mock的bean在Spring ApplicationContext早已存在，则对其进行替换
   * 若要mock的bean在Spring ApplicationContext不存在，则将该项bean添加到Context中
   * 对于在ApplicationContext中存在但并未生成bean的依赖进行mock时，将会和其依赖一并添加入Context.
   * 相关链接：
      * [Mocking and Spying Beans](https://docs.spring.io/spring-boot/docs/current/reference/html/boot-features-testing.html#boot-features-testing-spring-boot-applications-mocking-beans "Mocking and Spying Beans")
      * [MockBean API](https://docs.spring.io/spring-boot/docs/2.1.7.RELEASE/api/org/springframework/boot/test/mock/mockito/MockBean.html)
2. Context
   * TestContext
      * 为其负责的测试用例提供context的管理及缓存的支持.
      * 对实现了ApplicationContextAware接口的测试类将提供对ApplicationContext的访问
      * 一旦通过TestContext为测试加载了ApplicationContext,则被缓存的context将会应用到所有的测试用例中,且该context将由加载它的所有配置参数所所生成的context cache key唯一确定
   * Context Management and Caching
      * Spring TestContext Framework提供对Spring中ApplicationContext和WebApplicationContext的统一加载及所涉及的缓存的访问
      * 默认情况下，对ApplicationContext的加载将会对所有测试用例起作用，这将加快测试用例的执行速度；但是，若在下一个测试用例需要启用不同的context环境时，将会对ApplicationContext进行重启
   * Context Caching
      * locations
      * classes
      * contextInitializerClasses
      * contextCustomizers
      * contextLoader
      * parent
      * activeProfiles
      * propertySourceLocations
      * propertySourceProperties
      * resourceBasePath
   * 相关链接:
      * [Content Management](https://docs.spring.io/spring/docs/current/spring-framework-reference/testing.html#testcontext-ctx-management)
      * [Context Management and Caching](https://docs.spring.io/spring/docs/current/spring-framework-reference/testing.html#testing-ctx-management)
      * [Context Caching](https://docs.spring.io/spring/docs/current/spring-framework-reference/testing.html#testcontext-ctx-management-caching)
3. 危害
   * 破坏ApplicationContext的缓存规则，致使Spring Boot重启多次
   * 导致每个ApplicationContext中的contextCustomizer不同,从而导致存储在context cache中的ApplicationContext的uniquely key不同，致使无法在测试类之间共享
   * 相关链接
      * [org.springframework.boot.test.mock.mockito.MockitoContextCustomizerFactory源码](https://github.com/spring-projects/spring-boot/blob/3198bf4f59a375c3b4d70202b34be710788e6f40/spring-boot-test/src/main/java/org/springframework/boot/test/mock/mockito/MockitoContextCustomizerFactory.java)