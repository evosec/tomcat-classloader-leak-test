# tomcat-classloader-leak-test

[![Build Status](https://travis-ci.org/evosec/tomcat-classloader-leak-test.svg?branch=develop)](https://travis-ci.org/evosec/tomcat-classloader-leak-test)

An integration test for your webapp that checks if it cleans up after itself.

## Usage

`pom.xml`:
~~~
<plugin>
    <groupId>org.codehaus.mojo</groupId>
    <artifactId>build-helper-maven-plugin</artifactId>
    <executions>
        <execution>
            <id>add-test-source</id>
            <phase>generate-test-sources</phase>
            <goals>
                <goal>add-test-source</goal>
            </goals>
            <configuration>
                <sources>
                    <source>src/it/java</source>
                </sources>
            </configuration>
        </execution>
    </executions>
</plugin>

<plugin>
    <groupId>org.apache.maven.plugins</groupId>
    <artifactId>maven-failsafe-plugin</artifactId>
    <executions>
        <execution>
            <goals>
                <goal>integration-test</goal>
                <goal>verify</goal>
            </goals>
            <configuration>
                <classpathDependencyExcludes>
                    <classpathDependencyExclude>org.springframework.boot:spring-boot-devtools</classpathDependencyExclude>
                </classpathDependencyExcludes>
                <testSourceDirectory>${project.basedir}/src/it/java</testSourceDirectory>
                <systemPropertyVariables>
                    <warName>${project.build.finalName}</warName>
                </systemPropertyVariables>
            </configuration>
        </execution>
    </executions>
</plugin>
~~~

`src/it/java/com/example/ClassLoaderLeakTestIT`:
~~~
package com.example;

import java.nio.file.Paths;
import org.junit.Test;
import de.evosec.leaktest.WebAppTest;

public class ClassLoaderLeakTestIT {

    @Test
    public void test() throws Exception {
        System.setProperty("spring.profiles.active", "integration");
        String warName = System.getProperty("warName", "demo-0.0.1-SNAPSHOT");
        Path warPath = Paths.get("./target/" + warName + ".war");
        new WebAppTest().warPath(warPath).run();
    }

}
~~~
