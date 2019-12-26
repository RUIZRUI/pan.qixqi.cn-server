package club.qixqi.qq;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import java.util.Set;
import java.util.Random;
import java.util.UUID;

import javax.naming.NamingException;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import club.qixqi.qq.entity.Friend;
import club.qixqi.qq.entity.Message;
import club.qixqi.qq.entity.User;
import club.qixqi.qq.entity.FileLink;
import club.qixqi.qq.util.FileLinkUtil;

public class Test{
    public static void main(String[] args){
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Friend friend1 = new Friend(11, "qixqi", 'f', "19818965587", "default.png", "1999-12-14", df.format(new Date()), df.format(new Date()), df.format(new Date()));
        // System.out.println(JSON.toJSONString(friend1));
        Friend friend2 = new Friend(12, "qixqi", 'f', "19818965587", "default.png", "1999-12-14", df.format(new Date()), df.format(new Date()), df.format(new Date()));
        Friend friend3 = new Friend(13, "qixqi", 'f', "19818965587", "default.png", "1999-12-14", df.format(new Date()), df.format(new Date()), df.format(new Date()));

        List<Friend> friend_list = new ArrayList<>();
        friend_list.add(friend1);
        friend_list.add(friend2);
        friend_list.add(friend3);
        // System.out.println(JSON.toJSONString(friend_list));
        
        // json字符串解析成对象
        // String friendStr = JSON.toJSONString(friend1);
        // Friend friend4 = JSON.parseObject(friendStr, Friend.class);
        // System.out.println(friend4);

        // List<User> list = JSON.parseArray(jsonStr, User.class);
        // for(int i=0; i<list.size(); i++){
        //     System.out.println(list.get(i));
        // }


        Map<String, String> map = new HashMap<String, String>();
        map.put("id", "1000000");
        map.put("username", "qixqi");
        map.put("sex", "女");
        // System.out.println(map);

        Set<String> set = map.keySet();
        for(String key : set){
            // System.out.println(key + " = " + map.get(key));
        }


        // User user = JSON.parseObject("null", User.class);
        // if(user == null){
        //     System.out.println("null");
        // }


        Message message = new Message(207794, 207794, "张雪花", "default-icon.png", 801935, 'f', 'w', "I love you, heihei", df.format(new Date()), 'i');
        // System.out.println(JSON.toJSONString(message));


        Map<Integer, String> mapList = new HashMap<Integer, String>();
        mapList.put(1, "hello");
        mapList.put(1, "world");
        // System.out.println("size = " + mapList.keySet().size());


        


        // String 转 JSON对象
        JSONObject jsonObject2 = JSON.parseObject(JSON.toJSONString(message));
        // Bean对象 转 String
        // System.out.println(JSON.toJSONString(message));
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("method", "searchAll");
        // Bena对象 转 JSON对象
        JSONObject jsonObject3 = (JSONObject)JSON.toJSON(message);
        jsonObject.put("message", jsonObject3);
        // Json对象转Bean对象
        // JSONObject userJson = JSONObject.parseObject(userString);
        // User user = JSON.toJavaObject(userJson,User.class);
        // System.out.println(jsonObject.toJSONString());
        // System.out.println();
        // System.out.println(jsonObject.getString("method"));
        // System.out.println(jsonObject.containsKey("method"));


        JSONObject jsonObject4 = new JSONObject();
        jsonObject4.put("method", "searchAll");
        jsonObject4.put("userId1", 207794);
        jsonObject4.put("userId2", 801935);
        // System.out.println(jsonObject4.toJSONString());


        JSONObject jsonObject5 = new JSONObject();
        jsonObject5.put("method", "add");
        jsonObject5.put("message", jsonObject3);
        // jsonObject5.put("message1", jsonObject3.toJSONString());
        // System.out.println(jsonObject5.toJSONString());
        // System.out.println(jsonObject5.getString("message"));
        // System.out.println(jsonObject5.getString("message1"));



        String testStr = "1000;1001;1002;2121;";
        String[] str = testStr.split(";");
        for(String item: str){
            // System.out.println(item);
        }
        // System.out.println(str.length);


        String fileName = "helloworld.";
        String test = fileName.substring(fileName.lastIndexOf(".")+1);
        if(test == null){
            // System.out.println("null");
        }else if(test.equals("")){
            // System.out.println("empty");
        }
        // System.out.println(test);


        // int a = Integer.parseInt("");
        // System.out.println(a);

        

        List<FileLink> folderList = null;
        List<FileLink> fileList = null;
        // System.out.println(JSON.toJSONString(folderList));
        JSONObject folderJson = (JSONObject) JSON.toJSON(folderList);
        JSONObject fileJson = (JSONObject) JSON.toJSON(fileList);
        JSONObject objects = new JSONObject();
        objects.put("folder", folderJson);
        objects.put("file", "null");
        // System.out.println(objects.toJSONString());


        // 注册登录成功后，创建用户的根文件夹
        // try{
            // int linkId = (int)((Math.random()*9+1)*1000000);    // 7位随机整数
            // String createLinkTime = df.format(new Date());
            // FileLink rootFolder = new FileLink(linkId, 801935, -1, null, null, 'y', "郑翔", "", "", 'y', -1, createLinkTime);
            // if(FileLinkUtil.add(rootFolder)){   // 新建根文件夹成功
                // System.out.println(JSON.toJSONString("新建文件夹成功"));        
            // }else{  // 新建根文件夹失败
                // System.out.println("error");
                // System.out.println("新建根文件夹失败");
            // }
        // } catch(NamingException ne){
            // ne.printStackTrace();
            // System.out.println(ne.getMessage());
        // } catch(SQLException e){
            // e.printStackTrace();
            // System.out.println(e.getMessage());
        // }

        int linkId = (int)((Math.random()*9+1)*1000000);
        String createLinkTime = df.format(new Date());
        System.out.println(linkId);
        System.out.println(createLinkTime);


        String strJson = "{\"rootFolder\":{\"parent\":-1,\"isRoot\":\"y\",\"createLinkTime\":\"2019-12-19 21:26:18.0\",\"folderList\":\"\",\"userId\":801935,\"isFolder\":\"y\",\"linkId\":1706130,\"fileSize\":0,\"folderName\":\"郑翔\",\"fileList\":\"\",\"fileId\":-1}}";
        // System.out.println(strJson);
        JSONObject jsonObject6 = JSONObject.parseObject(strJson);
        JSONObject jsonObject7 = jsonObject6.getJSONObject("rootFolder");
        // System.out.println(jsonObject7.toJSONString());


        String strJson1 = "{}";
        JSONObject jsonObject8 = JSON.parseObject(strJson1);
        if(jsonObject8.getJSONObject("fileList") == null){
            // System.out.println("null");
        }

        
        String strJson2 = "{\"test\":[{\"parent\":12},{\"parent\":13}]}";
        JSONObject jsonObject9 = JSON.parseObject(strJson2);
        // System.out.println(jsonObject9.toJSONString());
        

        Random random = new Random();
        StringBuilder builder = new StringBuilder();
        for(int i=0; i<4; i++){
            int key = random.nextInt(36);
            if(key < 10){       // 产生数字
                builder.append(key);
            }else{              // 产生小写字母
                builder.append((char)(key+87));
            }
        }
        System.out.println(builder.toString());


        String shareMask = UUID.randomUUID().toString().replaceAll("-", "");
        System.out.println(shareMask);


    }
}