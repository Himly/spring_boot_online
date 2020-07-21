package com.itheima.esdemo.test;

import com.google.gson.Gson;
import com.itheima.esdemo.pojo.Item;
import org.apache.http.HttpHost;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.text.Text;
import org.elasticsearch.common.unit.Fuzziness;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.Operator;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.Aggregations;
import org.elasticsearch.search.aggregations.bucket.terms.Terms;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightField;
import org.elasticsearch.search.sort.SortOrder;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/*
    spring-boot项目进行单元测试步骤:
        1. pom 文件添加 spring-boot-starter-test 依赖;
        2. 在测试类上打注解: @RunWith 和 @SpringBootTest;
        3. @Autowired 从 spring 的 ioc 容器中注入相关对象,调用指定方法进行测试
    注意:
        这个 @SpringBootTest(classes = ElasticSearchApplication.class) 注解规定:
            如果测试类ElasticSearchTest 所在的包与启动类ElasticSearchApplication 所在的包为同一个;
            或者测试类ElasticSearchTest 所在的包是启动类ElasticSearchApplication 所在的包的子包,
            那么,(classes = ElasticSearchApplication.class) 可以省略不写
 */
@RunWith(SpringRunner.class)
// @SpringBootTest(classes = ElasticSearchApplication.class) // 省略原因见上面
@SpringBootTest
public class ElasticSearchTest {

    @Autowired
    private RestHighLevelClient restHighLevelClient;

    @Autowired
    private Gson gson;

    /*
        连接es服务器
     */
    @Before
    public void init() {
        // 初始化客户端 restHighLevelClient
        restHighLevelClient = new RestHighLevelClient(RestClient.builder(HttpHost.create("http://127.0.0.1:9200")));
    }

    /*
        关闭客户端 restHighLevelClient
     */
    @After
    public void close() throws IOException {
        restHighLevelClient.close();
    }

    /*
        1.1 新增文档内容
     */
    @Test
    public void testAddDocument() throws Exception {
        Item item = new Item(2L, "小米手机9", "手机", "小米", 3599.00, "http://image.leyou.com/1231233.jpg");
        // item 的json 字符串
        String itemJsonStr = gson.toJson(item);
        // 创建新的索引请求
        IndexRequest indexRequest = new IndexRequest("item", "docs", item.getId().toString()).source(itemJsonStr, XContentType.JSON);
        // 发送请求到es服务器
        IndexResponse response = restHighLevelClient.index(indexRequest, RequestOptions.DEFAULT);
        // 打印响应内容
        System.out.println("response: " + response);
    }

    /*
        1.2 根据唯一标识id进行查询
     */
    @Test
    public void testFindDocument() throws Exception {
        // 构造查询请求对象
        GetRequest getRequest = new GetRequest("item", "docs", "1");
        // 发送查询请求
        GetResponse response = restHighLevelClient.get(getRequest, RequestOptions.DEFAULT);
        // 解析获取到的内容
        String itemJsonStr = response.getSourceAsString();
        System.out.println("itemJsonStr: " + itemJsonStr);
        // 把json反序列化为item对象
        Item item = gson.fromJson(itemJsonStr, Item.class);
        System.out.println("item: " + item);
    }

    /*
        1.3 根据唯一标识id删除
     */
    @Test
    public void testDeleteDocument() throws Exception {
        DeleteRequest deleteRequest = new DeleteRequest("item", "docs", "2");
        DeleteResponse response = restHighLevelClient.delete(deleteRequest, RequestOptions.DEFAULT);
        System.out.println("response: " + response);
    }

    /*
        1.4 批量 添加文档
     */
    @Test
    public void testBulkIndex() throws Exception {
        List<Item> list = new ArrayList<>();
        list.add(new Item(1L, "小米手机7", "手机", "小米", 3299.00,
                "http://image.leyou.com/13123.jpg"));
        list.add(new Item(2L, "坚果手机R1", "手机", "锤子", 3699.00,
                "http://image.leyou.com/13123.jpg"));
        list.add(new Item(3L, "华为META10", "手机", "华为", 4499.00,
                "http://image.leyou.com/13123.jpg"));
        list.add(new Item(4L, "小米Mix2S", "手机", "小米", 4299.00,
                "http://image.leyou.com/13123.jpg"));
        list.add(new Item(5L, "荣耀V10", "手机", "华为", 2799.00,
                "http://image.leyou.com/13123.jpg"));
        list.add(new Item(6L, "荣耀V8", "手机", "华为", 1799.00,
                "http://image.leyou.com/13123.jpg"));
        list.add(new Item(7L, "ViVoX20", "手机", "Vivo", 3799.00,
                "http://image.leyou.com/13123.jpg"));
        list.add(new Item(8L, "Oppo F10", "手机", "Oppo", 2399.00,
                "http://image.leyou.com/13123.jpg"));
        list.add(new Item(9L, "苹果 11", "手机", "apple", 4399.00,
                "http://image.leyou.com/13123.jpg"));
        list.add(new Item(10L, "小米 10", "手机", "小米", 3399.00,
                "http://image.leyou.com/13123.jpg"));
        list.add(new Item(11L, "华为P40", "手机", "华为", 4399.00,
                "http://image.leyou.com/13123.jpg"));

        // 创建批量请求对象
        BulkRequest bulkRequest = new BulkRequest();
        for (Item item : list) {
            bulkRequest.add(new IndexRequest("item", "docs", item.getId().toString()).source(gson.toJson(item), XContentType.JSON));
        }

        // 把批量请求发送给服务器
        BulkResponse response = restHighLevelClient.bulk(bulkRequest, RequestOptions.DEFAULT);
        System.out.println("response: " + response);
    }

    /*
        2.1 查询全部
        match_all 默认返回10条数据
     */
    @Test
    public void testMatchAll() throws Exception {
        // 创建搜索对象
        SearchRequest searchRequest = new SearchRequest();
        // 创建查询工具
        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
        // 添加查询条件,QueryBuilders 里面包含了各种查询方式
        sourceBuilder.query(QueryBuilders.matchAllQuery());
        // 把查询工具放入请求对象
        searchRequest.source(sourceBuilder);
        // 把搜索对象发送到es服务器
        SearchResponse response = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);
        // 解析返回内容
        SearchHits searchHits = response.getHits();
        System.out.println("结果总数: " + searchHits.totalHits);
        System.out.println("结果的最高分: " + searchHits.getMaxScore());
        // 获取真正的所有结果内容
        SearchHit[] hits = searchHits.getHits();
        for (SearchHit hit : hits) {
            // 文档的json字符串
            String docJsonStr = hit.getSourceAsString();
            System.out.println("docJsonStr: " + docJsonStr);
            // 反序列化
            Item item = gson.fromJson(docJsonStr, Item.class);
            System.out.println("item对象: " + item);
        }
    }

    /*
        2.2 match 匹配查詢
     */
    @Test
    public void testMatchQuery() throws Exception {
        // 查询构建工具
        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
        // 添加查询条件,QueryBuilders 里面包含了各种查询方式
        // 参数1:索引库的自定义属性名, 参数2:要搜索的文本内容
        // sourceBuilder.query(QueryBuilders.matchQuery("title", "小米手机")); // match 查询默认是用 OR 连接词条
        // match查询: 关键词查询,默认是OR,需要改成AND
        sourceBuilder.query(QueryBuilders.matchQuery("title", "小米手机").operator(Operator.AND));
        basicQuery(sourceBuilder);
    }

    /*
        2.3 range 范围查詢
     */
    @Test
    public void testRangeQuery() throws Exception {
        // 查询构建工具
        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
        // 添加查询条件,通过QueryBuilders获取各种查询
        sourceBuilder.query(QueryBuilders.rangeQuery("price").gt(1000).lt(2000));
        basicQuery(sourceBuilder);
    }

    /*
        2.4 source 过滤查詢
     */
    @Test
    public void testSourceFilter() throws Exception {
        // 查询构建工具
        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
        // 添加查询条件,通过QueryBuilders获取各种查询
        sourceBuilder.query(QueryBuilders.matchAllQuery());
        // 添加source过滤
        sourceBuilder.fetchSource(new String[]{"id", "title", "price"}, null);
        basicQuery(sourceBuilder);
    }

    /*
        2.5 fuzzy 容错查询,指定容错度
     */
    @Test
    public void testFuzzyQuery() throws Exception {
        // 查询构建工具
        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
        // 添加查询条件,通过 QueryBuilders 获取各种查询方式
        sourceBuilder.query(QueryBuilders.fuzzyQuery("title", "华米").fuzziness(Fuzziness.ONE)); // 指定容错度为1

        basicQuery(sourceBuilder);
    }

    /*
        2.6 bool 查询,组合查询: must、must not、should、filter
     */
    @Test
    public void testBoolQuery() throws Exception {
        // 查询构建工具
        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
        // // 添加查询条件,通过 QueryBuilders 获取各种查询方式
        sourceBuilder.query(QueryBuilders.boolQuery()
                .must(QueryBuilders.termQuery("brand", "华为"))
                .filter(QueryBuilders.rangeQuery("price").gt(3000))
        );

        basicQuery(sourceBuilder);
    }


    /*
        3. 排序查询
     */
    @Test
    public void testSortQuery() throws Exception {
        // 查询构建工具
        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
        // 添加查询条件,通过 QueryBuilders 获取各种查询方式
        sourceBuilder.query(QueryBuilders.matchAllQuery());

        // 添加排序
        sourceBuilder.sort("price", SortOrder.DESC);
        basicQuery(sourceBuilder);
    }

    /*
        4. 分页查询
     */
    @Test
    public void testSortAndPageQuery() throws Exception {
        // 查询构建工具
        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
        // 添加查询条件,通过 QueryBuilders 获取各种查询方式
        sourceBuilder.query(QueryBuilders.matchAllQuery());

        // 添加排序
        sourceBuilder.sort("price", SortOrder.ASC);

        // 添加分页
        int page = 1;
        int size = 3;
        int start = (page - 1) * size;
        // 配置分页
        sourceBuilder.from(start);
        sourceBuilder.size(size);

        basicQuery(sourceBuilder);
    }

    /*
        5. 聚合 Aggregation
     */
    @Test
    public void testAggs() throws Exception {
        // 创建搜索对象
        SearchRequest searchRequest = new SearchRequest();
        // 查询构建工具
        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
        // 添加查询条件,通过 QueryBuilders 获取各种查询方式
        sourceBuilder.query(QueryBuilders.matchAllQuery());
        // 添加排序
        sourceBuilder.sort("price", SortOrder.DESC);
        // 配置size为0,因为不需要显示具体数据,只要聚合结果
        sourceBuilder.size(0);
        // 添加聚合
        String aggsName = "brandAggs";
        sourceBuilder.aggregation(AggregationBuilders.terms(aggsName).field("brand"));
        // 查询工具放入搜索对象
        searchRequest.source(sourceBuilder);
        // 把搜索对象发送到es服务端
        SearchResponse response = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);

        // 解析返回的内容,因为上面设置了不显示具体数据,所以这里不需要做了
        /*SearchHits searchHits = response.getHits();
        System.out.println("结果总分: " + searchHits.getTotalHits());
        System.out.println("结果最高分: " + searchHits.getMaxScore());*/
        // 获取聚合内容对象
        Aggregations aggregations = response.getAggregations();
        // 使用聚合名字,从对象中获取结果,不能使用接口,接口方法太少
        // Aggregation 接口的子接口有好多,那么我们这里用哪个呢?
        // 上面用的是terms查询,那么我们这里可以用terms这个子接口
        Terms terms = aggregations.get(aggsName);
        // 获取桶buckets
        List<? extends Terms.Bucket> buckets = terms.getBuckets();
        for (Terms.Bucket bucket : buckets) {
            System.out.println("品牌名称: " + bucket.getKeyAsString());
            System.out.println("品牌对应的商品数量: " + bucket.getDocCount());
        }

    }

    /*
        6. 高亮
     */
    @Test
    public void testHighLight() throws Exception {
        // 创建搜索对象
        SearchRequest searchRequest = new SearchRequest();
        // 查询构建工具
        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
        // 添加查询条件,通过 QueryBuilders 获取各种查询方式
        sourceBuilder.query(QueryBuilders.matchQuery("title", "小米"));
        // 添加高亮
        String highlightField = "title";
        HighlightBuilder highlightBuilder = new HighlightBuilder();
        // 设置指定的前置标签、后置标签、字段fields
        highlightBuilder.preTags("<span style='color:red'>").postTags("</span>").field(highlightField);
        // 把高亮内容放入对象
        sourceBuilder.highlighter(highlightBuilder);
        // 把查询工具放入请求对象
        searchRequest.source(sourceBuilder);
        // 把搜索工具放松到es客户端
        SearchResponse response = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);

        // 解析返回内容
        SearchHits searchHits = response.getHits();
        System.out.println("结果总数=" + searchHits.getTotalHits());
        System.out.println("结果的最高分=" + searchHits.getMaxScore());
        // 获取高亮内容
        for (SearchHit hit : searchHits.getHits()) {
            // 从hit对象中获取高亮结果
            Map<String, HighlightField> highlightFields = hit.getHighlightFields();
            // 获取title高亮
            HighlightField field = highlightFields.get(highlightField);
            // 从高亮字段中获取碎片
            Text[] fragments = field.getFragments();
            System.out.println("fragments=" + fragments);
            String highResult = fragments[0].toString();
            // System.out.println("highResult=" + highResult);

            // 文档的json字符串
            String docJsonStr = hit.getSourceAsString();
            // 反序列化
            Item item = gson.fromJson(docJsonStr, Item.class);
            item.setTitle(highResult);
            System.out.println("item对象=" + item);

        }

    }


    /*
        将基础的查询代码抽取成一个方法

     */
    public void basicQuery(SearchSourceBuilder sourceBuilder) throws IOException {
        // 创建搜索对象
        SearchRequest searchRequest = new SearchRequest();
        searchRequest.source(sourceBuilder);
        // 把搜索对象发送到es服务器
        SearchResponse response = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);
        // 解析返回内容
        SearchHits searchHits = response.getHits();
        // 获取真正的所有结果内容
        SearchHit[] hits = searchHits.getHits();
        for (SearchHit hit : hits) {
            // 文档的json字符串
            String docJsonStr = hit.getSourceAsString();
            // 反序列化
            Item item = gson.fromJson(docJsonStr, Item.class);
            System.out.println("item对象: " + item);
        }
    }
}
