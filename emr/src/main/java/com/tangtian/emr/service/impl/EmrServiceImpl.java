package com.tangtian.emr.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.tangtian.emr.pojo.Emr;
import com.tangtian.emr.repository.EmrRepository;
import com.tangtian.emr.service.EmrService;
import com.tangtian.emr.vo.TreeVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class EmrServiceImpl implements EmrService {
    @Autowired
    private MongoTemplate mongoTemplate;

    @Autowired
    private EmrRepository emrRepository;

    //添加
    public void save() {
        Emr emr = new Emr();
//        user.setAge(20);
        Emr emr1 = emrRepository.save(emr);
    }

    //查询所有
    public void findAll() {
        List<Emr> all = emrRepository.findAll();
        System.out.println(all);
    }

    //id查询
    public void getById() {
        Emr emr = emrRepository.findById("5ffbfe8197f24a07007bd6ce").get();
        System.out.println(emr);
    }

    //条件查询
    public void findUserList() {
        Emr emr = new Emr();
//        user.setName("张三");
//        user.setAge(20);
        Example<Emr> example = Example.of(emr);
        List<Emr> emrList = emrRepository.findAll(example);
        System.out.println(emrList);
    }

    //模糊查询
    public void findUsersLikeName() {
        //创建匹配器，即如何使用查询条件
        ExampleMatcher matcher = ExampleMatcher.matching() //构建对象
                .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING) //改变默认字符串匹配方式：模糊查询
                .withIgnoreCase(true); //改变默认大小写忽略方式：忽略大小写
        Emr emr = new Emr();
//        user.setName("三");
        Example<Emr> example = Example.of(emr, matcher);
        List<Emr> emrList = emrRepository.findAll(example);
        System.out.println(emrList);
    }

    //分页查询
    public void findByPage() {
        Sort sort = Sort.by(Sort.Direction.DESC, "age");
        //0为第一页
        Pageable pageable = PageRequest.of(0, 10, sort);
        //创建匹配器，即如何使用查询条件
        ExampleMatcher matcher = ExampleMatcher.matching() //构建对象
                .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING) //改变默认字符串匹配方式：模糊查询
                .withIgnoreCase(true); //改变默认大小写忽略方式：忽略大小写
        Emr emr = new Emr();
//        user.setName("三");
        //创建实例
        Example<Emr> example = Example.of(emr, matcher);
        Page<Emr> pages = emrRepository.findAll(example, pageable);
        System.out.println(pages);
    }

    //修改
    public void update() {
        Emr emr = emrRepository.findById("5ffbfe8197f24a07007bd6ce").get();
//        user.setName("张三_1");
//        user.setAge(25);
//        user.setEmail("883220990@qq.com");
        Emr save = emrRepository.save(emr);
        System.out.println(save);
    }

    //删除
    public void delete() {
        emrRepository.deleteById("5ffbfe8197f24a07007bd6ce");
    }

    @Override
    public void save(Map<String, Object> params) {
        params.put("createTime",new Date());
        params.put("updateTime",new Date());
        mongoTemplate.save(params, "emr");
    }

    @Override
    public List<TreeVo> findTree() {
        List<JSONObject> all = mongoTemplate.findAll(JSONObject.class, "emr");
        HashMap<String, String> map = new HashMap<>();
        for (JSONObject item:all) {
            map.put(item.getString("name"),"");
        }

        ArrayList<TreeVo> list = new ArrayList<>();
        for (Map.Entry<String, String> entry: map.entrySet()) {
            String key = entry.getKey();
            TreeVo tree = this.getTree(key, all);
            list.add(tree);
        }
        return list;
    }

    @Override
    public JSONObject getById(String id) {
        JSONObject emr = mongoTemplate.findById(id, JSONObject.class, "emr");
        return emr;
    }

    public TreeVo getTree(String key,List<JSONObject> all){
        TreeVo treeVo = new TreeVo();
        treeVo.setId(key);
        treeVo.setLabel(key);
        ArrayList<TreeVo> treeVos = new ArrayList<>();
        for (JSONObject jsonObject : all) {
            if (jsonObject.getString("name").equals(key)) {
                TreeVo child = new TreeVo();
                child.setId(jsonObject.getString("_id"));
                child.setTemplateId(jsonObject.getString("templateId"));
                child.setLabel(jsonObject.getString("medicalRecordName"));
                treeVos.add(child);
            }
        }
        treeVo.setChildren(treeVos);
        return treeVo;
    }

}
