Tomcat üzerinde \conf\server.xml altında <GlobalNamingResources> tagi altinda  aşağıdaki resource eklenir.

```
<Resource name="jdbc/blabla"
  auth="Container"
  type="javax.sql.DataSource"
  factory="org.apache.tomcat.jdbc.pool.DataSourceFactory"
  driverClassName="oracle.jdbc.driver.OracleDriver"
  url="jdbc:oracle:thin:@bla.bla.COM:1521/bla"
  username="kul"
  password="sif"
  maxActive="20"
  maxIdle="10"
  maxWait="-1"
  validationQuery="select 1 from dual" />
  ```
  
  
  Ardından conf\context.xml altında <Context> tagı içeresinde ResourceLink eklenir.
  
 ```
  	<ResourceLink
  	  name="jdbc/blabla"
  	  global="jdbc/blabla"
  	  type="javax.sql.DataSource"/>
 ```
 
   Ardından kod içeresinde java:comp/env/jdbc/blabla şeklinde jdni üzerinden çekilir.
   
   
   Spring boot uygalamasında jdni kullanıcaksa TomcatServletWebServerFactory üzerinde resource tanımları yapılır.
   
   ```
      @Bean
       public TomcatServletWebServerFactory tomcatFactory() {
           return new TomcatServletWebServerFactory() {
               @Override
               protected TomcatWebServer getTomcatWebServer(org.apache.catalina.startup.Tomcat tomcat) {
                   tomcat.enableNaming();
                   return super.getTomcatWebServer(tomcat);
               }
   
               @Override
               protected void postProcessContext(Context context) {
                   // context
                   ContextResource resource = new ContextResource();
                   resource.setName("jdbc/blabla");
                   resource.setAuth("Container");
                   resource.setType("javax.sql.DataSource");
                   resource.setProperty("driverClassName", "oracle.jdbc.driver.OracleDriver");
                   resource.setProperty("url", "jdbc:oracle:thin:@bla.bla.COM:1521/bla");
                   resource.setProperty("username", "kul");
                   resource.setProperty("password", "sifre");
                   context.getNamingResources().addResource(resource);
               }
           };
       }
 ```
 
  ```
         <dependency>
             <groupId>org.springframework.boot</groupId>
             <artifactId>spring-boot-starter-tomcat</artifactId>
             <scope>provided</scope>
         </dependency>
 
         <dependency>
             <groupId>org.apache.tomcat</groupId>
             <artifactId>tomcat-dbcp</artifactId>
             <version>9.0.12</version>
         </dependency>
         
   ```
 
 