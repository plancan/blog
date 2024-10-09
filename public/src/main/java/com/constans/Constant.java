package com.constans;

/**
 * @author 今昔
 * @description 常量类
 * @date 2022/11/11 17:54
 */
public class Constant {
    //内容热度系数
    public static final double CONTENT = 0.75;
    //一天的毫秒数
    public static final int TIME = 84600000;
    //点赞热度系数
    public static final int LIKE = 2;
    //浏览热度系数
    public static final int VIEW = 1;
    //收藏热度系数
    public static final int COLLECTION = 5;
    //重力因子
    public static final double GRAVITY = -0.05;
    //redis存储文章id的key
    public static final String AETICLEIDKEY = "articleId:";
    //redis储存点赞情况的key
    public static final String LIKEKEY = "like:";
    //redis存储收藏情况的key
    public static final String CollectKey = "collect:";
    //储存推荐文章的key
    public static final String RECOMMENDKEY = "recommend:";
    //进行收藏
    public static final boolean TOCOLLECT = true;
    //取消收藏
    public static final boolean CANCELCOLLECT = false;
    //进行点赞
    public static final boolean TOLIKE = true;
    //取消点赞
    public static final boolean CANCELLIKE = false;
    //redis存储点赞情况的时间
    public static final long LIKESTORETIME = 24 * 3600 * 5;
    //redis储存收藏情况的key
    public static final String COLLECTIONKEY = "collection:";
    ;
    //在redis存储文章的key
    public static final String ARTICLEKEY = "article:";
    //redis存储文章的时间
    public static final long ARTICLSTORETIME = 24 * 3600 * 20;
    //登录用户存储时间
    public static final long LOGINUSERTIME = 24 * 3600 * 20;
    //登录用户存储id
    public static final String LOGINUSERKEY = "loginUser:";
    //图片验证码储存key
    public static final String IMAGEVERIFYLEY = "imageVerify";
    //邮箱验证码储存key
    public static final String EMAILVERIFYKEY = "emailVerify";
    //获取过邮箱验证码的用户
    public static final String SEMAILVERIFYUSER = "emailVeifyUser:";
    //验证码储存时间
    public static final long VERIFYTIME = 10 * 60;
    //我的邮箱号
    public static final String MYEMAIL = "188149357@qq.com";
    //用户的默认身份，即普通用户
    public static final int USERIDENTITY = 1;
    //redis草稿表key
    public static final String SCRIPT = "script";
    // 聊天记录表key
    public static final String MESSAGE="message:";
}
