package com.tangtian.emr.repository;

import com.tangtian.emr.pojo.Template;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TemplateRepository extends MongoRepository<Template, String> {

}

