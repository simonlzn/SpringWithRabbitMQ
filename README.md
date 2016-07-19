# SpringWithGradle
Minimal setup of a Spring application with Gradle.

The very basic web application with Spring MVC framework. No specific IDE is required. Gradle is used as the dependency management and build tool.

"gradle war" generates the .war file, which can be deployed to tomcat. Hibernate config is included to create sessions for database operations.

Gradle-Tomcat plugin is also included in the build.gradle configuration file, which enables possibility of using the embedded tomcat internally. "gradle tomcatRunWar" can be used to launch the application without installation of tomcat.

From Servlet 3.0 on, all the configurations can be done in the Java code, ApplicationInitializer is equivalent to the seetings of servlet and dispatcher, WebConfig has the setting of the view resolver.
