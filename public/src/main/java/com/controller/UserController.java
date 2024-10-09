package com.controller;

import com.constans.Constant;
import com.domain.Article;
import com.domain.Resp;
import com.domain.User;
import com.domain.UserVo;
import com.enums.HttpCodeEnum;
import com.service.UserService;
import com.service.ValidateDataService;
import com.service.VerifyService;
import com.service.login.LoginService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * @author 今昔
 * @description 用户相关操作接口
 * @date 2022/11/9 16:07
 */
@RestController
@CrossOrigin
@RequestMapping("/blog/user")
@Api(tags = "用户信息接口")
public class UserController {

    @Autowired
    private LoginService loginService;
    @Autowired
    private ValidateDataService validateDataService;
    @Autowired
    private UserService userService;
    @Autowired
    private VerifyService verifyService;

    @PostMapping("/login/{verify}/{key}")
    @ApiOperation(nickname = "登陆", value = "用户登陆")
    @ApiImplicitParams({@ApiImplicitParam(name = "user", value = "用户对象", required = true), @ApiImplicitParam(name = "verify", value = "图片验证码", required = true), @ApiImplicitParam(name = "key", value = "验证码的key", required = true)})
    public Resp<UserVo> login(@RequestBody User user, @PathVariable String verify, @PathVariable String key) {
        if (verifyService.validateImageVerify(verify, key)) {
            UserVo login = loginService.login(user);
            return new Resp(login);
        }
        return new Resp(HttpCodeEnum.CODE_ERROR);
    }

    @GetMapping("/logout")
    @ApiOperation(value = "退出登陆")
    public Resp logout() {
        loginService.logout();
        return new Resp();
    }


    @GetMapping("/getVerify/{type}/{email}")
    @ApiOperation(value = "获得验证码")
    @ApiImplicitParams({@ApiImplicitParam(name = "type", value = "验证码类型：imageVerify/emailVerify 二选一", required = true), @ApiImplicitParam(name = "email", value = "邮箱号，仅需要发送邮箱验证码时需要", required = false)})
    public Resp<Map> getVerify(@PathVariable String type, @PathVariable String email) {
        if (Constant.IMAGEVERIFYLEY.equals(type)) {
            Map imageVerify = verifyService.getImageVerify();
            return new Resp<>(imageVerify);
        }
        if (Constant.EMAILVERIFYKEY.equals(type)) {
            //邮箱号验证成功后才可发送验证码
            if (validateDataService.validateEmail(email)) {
                verifyService.getEmailVerify(email);
                return new Resp();
            }
            return new Resp<>(HttpCodeEnum.EMAiL_ERROR);
        }
        return new Resp<>(HttpCodeEnum.SYSTEM_ERROR);

    }

    @PostMapping("/register/{verify}")
    @ApiOperation(value = "用户注册")
    @ApiImplicitParams({@ApiImplicitParam(name = "user", value = "新注册的用户对象", required = true), @ApiImplicitParam(name = "verify", value = "邮箱验证码", required = true)})
    public Resp register(@RequestBody User user, @PathVariable int verify) throws IOException {
        //验证码验证成功后才能登陆
        if (!verifyService.validateEmailVerify(verify, user.getEmail())) {
            return new Resp(HttpCodeEnum.CODE_ERROR);
        }
        //用户信息验证合法后才能成功登陆
        if (!validateDataService.validateUserInfo(user)) {
            return new Resp(HttpCodeEnum.INFORMATION_ERROR);
        }
        User login = userService.register(user);
        return new Resp(login);
    }

    @GetMapping("/getUserInfo/{id}")
    @ApiOperation(value = "查询用户信息，需要登录")
    @ApiImplicitParams({@ApiImplicitParam(name = "id", value = "用户id", required = true)})
    public Resp<User> getUserInfo(@PathVariable int id) {
        User user = userService.getUserInfo(id);
        return new Resp<>(user);
    }

    @PutMapping("/updateUserInfo")
    @ApiOperation(value = "修改用户信息，需要登录")
    @ApiImplicitParams({@ApiImplicitParam(name = "user", value = "修改后的用户对象", required = true)})
    public Resp updateUserInfo(@RequestBody User user) {
        userService.updateUserInfo(user);
        return new Resp();
    }

    @PutMapping("/updateUserEmail/{verify}")
    @ApiOperation(value = "修改邮箱号，需要登录")
    @ApiImplicitParams({@ApiImplicitParam(name = "verify", value = "邮箱验证码", required = true), @ApiImplicitParam(name = "user", value = "需修改的用户对象，必须包含邮箱号", required = true)})
    public Resp updateUserEmail(@RequestBody User user, @PathVariable int verify) {
        //邮箱验证通过后才能修改
        verifyService.validateEmailVerify(verify, user.getEmail());
        userService.updateUserEmail(user);
        return new Resp();
    }

    @PutMapping("/updateUserPassword/{oldPassword}/{newPassword}")
    @ApiOperation(value = "更改密码，需要登录")
    @ApiImplicitParams({@ApiImplicitParam(name = "oldPassword", value = "旧密码", required = true), @ApiImplicitParam(name = "newPassword", value = "新密码", required = true)})
    public Resp updatePassword(@PathVariable String oldPassword, @PathVariable String newPassword) {
        if (!validateDataService.validatePassword(oldPassword, newPassword)) {
            return new Resp(HttpCodeEnum.PASSWORD_ERROE);
        }
        return new Resp();
    }

    @PutMapping("/resetPassword/{verify}")
    @ApiOperation(value = "重设密码，调用前需请求邮箱验证码，需要登录")
    @ApiImplicitParams({@ApiImplicitParam(name = "verify", value = "邮箱验证码", required = true),})
    public Resp resetPassword(@RequestBody User user, @PathVariable int verify) {
        if (!verifyService.validateEmailVerify(verify, user.getEmail())) {
            return new Resp(HttpCodeEnum.CODE_ERROR);
        }
        if (!validateDataService.validatePassword(user.getPassword())) {
            return new Resp(HttpCodeEnum.INFORMATION_ERROR);
        }
        return new Resp();
    }

    @PutMapping("updatePicture/{type}")
    @ApiOperation(value = "上传头像或空间照片，需要登录")
    @ApiImplicitParams({@ApiImplicitParam(name = "file", value = "图片", required = true), @ApiImplicitParam(name = "type", value = "类型 avatar/space 二选一", required = true)})
    public Resp<String> updateAvatar(@RequestParam("file") MultipartFile file, @PathVariable String type) throws IOException {
        if ("avatar".equals(type)) {
            String avatar = userService.uploadAvatar(file);
            return new Resp<String>(avatar);
        }
        if ("space".equals(type)) {
            String space = userService.uploadSpace(file);
            return new Resp<String>(space);
        }
        return new Resp<>(HttpCodeEnum.INFORMATION_ERROR);
    }

    @PutMapping("/follow/{id}")
    @ApiOperation(value = "关注用户，如果已经关注了则取消关注，返回true表示进行关注成功，返回false表示取消关注成功")
    @ApiImplicitParams({@ApiImplicitParam(name = "id", value = "需要关注的用户id", required = true)})
    public Resp follow(@PathVariable int id) {
        boolean follow = userService.follow(id);
        return new Resp(follow);
    }

    @GetMapping("/getUserFollow/{id}")
    @ApiOperation(value = "获得用户的关注列表，需要登录")
    @ApiImplicitParams({@ApiImplicitParam(name = "id", value = "用户id", required = true)})
    public Resp<List<User>> getUserFollow(@PathVariable int id) {
        List<User> followList = userService.getFollowList(id);
        return new Resp<List<User>>(followList);
    }

    @GetMapping("/getUserCollection")
    @ApiOperation(value = "获得用户的收藏列表，不需要参数，需要登录，且用户只能看到自己的")
    @ApiImplicitParams({@ApiImplicitParam(name = "id", value = "用户id", required = true)})
    public Resp<List<Article>> getUserCollection() {
        List<Article> collection = userService.getCollection();
        return new Resp<>(collection);
    }
}
