#Che Gradle Plugin

Provides ability to create Gradle Projects in [Eclipse Che](http://github.com/codenvy/che).

####How to use this plugin
```$ mkdir -p ~/che/gradle && cd ~/che/gradle```

```$ git clone https://github.com/vzhukovskii/che-gradle-plugin.git```

```$ cd che-gradle-plugin```

```$ mvn clean install```

```$ cd .. && git clone https://github.com/codenvy/che.git```

```$ cd che```

In `che/assembly-machine-war/pom.xml` add:

```
        <dependency>
            <groupId>pro.javax.che.plugin.gradle</groupId>
            <artifactId>gradle-server</artifactId>
            <version>1.0-SNAPSHOT</version>
        </dependency>
```

In `che/assembly-ide-war/pom.xml` add:

```
        <dependency>
            <groupId>pro.javax.che.plugin.gradle</groupId>
            <artifactId>gradle-client</artifactId>
            <version>1.0-SNAPSHOT</version>
        </dependency>
```

In `che/assembly-ide-war/src/main/resources/org/eclipse/che/ide/IDE.gwt.xml` add:

```
    <inherits name="pro.javax.che.plugin.gradle.Gradle"/>
```

:warning: during `che` build, maven enforcer plugin will warn that you provide invalid dependency. So, in this case you should build `che` with argument `-Denforcer.skip=true`:

```$ mvn clean install -Denforcer.skip=true```

```$ cd assembly-main/target/eclipse-che-4.0.0-beta-12-SNAPSHOT/eclipse-che-4.0.0-beta-12-SNAPSHOT/bin && ./che.sh run```

Finally, go to: [http://127.0.0.1:8080/ide/workspace](http://127.0.0.1:8080/ide/workspace)

####Licensing
This code is under GNU GPLv3 license. You can redistribute it by your own.

####Contributing
Contributing performs via Pull Requests.