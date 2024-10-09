package com.service.impl;

import com.constans.Constant;
import com.enums.HttpCodeEnum;
import com.exception.SystemException;
import com.service.VerifyService;
import com.utils.ImageCode;
import com.utils.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

/**
 * @author 今昔
 * @description 验证码发送实现
 * @date 2022/11/13 13:51
 */
@Service
public class VerifyServiceImpl implements VerifyService {
    @Autowired
    private RedisUtil redisUtil;

    @Autowired
    private JavaMailSender javaMailSender;
    @Override
    public Map getImageVerify() {
        ImageCode imageCode=new ImageCode();
        String base64 = ImageCode.getBase64(imageCode.getImage());
        String key= UUID.randomUUID().toString();
        redisUtil.set(Constant.IMAGEVERIFYLEY+key,imageCode.getCode().toUpperCase().toUpperCase(), Constant.VERIFYTIME);
        Map map =new HashMap();
        map.put("key",key);
        map.put("img",base64);
        return map;
    }

    @Override
    public void getEmailVerify(String email) {
        SecureRandom secureRandom = new SecureRandom();
        //生成验证码并将其储存在redis当中
        int number = 100000 + secureRandom.nextInt(900000);
        //加上email防止存储混乱,有效时间10分钟
        redisUtil.set(Constant.EMAILVERIFYKEY+email, number, Constant.VERIFYTIME);
        if (!Objects.isNull(redisUtil.get(Constant.SEMAILVERIFYUSER))) {
            //如果能获取到出储存的，说明重复获取了验证码
            throw new SystemException(HttpCodeEnum.RELREAT_VERTIFY);
        } else {
            //如果不能获取则存入该id，设置时间未60s
            redisUtil.set(Constant.SEMAILVERIFYUSER, email, 60);
        }
        //发送邮件
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setFrom(Constant.MYEMAIL);
        simpleMailMessage.setTo(email);
        simpleMailMessage.setSubject("修改密码");
        simpleMailMessage.setText("验证码:" + number);
        javaMailSender.send(simpleMailMessage);
    }

    @Override
    public boolean validateEmailVerify(int verify,String email) {
        Object o = redisUtil.get(Constant.EMAILVERIFYKEY + email);
        if(Objects.isNull(o))
        {
            return false;
        }
        if((int)o!=verify)
        {
            return false;
        }
        redisUtil.del(Constant.EMAILVERIFYKEY+email);
        return true;
    }

    @Override
    public boolean validateImageVerify(String verify, String key) {
        String o = (String) redisUtil.get(Constant.IMAGEVERIFYLEY + key);
        if(!Objects.isNull(o)){
            if(o.equals(verify.toUpperCase())){
                redisUtil.del(Constant.IMAGEVERIFYLEY+key);
                return true;
            }
            return false;
        }
        return false;
    }
}
