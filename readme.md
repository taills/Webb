# Webb

取名自詹姆斯·韦伯空间望远镜（英语：James Webb Space Telescope, JWST）中的 Webb，在此程序中也释义为 Web Beacon

## 开发环境

- jdk 1.8.0_322
- maven 3.8.5

### 安装依赖


#### 方法一

从 ` https://github.com/JFormDesigner/FlatLaf.git` 编译项目，并把 `flatlaf-demo` 安装到 maven 本地仓库。

#### 方法二

直接使用`lib`目录下编译好的

```shell
mvn install:install-file -Dfile=lib/flatlaf-demo-2.3.jar  -DgroupId=com.formdev -DartifactId=flatlaf-demo -Dversion=2.3 -Dpackaging=jar
```

## 构建

默认 `mvn package` 即可生成2种类型的jar包，按需使用即可。

###  Webb-xxxx-jar-with-dependencies.jar

包含所有依赖，运行方式。

```shell
java -jar Webb-xxxx-jar-with-dependencies.jar
```


### Webb-xxxx.jar
不包含依赖， 分发时需要附带相关依赖文件。
```shell
mvn dependency:copy-dependencies
# 复制所需要的依赖,默认会把所需的依赖复制到 target/dependency 目录下
```