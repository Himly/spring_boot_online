package com.itheima.esdemo.test;

import com.itheima.esdemo.pojo.Goods;
import com.itheima.esdemo.repository.GoodsRepository;
import com.itheima.esdemo.service.SearchResultMapperImpl;
import org.elasticsearch.index.query.Operator;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.aggregations.Aggregation;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.Aggregations;
import org.elasticsearch.search.aggregations.bucket.terms.Terms;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.aggregation.AggregatedPage;
import org.springframework.data.elasticsearch.core.query.FetchSourceFilter;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TestSpringDataEs {
    // 注入spring data提供的ES模板
    @Autowired
    private ElasticsearchTemplate esTemplate;

    // 注入CRUD接口
    @Autowired
    private GoodsRepository goodsRepository;

    // 创建索引库
    @Test
    public void testCreateIndex() throws Exception {
        boolean index = esTemplate.createIndex(Goods.class);
        System.out.println(index);
    }

    // 设置映射
    @Test
    public void testPutMapping() throws Exception {
        boolean b = esTemplate.putMapping(Goods.class);
        System.out.println(b);
    }

    // 添加索引数据(文档)
    @Test
    public void testCreateDoc() throws Exception {
        Goods goods = new Goods(1L, "小米手机9", "手机", "小米", 3599.00, "http://image.leyou.com/1231233.jpg");
        goodsRepository.save(goods);
    }

    // 批量添加索引数据(文档)
    @Test
    public void testBulkAddDocs() throws Exception {
        // 准备文档数据：
        List<Goods> list = new ArrayList<>();
        list.add(new Goods(1L, "小米手机7", "手机", "小米", 3299.00, "/13123.jpg"));
        list.add(new Goods(2L, "坚果手机R1", "手机", "锤子", 3699.00, "/13123.jpg"));
        list.add(new Goods(3L, "华为META10", "手机", "华为", 4499.00, "/13123.jpg"));
        list.add(new Goods(4L, "小米Mix2S", "手机", "小米", 4299.00, "/13123.jpg"));
        list.add(new Goods(5L, "荣耀V10", "手机", "华为", 2799.00, "/13123.jpg"));

        // 添加索引数据
        goodsRepository.saveAll(list);
    }

    // 根据id查询文档
    @Test
    public void testFindDocById() throws Exception {
        Optional<Goods> optional = goodsRepository.findById(1L);
        System.out.println(optional.isPresent());
        System.out.println("goods==" + optional.get());
        System.out.println(optional.orElse(null));

    }

    // 查询所有文档
    @Test
    public void testFindAllDocs() throws Exception {
        Iterable<Goods> goodsList = goodsRepository.findAll();
        for (Goods goods : goodsList) {
            System.out.println("goods: " + goods);
        }
    }

    // 删除文档
    @Test
    public void testDeleteDocById() throws Exception {
        goodsRepository.deleteById(4L);
    }

    // 自定义查询测试
    @Test
    public void testCustomQuery() throws Exception {
        List<Goods> goodsList = goodsRepository.findByPriceBetweenOrderByPriceDesc(3000.0, 5000.0);
        goodsList.forEach(System.out::println);
    }

    // 原生查询
    @Test
    public void testNativeQuery() throws Exception {
        // 创建原生查询构建器
        NativeSearchQueryBuilder queryBuilder = new NativeSearchQueryBuilder();
        // 1.1 设置返回字段(source过滤)
        queryBuilder.withSourceFilter(new FetchSourceFilter(new String[]{"id", "title", "price"}, null));
        // 1.2 设置关键词查询条件
        queryBuilder.withQuery(QueryBuilders.matchQuery("title", "小米").operator(Operator.AND));
        // 1.3 分页及排序
        queryBuilder.withPageable(PageRequest.of(0, 2, Sort.by(Sort.Order.desc("price"))));
        // 1.4 高亮显示
        queryBuilder.withHighlightFields(new HighlightBuilder.Field("title"));
        // 1.5 聚合
        queryBuilder.addAggregation(AggregationBuilders.terms("brandAggs").field("brand"));

        // 把查询的内容发送到es服务器,要查哪个索引,type
        // AggregatedPage<Goods> result = esTemplate.queryForPage(queryBuilder.build(), Goods.class);
        //包含高亮的操作
        SearchResultMapperImpl searchResultMapper = new SearchResultMapperImpl();
        AggregatedPage<Goods> result = esTemplate.queryForPage(queryBuilder.build(), Goods.class, searchResultMapper);

        // 2.解析结果
        // 2.1 分页结果
        long total = result.getTotalElements();
        int totalPages = result.getTotalPages();
        List<Goods> goodsList = result.getContent();
        System.out.println("总条数 = " + total);
        System.out.println("总页数 = " + totalPages);
        System.out.println(goodsList);

        // 2.2 获取聚合对象
        Aggregations aggregations = result.getAggregations();
        // 根据名字获取聚合结果
        Terms terms = aggregations.get("brandAggs");
        // 从terms中获取buckets(桶)
        terms.getBuckets().forEach(b -> {
            System.out.println("品牌 = " + b.getKeyAsString());
            System.out.println("数量 = " + b.getDocCount());
        });

    }


}