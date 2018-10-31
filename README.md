# JWT-Security-Spring-Boot-Starter

## 一个基于 Spring Security 的 JWT Token认证模块

### 安装
添加到POM依赖
````
<dependency>
    <groupId>com.github</groupId>
    <artifactId>jwt-security-spring-boot-starter</artifactId>
    <version>1.0.0</version>
</dependency>
````

### 配置
#### 1. 声明`JwtUserFactory`、`JwtTokenUtils`Bean

- `JwtTokenUtils`  
  Jwt Token 生成和解析工具。有多个构造器选择，可以自定义签名，算法，Key.
- `JwtUserFactory`  
  根据`Claims`生成`JwtUser`
  

#### 修改Configuration

##### 默认配置

若没有声明`WebSecurityConfigurerAdapter`Bean，
将会使用`JwtConfigurerAdapter`作为默认配置。

##### 手动配置

在你的`WebSecurityConfigurerAdapter`中的`configure(HttpSecurity http)`方法中添加如下代码
```
http
         .apply(new JwtConfigurer<>(jwtTokenUtils(), jwtUserFactory()))
```

其中`jwtTokenUtils`和`jwtUserFactory`为先前声明的Bean。

### 使用

#### 生成Token

请自定义验证策略，例如用户名密码验证，可以配合Spring Security的其他安全策略进行验证，又或者自定义验证`Controller`。    
验证成功之后，自行决定Token需要携带的`Claims`，通过调用如下方法生成Token并返回给前端。
```
jwtTokenUtils.createJWT(Map<String, Object> claims, int expireField, int expireAmount)
```

#### 验证Token

前端将Token缓存，当需要认证的时候请将Token以如下形式附加在请求头
````
Authorization: Bearer your_token
````

请求头为`Authorization`，其值为"Bearer " +  你的Token，请注意空格的存在，前缀总共有7位。

#### 获取JwtUser
`JwtUser` 通过`jwtUserFactory`解析Token中的payload`Claims`生成。
生成之后可以在`Controller`通过如下形式获取，在参数加上如下参数即可获得
````
@TokenUser JwtUser xxx
````

#### 选择Authority
JWT-Security 支持Spring Security的Authority权限控制，只需要在`JwtUserFactory`Bean中实现生成的`JwtUser`中的`getGrantedAuthorities`方法。