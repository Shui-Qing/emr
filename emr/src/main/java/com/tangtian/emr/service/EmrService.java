package com.tangtian.emr.service;

import com.alibaba.fastjson.JSONObject;
import com.tangtian.emr.vo.TreeVo;

import java.util.List;
import java.util.Map;

public interface EmrService {

    void save(Map<String, Object> params);

    List<TreeVo> findTree();

    JSONObject getById(String id);
}
