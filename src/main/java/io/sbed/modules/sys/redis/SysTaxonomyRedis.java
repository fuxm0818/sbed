package io.sbed.modules.sys.redis;

import io.sbed.common.utils.RedisUtils;
import io.sbed.modules.sys.entity.SysTaxonomy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author heguoliang
 * @Description: TODO()
 * @date 2017-8-24 10:28
 */
@Component
public class SysTaxonomyRedis {

    private static final String NAME="SysTaxonomy:";

    @Autowired
    private RedisUtils redisUtils;

    public void saveOrUpdate(SysTaxonomy taxonomy) {
        if(taxonomy==null){
            return ;
        }

        String id=NAME+taxonomy.getId();
        redisUtils.set(id, taxonomy);

        String slug=NAME+taxonomy.getSlug();
        redisUtils.set(slug, taxonomy);
    }

    public void delete(SysTaxonomy taxonomy) {
        if(taxonomy==null){
            return ;
        }

        redisUtils.delete(NAME+taxonomy.getId());
        redisUtils.delete(NAME+taxonomy.getSlug());
    }

    public SysTaxonomy get(Object key){
        return redisUtils.get(NAME+key, SysTaxonomy.class);
    }

}
