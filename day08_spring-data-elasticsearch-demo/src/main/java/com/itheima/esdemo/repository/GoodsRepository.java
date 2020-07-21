package com.itheima.esdemo.repository;

import com.itheima.esdemo.pojo.Goods;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.List;

/**
 * Goods索引库的CRUD
 * ElasticsearchRepository 提供了基本CRUD,我们直接继承它就可以用了,而且不需要编写实现类,springdata提供了,执行的时候真正工作的是提供好的实现类
 * ElasticsearchRepository<Goods,Long>
 *  Goods 这个位置: 操作 GoodsRepository 接口时对应的索引库在哪个类中
 *  Long 这个位置: goods 实体类中被 @ID 注解标记的属性的类型
 */
public interface GoodsRepository extends ElasticsearchRepository<Goods, Long> {

    /**
     * 查询价格区间
     */
    List<Goods> findByPriceBetweenOrderByPriceDesc(Double min, Double max);
}
