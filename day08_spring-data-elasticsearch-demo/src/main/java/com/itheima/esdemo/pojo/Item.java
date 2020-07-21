package com.itheima.esdemo.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/*
    商品数据
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Item {
    /**
     * 商品的主键id,把原始数据放入es中
     * 当前文档的唯一标识Id  _id  的值和这个id内容一样
     */
    private  Long id;
    /**
     * 商品名字
     * 需要做全文检索 ，类型Text  ，选择分词器ik_max_word
     */
    private String title;
    /**
     * 商品分类 ，如 手机、电脑、电视
     * 不需要分词，keyWord
     */
    private String category;
    /**
     * 商品品牌
     * 不需要分析，keyword
     */
    private String brand;
    /**
     * 商品价格
     */
    private Double price;
    /**
     * 商品图片
     * keyword ，不参与搜索，index=false
     */
    private String images;

}
