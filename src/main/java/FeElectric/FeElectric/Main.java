package FeElectric.FeElectric;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Properties;

import Utils.CsvUtil;
import edu.stanford.nlp.ie.util.RelationTriple;
import edu.stanford.nlp.ling.CoreAnnotations;
import edu.stanford.nlp.naturalli.NaturalLogicAnnotations;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.util.CoreMap;

/**
 * 
 * @author denggeng
 *         主程序入口
 */

public class Main {

	private static Properties props;
	private static StanfordCoreNLP pipeline;

	public static void main(String[] args) throws Exception {
		props = new Properties();
		// 设置用到的模块属性
		props.setProperty("annotators", "tokenize,ssplit,pos,lemma,depparse,natlog,openie");
//		props.setProperty("ner.fine.regexner.mapping", "C:\\Users\\Jabin\\Desktop\\myrule.rules");
//		props.setProperty("ner.fine.regexner.ignorecase", "true");
//		props.setProperty("openie.max_entailments_per_clause","100");
//		props.setProperty("openie.affinity_probability_cap","0.3");
		props.setProperty("openie.triple.all_nominals","false");
		pipeline = new StanfordCoreNLP(props);

		String filePath = ".\\data\\带时间戳的文献\\";
		String fileName = "new.csv";
		// 读取原始数据
		List<Article> articles = CsvUtil.readCsv(filePath + fileName);
		articles.remove(0); // 移除头部的文章
		// 设置csv列名
		String[] headers = new String[] { "category", "index", "s1", "r", "s2" };
		List<String[]> bData = new ArrayList<String[]>(); // 冗余筛选前三元组
		List<String[]> aData = new ArrayList<String[]>(); // 冗余筛选后三元组
		
		// 提取一篇文章的三元组
		Article article = articles.get(0);
		Extract(article);
		System.out.println("提取三元组个数：" + article.getTriples().size());
		for (String[] triples : article.getTriples()) {
			bData.add(new String[] { article.getCategory(), article.getIndex(), triples[0], triples[1], triples[2] });
		}
//		// 对三元组进行冗余筛选
//		Process p = new Process();
//		p.init(article.getTriples(), 1);
//		article.setTriples(p.getResult());
//		System.out.println("冗余筛选后三元组个数：" + article.getTriples().size());
//		for (String[] triples : article.getTriples()) {
//			aData.add(new String[] { article.getCategory(), article.getIndex(), triples[0], triples[1], triples[2] });
//		}
		
//		for (Article article : articles) {
//			// 对每一篇文章提取三元组
//			Extract(article);
//			System.out.println("提取三元组个数：" + article.getTriples().size());
//			// 对三元组进行冗余筛选
//			Process p = new Process();
//			p.init(article.getTriples());
//			article.setTriples(p.getResult());
//			System.out.println("冗余筛选后三元组个数：" + article.getTriples().size());
//			for (String[] triples : article.getTriples()) {
//				data.add(new String[] { article.getCategory(), article.getIndex(), triples[0], triples[1], triples[2] });
//			}
//		}
		// 指定输出路径
		String out1Path = filePath + "triples.csv";
		CsvUtil.writeCsv(headers, bData, out1Path);
		String out2Path = filePath + "result.csv";
		CsvUtil.writeCsv(headers, aData, out2Path);

	}

	public static void Extract(Article article) {
		Annotation doc = new Annotation(article.getSummary());
		pipeline.annotate(doc);

		List<String[]> data = new ArrayList<String[]>();
		// 遍历文档中的句子
		for (CoreMap sentence : doc.get(CoreAnnotations.SentencesAnnotation.class)) {

			// 3个步骤
			// 1.	调用ClauseSplitter 对sentence进行clause分割,并且clause数目小于最大分割阈
			// 2.	调用FowardEntailer 对已经划分出来的clause集合进行处理，最大限度缩短clause
			// 3.	调用RelationTripleSegmenter 对最大限度缩短的clause 
			//      提取三元组（关系模式也就是在此提取，依照的是正则表达式代表的模式）
			
			// 提取三元组
			Collection<RelationTriple> triples = sentence.get(NaturalLogicAnnotations.RelationTriplesAnnotation.class);
			// 打印三元组
			for (RelationTriple triple : triples) {
				// 输出置信度为1的
				if (triple.confidence == 1.0) {
					data.add(new String[] { triple.subjectLemmaGloss().toString(), triple.relationLemmaGloss().toString(),
							triple.objectLemmaGloss().toString() });
				}

			}
		}
		article.setTriples(data);
	}

}