package com.tangtian.emr.repository;

import com.tangtian.emr.pojo.Emr;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmrRepository extends MongoRepository<Emr, String> {

}

