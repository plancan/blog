package com.domain;

/**
 * @author 今昔
 * @description 用户特征向量
 * @date 2022/11/9 14:29
 */
public class Feature {
    private int uid;
    private int featureId;
    private String feature;
    private int value;

    public Feature(){

    }

    public Feature(int uid, String feature, int value) {
        this.uid = uid;
        this.feature = feature;
        this.value = value;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public String getFeature() {
        return feature;
    }

    public void setFeature(String feature) {
        this.feature = feature;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "Feature{" +
                "uid=" + uid +
                ", feature='" + feature + '\'' +
                ", value=" + value +
                '}';
    }
}
