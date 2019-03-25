

问题列表

解决跨域问题：
1. pom.xml增加以下代码

<!-- 跨域过滤器相关 -->  
    <dependency>  
        <groupId>com.thetransactioncompany</groupId>  
        <artifactId>java-property-utils</artifactId>  
        <version>1.9.1</version>  
    </dependency>  
          
    <dependency>  
        <groupId>com.google.code.gson</groupId>  
        <artifactId>gson</artifactId>  
        <version>2.6.2</version>  
    </dependency>  
<!-- 跨域过滤器相关 -->

2. web.xml增加以下代码
<filter>
	<description>跨域过滤器</description>
	<filter-name>CORS</filter-name>
	<filter-class>com.thetransactioncompany.cors.CORSFilter</filter-class>
	<init-param>
		<param-name>cors.allowOrigin</param-name>
		<param-value>*</param-value>
	</init-param>
	<init-param>
		<param-name>cors.supportedMethods</param-name>
		<param-value>GET, POST, HEAD, PUT, DELETE</param-value>
	</init-param>
	<init-param>
		<param-name>cors.supportedHeaders</param-name>
		<param-value>Accept, Origin, X-Requested-With, Content-Type, Last-Modified</param-value>
	</init-param>
	<init-param>
		<param-name>cors.exposedHeaders</param-name>
		<param-value>Set-Cookie</param-value>
	</init-param>
	<init-param>
		<param-name>cors.supportsCredentials</param-name>
		<param-value>true</param-value>
	</init-param>
</filter>

<filter-mapping>
	<filter-name>CORS</filter-name>
	<url-pattern>/*</url-pattern>
</filter-mapping>  