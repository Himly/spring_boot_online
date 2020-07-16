package com.itheima.lucenedemo;

import com.itheima.lucenedemo.entity.JobInfo;
import com.itheima.lucenedemo.service.IJobInfoService;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.*;
import org.apache.lucene.index.*;
import org.apache.lucene.search.*;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.wltea.analyzer.lucene.IKAnalyzer;

import java.io.File;
import java.util.List;

@SpringBootTest
class LuceneDemoApplicationTest {

    // 注入 JobInfoService
    @Autowired
    private IJobInfoService jobInfoService;

    /*
        获取工作信息列表的方法
     */
    @Test
    void getJobList() {
        List<JobInfo> list = jobInfoService.list();
        System.out.println("size: " + list.size());
        list.forEach(System.out::println);
    }

    // 创建数据索引
    @Test
    void createIndex() throws Exception {
        // 1.指定索引文件存放路径
        File file = new File("D:/data");
        // 2.使用 Lucene 的 Directory 打开目录(多态)
        Directory directory = FSDirectory.open(file);
        // 3.创建索引对象(多态),顺序:由下往上
        // Analyzer analyzer = new StandardAnalyzer(); // 标准分词器,不能对中文进行合理分析,默认一个汉字一个词,所以不用这个
        Analyzer analyzer = new IKAnalyzer(); // 中文分词器
        IndexWriterConfig config = new IndexWriterConfig(Version.LATEST, analyzer);
        IndexWriter indexWriter = new IndexWriter(directory, config);
        // 删除之间的索引
        indexWriter.deleteAll();
        // 4.获取原始文档
        List<JobInfo> jobInfoList = jobInfoService.list();
        // 5.根据原始文档构建 Lucene 能够识别的 Document
        for (JobInfo jobInfo : jobInfoList) {
            Document document = new Document();
            // 文档 --> 参数1:域名(字段名); 参数2:字段值; 参数3:源数据是否要存储在索引库中
            document.add(new LongField("id", jobInfo.getId(), Field.Store.YES));
            // TextField 表明这个字段会被分词
            document.add(new TextField("companyName", jobInfo.getCompanyName(), Field.Store.YES));
            document.add(new TextField("companyAddr", jobInfo.getCompanyAddr(), Field.Store.YES));
            document.add(new TextField("jobName", jobInfo.getJobName(), Field.Store.YES));
            document.add(new TextField("jobAddr", jobInfo.getJobAddr(), Field.Store.YES));
            document.add(new IntField("salaryMin", jobInfo.getSalaryMin(), Field.Store.YES));
            document.add(new IntField("salaryMax", jobInfo.getSalaryMax(), Field.Store.YES));
            // StringField 不需要分词
            document.add(new StringField("url", jobInfo.getUrl(), Field.Store.YES));
            // 有了文档对象,可以将文档放入到索引库中
            indexWriter.addDocument(document);
        }
        // 关闭索引操作
        indexWriter.close();
    }

    // 查询索引
    @Test
    void searchIndex() throws Exception {
        // 1.指定索引文件存放路径
        File file = new File("D:/data");
        // 2.使用 Lucene 的 Directory 打开目录
        Directory directory = FSDirectory.open(file);
        // 3.构建索引读取对象
        IndexReader indexReader = DirectoryReader.open(directory);
        // 4.构建索引查询对象
        IndexSearcher indexSearcher = new IndexSearcher(indexReader);
        // 5.添加查询条件,根据公司名称来查询
        // Query query = new TermQuery(new Term("companyName", "京"));
        // Query query = new TermQuery(new Term("companyName", "北京"));
        Query query = new TermQuery(new Term("companyName", "慧点"));
        // 6.使用查询对象查询索性,第一个参数为查询条件,第二个参数为返回的数量
        TopDocs topDocs = indexSearcher.search(query, 100); // 显示100条
        // 7.分析结果
        int totalHits = topDocs.totalHits; // 查询命中的文档个数
        System.out.println("符合条件的文档总数: " + totalHits);
        ScoreDoc[] scoreDocs = topDocs.scoreDocs; // 查询结果
        // 遍历结果
        Document document = new Document();
        for (ScoreDoc scoreDoc : scoreDocs) {
            int id = scoreDoc.doc; // 文档id(自动生成的)
            // 根据id进行查询
            document = indexSearcher.doc(id);
            System.out.println("id" + document.get("id"));
            System.out.println("companyName: " + document.get("companyName"));
            System.out.println("companyAddr: " + document.get("companyAddr"));
            System.out.println("salaryMin: " + document.get("salaryMin"));
            System.out.println("salaryMax: " + document.get("salaryMax"));
            System.out.println("===================================================");
        }
    }

}
