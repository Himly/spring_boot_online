package com.itheima.springbootredis.test;

import org.springframework.context.annotation.ImportSelector;
import org.springframework.core.type.AnnotationMetadata;

// TestA 交给 Spring IOC 容器管理方式三
public class MyImportSelector implements ImportSelector {

    @Override
    public String[] selectImports(AnnotationMetadata importingClassMetadata) {
        // 返回需要加载类的全路径
        return new String[]{"com.itheima.springbootredis.test.TestA"};
    }
}
