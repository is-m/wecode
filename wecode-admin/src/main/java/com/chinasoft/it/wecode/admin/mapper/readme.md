该目录只有转换接口，其中转换的实现是由structmap在编译期自动实现的（maven compile || mvn install）

在eclipse里面要将编译好的文件/target/generated-sources/annotations 文件夹进行包含到buildPath中来，否则会出现找不到接口实现类的异常
