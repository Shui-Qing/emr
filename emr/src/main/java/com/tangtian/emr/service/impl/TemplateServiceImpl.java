package com.tangtian.emr.service.impl;

import com.tangtian.emr.pojo.Emr;
import com.tangtian.emr.pojo.Template;
import com.tangtian.emr.repository.TemplateRepository;
import com.tangtian.emr.service.TemplateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class TemplateServiceImpl implements TemplateService {

    @Autowired
    private TemplateRepository templateRepository;

    @Override
    public void save(Template template) {
        if (StringUtils.isEmpty(template.getId())){
            template.setCreateTime(new Date());
        }
        template.setUpdateTime(new Date());
        System.out.println(template);
        templateRepository.save(template);
    }

    @Override
    //删除
    public void delete(String[] ids) {
        for (String id:ids) {
            templateRepository.deleteById(id);
        }
    }

    @Override
    public Template getById(String id) {
        Template template = templateRepository.findById(id).get();
        return template;
    }

    //分页查询
    @Override
    public Page<Template> findByPage(Map<String, Object> params) {
        Integer page = Integer.parseInt(params.get("page").toString());
        Integer limit = Integer.parseInt(params.get("limit").toString());
        String key = params.get("key").toString();

        Sort sort = Sort.by(Sort.Direction.DESC, "updateTime");
        //0为第一页
        Pageable pageable = PageRequest.of(page-1, limit, sort);
        //创建匹配器，即如何使用查询条件
        ExampleMatcher matcher = ExampleMatcher.matching() //构建对象
                .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING) //改变默认字符串匹配方式：模糊查询
                .withIgnoreCase(true); //改变默认大小写忽略方式：忽略大小写
        Template template = new Template();
        if (!StringUtils.isEmpty(key)){
            template.setMedicalRecordName(key);
        }
        //创建实例
        Example<Template> example = Example.of(template, matcher);
        Page<Template> pages = templateRepository.findAll(example, pageable);
        System.out.println(pages);
        return pages;
    }

    @Override
    public List<Template> findAll() {
        Template template = new Template();
        template.setStatus(1);
        Example<Template> example = Example.of(template);
        List<Template> all = templateRepository.findAll(example);
        return all;
    }


}
