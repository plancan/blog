package com.domain;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @author 今昔
 * @description 标签类
 * @date 2022/11/11 18:23
 */
@ApiModel(value = "标签类")
public class Label {
    @ApiModelProperty(notes = "标签id")
    private int labelId;
    @ApiModelProperty(notes = "标签")
    private String label;

    public int getLabelId() {
        return labelId;
    }

    public void setLabelId(int labelId) {
        this.labelId = labelId;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    @Override
    public String toString() {
        return "Label{" +
                "id=" + labelId +
                ", label='" + label + '\'' +
                '}';
    }
}
