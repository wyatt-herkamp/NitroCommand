# NitroCommand

A simple to use command framework

Supported Implementations
1. JDA3

This is the succession to [TuxCommand](https://github.com/wherkamp/TuxCommand) and [MonoCommand](https://github.com/Monology/MonoCommand)

Developers: 
 [Wyatt](https://github.com/wherkamp) and [Peter](https://github.com/monology)

## Maven
```xml
   <repository>
      <id>kingtux-repo</id>
      <url>https://repo.kingtux.me/repository/maven-public/</url>
    </repository>
       <dependency>
         <groupId>dev.nitrocommand</groupId>
         <artifactId>core</artifactId>
         <!---Make sure you use Latest Version!-->
         <version>1.0-SNAPSHOT</version>
         <scope>compile</scope>
       </dependency>
```
## Gradle
```
repositories {
  maven { url 'https://repo.kingtux.me/repository/maven-public/' }
}
dependencies {
   compile "dev.nitrocommand:core:1.0-SNAPSHOT"
}
```