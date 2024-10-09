package com.job;

import com.constans.Constant;
import com.dao.LikeMapper;
import com.domain.Article;
import com.domain.Collection;
import com.domain.Like;
import com.service.ArticleService;
import com.utils.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.*;

/**
 * @author 今昔
 * @description 定时任务
 * @date 2022/11/16 9:20
 */
@Component
public class BlogJob {
    @Autowired
    private ArticleService articleService;
    @Autowired
    private LikeMapper likeMapper;
    @Autowired
    private RedisUtil redisUtil;

    /**
     * @param * @param
     * @return
     * @description 对定期文章进行持久化, 4小时执行一次
     **/
    @Scheduled(cron = "0 0 0/4 * * ? ")
//    @Scheduled(cron = "0/10 * * * * ? ")
    @Async
    public void updateArticles() {
        List<String> allArticleId = articleService.getAllArticleId();
        for (String id : allArticleId) {
            //取
            Article article = (Article) redisUtil.hget(Constant.ARTICLEKEY, id.toString());
            //如果不为空
            if (!Objects.isNull(article)) {
                articleService.setPopularity(article);
                articleService.updateArticleInfo(article);
                redisUtil.set(Constant.ARTICLEKEY + id, article, Constant.ARTICLSTORETIME);
            }
        }

    }

    /**
     * @param * @param
     * @return
     * @description 对点赞，收藏情况进行更新
     **/
    @Scheduled(cron = "0 0 0/4 * * ? ")
//    @Scheduled(cron = "0/10 * * * * ? ")
    @Async
    public void updateLikesAndCollections() {
        Map<String, Object> map = redisUtil.hmget(Constant.LIKEKEY);
        List<Like> likes = new ArrayList<>();
        Like like = new Like();

        for (Map.Entry entry : map.entrySet()) {
            //切割出articleId和uid
            String[] s = StringUtils.split(entry.getKey().toString(), ":");
            like.setArticleId(s[0]);
            like.setUid(s[1]);
            like.setLiked((boolean) entry.getValue());
            likes.add(like);
        }
        //不空进行更新
        if (!likes.isEmpty()) {
            likeMapper.updateLike(likes);
        }
        Map<String, Object> collections = redisUtil.hmget(Constant.CollectKey);
        List<Collection> collectionList = new ArrayList<>();
        List<Collection> deleteCollections = new ArrayList<>();
        Collection collection = new Collection();
        for (Map.Entry entry : collections.entrySet()) {
            String[] s = StringUtils.split(entry.getKey().toString(), ":");
            collection.setArticleId(s[0]);
            collection.setUid(s[1]);
            if ((boolean) entry.getValue()) {
                collectionList.add(collection);
            } else {
                deleteCollections.add(collection);
            }
        }
        //不空才执行操作
        if (!collectionList.isEmpty()) {
            likeMapper.updateCollections(collectionList);
        }
        if (!deleteCollections.isEmpty()) {
            likeMapper.deleteCollections(deleteCollections);
        }
    }

    public void storeMessage(){

    }


}

