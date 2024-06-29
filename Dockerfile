# 使用包含Maven的基础镜像
FROM maven:3.8.4-openjdk-17 as builder

# 设置工作目录
WORKDIR /app

# 复制pom.xml和源代码到工作目录
COPY pom.xml .
COPY src ./src

# 使用Maven打包应用，跳过单元测试
RUN mvn clean package -DskipTests

# 使用Java基础镜像运行打包好的应用
FROM openjdk:17

WORKDIR /app

# 从构建阶段复制打包好的jar文件到工作目录
COPY --from=builder /app/target/*.jar app.jar

# 指定容器启动时运行的命令
CMD ["java", "-jar", "app.jar"]
