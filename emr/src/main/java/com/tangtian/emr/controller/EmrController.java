package com.tangtian.emr.controller;

import com.alibaba.fastjson.JSONObject;
import com.tangtian.emr.pojo.Template;
import com.tangtian.emr.service.EmrService;
import com.tangtian.emr.service.TemplateService;
import com.tangtian.emr.utils.R;
import com.tangtian.emr.vo.TreeVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@CrossOrigin
@Api(tags = "电子病历文档API接口")
@RestController
@RequestMapping("/api/emr")
public class EmrController {

    @Autowired
    private EmrService emrService;

    @ApiOperation("保存")
    @PostMapping("/save")
    public R save(@RequestBody Map<String, Object> params) {
        emrService.save(params);

        return R.ok();
    }

    @ApiOperation("查询树")
    @GetMapping("/getTree")
    public R getTree() {
        List<TreeVo> list = emrService.findTree();
        return R.ok().put("data",list);
    }

    @ApiOperation("查询单个文档")
    @GetMapping("/getById/{id}")
    public R getById(@PathVariable("id") String id) {
        JSONObject json = emrService.getById(id);
        return R.ok().put("data",json);
    }
}
