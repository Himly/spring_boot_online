package com.itheima.esdemo.service;

import com.google.gson.Gson;
import org.apache.commons.beanutils.BeanUtils;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.common.text.Text;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.aggregations.Aggregations;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightField;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.SearchResultMapper;
import org.springframework.data.elasticsearch.core.aggregation.AggregatedPage;
import org.springframework.data.elasticsearch.core.aggregation.impl.AggregatedPageImpl;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class SearchResultMapperImpl implements SearchResultMapper {

    @Override
    public <T> AggregatedPage<T> mapResults(SearchResponse response, Class<T> clazz, Pageable pageable) {
        long total = response.getHits().getTotalHits(); //返回时需要的参数
        Aggregations aggregations = response.getAggregations(); //返回时需要的参数
        String scrollId = response.getScrollId(); //返回时需要的参数
        float maxScore = response.getHits().getMaxScore(); //返回时需要的参数

        // 处理我们想要的高亮结果
        SearchHit[] hits = response.getHits().getHits();
        System.out.println("hits=" + hits);
        Gson gson = new Gson();
        List<T> content = new ArrayList<>();
        {
            for (SearchHit hit : hits) {
                String jsonString = hit.getSourceAsString();
                System.out.println("jsonString=" + jsonString);
                T t = gson.fromJson(jsonString, clazz);
                Map<String, HighlightField> highlightFields = hit.getHighlightFields();
                HighlightField highlightField = highlightFields.get("title");
                Text[] fragments = highlightField.getFragments();
                if (fragments != null && fragments.length > 0) {
                    String title = fragments[0].toString();
                    try {
                        // 把T对象中的 “title”属性的值替换成 title
                        BeanUtils.copyProperty(t, "title", title);
                        //t.setTitle(title);
                    } catch (Exception e) {
                        e.printStackTrace();
                        System.out.println("SSSSSSSS");
                    }
                }
                content.add(t);
            }
            return new AggregatedPageImpl<T>(content, pageable, total,
                    aggregations, scrollId, maxScore);
        }
    }
}
