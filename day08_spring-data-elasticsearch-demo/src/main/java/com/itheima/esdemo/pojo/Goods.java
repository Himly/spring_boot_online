package com.itheima.esdemo.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

@Data
@NoArgsConstructor
@AllArgsConstructor
// 声明索引库配置
@Document(indexName = "goods", type = "docs", shards = 3)
// Goods类的每一个对象对应es索引库中的一个document,indexName --> 索引库名称; type --> 类型名称,默认是"docs"; shards --> 分片数量,默认5; replicas -->副本数量,默认1
public class Goods {

    /**
     * 商品的主键id,把原始数据放入es中
     * 当前文档的唯一标识Id, _id 的值和这个id内容一样
     */
    @Id // 标识当前是唯一标识id
    @Field(type = FieldType.Keyword)
    private Long id;
    /**
     * 商品名字
     * 需要做全文检索 ，类型Text  ，选择分词器ik_max_word
     */
    @Field(type = FieldType.Text, analyzer = "ik_max_word")
    private String title;
    /**
     * 商品分类 ，如 手机、电脑、电视
     * 不需要分词，keyWord
     */
    @Field(type = FieldType.Keyword)
    private String category;
    /**
     * 商品品牌
     * 不需要分析，keyword
     */
    @Field(type = FieldType.Keyword)
    private String brand;
    /**
     * 商品价格
     */
    @Field(type = FieldType.Double)
    private Double price;
    /**
     * 商品图片
     * keyword ，不参与搜索，index=false
     */
    @Field(type = FieldType.Keyword, index = false)
    private String images;

}
