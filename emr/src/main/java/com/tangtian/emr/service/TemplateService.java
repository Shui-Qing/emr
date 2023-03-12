package com.tangtian.emr.service;

import com.tangtian.emr.pojo.Template;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Map;

public interface TemplateService {
    void save(Template template);

    void delete(String[] ids);

    Template getById(String id);

    Page<Template> findByPage(Map<String, Object> params);

    List<Template> findAll();
}
