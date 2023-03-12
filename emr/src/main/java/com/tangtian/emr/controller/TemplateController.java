package com.tangtian.emr.controller;

import com.tangtian.emr.pojo.Template;
import com.tangtian.emr.service.TemplateService;
import com.tangtian.emr.utils.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@CrossOrigin
@Api(tags = "电子病历模板API接口")
@RestController
@RequestMapping("/api/template")
public class TemplateController {
    @Autowired
    private TemplateService templateService;

    @ApiOperation("保存")
    @PostMapping("/save")
    public R save(@RequestBody Template template){
        templateService.save(template);
        return R.ok();
    }


    @ApiOperation("删除")
    @PostMapping("/delete")
    public R delete(@RequestBody String[] ids){
        templateService.delete(ids);
        return R.ok();
    }

    @ApiOperation("通过id查询")
    @GetMapping("/getById/{id}")
    public R getById(@PathVariable("id") String id){
        Template template = templateService.getById(id);
        return R.ok().put("data",template);
    }

    @GetMapping("/list")
    @ApiOperation("条件分页查询列表")
    public R list(@RequestParam Map<String, Object> params){
        Page<Template> page = templateService.findByPage(params);

        return R.ok().put("data", page);
    }

    @GetMapping("/findAll")
    @ApiOperation("查询所有")
    public R findAll(){
        List<Template> templateList = templateService.findAll();

        return R.ok().put("data", templateList);
    }

}
