package io.sbed.modules.sys.service.impl;

import io.sbed.modules.sys.dao.SysTaxonomyDao;
import io.sbed.modules.sys.entity.SysTaxonomy;
import io.sbed.modules.sys.redis.SysTaxonomyRedis;
import io.sbed.modules.sys.service.SysTaxonomyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author heguoliang
 * @Description: TODO()
 * @date 2017-8-22 10:25
 */
@Service("sysTaxonomyService")
public class SysTaxonomyServiceImpl implements SysTaxonomyService{

    @Autowired
    private SysTaxonomyDao sysTaxonomyDao;

    @Autowired
    private SysTaxonomyRedis sysTaxonomyRedis;

    @Override
    public List<SysTaxonomy> queryList(Map<String, Object> map) {
        return sysTaxonomyDao.queryList(map);
    }

    @Override
    public int queryTotal(Map<String, Object> map) {
        return sysTaxonomyDao.queryTotal(map);
    }

    @Override
    public List<SysTaxonomy> queryListByType(Integer type) {
        return sysTaxonomyDao.queryListByType(type);
    }

    @Override
    @Transactional
    public void save(SysTaxonomy taxonomy) {
        taxonomy.setCreateTime(new Date());
        sysTaxonomyDao.save(taxonomy);
        sysTaxonomyRedis.saveOrUpdate(taxonomy);
    }

    @Override
    @Transactional
    public void update(SysTaxonomy taxonomy) {
        sysTaxonomyRedis.delete(taxonomy);
        sysTaxonomyDao.update(taxonomy);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        SysTaxonomy taxonomy=queryObject(id);
        sysTaxonomyRedis.delete(taxonomy);

        sysTaxonomyDao.delete(id);
    }

    @Override
    public SysTaxonomy queryObject(Long id) {
        SysTaxonomy taxonomy=sysTaxonomyRedis.get(id);
        if(taxonomy==null){
            taxonomy=sysTaxonomyDao.queryObject(id);
            sysTaxonomyRedis.saveOrUpdate(taxonomy);
        }
        return taxonomy;
    }

    @Override
    public List<SysTaxonomy> queryListByParentId(Long parentId) {
        return sysTaxonomyDao.queryListByParentId(parentId);
    }

    @Override
    public SysTaxonomy queryObjectBySlug(String slug) {
        SysTaxonomy taxonomy=sysTaxonomyRedis.get(slug);
        if(taxonomy==null){
            taxonomy=sysTaxonomyDao.queryObjectBySlug(slug);
            sysTaxonomyRedis.saveOrUpdate(taxonomy);
        }
        return taxonomy;
    }

}
