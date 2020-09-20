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


## Web网盘后端实现
### 状态码
#### PanUser
1. 成功：100
2. 失败：200
	- 201 更新密码时，原密码错误

### Extends
1. 文件实体：分为私有和公有，私有不可分享，删除文件链接时删除文件实体；公有的可以分享，删除文件链接时，不删除文件实体。

### Problems
1. 公有文件实体什么时候删除好呢？
	- 目前删除
2. 分享文件，源文件删除后，取消外键约束，分享链接仍存在，指向一个提示文件已删除的页面，那怎么删除分享链接呢？
	- 目前定期扫描
3. 好友中 uid1 < uid2