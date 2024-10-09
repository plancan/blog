package com.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.domain.User;
import com.domain.UserVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author 今昔
 * @description 用户信息查询
 * @date 2022/11/10 18:51
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {
    /**
     * @param * @param user
     * @return {@link UserVo}
     * @description
     **/
    public User login(User user);

    /**
     * @param * @param user
     * @return
     * @description 添加新用户
     **/
    public void addUser(User user);

    /**
     * @param * @param email
     * @return
     * @description 查询emial是否重复
     **/
    public String getIdByEmail(String email);

    /**
     * @param * @param id
     * @return {@link List< UserVo>}
     * @description 获得用户关注列表
     **/
    public List<User> getUserFollow(int id);

    /**
     * @param * @param id
     * @return {@link String}
     * @description 获得以前的头像
     **/
    public String getAvatar(int id);

    /**
     * @param * @param avatar
     * @return
     * @description 更新用户头像
     **/
    public void updateAvatar(String avatar);

    /**
     * @param * @param id
     * @return {@link String}
     * @description 获得以前的背景图
     **/
    public String getSpace(int id);

    /**
     * @param * @param space
     * @return
     * @description 更新用户背景图
     **/
    public void updateSpace(String space);

    /**
     * @param *        @param uid
     * @param followId
     * @return {@link Integer}
     * @description 查询用户是否关注了某个人
     **/
    public Integer getFollow(@Param("uid") int uid, @Param("followId") int followId);

    /**
     * @param * @param id
     * @return
     * @description 取消关注
     **/
    public void cancelFollow(int id);

    /**
     * @param * @param null
     * @return {@link null}
     * @description 进行关注
     **/
    public void toFollow(@Param("uid") int uid, @Param("followId") int followId);
}
