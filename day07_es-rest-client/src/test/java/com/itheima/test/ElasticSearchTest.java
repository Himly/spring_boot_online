package com.itheima.test;

import com.google.gson.Gson;
import com.itheima.pojo.Item;
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
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

// 测试restAPI
@SpringBootTest
public class ElasticSearchTest {

    private RestHighLevelClient restHighLevelClient;

    private Gson gson = new Gson();

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
        新增文档内容
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
        根据唯一标识id进行查询
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
        根据唯一标识id删除
     */
    @Test
    public void testDeleteDocument() throws Exception {
        DeleteRequest deleteRequest = new DeleteRequest("item", "docs", "2");
        DeleteResponse response = restHighLevelClient.delete(deleteRequest, RequestOptions.DEFAULT);
        System.out.println("response: " + response);
    }

    /*
        批量 添加文档
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
        查询全部
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
        match 匹配查詢
     */
    @Test
    public void testMatchQuery() throws Exception {
        // 创建搜索对象
        SearchRequest searchRequest = new SearchRequest();
        // 查询构建工具
        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
        // 添加查询条件,QueryBuilders 里面包含了各种查询方式
        // 参数1:索引库的自定义属性名, 参数2:要搜索的文本内容
        sourceBuilder.query(QueryBuilders.matchQuery("title", "小米手机"));
        // 把查询工具放入请求对象
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

    /*
        range 范围查詢
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
        source 过滤查詢
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
