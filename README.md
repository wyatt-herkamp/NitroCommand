# NitroCommand

#### Implementations
1. JDA3

#### Information

NitroCommand is a simple, easy-to-use and extensible command framework.

This is the succession to [TuxCommand](https://github.com/wherkamp/TuxCommand) and [MonoCommand](https://github.com/Monology/MonoCommand)

#### Developers
- [Wyatt](https://github.com/wherkamp)

- [Monology](https://github.com/monology)

#### Maven
```xml
<repositories>
    <repository>
       <id>kingtux-repo</id>
       <url>https://repo.kingtux.me/repository/maven-public/</url>
    </repository>
</repositories>

<dependencies>
    <dependency>
       <groupId>dev.nitrocommand</groupId>
       <artifactId>core</artifactId>
       <!--- Make sure to use the latest version! -->
       <version>1.0-SNAPSHOT</version>
       <scope>compile</scope>
    </dependency>
</depenencies>
```
#### Gradle
```
repositories {
  maven { url 'https://repo.kingtux.me/repository/maven-public/' }
}

dependencies {
   compile "dev.nitrocommand:core:1.0-SNAPSHOT"
}
```
