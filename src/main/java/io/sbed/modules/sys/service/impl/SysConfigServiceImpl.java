package io.sbed.modules.sys.service.impl;

import com.google.gson.Gson;
import io.sbed.common.exception.SbedException;
import io.sbed.modules.sys.dao.SysConfigDao;
import io.sbed.modules.sys.entity.SysConfig;
import io.sbed.modules.sys.service.SysConfigService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service("sysConfigService")
public class SysConfigServiceImpl implements SysConfigService {

    @Autowired
    private SysConfigDao sysConfigDao;

    @Override
    @Transactional
    public void save(SysConfig config) {
        sysConfigDao.save(config);
    }

    @Override
    @Transactional
    public void update(SysConfig config) {
        sysConfigDao.update(config);
    }

    @Override
    @Transactional
    public void deleteBatch(Long[] ids) {
        for (Long id : ids) {
            SysConfig config = queryObject(id);
        }

        sysConfigDao.deleteBatch(ids);
    }

    @Override
    public List<SysConfig> queryList(Map<String, Object> map) {
        return sysConfigDao.queryList(map);
    }

    @Override
    public int queryTotal(Map<String, Object> map) {
        return sysConfigDao.queryTotal(map);
    }

    @Override
    public SysConfig queryObject(Long id) {
        SysConfig config = sysConfigDao.queryObject(id);
        return config;
    }

    @Override
    public String getValue(String key) {
        SysConfig config = sysConfigDao.queryObjectByKey(key);
        return config == null ? null : config.getValue();
    }

    @Override
    public <T> T getConfigObject(String key, Class<T> clazz) {
        String value = getValue(key);
        if (StringUtils.isNotBlank(value)) {
            return new Gson().fromJson(value, clazz);
        }

        try {
            return clazz.newInstance();
        } catch (Exception e) {
            throw new SbedException("获取参数失败");
        }
    }

    @Override
    public SysConfig queryObjectByKey(String key) {
        SysConfig config = sysConfigDao.queryObjectByKey(key);
        return config;
    }

}
