This repository contains various [Checkstyle](http://checkstyle.sourceforge.net/) checks and modifications
to support students in learning Java.

### How to use this repository

- `mvn package`: Builds a JAR and stores it in `target/` with the name `checkstyle-teaching-1.0-SNAPSHOT.jar`

- `mvn install`: Deploys the JAR locally

- `mvn eclipse:eclipse`: Generates an Eclipse project

- To enable the Checkstyle extensions of this repository in a Maven project, add the following to the `pom.xml`:

  ```
   <build>
      ...
    <plugins>
      ...
       <plugin>
         <groupId>org.apache.maven.plugins</groupId>
         <artifactId>maven-checkstyle-plugin</artifactId>
         <version>2.10</version>
         <configuration>
          ...
         </configuration>
         <dependencies>
           <dependency>
             <groupId>edu.kit.checkstyle</groupId>
             <artifactId>checkstyle-teaching</artifactId>
             <version>1.0-SNAPSHOT</version>
           </dependency>
         </dependencies>
       </plugin>
     </plugins>
   </build>
  ```
  After that the checkstyle plugin can be executed with `mvn checkstyle:check`. Remember to deploy the extensions with `mvn install` before doing a Checkstyle check.

- To enable the Checkstyle extensions of this repository on the command line, use the following command:

  ```
  java -classpath target/dependencies/*:\
  target/checkstyle-teaching-1.0-SNAPSHOT.jar:\
  lib/checkstyle-5.6-all.jar \
  com.puppycrawl.tools.checkstyle.Main \
  -c checkstyle.xml -r <path>
  ```
  
  where `<path>` denotes the location of the sources that should be checked.

### Resources on how to learn modifying Checkstyle

- [Writing Checks](http://checkstyle.sourceforge.net/writingchecks.html)

- [Maven Checkstyle Plugin](http://maven.apache.org/plugins/maven-checkstyle-plugin/)

- [Use Modifications with Maven](http://maven.apache.org/plugins/maven-checkstyle-plugin/examples/custom-developed-checkstyle.html)

- [Checkstyle Source repository](http://checkstyle.hg.sourceforge.net/hgweb/checkstyle/checkstyle/)