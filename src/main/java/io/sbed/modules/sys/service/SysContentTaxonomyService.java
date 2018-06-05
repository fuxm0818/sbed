package io.sbed.modules.sys.service;

import java.util.List;

/**
 * @author heguoliang
 * @Description: TODO(内容与分类对应关系)
 * @date 2017-08-30 14:29:48
 */
public interface SysContentTaxonomyService {

    void saveOrUpdate(Long contentId, List<Long> taxonomyIdList, Object[] tagNames);

    /**
     * 根据内容ID 类型，获取分类,专题,标签ID列表
     */
    List<Long> queryTaxonomyIdList(Long contentId, Integer[] types);

    /**
     * 根据内容ID 类型，获取分类,专题,标签的name列表
     */
    List<String> queryTaxonomyNameList(Long contentId, Integer[] types);

    void delete(Long contentId);

    void deleteBatch(Long[] contentIds);

}
