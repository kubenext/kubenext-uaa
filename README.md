# Kubenext User Account 
Kubenext Uaa

## Hazelcast 注意事项

- java11中增加以下modules
```
--add-modules java.se --add-exports java.base/jdk.internal.ref=ALL-UNNAMED --add-opens java.base/java.lang=ALL-UNNAMED --add-opens java.base/java.nio=ALL-UNNAMED --add-opens java.base/sun.nio.ch=ALL-UNNAMED --add-opens java.management/sun.management=ALL-UNNAMED --add-opens jdk.management/com.sun.management.internal=ALL-UNNAMED
```

- 版本

spring boot 2.1.3 需要依赖`hazelcast-hibernate53`