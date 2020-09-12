## 网盘的后端实现


### Tips
1. mysql 不支持check约束
2. mariadb 10.2 以上版本开始支持check约束，但是check约束内不能有 len()函数
3. 依赖库 lib文件夹应该在 WEB-INF 下
4. tomcat 出现问题应该首先查看日志文件
    ```shell
    # 实时查看日志，在tomcat/logs下执行
    tail -f catalina.out
    ```



### Problems

1. 文件上传需要的jar包，commons-fileupload.jar, commons-io.jar, commons-logging.jar（可能需要）