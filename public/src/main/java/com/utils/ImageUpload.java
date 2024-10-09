package com.utils;

import com.enums.HttpCodeEnum;
import com.exception.SystemException;
import com.google.gson.Gson;
import com.qiniu.util.Auth;
import com.service.login.LoginService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.multipart.MultipartFile;
import com.qiniu.common.QiniuException;
import com.qiniu.http.Response;
import com.qiniu.storage.BucketManager;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.Region;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.DefaultPutRet;
import com.qiniu.storage.model.FileInfo;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.UUID;

/**
 * @author 今昔
 * @description 图片上传工具类
 * @date 2022/11/15 16:56
 */
public class ImageUpload {

    //设置信息
    private static final String accessKey = "pyn1rc2bNW-nbYglS1iNJIB4zNcsUyRlSrHP3rUO";
    private static final String secretKey = "tczuln5vbgDrxreeCT2QQBlenHGXNrMkzTKfY444";
    private static final String bucket = "haoproject";

    public static String uploadAvatar(MultipartFile picture) {
        //获取原文件名
        String originalFilename = picture.getOriginalFilename();
        //生成新文件名
        String newname = UUID.randomUUID().toString() + "." + StringUtils.substringAfterLast(originalFilename, ".");

        //构造一个带指定 Region 对象的配置类
        Configuration cfg = new Configuration(Region.autoRegion());

        UploadManager uploadManager = new UploadManager(cfg);
        //生成上传凭证，然后准备上传

        //默认不指定key的情况下，以文件内容的hash值作为文件名
        String key = "blog/avatar/" + newname;

        try {
            //获得文件流
            InputStream inputStream = picture.getInputStream();
            Auth auth = Auth.create(accessKey, secretKey);
            String upToken = auth.uploadToken(bucket);

            try {
                Response response = uploadManager.put(inputStream, key, upToken, null, null);
                //解析上传成功的结果
                DefaultPutRet putRet = new Gson().fromJson(response.bodyString(), DefaultPutRet.class);
                return "http://img.massoao.cn/blog/avatar/" + newname;

            } catch (QiniuException ex) {
                Response r = ex.response;
                System.err.println(r.toString());
                try {
                    System.err.println(r.bodyString());
                } catch (QiniuException ex2) {
                    //ignore
                }
            }
        } catch (UnsupportedEncodingException ex) {
            //ignore
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static String uploadSpace(MultipartFile picture) {
        //获取原文件名
        String originalFilename = picture.getOriginalFilename();
        //生成新文件名
        String newname = UUID.randomUUID().toString() + "." + StringUtils.substringAfterLast(originalFilename, ".");

        //构造一个带指定 Region 对象的配置类
        Configuration cfg = new Configuration(Region.autoRegion());

        UploadManager uploadManager = new UploadManager(cfg);
        //生成上传凭证，然后准备上传

        //默认不指定key的情况下，以文件内容的hash值作为文件名
        String key = "blog/space/" + newname;

        try {
            //获得文件流
            InputStream inputStream = picture.getInputStream();
            Auth auth = Auth.create(accessKey, secretKey);
            String upToken = auth.uploadToken(bucket);

            try {
                Response response = uploadManager.put(inputStream, key, upToken, null, null);
                //解析上传成功的结果
                DefaultPutRet putRet = new Gson().fromJson(response.bodyString(), DefaultPutRet.class);
                return "http://img.massoao.cn/blog/space/" + newname;

            } catch (QiniuException ex) {
                Response r = ex.response;
                System.err.println(r.toString());
                try {
                    System.err.println(r.bodyString());
                } catch (QiniuException ex2) {
                    //ignore
                }
            }
        } catch (UnsupportedEncodingException ex) {
            //ignore
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }
    public static String uploadArticle(MultipartFile picture) {
        //获取原文件名
        String originalFilename = picture.getOriginalFilename();
        //生成新文件名
        String newname = UUID.randomUUID().toString() + "." + StringUtils.substringAfterLast(originalFilename, ".");

        //构造一个带指定 Region 对象的配置类
        Configuration cfg = new Configuration(Region.autoRegion());

        UploadManager uploadManager = new UploadManager(cfg);
        //生成上传凭证，然后准备上传

        //默认不指定key的情况下，以文件内容的hash值作为文件名
        String key = "blog/article/content/" + newname;

        try {
            //获得文件流
            InputStream inputStream = picture.getInputStream();
            Auth auth = Auth.create(accessKey, secretKey);
            String upToken = auth.uploadToken(bucket);

            try {
                Response response = uploadManager.put(inputStream, key, upToken, null, null);
                //解析上传成功的结果
                DefaultPutRet putRet = new Gson().fromJson(response.bodyString(), DefaultPutRet.class);
                return "http://img.massoao.cn/blog/article/content/" + newname;

            } catch (QiniuException ex) {
                Response r = ex.response;
                System.err.println(r.toString());
                try {
                    System.err.println(r.bodyString());
                } catch (QiniuException ex2) {
                    //ignore
                }
            }
        } catch (UnsupportedEncodingException ex) {
            //ignore
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }
    public static String uploadArticleCover(MultipartFile picture) {
        //获取原文件名
        String originalFilename = picture.getOriginalFilename();
        //生成新文件名
        String newname = UUID.randomUUID().toString() + "." + StringUtils.substringAfterLast(originalFilename, ".");

        //构造一个带指定 Region 对象的配置类
        Configuration cfg = new Configuration(Region.autoRegion());

        UploadManager uploadManager = new UploadManager(cfg);
        //生成上传凭证，然后准备上传

        //默认不指定key的情况下，以文件内容的hash值作为文件名
        String key = "blog/article/cover/" + newname;

        try {
            //获得文件流
            InputStream inputStream = picture.getInputStream();
            Auth auth = Auth.create(accessKey, secretKey);
            String upToken = auth.uploadToken(bucket);

            try {
                Response response = uploadManager.put(inputStream, key, upToken, null, null);
                //解析上传成功的结果
                DefaultPutRet putRet = new Gson().fromJson(response.bodyString(), DefaultPutRet.class);
                return "http://img.massoao.cn/blog/article/cover/" + newname;

            } catch (QiniuException ex) {
                Response r = ex.response;
                System.err.println(r.toString());
                try {
                    System.err.println(r.bodyString());
                } catch (QiniuException ex2) {
                    //ignore
                }
            }
        } catch (UnsupportedEncodingException ex) {
            //ignore
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }
    public static boolean delete(String path) {
        //构造一个带指定 Region 对象的配置类
        Configuration cfg = new Configuration(Region.autoRegion());
        //获取key
        String key = path.replace("http://img.massoao.cn/", "");
        Auth auth = Auth.create(accessKey, secretKey);
        BucketManager bucketManager = new BucketManager(auth, cfg);
        try {
            bucketManager.delete(bucket, key);
        } catch (QiniuException ex) {
            //如果遇到异常，说明删除失败
            throw new SystemException(HttpCodeEnum.SYSTEM_ERROR);
        }
        return true;
    }

}
