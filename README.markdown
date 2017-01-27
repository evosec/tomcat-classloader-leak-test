# tomcat-classloader-leak-test

[![Build Status](https://travis-ci.org/evosec/tomcat-classloader-leak-test.svg?branch=develop)](https://travis-ci.org/evosec/tomcat-classloader-leak-test)

An integration test for your webapp that checks if it cleans up after itself.

## Usage

~~~

Path warPath = Paths.get("sample.war");
new de.evosec.leaktest.WebAppTest().warPath(warPath).run();

~~~
