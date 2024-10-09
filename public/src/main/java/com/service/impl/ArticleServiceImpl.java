package com.service.impl;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.dao.LikeMapper;
import com.domain.*;
import com.domain.Collection;
import com.enums.HttpCodeEnum;
import com.exception.SystemException;
import com.service.ArticleService;

import com.service.CommentService;
import com.service.LabelService;
import com.service.UserService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.constans.Constant;
import com.dao.ArticleMapper;
import com.dao.UserMapper;
import com.service.login.LoginService;
import com.utils.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author 今昔
 * @description 文章查询接口实现类
 * @date 2022/11/9 16:51
 */
@Service
public class ArticleServiceImpl extends ServiceImpl<ArticleMapper, Article> implements ArticleService {
    @Autowired
    private ArticleMapper articleMapper;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private LabelService labelService;
    @Autowired
    private UserService userService;
    @Autowired
    private CommentService commentService;
    @Autowired
    private LoginService loginService;
    @Autowired
    private LikeMapper likeMapper;
    //锁
    private static ReentrantLock reentrantLock = new ReentrantLock();
    @Autowired
    private RedisUtil redisUtil;

    @Override
    public IPage<Article> hotArticleList(int current, int size) {
        IPage<Article> page = new Page<Article>(current, size);
        QueryWrapper<Article> wrapper = new QueryWrapper<Article>();
        wrapper.eq("view", true);
        wrapper.select(
                "id", "title", "summary", "create_time", "view_counts", "comment_counts","update_time"
                , "like_counts", "collection_counts", "popularity", "uid","cover");
        wrapper.orderByDesc("popularity");
        IPage<Article> ipage = articleMapper.selectPage(page, wrapper);
        //增加作者相关信息
        userService.setAuthorInfo(ipage.getRecords());
        return ipage;
    }

    @Override
    public Article getArticle(int id) {
        //先从redis当中取
        Article article = (Article) redisUtil.hget(Constant.ARTICLEKEY, String.valueOf(id));
        if (Objects.isNull(article)) {
            //获得锁
            try {
                if (reentrantLock.tryLock(100, TimeUnit.MILLISECONDS)) {
                    try {
                        article = articleMapper.getArticle(id);
                        if (Objects.isNull(article)) {
                            return null;
                        }
                        //查询作者简略信息
                        User author = userService.getBriefUser(article.getUid());
                        article.setAuthorAvatar(author.getAvatar());
                        article.setAuthorName(author.getName());
                        //查询评论
                        List<Comment> comments = commentService.getComments(id);
                        article.setCommentList(comments);
                        //查询标签
                        article.setLabels(labelService.getLabels(id));
                        //热度在定时任务里面计算了
                        //浏览量加一
                        article.setViewCounts(article.getViewCounts() + 1);
                        //redis中没有的存入redis
                        redisUtil.hset(Constant.ARTICLEKEY, String.valueOf(id), article, Constant.ARTICLSTORETIME);
                        article.setLiked(getLiked(id));
                        return article;
                    } finally {//释放锁
                        reentrantLock.unlock();
                    }
                } else {
                    return getArticle(id);
                }
            } catch (InterruptedException e) {
                throw new SystemException(HttpCodeEnum.THREAD_REEOR);
            }
        }
        if(loginService.getUserId()!=article.getUid()){
            //如果不是本人且文章为发布或不允许公开
            if(!article.getView()){
                throw new SystemException(HttpCodeEnum.ARTICLE_INFOERROR);
            }
        }
        article.setViewCounts(article.getViewCounts() + 1);
        redisUtil.hset(Constant.ARTICLEKEY, String.valueOf(id), article, Constant.ARTICLSTORETIME);
        article.setLiked(getLiked(id));
        article.setCollected(getCollected(id));
        return article;


    }

    @Override
    public void setPopularity(Article article) {
        //p=(log10(N)+log(1+viewCounts)+a*likeCounts+b*collectionCounts)*exp(-y(t1-t1))
        int popularity = (int) ((Math.log10(article.getContent().length()) * Constant.CONTENT +
                Constant.VIEW * Math.log(1 + article.getViewCounts())
                + Constant.LIKE * article.getLikeCounts() +
                Constant.COLLECTION * article.getCollectionCounts())
                * Math.exp(Constant.GRAVITY * (System.currentTimeMillis() - article.getCreateTime()) / Constant.TIME));
        article.setPopularity(popularity);
    }

    @Override
    public IPage<Article> getLabelArticles(int labelId, int current, int size) {
        IPage<Article> iPage = new Page<>(current, size);
        QueryWrapper<Article> wrapper = new QueryWrapper<>();
        //in方法入参是一个数组
        List list = labelService.getLabelArticles(labelId);
        wrapper.eq("view", true);
        wrapper.select(
                "id", "title", "summary", "view_counts", "create_time", "comment_counts","update_time"
                , "like_counts", "collection_counts", "popularity", "uid","cover");
        wrapper.in(list.size() > 0, "id", list);
        IPage<Article> page = articleMapper.selectPage(iPage, wrapper);
        userService.setAuthorInfo(page.getRecords());
        return page;
    }

    @Override
    public IPage<Article> getArticlesByKeyword(String keyword, int current, int size) {
        IPage<Article> iPage = new Page(current, size);
        QueryWrapper<Article> wrapper = new QueryWrapper<>();
        wrapper.select(
                "id", "title", "summary", "view_counts", "create_time", "comment_counts","update_time"
                , "like_counts", "collection_counts", "popularity", "uid","cover");
        wrapper.eq("view",true);
        wrapper.like("title", keyword);
        wrapper.or();
        wrapper.like("summary", keyword);
        IPage<Article> page = articleMapper.selectPage(iPage, wrapper);
        userService.setAuthorInfo(page.getRecords());
        return page;
    }


    @Override
    public void addArticle(Article article) {
        //为文章添加作者id
        article.setUid(loginService.getUserId());

        //添加文章日期
        article.setCreateTime(System.currentTimeMillis());
        articleMapper.addArticle(article);
        //为文章添加标签
        article.setLabels(labelService.getLabelById(article.getLabels()));
        articleMapper.addLabels(article);

    }

    @Override
    @Transactional
    public void deleteArticle(int articleId) {
        //如果这篇文章id不属于这个用户
        if(loginService.getUserId()!=getArticle(articleId).getUid())
        {
            throw new SystemException(HttpCodeEnum.ARTICLE_NOAUTHORITY);
        }
        articleMapper.deleteById(articleId);
        //删除redis中的数据
        redisUtil.hdel(Constant.ARTICLEKEY,String.valueOf(articleId));
        //删除文章标签
        labelService.deleteLabels(articleId);
    }

    @Override
    public void updateArticle(Article article) {
        //文章作者不是该用户
        if(getArticle(article.getId()).getUid()!=loginService.getUserId())
        {
            throw new SystemException(HttpCodeEnum.ARTICLE_NOAUTHORITY);
        }
        article.setUpdateTime(System.currentTimeMillis());
        articleMapper.updateArticle(article);
        Article origin= (Article) redisUtil.hget(Constant.ARTICLEKEY,String.valueOf(article.getId()));
        origin.setContent(article.getContent());
        origin.setHtmlContent(article.getHtmlContent());
        origin.setTitle(article.getTitle());
        origin.setView(article.getView());
        origin.setComment(article.getComment());
        origin.setUpdateTime(article.getUpdateTime());
        redisUtil.hset(Constant.ARTICLEKEY,String.valueOf(article.getId()),origin,Constant.ARTICLSTORETIME);

    }

    @Override
    public IPage<Article> getUserArticles(int id, int current, int size) {
        if(loginService.getUserId()==id) {
            return getMyArticles(current,size);
        }
        IPage<Article> iPage = new Page<>(current, size);
        QueryWrapper<Article> wrapper = new QueryWrapper<>();
        wrapper.select("id","uid", "title", "summary", "create_time", "view_counts","update_time",
                "comment_counts", "like_counts", "collection_counts", "popularity","cover");
        wrapper.eq("uid", id);
        //看的不是自己的
        wrapper.eq("view", true);
        wrapper.orderByDesc("create_time");
        IPage<Article> page = articleMapper.selectPage(iPage, wrapper);
        userService.setAuthorInfo(page.getRecords());
        return page;
    }

    @Override
    public IPage<Article> getMyArticles(int current, int size) {
        IPage<Article> iPage = new Page<>(current, size);
        QueryWrapper<Article> wrapper = new QueryWrapper<>();
        wrapper.select("id", "title","summary", "create_time", "view_counts","uid","update_time",
                "comment_counts", "like_counts", "collection_counts", "popularity","view","comment","original","cover");
        wrapper.orderByDesc("create_time");
        wrapper.eq("uid", loginService.getUserId());
        IPage<Article> page = articleMapper.selectPage(iPage, wrapper);
        userService.setAuthorInfo(page.getRecords());
        return page;
    }

    @Override
    public List<String> getAllArticleId() {
            List<String> allArticleId = articleMapper.getAllArticleId();
            return allArticleId;
    }

    @Override
    public void updateArticleInfo(Article article) {
        UpdateWrapper<Article> wrapper = new UpdateWrapper();
        wrapper.set("view_counts", article.getViewCounts());
        wrapper.set("collection_counts", article.getCollectionCounts());
        wrapper.set("like_counts", article.getLikeCounts());
        wrapper.set("popularity", article.getPopularity());
        wrapper.eq("id", article.getId());
        articleMapper.update(null,wrapper);
    }

    @Override
    public boolean likeArticle(int articleId) {
        //如果用户对该文章点了赞
        int uid = loginService.getUserId();
        Article article = getArticle(articleId);
        if (Objects.isNull(article)) {
            throw new SystemException(HttpCodeEnum.ARTICLE_NOTEXIST);
        }
        if (getLiked(articleId)) {
            //对点赞情况进行更新
            article.setLikeCounts(article.getLikeCounts() - 1);
            redisUtil.hset(Constant.ARTICLEKEY, String.valueOf(articleId), article,Constant.ARTICLSTORETIME);
            redisUtil.hset(Constant.LIKEKEY, articleId + ":" + uid, Constant.CANCELLIKE, Constant.LIKESTORETIME);
            return false;
        } else {
            //如果没有点赞，则设置当前状态为已点赞
            //更新点赞数
            article.setLikeCounts(article.getLikeCounts() + 1);
            redisUtil.hset(Constant.LIKEKEY, articleId + ":" + uid, Constant.TOLIKE, Constant.LIKESTORETIME);
            redisUtil.hset(Constant.ARTICLEKEY, String.valueOf(articleId), article,Constant.ARTICLSTORETIME);
            return true;
        }
    }

    @Override
    public boolean getLiked(int articleId) {
        //获得点赞用户的id
        int uid = loginService.getUserId();
        //先从redis中查是否有点赞信息
        //如果没有查到
        Object isLike= redisUtil.hget(Constant.LIKEKEY,articleId+":"+uid);
        if (Objects.isNull(isLike)) {
            //再去数据库中查
            Boolean userLike = likeMapper.getUserLike(articleId, uid);
            //任然没有查到
            if (Objects.isNull(userLike)) {
                //说明用户未对该文章点过赞
                return false;
            }
            if (userLike==Constant.TOLIKE) {
                //如果查到是已经点了赞
                return true;
            }
            //反之则是没有点赞
            return false;
        }
        //redis中查到了，进行匹配，如果为1说明已经点了赞
        if ((boolean)isLike==Constant.TOLIKE) {
            return true;
        }
        return false;
    }

    @Override
    public boolean getCollected(int articleId) {
        //获得点赞用户的id
        int uid = loginService.getUserId();
        //先从redis中查是否有点赞信息
        Object isCollect = redisUtil.hget(Constant.CollectKey, articleId + ":" + uid);
        //如果没有查到
        if (Objects.isNull(isCollect)) {
            //再去数据库中查
            Boolean userCollection = likeMapper.getUserCollection(articleId, uid);
            //任然没有查到
            if (Objects.isNull(userCollection)) {
                //说明用户未对该文章点过赞
                return false;
            }
            //如果能查到
            return true;
        }
        //redis中查到了，进行匹配，如果为1说明已经点了赞
        if ((boolean) isCollect==Constant.TOCOLLECT) {
            return true;
        }
        return false;
    }


    @Override
    public boolean collectArticle(int articleId) {
        //如果用户对该文章点了赞
        int uid = loginService.getUserId();
        Article article = getArticle(articleId);
        if (Objects.isNull(article)) {
            throw new SystemException(HttpCodeEnum.ARTICLE_NOTEXIST);
        }
        if (getCollected(articleId)) {
            //对点赞情况进行更新
            article.setCollectionCounts(article.getCollectionCounts() - 1);
            redisUtil.hset(Constant.ARTICLEKEY, String.valueOf(articleId), article,Constant.ARTICLSTORETIME);
            redisUtil.hset(Constant.CollectKey, articleId + ":" + uid, Constant.CANCELCOLLECT, Constant.LIKESTORETIME);
            likeMapper.deleteCollection(articleId,uid);
            return false;
        } else {
            //如果没有点赞，则设置当前状态为已点赞
            //更新点赞数
            article.setCollectionCounts(article.getCollectionCounts() + 1);
            redisUtil.hset(Constant.CollectKey, articleId + ":" + uid,Constant.TOCOLLECT, Constant.LIKESTORETIME);
            redisUtil.hset(Constant.ARTICLEKEY, String.valueOf(articleId), article,Constant.ARTICLSTORETIME);
            likeMapper.addCollection(articleId,uid);
            return true;
        }
    }

    @Override
    public IPage<Article> getUserRecommend(int current,int size) {
        IPage<Article> page=new Page<>(current,size);
        List<Show> list= (List<Show>) redisUtil.hget(Constant.RECOMMENDKEY, String.valueOf(loginService.getUserId()));
        list.subList((current-1)*size,current*size);
        return null;
    }

    @Override
    public IPage<Article> getUserArticleByLabelId(int labelId,int uid,int current,int size) {
        IPage<Article> iPage = new Page<>(current, size);
        QueryWrapper<Article> wrapper=new QueryWrapper<>();
        wrapper.select("id", "title", "uid","summary", "create_time", "view_counts","update_time",
                "comment_counts", "like_counts", "collection_counts", "popularity","cover","view","comment","original");
        wrapper.eq("uid",uid);
        //看的不是自己的
        if(loginService.getUserId()!=uid)
        {
            wrapper.eq("view",true);
        }
        List list=labelService.getLabelArticles(labelId);
        wrapper.in(list.size()>0,"id",list);
        IPage<Article> page = articleMapper.selectPage(iPage, wrapper);
        userService.setAuthorInfo(page.getRecords());
        return page;
    }

    @Override
    public void autoSave(Article article) {
        redisUtil.hset(Constant.SCRIPT,String.valueOf(loginService.getUserId()),article,Constant.ARTICLSTORETIME);
    }

    @Override
    public Article getScript() {
        Object o= redisUtil.hget(Constant.SCRIPT,String.valueOf(loginService.getUserId()));
        if(Objects.isNull(o)){
            return null;
        }
        return (Article) o;
    }


}
