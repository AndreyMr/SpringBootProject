### Description
This is a training project created from video tutorials from YouTube channel [letsCode](https://www.youtube.com/channel/UC1g3kT0ZcSXt4_ZyJOshKJQ) using the Spring framework (MVC pattern) + ORM framework Hibernate + MySQL database. It is a clone of Twitter, where each authorized user can create and view messages in the form of a card with a header, message text and selected image.
### Used technologies
The project was created as a Maven project using [SpringBoot 2.1.6.RELEASE](https://spring.io/projects/spring-boot#overview)
+ Versions of the main applied modules:
  + Spring Core 5.1.8.RELEASE
  + Spring MVC 5.1.8.RELEASE
  + Spring Security 5.1.5.RELEASE
  + Spring Data JPA 2.1.9.RELEASE 
  + Hibernate 5.3.10.Final
  + mysql-connector-java 8.0.16
+ Version MySQL Server
  + MySQL Community Server 5.7.20

Web pages were created using the [FreeMarker](https://freemarker.apache.org/docs/dgui.html) template library, external   design [Bootstrap](https://getbootstrap.com/) version 4.1.3. 
For database migration used library [Flyway](https://flywaydb.org/) 
### Project structure
+ src.main.
  + [java.com.](https://github.com/AndreyMr/SpringBootProject/tree/master/src/main/java/com)
    + [config](https://github.com/AndreyMr/SpringBootProject/tree/master/src/main/java/com/config) // *package with Spring configuration classes(@Configuration)* 
    + [controllers](https://github.com/AndreyMr/SpringBootProject/tree/master/src/main/java/com/controllers) // *package with Spring controllers classes (@Controller)* 
    + [entity](https://github.com/AndreyMr/SpringBootProject/tree/master/src/main/java/com/entity) // *package with entity clasess @Entity*
    + [repository](https://github.com/AndreyMr/SpringBootProject/tree/master/src/main/java/com/repository) // *package with repository interfaces*   
    + [service](https://github.com/AndreyMr/SpringBootProject/tree/master/src/main/java/com/service) // *package with service classes*
    + [Appcication.java](https://github.com/AndreyMr/SpringBootProject/blob/master/src/main/java/com/Application.java) // *class with main() method*
  + [resources.](https://github.com/AndreyMr/SpringBootProject/tree/master/src/main/resources)
    + [db.migration](https://github.com/AndreyMr/SpringBootProject/tree/master/src/main/resources/db/migration) // *database migration files*
	+ [static](https://github.com/AndreyMr/SpringBootProject/tree/master/src/main/resources/static) // *static elements for web pages*
	+ [templates](https://github.com/AndreyMr/SpringBootProject/tree/master/src/main/resources/templates) // *Web page templates created with FreeMarker* 
+ [pom.xml](https://github.com/AndreyMr/SpringBootProject/blob/master/pom.xml) // *pom file with dependents and configuration Maven*
