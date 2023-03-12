package com.tangtian.emr.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TreeVo {
    private String id;
    private String label;
    private String templateId;
    private List<TreeVo> children;
}
