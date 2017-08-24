# tomcat-classloader-leak-test

[![Build Status](https://travis-ci.org/evosec/tomcat-classloader-leak-test.svg?branch=develop)](https://travis-ci.org/evosec/tomcat-classloader-leak-test)

An integration test for your webapp that checks if it cleans up after itself.

## Usage

`pom.xml`:
~~~
<dependency>
    <groupId>de.evosec</groupId>
    <artifactId>tomcat-classloader-leak-test</artifactId>
    <version>0.0.1</version>
    <scope>test</scope>
</dependency>

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
                <argLine><![CDATA[-XX:+HeapDumpOnOutOfMemoryError -XX:HeapDumpPath=${project.build.directory} -XX:MaxMetaspaceSize=160m]]></argLine>
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

## How it works

1. The test setups an embedded Tomcat.
2. Then the war file from the build is added to the Tomcat
3. Wait for the application to start (By default it waits until the root returns HTTP 200 OK)
4. Get a WeakReference to the started Context
5. Stop the application
6. Start creating classes to put the Metaspace/PermGen under pressure - thus triggering GC eventually.
7. Stop when WeakReference is null - thus the Context has been cleaned up properly. If not, then there is a ClassLoader leak.
