# WebGPUJ4W Examples
This repository has examples using the WebGPUJ4W library.

## Build
To compile the Java code to JavaScript, use  ```mvn package```. The output will be built to the ```.\target\classes``` directory. Then copy the ```.\src\main\resources\index.html``` file to the built output at ```.\target\classes``` and open the copied index.html in a browser which supports WebGPU (Chrome).

## Dependency
Ensure that you have the following repository in your pom.xml:  

```
		<!-- WebGPUJ4W repository -->
		<repository>
			<id>mason-coffee</id>
			<url>http://maven.mason.coffee/releases</url>
		</repository>
```

Ensure that you have the following dependencies in your pom.xml:  
  
  
```
		<!-- TeaVM dependency -->
		<dependency>
			<groupId>org.teavm</groupId>
			<artifactId>teavm-classlib</artifactId>
			<version>0.8.1</version>
		</dependency>
		<!-- WebGPUJ4W dependency -->
		<dependency>
			<groupId>coffee.mason.webgpuj4w</groupId>
			<artifactId>WebGPUJ4W</artifactId>
			<version>0.0.1</version>
		</dependency>
```

Ensure that you have the following build in your pom.xml:  
  
```
	<build>
		<plugins>
			<!-- TeaVM Maven Plugin -->
			<plugin>
				<groupId>org.teavm</groupId>
				<artifactId>teavm-maven-plugin</artifactId>
				<version>0.8.1</version>
				<executions>
					<execution>
						<phase>package</phase>
						<goals>
							<goal>compile</goal>
						</goals>
						<configuration>
							<source>11</source>
							<target>11</target>
							<mainClass>my.main.class.Main</mainClass>
						</configuration>
					</execution>
				</executions>
			</plugin>
		</plugins>
	</build>
```

If you encounter issues building (source 5), consider adding the following to your pom.xml:  

```
	<properties>
		<maven.compiler.source>1.8</maven.compiler.source>
		<maven.compiler.target>1.8</maven.compiler.target>
	</properties>
```
