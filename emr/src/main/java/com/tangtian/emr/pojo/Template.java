package com.tangtian.emr.pojo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@ApiModel(description = "Template")
@Document("Template")
public class Template extends BaseMongoEntity {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "病历名称")
    @Indexed(unique = true) //唯一索引
    private String medicalRecordName;

    @ApiModelProperty(value = "表单属性")
    private String formProps;

    @ApiModelProperty(value = "表单选项")
    private String formOptions;

    @ApiModelProperty(value = "描述")
    private String description;

    @ApiModelProperty(value = "状态 0：未上线 1：已上线")
    private Integer status;

}