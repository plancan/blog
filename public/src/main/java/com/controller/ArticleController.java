package com.controller;

import com.domain.Label;
import com.service.ArticleService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.domain.Article;
import com.domain.Resp;
import com.enums.HttpCodeEnum;
import com.service.LabelService;
import com.service.ValidateDataService;
import com.utils.ImageUpload;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * @author 今昔
 * @description 文章展示相关操作类
 * @date 2022/4/20 16:09
 */
@CrossOrigin
@RestController
@RequestMapping("/blog/article")
@Api(tags = "文章信息接口")
public class ArticleController {
    @Autowired
    private ArticleService articleService;
    @Autowired
    private ValidateDataService validateDataService;
    @Autowired
    private LabelService labelService;

    @GetMapping("/getHotArticles/{current}/{size}")
    @ApiOperation(nickname = "热门文章",value = "查询热门文章列表")
    @ApiImplicitParams(
            {@ApiImplicitParam(name = "current",value = "当前页数",required = true),
             @ApiImplicitParam(name = "size",value = "每页大小",required = true)}
    )
    public Resp<Article> getHotArticles(@PathVariable int current, @PathVariable int size) {
        IPage<Article> page = articleService.hotArticleList(current, size);
        Resp<Article> resp = new Resp(page);
        return resp;
    }

    @GetMapping("/getLabelArticles/{labelId}/{current}/{size}")
    @ApiOperation(nickname = "分类查询",value = "根据标签查询文章列表")
    @ApiImplicitParams(
            {
                    @ApiImplicitParam(name = "labelId",value = "标签id",required = true),
                    @ApiImplicitParam(name = "current",value = "当前页数",required = true),
                    @ApiImplicitParam(name = "size",value = "每页大小",required = true)}
    )
    public Resp<Article> getLabelArticles(@PathVariable int labelId, @PathVariable int current, @PathVariable int size) {
        IPage<Article> page = articleService.getLabelArticles(labelId, current, size);
        Resp<Article> resp = new Resp(page);
        return resp;
    }

    @GetMapping("/getArticle/{id}")
    @ApiOperation(nickname = "查询文章",value = "查询文章详情，需要登录")
    @ApiImplicitParams(
            {
                    @ApiImplicitParam(name = "id",value = "文章id",required = true)}
    )
    public Resp<Article> getArticle(@PathVariable int id){
        System.out.println("dddd");
        Resp resp=new Resp(articleService.getArticle(id));
        return resp;
    }


    @GetMapping("/getArticlesByKeyword/{keyword}/{current}/{size}")
    @ApiOperation(nickname = "搜索文章",value = "根据关键词查询文章列表")
    @ApiImplicitParams(
            {
                    @ApiImplicitParam(name = "keyWord",value = "关键词",required = true),
                    @ApiImplicitParam(name = "current",value = "当前页数",required = true),
                    @ApiImplicitParam(name = "size",value = "每页大小",required = true)}
    )
    public Resp<IPage> getArticlesByKeyword(@PathVariable String keyword,@PathVariable int current, @PathVariable int size){
        IPage<Article> page=articleService.getArticlesByKeyword(keyword,current,size);
        return new Resp<IPage>(page);
    }

    @GetMapping("/getLabels")
    @ApiOperation(value = "获得当前所有可选标签，无需参数,不用登录")
    public Resp<List<Label>> getLabels(){
        return new Resp<List<Label>>(labelService.getAllLabels());
    }

    @PostMapping("/uploadPicture")
    @ApiOperation(value = "上传文章图片，需要登录，返回图片地址")
    public Resp uploadPicture(@RequestParam("file")MultipartFile file)
    {
        String path = ImageUpload.uploadArticle(file);
        return new Resp(path);
    }
    @PostMapping("/autoSave")
    @ApiOperation(value = "自动保存草稿，需要登陆")
    public Resp autoSave(@RequestBody Article article){
        articleService.autoSave(article);
        return new Resp();
    }

    @GetMapping("/getScript")
    @ApiOperation(value = "获取用户草稿,需要登陆")
    public Resp getScript(){
        Article article=articleService.getScript();
        return new Resp(article);
    }
    @PostMapping("/addArticle")
    @ApiOperation(nickname = "发表文章",value = "添加一篇新文章，需要登录")
    @ApiImplicitParams(
            {
                    @ApiImplicitParam(name = "article",value = "文章对象",required = true)}
    )
    public Resp addArticle(@RequestBody Article article){
        //经过验证才能添加文章
        if(!validateDataService.validateArticle(article))
        {
            return new Resp(HttpCodeEnum.ARTICLE_INFOERROR);
        }
        articleService.addArticle(article);
        return new Resp();
    }

    @DeleteMapping("/deleteArticle/{id}")
    @ApiOperation(value = "删除一篇文章，只能删除用户本人的文章，需要登录")
    @ApiImplicitParams(
            {
                    @ApiImplicitParam(name = "id",value = "待删除文章id",required = true)}
    )
    public Resp deleteArticle(@PathVariable int id){
        articleService.deleteArticle(id);
        return new Resp();
    }

    @PutMapping("/updateArticle")
    @ApiOperation(value = "更新一篇文章，需要传入文章对象，需要登录")
    public Resp updateArticle(@RequestBody Article article)
    {
        if(!validateDataService.validateArticle(article))
        {
            return new Resp(HttpCodeEnum.ARTICLE_INFOERROR);
        }
        articleService.updateArticle(article);
        return new Resp();
    }

    @GetMapping("/getUserArticles/{id}/{current}/{size}")
    @ApiOperation(nickname = "查询用户写的文章",value = "查询一个用户的文章，需要登录,返回的是文章列表")
    @ApiImplicitParams(
            {
                    @ApiImplicitParam(name = "id",value = "用户id",required = true),
                    @ApiImplicitParam(name = "current",value = "当前页数",required = true),
                    @ApiImplicitParam(name = "size",value = "每页大小",required = true)}
    )
    public Resp<IPage> getUserArticles(@PathVariable int id,@PathVariable int current,@PathVariable int size){
        if(id==0)
        {
            IPage page=articleService.getMyArticles(current,size);
            return new Resp(page);
        }
        else {
            IPage page=articleService.getUserArticles(id,current,size);
            return new Resp(page);
        }
    }

    @GetMapping("/getUserArticlesByLabel/{uid}/{labelId}/{current}/{size}")
    @ApiOperation(value = "查询用户某一类的文章，需要登录")
    @ApiImplicitParams(
            {
                    @ApiImplicitParam(name = "uid",value = "用户id",required = true),
                    @ApiImplicitParam(name = "labelId",value = "标签id",required = true),
                    @ApiImplicitParam(name = "current",value = "当前页数",required = true),
                    @ApiImplicitParam(name = "size",value = "每页大小",required = true)
            }
    )
    public Resp getUserArticlesByLabelId(@PathVariable int uid,@PathVariable int labelId,@PathVariable int current,@PathVariable int size)
    {
        IPage<Article> articles = articleService.getUserArticleByLabelId(labelId, uid,current,size);
        return new Resp(articles);
    }

    @GetMapping("/likeArticle/{articleId}")
    @ApiOperation(value = "对文章点赞，需要登录,返回ture说明点赞成功，返回false说明取消了点赞",response = boolean.class)
    @ApiImplicitParams(
            {
                    @ApiImplicitParam(name = "articleId",value = "文章id",required = true),
                    }
    )
    public Resp likeArticle(@PathVariable int articleId){
        boolean b = articleService.likeArticle(articleId);
        return new Resp(b);
    }

    @GetMapping("/collectArticle/{articleId}")
    @ApiOperation(value = "对文章进行收藏，需要登录，返回true说明收藏成功，返回false说明取消了收藏")
    @ApiImplicitParams(
            {
                    @ApiImplicitParam(name = "articleId",value = "文章id")
            }
    )
    public Resp<Boolean> collectArticle(@PathVariable int articleId){
        return new Resp(articleService.collectArticle(articleId));
    }

}
