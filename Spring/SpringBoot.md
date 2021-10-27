#### 常见注解

##### @Configuration

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

##### @Import导入组件

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

![Snipaste_2021-09-06_09-38-17](D:\Download\Typora\Spring\imgs\boot\Snipaste_2021-09-06_09-38-17.png)

![Snipaste_2021-09-06_09-40-11](D:\Download\Typora\Spring\imgs\boot\Snipaste_2021-09-06_09-40-11.png)

##### @Conditional条件装配

满足相应条件就加载bean

















#### 核心注解

 @SpringBootApplication

启动类上的注解，主要包含了以下3个注解

![Snipaste_2021-06-07_14-18-09](imgs\boot\Snipaste_2021-06-07_14-18-09.png)

##### 1,@SpringBootConfiguration

组合了@Configuration注解，实现配置文件的功能

![Snipaste_2021-06-07_14-32-59](imgs\boot\Snipaste_2021-06-07_14-32-59.png)

在SpringBootConfiguration上有一个@Configuration注解，说明这是一个配置类，配置类就是对应Spring的xml配置文件

![Snipaste_2021-06-07_14-34-23](imgs\boot\Snipaste_2021-06-07_14-34-23.png)

在Configuration上有一个@Component注解，说明这个类是Spring中的一个组件

##### 2,@EnableAutoConfiguration

打开了自动配置的功能，也可以关闭某个自动自动装配的选项

如关闭数据源自动配置功能：@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})

![Snipaste_2021-06-07_14-56-25](imgs\boot\Snipaste_2021-06-07_14-56-25.png)

在EnableAutoConfiguration上引入了一个类：AutoConfigurationImportSelector.class （自动配置导入选择器）

点进去，发现有一个方法getCandidateConfigurations() （得到候选配置）

![Snipaste_2021-06-07_15-03-36](imgs\boot\Snipaste_2021-06-07_15-03-36.png)

getSpringFactoriesLoaderFactoryClass()方法

![Snipaste_2021-06-07_15-05-58](imgs\boot\Snipaste_2021-06-07_15-05-58.png)

继续，查看SpringFactoriesLoader.loadFactoryNames()方法

![Snipaste_2021-06-07_15-09-20](imgs\boot\Snipaste_2021-06-07_15-09-20.png)

继续，查看loadSpringFactories()；

![Snipaste_2021-06-07_15-13-45](D:\Download\Typora\spring\imgs\boot\Snipaste_2021-06-07_15-13-45.png)

全局搜索spring.factories文件

![Snipaste_2021-06-07_15-20-14](D:\Download\Typora\spring\imgs\boot\Snipaste_2021-06-07_15-20-14.png)

###### 自动配置

自动配置真正实现是从classpath中搜寻所有的 <u>META-INF/spring.facatories</u> 配置文件，并将其中对应的 <u>org.springframework.boot.autoconfigure.</u> 包下的配置项，通过反射实例化为对应标注了 @Configuration 的 JavaConfig 形式的ioc容器配置类，然后将这些都汇总成为一个实例并加载到ioc容器中。

结论：

1. SpringBoot 在启动的时候从类路径下的 <u>META-INF/spring.factories</u> 中获取 EnableAutoConfiguration 指定的值
2. 将这些值作为 自动配置类 导入容器，自动配置类就生效，帮我们镜像自动配置工作
3. 整个J2EE的整体解决方案的自动配置都在 springboot-autoconfigure 的jar包中
4. 它会给容器中导入非常多的自动配置类 （xxxAutoConfiguration)，就是给容器中导入这个场景需要的所有组件，并配置号这些组件
5. 有了自动配置类，免去了我们手动编写配置注入功能组件等的工作

##### 3,@ComponentScan

Spring组件、bean扫描，将这个bean定义加载到IOC容器

#### JavaConfig

用纯Java的方法配置Spring IoC 容器的方法，有助于避免使用xml配置。

#### SpringBoot自动装配原理

注解@EnableAutoConfiguration,@Configuration,@ConditionalOnClass 就是自动配置的核心

![微信图片_20210608101301](imgs\boot\微信图片_20210608101301.png)

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

#### SpringBoot 配置加载顺序

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

#### SpringBoot中跨域问题

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

