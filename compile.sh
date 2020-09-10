#!/bin/bash

# 不能有空格
cur_dir=$(pwd)    # 项目文件当前目录
# echo $cur_dir

function compile(){
    qq=$cur_dir     # java 项目文件目录
    qq_src=$cur_dir/src     # 源代码
    qq_lib=$cur_dir/lib     # 依赖库
    # echo $qq
    # echo $qq_src
    # echo $qq_lib

    qq_class=$cur_dir/WEB-INF/classes   # 编译后的文件目录
    rm -rf $qq_src/sources.list     # 将src目录下的所有java文件的名称存入src/sources.list 文件中
    find $qq_src -name "*.java" > $qq_src/sources.list
    cat $qq_src/sources.list

    # 批量编译java文件
    # 编码格式 utf-8
    # 依赖库:隔开
    javac -d $qq_class -encoding utf-8 -cp .:$qq_lib/fastjson-1.2.62.jar:$qq_lib/servlet-api.jar:$qq_lib/websocket-api.jar:$qq_lib/commons-fileupload-1.3.2.jar -g -sourcepath $qq_src @$qq_src/sources.list

    # 以下脚本内容Web端不需要，只针对一般项目
    # cd $qq_class
    # jar -cvfm $cur_dir/qq.jar $cur_dir/MANIFEST.MF *
    # 赋予可执行权限
    # sudo chmod a+x $cur_dir/qq.jar

    # 生成 war包（当前目录下）
    # jar -cvf qq.war *
}

compile
exit 0