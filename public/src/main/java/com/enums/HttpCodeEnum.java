package com.enums;

/**
 * @author 今昔
 * @description 异常枚举
 * @date 2022/11/9 15:15
 */
public enum HttpCodeEnum {

        //操作成功
        SUCCESS(200,"操作成功"),
        //未登录
        WITHOUT_LOGIN(401,"您尚未登录"),
        //邮箱号不正确或重复
        EMAiL_ERROR(402,"邮箱号错误或已被使用"),
        //没有权限
        WITHOUT_AUTH0RITY(403,"您没有此权限"),
        //用户信息错误
        INFORMATION_ERROR(405,"用户信息错误"),
        //文章信息错误
        ARTICLE_INFOERROR(406,"文章信息错误"),
        //评论信息错误
        COMMENT_ERROR(407,"评论信息错误"),
        //验证码错误
        CODE_ERROR(408,"验证码错误"),
        //原密码错误或新密码不符合要求
        PASSWORD_ERROE(409,"原密码错误或新密码不符合要求"),
        //服务器错误
        SYSTEM_ERROR(500,"服务器错误"),
        //用户名或密码错误
        LOGIN_ERROR(505,"账号或密码错误"),
        //重复提交
        REPEAT_SUBMIT(504,"请勿重复提交"),
        //重复获取验证码
        RELREAT_VERTIFY(505,"请一分钟后再获取验证码"),
        //该标签不存在
        LABEL_NOTEXIST(506,"该标签不存在"),
        //线程错误
        THREAD_REEOR(507,"线程错误"),
        //文章不存在
        ARTICLE_NOTEXIST(508,"该id的文章不存在"),
        //您无权操作该文章
        ARTICLE_NOAUTHORITY(509,"你无权操作该文章");
        int code;
        String message;
        public int getCode() {
            return code;
        }

        public void setCode(int code) {
            this.code = code;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        HttpCodeEnum(int code, String message) {
            this.code = code;
            this.message = message;
        }
    }


