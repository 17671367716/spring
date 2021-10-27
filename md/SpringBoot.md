### 常见注解
#### @Configuration

```java
//	如果 @Configuration(proxyBeanMethods = true) ，则该配置内里面的 bean 都是单实例，即 会在容器内获取 bean
//	如果 @Configuration(proxyBeanMethods = false) ，则不会将 bean 放入容器，即 每次获取 bean 都不一样
//	告诉 SpringBoot 这是一个配置类（配置文件）
@Configuration
public class MyConfig {

    //	配置类里使用 @Bean 标注方法给容器注册组件
    //	默认是单例
    @Bean
    public void zhangsan(){
        User user = new User("zhangsan",18);
    }

    @Bean
    public void lisi(){
        new User("lisi",20);
    }
}
```

获取容器里面的 bean

```java
@Component
public class SpringUtils implements ApplicationContextAware {

    private static ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        if(SpringUtils.applicationContext == null){
            SpringUtils.applicationContext = applicationContext;
        }
    }

    //  获取applicationContext
    public static ApplicationContext getApplicationContext(){
        return applicationContext;
    }

    //  通过name获取bean
    public static Object getBean(String name){
        return getApplicationContext().getBean(name);
    }

    //通过class获取Bean.
    public static <T> T getBean(Class<T> clazz){
        return getApplicationContext().getBean(clazz);
    }

    //通过name,以及Clazz返回指定的Bean
    public static <T> T getBean(String name,Class<T> clazz){
        return getApplicationContext().getBean(name, clazz);
    }
}
```
#### @Import导入组件

```java
//	import({Class1.class,Class2.class})
//	给容器中指定创建出导入类的组件，默认组件名称是券类名
@Configuration()
@Import({User.class})
public class JavaConfig {

    @Bean
    public User zhangsan(){
        return new User("zhangsan",18);
    }

    @Bean
    public User lisi(){
        return new User("lisi",20);
    }
}
```

![Snipaste_2021-09-06_09-38-17](imgs\boot\Snipaste_2021-09-06_09-38-17.png)

![Snipaste_2021-09-06_09-40-11](imgs\boot\Snipaste_2021-09-06_09-40-11.png)
#### @Conditional条件装配

满足相应条件就加载bean，在springboot自动装配中会大量使用

可以用在类和方法上

用在类上时，条件成立时，该类的组件才会生效

用在方法上时，条件成立时，该方法返回的组件才会生效

bean的加载时有顺序的，作用在方法上时，要将条件bean写在前面

![微信图片_20210608101301](D:\Project\up\spring\md\imgs\boot\微信图片_20210608101301.png)

```java
@Configuration()
@Import({User.class})
public class JavaConfig {

    @Bean
    public User zhangsan(){
        return new User("zhangsan",18);
    }
//	这里先注释掉 lisi() 上面的 @bean
//    @Bean
    public User lisi(){
        return new User("lisi",20);
    }
}
```

```java
@SpringBootApplication
public class Springboot01Application {

	public static void main(String[] args) {

		ConfigurableApplicationContext run = SpringApplication.run(Springboot01Application.class, args);
		String[] names = run.getBeanDefinitionNames();
		for (String name : names) {
			System.out.println( " ------  " + name);
		}

		boolean zhangsan = run.containsBean("zhangsan");
		boolean lisi = run.containsBean("lisi");
		System.out.println(zhangsan);//	true
		System.out.println(lisi);	//	false
	}
}
```

___

```java
@Configuration()
@Import({User.class})
public class JavaConfig {
    
    @Bean
    public User lisi(){
        return new User("lisi",20);
    }
//	在这里加上一个 ConditionalOnBean，设置 zhangsan 加载的条件是 lisi 必须得加载
    @ConditionalOnBean(name = "lisi")
    @Bean
    public User zhangsan(){
        return new User("zhangsan",18);
    }
}
```

```java
@SpringBootApplication
public class Springboot01Application {

	public static void main(String[] args) {

		ConfigurableApplicationContext run = SpringApplication.run(Springboot01Application.class, args);
		String[] names = run.getBeanDefinitionNames();
		for (String name : names) {
			System.out.println( " ------  " + name);
		}

		boolean zhangsan = run.containsBean("zhangsan");
		boolean lisi = run.containsBean("lisi");
		System.out.println(zhangsan);//	false
		System.out.println(lisi);	//	false
	}
}
```
#### @ImportResource导入spring资源文件


#### @ConfigurationProperties配置绑定

将配置文件中的信息和实体类做绑定

```yaml
dog.name = "peiqi"
dog.age = 18
```

方式一，在实体类上加上@Component注解

```java
@Data//	这里用了 lombok 插件
@Component
@ConfigurationProperties(prefix = "dog")//	这里的 prefix 表示匹配以什么开头的标识
public class Dog {
    String name;
    Integer age;
}
```

方式二，在配置类上加上@EnableConfigurationProperties()注解，并指明类

```java
@ImportResource
@Configuration()
@Import({User.class})
//	1.开启 Dog 配置绑定功能
//	2.把 Dog 这个组件自动注入到容器中
@EnableConfigurationProperties(Dog.class)	
public class JavaConfig {

    @ConditionalOnBean(name = "lisi")
    @Bean
    public User zhangsan(){
        return new User("zhangsan",18);
    }

//    @Bean
    public User lisi(){
        return new User("lisi",20);
    }
}
```










### 核心注解

<font color=red> @SpringBootApplication</font>

启动类上的注解，主要包含了以下3个注解

![Snipaste_2021-06-07_14-18-09](imgs\boot\Snipaste_2021-06-07_14-18-09.png)
#### 1,@SpringBootConfiguration

组合了@Configuration注解，实现配置文件的功能

![Snipaste_2021-06-07_14-32-59](imgs\boot\Snipaste_2021-06-07_14-32-59.png)

在SpringBootConfiguration上有一个@Configuration注解，说明这是一个配置类，配置类就是对应Spring的xml配置文件

![Snipaste_2021-06-07_14-34-23](imgs\boot\Snipaste_2021-06-07_14-34-23.png)

在Configuration上有一个@Component注解，说明这个类是Spring中的一个组件
#### 2,@EnableAutoConfiguration

打开了自动配置的功能，也可以关闭某个自动自动装配的选项

如关闭数据源自动配置功能：@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})

![Snipaste_2021-06-07_14-56-25](imgs\boot\Snipaste_2021-06-07_14-56-25.png)

___

@AutoConfigurationPackage自动配置包

点进去，发现导入了一个类 Registrar

![Snipaste_2021-09-06_11-12-50](D:\Project\up\spring\md\imgs\boot\Snipaste_2021-09-06_11-12-50.png)

发现这里 new 了一个 `PackageImports()`对象，字面翻译：包导入，里面设置了一个参数，我们用debug查看该参数的值

![Snipaste_2021-09-06_11-21-40](D:\Project\up\spring\md\imgs\boot\Snipaste_2021-09-06_11-21-40.png)

这里有一个属性的某个值好像对应的是 启动类 的名称，用条件表达式计算一下 `new PackageImports(metadata).getPackageNames()` ，查看得出来的值

![Snipaste_2021-09-06_11-25-16](D:\Project\up\spring\md\imgs\boot\Snipaste_2021-09-06_11-25-16.png)

得到的就是启动类所在的包

得出结论 `Registrar` 就是把某一个包下的所有组件注册到容器内

这里得到的是启动类所在的包，解释了为什么代码一般都放在启动类所在包的子包下面的原因，为什么 默认的包路径 是 启动类所在包的路径

___

继续观察，在`EnableAutoConfiguration`上引入了一个类：`AutoConfigurationImportSelector.class` （自动配置导入选择器）

点进来，往下看，发现一个名为`selectImports()` 的方法，里面的核心功能是 `getAutoConfigurationEntry()`  方法

![Snipaste_2021-09-06_11-46-41](D:\Project\up\spring\md\imgs\boot\Snipaste_2021-09-06_11-46-41.png)

接着往下点，用debug跑一下，在执行到 `getCandidateConfigurations()` 方法时，发现 `configurations` 里面有很多包路径

![Snipaste_2021-09-06_11-52-21](D:\Project\up\spring\md\imgs\boot\Snipaste_2021-09-06_11-52-21.png)

点进去，查看 `getCandidateConfigurations()` 方法到底干了些啥  （得到候选配置）

![Snipaste_2021-06-07_15-03-36](imgs\boot\Snipaste_2021-06-07_15-03-36.png)

getSpringFactoriesLoaderFactoryClass()方法

![Snipaste_2021-06-07_15-05-58](imgs\boot\Snipaste_2021-06-07_15-05-58.png)

继续，查看SpringFactoriesLoader.loadFactoryNames()方法

![Snipaste_2021-06-07_15-09-20](imgs\boot\Snipaste_2021-06-07_15-09-20.png)

继续，查看loadSpringFactories()；

![Snipaste_2021-06-07_15-13-45](imgs\boot\Snipaste_2021-06-07_15-13-45.png)

全局搜索spring.factories文件

![Snipaste_2021-06-07_15-20-14](imgs\boot\Snipaste_2021-06-07_15-20-14.png)

到这里就知道springboot 在启动时会加载那些类了，但是并不是这样的，springboot 会因为条件装配来按需配置

___
##### 自动配置

自动配置真正实现是从classpath中搜寻所有的 <u>META-INF/spring.facatories</u> 配置文件，并将其中对应的 <u>org.springframework.boot.autoconfigure.</u> 包下的配置项，通过反射实例化为对应标注了 @Configuration 的 JavaConfig 形式的ioc容器配置类，然后将这些都汇总成为一个实例并加载到ioc容器中。

结论：

1. SpringBoot 在启动的时候从类路径下的 <u>META-INF/spring.factories</u> 中获取 EnableAutoConfiguration 指定的值
2. 将这些值作为 自动配置类 导入容器，自动配置类就生效，帮我们镜像自动配置工作
3. 整个J2EE的整体解决方案的自动配置都在 springboot-autoconfigure 的jar包中
4. 它会给容器中导入非常多的自动配置类 （xxxAutoConfiguration)，就是给容器中导入这个场景需要的所有组件，并配置号这些组件
5. 有了自动配置类，免去了我们手动编写配置注入功能组件等的工作
#### 3,@ComponentScan

Spring组件、bean扫描，将这个bean定义加载到IOC容器
### SpringBoot自动装配原理

注解@EnableAutoConfiguration,@Configuration,@ConditionalOnClass 就是自动配置的核心

![Snipaste_2021-06-08_09-47-47](imgs\boot\Snipaste_2021-06-08_09-47-47.png)

@EnableAutoConfiguration 给容器导入 META-INF/spring.factories 里定义的自动配置类。

筛选有效的自动配置类。

每一个自动配置类结合对应的xxxProperties.java读取配置文件进行自动配置功能。



1. SpringBoot启动会加载大量的自动配置类
2. 查找需要的功能有没有在SpringBoot默认写好的自动配置类当中，查看这个自动配置类中到底配置了哪些组件（只要我们要用的组件存在其中，我们就不需要在手动配置了）
3. 给容器中自动配置类添加组件的时候，会从properties类中获取某些属性。我们只需要在配置文件中指定这些属性的值即可



xxxxAutoConfiguration(自动配置) -> xxxxProperties(封装配置文件中的相关属性) -> application.properties(配置文件) 



**xxxAutoConfiguration：自动配置类；（给容器中添加组件）**

**xxxProperties：封装配置文件中相关属性；**
### SpringBoot 配置加载顺序

配置加载方式：

1. properties文件
2. YAML文件
3. 系统环境变量
4. 命令行参数

优先级（优先级由高到底，高优先级的配置会覆盖低优先级的配置）：

1. 项目路径下的config文件下配置文件
2. 项目路径下配置文件
3. 资源路径下的config文件夹配置文件
4. 资源路径下配置文件

**SpringBoot会从这四个位置全部加载主配置文件；互补配置；**

启动项目的时候来指定配置文件的新位置

相同配置，外部指定的配置文件优先级最高

```
java -jar spring-boot-config.jar --spring.config.location=F:/application.properties
```
### SpringBoot中跨域问题

推荐在后端通过 （CORS，Cross-origin resource sharing） 来解决跨域问题。这种解决方案并非 Spring Boot 特有的，在传统的 SSM 框架中，就可以通过 CORS 来解决跨域问题，只不过之前我们是在 XML 文件中配置 CORS ，现在可以通过实现WebMvcConfigurer接口然后重写addCorsMappings方法解决跨域问题。

```java
@Configuration
public class CorsConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("*")
                .allowCredentials(true)
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                .maxAge(3600);
    }
}
```
### Web开发

源码：DispatcherServlet.doDispatch()
#### 静态资源访问

只要静态资源放在类路径下 `/static` (or `/public` or `/resources` or `/META-INF/resources`)

访问：当前项目根路径/ + 静态资源名



原理：静态映射/**

当有请求时，会先去找controller看能不能处理，不能处理的所有请求都会交给静态资源处理器

如果静态资源也找不到，那就返回404

![Snipaste_2021-09-07_09-14-38](D:\Project\up\spring\md\imgs\boot\Snipaste_2021-09-07_09-14-38.png)
#### 静态资源访问前缀

静态资源访问默认无前缀

设置静态资源访问前缀

```properties
#	此时访问静态资源的路径就变为 /resources/**
spring.mvc.static-path-pattern=/resources/**
```

设置静态资源存放路径

```properties
#	可以写多个路径
spring.web.resources.static-locations= [classpath:/abc]
```
#### 常用参数注解使用
##### @PathVariable

<font color=red>获取路径变量</font>

如果方法参数是{@link java.util.Map Map&lt;String, String&gt;},则映射将使用所有路径变量名和值填充。

localhost:9999/parameter/getUser/家里蹲/张三/abc

```java
//	localhost:9999/parameter/getUser/家里蹲/张三/abc
---------------------
	@RequestMapping("/getUser/{school}/{name}/abc")
    public Map pathVariable(@PathVariable(name = "school") String school,
                                       @PathVariable(name = "name") String name,
                                       @PathVariable Map<String,String> map){
        HashMap<String, Object> responce = new HashMap<>();
        responce.put("school",school);
        responce.put("name",name);
        responce.put("map",map);
        return responce;
    }
    ---------------------
    {
        "school": "家里蹲",
        "name": "张三",
        "map": {
            "school": "家里蹲",
            "name": "张三"
        }
    }
```
##### @RequestHeade

<font color=red>获取请求头</font>

如果方法参数是{@link java.util.Map Map&lt;String, String&gt;},则映射将使用所有路径变量名和值填充。

```java
//    localhost:9999/parameter/requestHeader
------------------
	@RequestMapping("/requestHeader")
    public Map requestHeader(@RequestHeader Map<String,String> map,
                             @RequestHeader("Host") String host){
        HashMap<String, Object> responce = new HashMap<>();
        responce.put("map",map);
        responce.put("host",host);
        return responce;
    }
------------------
    {
        "host": "localhost:9999",
        "map": {
            "name": "zhangsan",
            "user-agent": "PostmanRuntime/7.26.8",
            "accept": "*/*",
            "postman-token": "0ab46df2-350b-41ec-8963-8fc9b01a0748",
            "host": "localhost:9999",
            "accept-encoding": "gzip, deflate, br",
            "connection": "keep-alive"
        }
	}
```
##### @RequestParam

<font color=red>获取请求参数</font>

当参数名对应多个参数值时，在参数列表中可以用list经行接受

当参数名对应多个参数值时，此时用map经行接受，会有参数丢失

```java
//	localhost:9999/parameter/requestParam?name=李四&inters=王者荣耀&inters=DNF-M
------------
@RequestMapping("/requestParam")
public Map requestParam(@RequestParam("name") String name,
                            @RequestParam("inters") String inters,
                            @RequestParam("inters") List list,
                            @RequestParam Map<String,String> map){
        HashMap<String, Object> responce = new HashMap<>();
        responce.put("name",name);
        responce.put("inters",inters);
        responce.put("list",list);
        responce.put("map",map);
        return responce;
}
---------------
{
    "inters": "王者荣耀,DNF-M",
    "name": "李四",
    "list": [
        "王者荣耀",
        "DNF-M"
    ],
    "map": {
        "name": "李四",
        "inters": "王者荣耀"
    }
}
```
##### @RequestBody

<font color=red>获取请求体，只有POST请求才有请求体</font>

```}
public User requestBody(@RequestBody User user){}
```
##### @CookieValue

<font color=red>获取cookie值</font>
##### @RequestAttribute

<font color=red>获取 request 域属性</font>
##### @MatrixVariable

<font color=red>矩阵变量</font>

springboot 默认禁用矩阵变量

使用矩阵变量，需要单独配置

配置类

```java
//	两种写法
@Configuration
public class ParameterConfig /*implements WebMvcConfigurer*/ {
//    @Override
//    public void configurePathMatch(PathMatchConfigurer configurer) {
//        UrlPathHelper urlPathHelper = new UrlPathHelper();
//        urlPathHelper.setRemoveSemicolonContent(false);
//        configurer.setUrlPathHelper(urlPathHelper);
//    }

    @Bean
    public WebMvcConfigurer webMvcConfigurer(){
        return new WebMvcConfigurer() {
            @Override
            public void configurePathMatch(PathMatchConfigurer configurer) {
                UrlPathHelper urlPathHelper = new UrlPathHelper();
                urlPathHelper.setRemoveSemicolonContent(false);
                configurer.setUrlPathHelper(urlPathHelper);
            }
        };
    }
}
```

测试

```java
//	localhost:9999/parameter/matrixVariable/zhangsan;age=18/lisi;inters=足球,篮球,排球
---------------------
@RequestMapping("/matrixVariable/{zhangsan}/{lisi}")
public Map matrixVariable(@MatrixVariable(value = "age",pathVar = "zhangsan") Integer age,
                          @MatrixVariable(value = "inters",pathVar = "lisi") List inters){
    Map<String, Object> responce = new HashMap<>();
    responce.put("zhangsan",age);
    responce.put("lisi",inters);
    return responce;
}
----------------------
{
    "lisi": ["足球","篮球","排球"],
    "zhangsan": 18
}
```
#### JWT

```xml
<dependency>
   <groupId>com.auth0</groupId>
   <artifactId>java-jwt</artifactId>
   <version>3.8.1</version>
</dependency>
```

```java
@Component
@ConfigurationProperties(prefix="jwt")
public class JWTUtils {

    //	在yaml文件里 配置 jwt.secret 的值
    public static String secret;

    public void setSecret(String secret) {
        JWTUtils.secret = secret;
    }

    /**
     * 获取TOKEN
     * @param obj
     * @return
     */
    public static String getToken(Object obj){
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MINUTE,30);
//        System.out.println(usr.toString());

        return  JWT.create().withHeader(new HashMap<>())
                .withClaim("user", JSONObject.toJSONString(obj)) // 添加对象
                .withExpiresAt(calendar.getTime())  //  设置过期事件
                .sign(Algorithm.HMAC256(secret));  // 签名
    }

    /**
     * 验证TOKEN
     * @param token
     * @return
     */
    public static DecodedJWT decodedJWT(String token){
//        JWTVerifier jwtVerifier = JWT.require(Algorithm.HMAC256(SECRET)).build();
//        DecodedJWT decodedJWT = jwtVerifier.verify(token);
//        return decodedJWT;
        try {
            return JWT.require(Algorithm.HMAC256(secret)).build().verify(token);
        }catch (TokenExpiredException e){
            throw new CustomExceptionHandler(ErrorMessage.TOKEN_TIME_OUT.getCode(),ErrorMessage.TOKEN_TIME_OUT.getMsg());
        }catch (Exception e){
            throw new CustomExceptionHandler(ErrorMessage.TOKEN_ERROR.getCode(),ErrorMessage.TOKEN_ERROR.getMsg());
        }
    }
}
```

#### 拦截器

##### 使用

1，编写拦截器，实现 `HandlerInterceptor` 接口

```java
@Slf4j
public class LoginInterceptor implements HandlerInterceptor {
    //  目标方法执行之前
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //	从request里获取session
        log.info(" ======   请求url : {}  ======",request.getRequestURI());
        HttpSession session = request.getSession();
        //	从 session 里获取名为 loginUser 的值
        User loginUser = (User)session.getAttribute("loginUser");
        if ( loginUser != null){
            log.info(" ======   用户已登录   ======");
            log.info(" ======   用户名：{}，密码：{}   ======",loginUser.getUsername(),loginUser.getPassword());
            return true;
        }
        return false;
    }

    //  目标方法执行完成之后
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    //  页面渲染后
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
```

> 先执行 `preHandle` 在执行 `postHandle` 最后执行 `afterCompletion`

___

2，将拦截器注册到容器中（实现 WebMvcConfigurer 的 addInterceptors ），指定拦截规则

```java
@Configuration
public class InterceptorConfig implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        //	添加自定义拦截器
        registry.addInterceptor(new LoginInterceptor())
            //	添加拦截路径
            .addPathPatterns("/**")	
            //	添加放行路径
            .excludePathPatterns("/login","/","/index");
    }
}
```

_____
##### 拦截器原理

1、根据当前请求，找到 **HandlerExecutionChain【**可以处理请求的handler以及handler的所有 拦截器】

2、先来**顺序执行** 所有拦截器的 preHandle方法

- 1、如果当前拦截器prehandler返回为true。则执行下一个拦截器的preHandle
- 2、如果当前拦截器返回为false。直接    倒序执行所有已经执行了的拦截器的  afterCompletion；

**3、如果任何一个拦截器返回false。直接跳出不执行目标方法**

**4、所有拦截器都返回True。执行目标方法**

**5、倒序执行所有拦截器的postHandle方法。**

**6、前面的步骤有任何异常都会直接倒序触发** afterCompletion

7、页面成功渲染完成以后，也会倒序触发 afterCompletion
#### 异常处理
- 默认情况下，Spring Boot提供`/error`处理所有错误的映射
- 对于机器客户端，它将生成JSON响应，其中包含错误，HTTP状态和异常消息的详细信息。对于浏览器客户端，响应一个“ whitelabel”错误视图，以HTML格式呈现相同的数据
##### 自定义错误页面

如果要为给定状态代码显示自定义 HTML 错误页面，可以将文件添加到`/error`目录。错误页面可以是静态 HTML（即添加到任何静态资源目录下），也可以是使用模板构建的。文件的名称应该是确切的状态代码或系列掩码。

例如，要映射`404`到静态 HTML 文件，您的目录结构将如下所示：

```
src/
 +- main/
     +- java/
     |   + <source code>
     +- resources/
         +- public/
             +- error/
             |   +- 404.html
             +- <other public assets>
```

要`5xx`使用 FreeMarker 模板映射所有错误，您的目录结构如下：

```
src/
 +- main/
     +- java/
     |   + <source code>
     +- resources/
         +- templates/
             +- error/
             |   +- 5xx.ftlh
             +- <other templates>
```
##### 自定义异常

###### 消息枚举类

```java
public enum ErrorMessage {

    SUCCESS(200, "成功!"),
    BODY_NOT_MATCH(400,"请求的数据格式不符!"),
    SIGNATURE_NOT_MATCH(401,"请求的数字签名不匹配!"),
    NOT_FOUND(404, "未找到该资源!"),
    INTERNAL_SERVER_ERROR(500, "服务器内部错误!"),
    SERVER_BUSY(503,"服务器正忙，请稍后再试!"),
    STUDENG_NULL(3, "学号不能为空");

    private Integer code;
    private String msg;

    ErrorMessage(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }
    public Integer getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}
```

###### 自定义异常类

```java
@Data
public class CustomExceptionHandler extends RuntimeException {

    private Integer code;
    private String msg;
    public CustomExceptionHandler() {
        super();
    }

    public CustomExceptionHandler(ErrorMessage errorInfoInterface) {
        this.code = errorInfoInterface.getCode();
        this.msg = errorInfoInterface.getMsg();
    }

    public CustomExceptionHandler(ErrorMessage errorInfoInterface, Throwable cause) {
        this.code = errorInfoInterface.getCode();
        this.msg = errorInfoInterface.getMsg();
    }

    public CustomExceptionHandler(String errorMsg) {
        super(errorMsg);
        this.msg = errorMsg;
    }

    public CustomExceptionHandler(Integer errorCode, String errorMsg) {
        this.code = errorCode;
        this.msg = errorMsg;
    }

    public CustomExceptionHandler(Integer errorCode, String errorMsg, Throwable cause) {
        this.code = errorCode;
        this.msg = errorMsg;
    }

    @Override
    public Throwable fillInStackTrace() {
        return this;
    }
}
```

###### 全局异常处理类

```java
/**
 * 自定义全局异常处理类
 */
@ControllerAdvice
public class GlobalExceptionHandler{

    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    /**
     * 处理自定义的业务异常
	 * @ExceptionHandler(value = 处理异常类型)
     * @param req
     * @param e
     * @return
     */
    @ExceptionHandler(value = CustomExceptionHandler.class)
    @ResponseBody
    public Responce bizExceptionHandler(HttpServletRequest req, CustomExceptionHandler e){
        logger.error("发生业务异常！原因是：{}",e.getMsg());
        return Responce.error(e.getCode(),e.getMsg());
    }

    /**
     * 处理空指针的异常
     * @param req
     * @param e
     * @return
     */
    @ExceptionHandler(value =NullPointerException.class)
    @ResponseBody
    public Responce exceptionHandler(HttpServletRequest req, NullPointerException e){
        logger.error("发生空指针异常！原因是:",e);
        return Responce.error(ErrorMessage.BODY_NOT_MATCH);
    }

    /**
     * 处理其他异常
     * @param req
     * @param e
     * @return
     */
    @ExceptionHandler(value = {Exception.class})
    @ResponseBody
    public Responce exceptionHandler(HttpServletRequest req, Exception e){
        logger.error("未知异常！原因是:",e);
        return Responce.error(ErrorMessage.INTERNAL_SERVER_ERROR);
    }

}
```

#### Web原生组件注入（Servlet、Filter、Listener）

##### 使用Servlet AP

```java
//	指定原生Servlet组件都放在那里,路径为servlet组件所在的包
//	可以放在启动类上，也可以放在配置类上
@ServletComponentScan(value = "com.example.springboot.servlet")
```

注册组件

```java
@WebFilter(urlPatterns = "/servlet")
public class MyFilter implements Filter {


    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        log.info("========   Filter初始化成功   ========");
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        log.info("========   Filter工作   ========");
        filterChain.doFilter(servletRequest,servletResponse);
    }

    @Override
    public void destroy() {
        log.info("========   Filter销毁   ========");
    }
}
```

```java
@WebServlet(urlPatterns = "/servlet")
public class MyServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.getWriter().write("返回的消息");
    }
}

```

```java
@WebListener(value = "/servlet")
public class MyServletContextListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        log.info("========    MyServletContextListener监听到项目初始化完成    ========");
    }

}
```

##### 使用RegistrationBean

`ServletRegistrationBean`, `FilterRegistrationBean`, and `ServletListenerRegistrationBean`

```java
@Configuration
public class MyRegistConfig {

    @Bean
    public ServletRegistrationBean myServlet(){
        MyServlet myServlet = new MyServlet();

        return new ServletRegistrationBean(myServlet,"/my","/my02");
    }

    @Bean
    public FilterRegistrationBean myFilter(){

        MyFilter myFilter = new MyFilter();
        //	两种写法
		//  return new FilterRegistrationBean(myFilter,myServlet());
        FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean(myFilter);
        filterRegistrationBean.setUrlPatterns(Arrays.asList("/my","/css/*"));
        return filterRegistrationBean;
    }

    @Bean
    public ServletListenerRegistrationBean myListener(){
        MySwervletContextListener mySwervletContextListener = new MySwervletContextListener();
        return new ServletListenerRegistrationBean(mySwervletContextListener);
    }
}
```

### 数据访问

#### SQL

##### 数据源的自动配置

###### 导入JDBC场景

```pom
//	springboot2 默认版本是 jdbc
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-data-jdbc</artifactId>
</dependency>
```

###### 修改配置

```yaml
spring:
  datsource:
    driver-class-name: com.mysql.cj.jdbc.Driver
    username: root
    password: qazOKM123
    url: jdbc:mysql://127.0.0.1:3306/test
```

##### 使用Druid数据源

###### druid官方github地址

https://github.com/alibaba/druid

###### 自定义方式

​	这种方式已经过时

1. 引入druid

   ```yaml
   <dependency>
      <groupId>com.alibaba</groupId>
      <artifactId>druid</artifactId>
      <version>1.1.10</version>
   </dependency>
   ```

2. 创建数据源
   ![Snipaste_2021-09-13_11-55-16](D:\Project\up\spring\md\imgs\boot\Snipaste_2021-09-13_11-55-16.png)

   创建配置类MyDataSourceConfig

   ```java
   //	这种方式已经过时
   @Deprecated
   //@Configuration
   public class MyDataSourceConfig {
   
       // 默认的自动配置是判断容器中没有才会配@ConditionalOnMissingBean(DataSource.class)
   //    @ConfigurationProperties("spring.datasource")
   //    @Bean
       public DataSource dataSource() throws SQLException {
           DruidDataSource druidDataSource = new DruidDataSource();
   
   //        druidDataSource.setUrl();
   //        druidDataSource.setUsername();
   //        druidDataSource.setPassword();
           //加入监控功能
   //        druidDataSource.setFilters("stat,wall");
   //        druidDataSource.setMaxActive(10);
           return druidDataSource;
       }
   
       /**
        * 配置 druid的监控页功能
        * @return
        */
   //    @Bean
       public ServletRegistrationBean statViewServlet(){
           StatViewServlet statViewServlet = new StatViewServlet();
           ServletRegistrationBean<StatViewServlet> registrationBean = new ServletRegistrationBean<>(statViewServlet, "/druid/*");
           registrationBean.addInitParameter("loginUsername","admin");
           registrationBean.addInitParameter("loginPassword","123456");
           return registrationBean;
       }
   
       /**
        * WebStatFilter 用于采集web-jdbc关联监控的数据。
        */
   //    @Bean
       public FilterRegistrationBean webStatFilter(){
           WebStatFilter webStatFilter = new WebStatFilter();
   
           FilterRegistrationBean<WebStatFilter> filterRegistrationBean = new FilterRegistrationBean<>(webStatFilter);
           filterRegistrationBean.setUrlPatterns(Arrays.asList("/*"));
           filterRegistrationBean.addInitParameter("exclusions","*.js,*.gif,*.jpg,*.png,*.css,*.ico,/druid/*");
   
           return filterRegistrationBean;
       }
   
   }
   ```

###### 使用官方starter方式

分析自动配置

- 扩展配置项 **spring.datasource.druid**
- DruidSpringAopConfiguration.**class**,   监控SpringBean的；配置项：**spring.datasource.druid.aop-patterns**

- DruidStatViewServletConfiguration.**class**, 监控页的配置：**spring.datasource.druid.stat-view-servlet；默认开启**
-  DruidWebStatFilterConfiguration.**class**, web监控配置；**spring.datasource.druid.web-stat-filter；默认开启**

- DruidFilterConfiguration.**class**}) 所有Druid自己filter的配置

```java
    private static final String FILTER_STAT_PREFIX = "spring.datasource.druid.filter.stat";
    private static final String FILTER_CONFIG_PREFIX = "spring.datasource.druid.filter.config";
    private static final String FILTER_ENCODING_PREFIX = "spring.datasource.druid.filter.encoding";
    private static final String FILTER_SLF4J_PREFIX = "spring.datasource.druid.filter.slf4j";
    private static final String FILTER_LOG4J_PREFIX = "spring.datasource.druid.filter.log4j";
    private static final String FILTER_LOG4J2_PREFIX = "spring.datasource.druid.filter.log4j2";
    private static final String FILTER_COMMONS_LOG_PREFIX = "spring.datasource.druid.filter.commons-log";
    private static final String FILTER_WALL_PREFIX = "spring.datasource.druid.filter.wall";
```

配置示例

```yaml
spring:
  datasource:
    url: jdbc:mysql://localhost:3306/test
    username: root
    password: qazOKM123
    driver-class-name: com.mysql.jdbc.Driver

    druid:
      aop-patterns: com.atguigu.admin.*  #监控SpringBean
      filters: stat,wall     # 底层开启功能，stat（sql监控），wall（防火墙）

      stat-view-servlet:   # 配置监控页功能
        enabled: true
        login-username: admin
        login-password: admin
        resetEnable: false

      web-stat-filter:  # 监控web
        enabled: true
        urlPattern: /*
        exclusions: '*.js,*.gif,*.jpg,*.png,*.css,*.ico,/druid/*'


      filter:
        stat:    # 对上面filters里面的stat的详细配置
          slow-sql-millis: 1000
          logSlowSql: true
          enabled: true
        wall:
          enabled: true
          config:
            drop-table-allow: false
```

##### 整合MyBatis操作

- 引入mybatis-starter

- **配置application.yaml中，指定mapper-location位置即可**

  sql.xml文件

- 编写Mapper接口并标注@Mapper注解

- 简单方法直接注解方式

- 复杂方法编写mapper.xml进行绑定映射
- *@MapperScan("com.atguigu.admin.mapper") 简化，其他的接口就可以不用标注@Mapper注解*



##### 整合Mybatis-Plus

引入mybatis-starter

```yaml
  <dependency>
            <groupId>com.baomidou</groupId>
            <artifactId>mybatis-plus-boot-starter</artifactId>
            <version>3.4.1</version>
  </dependency>
```

分页

https://mp.baomidou.com/guide/page.html



#### NoSQL

springboot 整合 redis 不难

1. 引入mybatis-starter

   ```pom
   		<dependency>
   			<groupId>org.springframework.boot</groupId>
   			<artifactId>spring-boot-starter-data-redis</artifactId>
   		</dependency>
   ```

2. 编写yaml

   ```yaml
   spring:
     redis:
       host: 127.0.0.1
       port: 6379
   #   有密码时要配置密码
   #    password: 
   ```

   

3. 编写序列化配置文件

   ```java
   @Configuration
   public class RedisConfig {
   
       @Bean
       @SuppressWarnings("all")
       public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory factory) {
           RedisTemplate<String, Object> template = new RedisTemplate<String, Object>();
           template.setConnectionFactory(factory);
   
   
           Jackson2JsonRedisSerializer jackson2JsonRedisSerializer = new 	Jackson2JsonRedisSerializer(Object.class);
   
           ObjectMapper om = new ObjectMapper();
           om.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
           om.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
   
           jackson2JsonRedisSerializer.setObjectMapper(om);
           StringRedisSerializer stringRedisSerializer = new StringRedisSerializer();
   
   
           // key采用String的序列化方式
           template.setKeySerializer(stringRedisSerializer);
           // hash的key也采用String的序列化方式
           template.setHashKeySerializer(stringRedisSerializer);
           // value序列化方式采用jackson
           template.setValueSerializer(jackson2JsonRedisSerializer);
           // hash的value序列化方式采用jackson
           template.setHashValueSerializer(jackson2JsonRedisSerializer);
           template.afterPropertiesSet();
           return template;
       }
   
   }
   ```

4. 测试

   ```java
   @SpringBootTest
   public class RedisTest {
   
       @Autowired
       UserService userService;
   
       @Autowired
       RedisTemplate redisTemplate;
   
       @Test
       public void set(){
           redisTemplate.opsForValue().set("myKey","阿萨大大");
    
           QueryWrapper<User> userQueryWrapper = new QueryWrapper<>();
           userQueryWrapper.lambda()
                   .eq(User::getUsername,"zhangsan")
                   .last("limit 1");
           User user = userService.getOne(userQueryWrapper);
           redisTemplate.opsForValue().set("user",user);
   
           keys.stream().forEach(System.out::println);
       }
   }
   ```

### 单元测试

#### junit5常用注解

- **@Test :**表示方法是测试方法。但是与JUnit4的@Test不同，他的职责非常单一不能声明任何属性，拓展的测试将会由Jupiter提供额外测试
- **@ParameterizedTest :**表示方法是参数化测试，下方会有详细介绍

- **@RepeatedTest :**表示方法可重复执行，下方会有详细介绍
- **@DisplayName :**为测试类或者测试方法设置展示名称

- **@BeforeEach :**表示在每个单元测试之前执行
- **@AfterEach :**表示在每个单元测试之后执行

- **@BeforeAll :**表示在所有单元测试之前执行
- **@AfterAll :**表示在所有单元测试之后执行

- **@Tag :**表示单元测试类别，类似于JUnit4中的@Categories
- **@Disabled :**表示测试类或测试方法不执行，类似于JUnit4中的@Ignore

- **@Timeout :**表示测试方法运行如果超过了指定时间将会返回错误
- **@ExtendWith :**为测试类或测试方法提供扩展类引用

#### 断言（assertions）

断言（assertions）是测试方法中的核心部分，用来对测试需要满足的条件进行验证。**这些断言方法都是 org.junit.jupiter.api.Assertions 的静态方法**。JUnit 5 内置的断言可以分成如下几个类别：

**检查业务逻辑返回的数据是否合理。**

**所有的测试运行结束以后，会有一个详细的测试报告；**

##### 简单断言

用来对单个值进行简单的验证。如：

| 方法            | 说明                                 |
| --------------- | ------------------------------------ |
| assertEquals    | 判断两个对象或两个原始类型是否相等   |
| assertNotEquals | 判断两个对象或两个原始类型是否不相等 |
| assertSame      | 判断两个对象引用是否指向同一个对象   |
| assertNotSame   | 判断两个对象引用是否指向不同的对象   |
| assertTrue      | 判断给定的布尔值是否为 true          |
| assertFalse     | 判断给定的布尔值是否为 false         |
| assertNull      | 判断给定的对象引用是否为 null        |
| assertNotNull   | 判断给定的对象引用是否不为 null      |

```java
@Test
public void simple() {
    //	这里要导包
    //	import static org.junit.jupiter.api.Assertions.*;
     assertEquals(3, 1 + 2, "simple math");
     assertNotEquals(3, 1 + 1);

     assertNotSame(new Object(), new Object());
     Object obj = new Object();
     assertSame(obj, obj);

     assertFalse(1 > 2);
     assertTrue(1 < 2);

     assertNull(null);
     assertNotNull(new Object());
}
```

##### 数组断言

通过 assertArrayEquals 方法来判断两个对象或原始类型的数组是否相等

```java
@Test
@DisplayName("array assertion")
public void array() {
 assertArrayEquals(new int[]{1, 2}, new int[] {1, 2});
}
```

##### 组合断言

assertAll 方法接受多个 org.junit.jupiter.api.Executable 函数式接口的实例作为要验证的断言，可以通过 lambda 表达式很容易的提供这些断言

```java
@Test
@DisplayName("assert all")
public void all() {
 assertAll("Math",
    () -> assertEquals(2, 1 + 1),
    () -> assertTrue(1 > 0)
 );
}
```

##### 异常断言

在JUnit4时期，想要测试方法的异常情况时，需要用**@Rule**注解的ExpectedException变量还是比较麻烦的。而JUnit5提供了一种新的断言方式**Assertions.assertThrows()** ,配合函数式编程就可以进行使用

```java
@Test
@DisplayName("异常测试")
public void exceptionTest() {
    ArithmeticException exception = Assertions.assertThrows(
           //扔出断言异常
            ArithmeticException.class, () -> System.out.println(1 % 0));
}
```

##### 超时断言

Junit5还提供了**Assertions.assertTimeout()** 为测试方法设置了超时时间

```java
@Test
@DisplayName("超时测试")
public void timeoutTest() {
    //如果测试方法时间超过1s将会异常
    Assertions.assertTimeout(Duration.ofMillis(1000), () -> Thread.sleep(500));
}
```

##### 快速失败

通过 fail 方法直接使得测试失败

```java
@Test
@DisplayName("fail")
public void shouldFail() {
 fail("This should fail");
}
```





### 指标监控

