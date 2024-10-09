package com.utils;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

/**
 * @author 今昔
 * @version 1.0
 * @description: 自定义工具栏类
 * @date 2023/3/9 10:21
 */
public class MyUtils {
    public static <T> List<T> castList(Object obj, Class<T> target)
    {
        List<T> result =new  LinkedList<T>();
        if(Objects.isNull(obj)){
            return null;
        }
        if(obj instanceof List<?>)
        {
            for (Object o : (List<?>) obj)
            {
                result.add(target.cast(o));
            }
            return result;
        }
        return null;
    }

}
