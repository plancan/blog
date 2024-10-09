package com;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.constans.Constant;
import com.dao.*;
import com.domain.Comment;
import com.domain.Message;
import com.domain.User;
import com.service.*;
import com.service.login.UserDetailService;
import com.service.login.LoginService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.domain.Article;

import com.utils.ImageUpload;
import com.utils.RedisUtil;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.support.MultipartFilter;
import springfox.documentation.spring.web.json.Json;


import javax.sql.DataSource;
import java.awt.*;
import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.List;


/**
 * @author 今昔
 * @description 测试
 * @date 2022/11/10 17:18
 */

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class 测试 {
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private DataSource dataSource;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    ArticleMapper articleMapper;
    @Autowired
    private UserDetailService userDetailsService;
    @Autowired
    CommentService commentService;
    @Autowired
    private CommentMapper commentMapper;
    @Autowired
    private ArticleService articleService;
    @Autowired
    private LabelService labelService;
    @Autowired
    private LoginService loginService;
    @Autowired
    private RedisUtil redisUtil;
    @Autowired
    private LabelMapper labelMapper;
    @Autowired
    private LikeMapper likeMapper;
    @Autowired
    private UserService userService;
    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private MessageService messageService;
    @Autowired
    private MessageMapper messageMapper;
    @Test
    public void teat() throws IOException {
        Date date = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        System.out.println(date.getTime());
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.select("uid", "id", "create_time", "content", "title");
        queryWrapper.eq("id", 4);
        Article article = articleMapper.selectOne(queryWrapper);
        Article a = new Article();
        a.setTitle("测试测试测试测试2");
        a.setId(4);


        char[] s = new char[100];
//            FileReader file=new FileReader("E:\\java-web-project\\myblog\\blog\\src\\main\\resources\\test.text");
//            file.read(s);
//            System.out.println(s);
//            file.close();
        date.setTime(article.getCreateTime());
        dateFormat.format(date);


        System.out.println(article.getTitle());
    }

    @Test
    public void test2() throws ParseException {

//        List<Comment> comments = commentService.getComments(2);
        //Article article=articleService.getArticle(2);
        List<Integer> list=new ArrayList<>();
        list.add(1);
        list.add(2);
        list.add(3);
        int b=1;
        System.out.println();
        IPage page=articleService.getLabelArticles(1,1,2);
        System.out.println(page.getTotal());


    }
    @Test
    public void tests2(){
//        redisUtil.hdel(Constans.ARTICLEKEY,"2");
//        System.out.println(redisUtil.hget(Constans.ARTICLEKEY,String.valueOf(2)));
        //redisUtil.del(Constans.ARTICLEKEY);
     String key="ssd";
     Map<String,Object> map=new HashMap();
     map.put(key,true);
     map.put("s",false);
     map.put("sd",true);
     map.put("ssa",false);
     for(Map.Entry entry:map.entrySet()){
         if((boolean)entry.getValue() ){
             System.out.println(1);
         }
     }
        System.out.println(map);


    }
    @Test
    public void test3() throws IOException {
        List list=new ArrayList();
//        Like like=new Like();
//        like.setUid("1");
//        like.setArticleId("2");
//        list.add(like);
//        Like like1=new Like();
//        like1.setUid("3");
//        like1.setArticleId("2");
//        list.add(like1);
        if(!list.isEmpty()){}
        likeMapper.updateLike(list);

    }

    @Test
    public void test4(){
//        Like like=new Like();
        List list=new ArrayList();
//        for(int i=0;i<10;i++){
//
//            like.setLiked(false);
//            list.add(like);
//        }
////        System.out.println(list);
//        List list=new ArrayList();
//        Collection collection=new Collection();
//        collection.setArticleId("2");
//        collection.setUid("2");
//        list.add(collection);
//        likeMapper.deleteCollection(list);
            list.add("ad");
            list.add("sd");
        System.out.println(list.subList(0,list.size()));
    }

    @Test
    public void test5() throws IOException {


//        File file = new File("../public/src/main/resources/img/img.png");
//        MultipartFile cMultiFile = new MockMultipartFile("file", file.getName(), null, new FileInputStream(file));
//        for(int i=0;i<20;i++)
//        {
//            String s = ImageUpload.uploadArticleCover(cMultiFile);
//            System.out.println(s);
//        }
//
        Article article=new Article();
        article.setContent("sdff");
        articleService.autoSave(article);
        Article article1=articleService.getScript();
        System.out.println(article1);
    }
    @Test
    public void test6(){
//        articleService.likeArticle(3);
//        System.out.println(articleService.getLiked(3));
//        List mes=new LinkedList();
//        Message message=new Message();
//        message.setContent("测试");
//        Message message1=new Message();
//        message1.setContent("测试2");
//        mes.add(message);
//        mes.add(message1);
////        messageMapper.storeMessage(mes);
//        System.out.println(messageService.getMessages(2));F
        MultipartFile file=null;
//        try {
//            userService.uploadAvatar(file);
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
//        System.out.println();
//        User user=new User();
//        user.setPassword("12345678");
//        user.setName("user4");
//        try {
//            userService.register(user);
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
//        BCryptPasswordEncoder cryptPasswordEncoder=new BCryptPasswordEncoder();
//        System.out.println(cryptPasswordEncoder.matches("12345678","$2a$10$T8OsXRZ43T/vfxesBo6y/ObuDH7MlxTGTkSOtpUmh/DJbLDtOMQ1G"));
//        System.out.println(cryptPasswordEncoder.encode("12345678"));
//        User user=new User();
//        user.setId(2);
//        user.setPassword("12345678");
//        System.out.println(loginService.login(user));
        List<Message> newMessage = messageService.getNewMessage(2, 2);
//        Map<Integer, List<Message>> messages = messageService.getMessages(2);
        messageService.getMessages(2);
        System.out.println();
    }


}
