package FeElectric.FeElectric;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Properties;

import Utils.CsvUtil;
import Utils.FileUtil;
import edu.stanford.nlp.ie.util.RelationTriple;
import edu.stanford.nlp.ling.CoreAnnotations;
import edu.stanford.nlp.naturalli.NaturalLogicAnnotations;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.simple.Document;
import edu.stanford.nlp.simple.Sentence;
import edu.stanford.nlp.util.CoreMap;

/**
 * A demo illustrating how to call the OpenIE system programmatically.
 */
public class OpenIE {

	private static Properties props;
	private static StanfordCoreNLP pipeline;
	
	public static void main(String[] args) throws Exception {
		
		// Create the Stanford CoreNLP pipeline
		props = new Properties();
		// 设置用到的模块属性
		props.setProperty("annotators", "tokenize,ssplit,pos,lemma,depparse,natlog,openie");
		pipeline = new StanfordCoreNLP(props);
//		
//		String filePath = "D:\\work\\FeElectric\\data\\";
//		String fileName = "test.csv";
//		// 读取原始数据
//		List<Article> articles = CsvUtil.readCsv(filePath + fileName);
		// 设置csv列名
		String[] headers = new String[] { "industry", "index", "s1", "r", "s2" };
		List<String[]> data = new ArrayList<String[]>();
//		for (Article article : articles) {
//			// 对每一篇文章提取三元组
//			Extract(article);
//			// 对三元组进行冗余筛选
//			Process p= new Process();
//			p.init(article.getTriples());
//			article.setTriples(p.getResult());
//		}
//		for (Article article : articles) {
//			for (String[] triples: article.getTriples()) {
//				data.add(new String[] {article.getIndustry(), article.getIndex(), triples[0], triples[1], triples[2]});
//			}
//		}
		List<String[]> triples = Extract(null);
		Process p= new Process();
		p.init(triples, 1);
		// 指定输出路径
		String outPath = "..\\data\\submission.csv";
//		CsvUtil.writeCsv(headers, data, outPath);
		CsvUtil.writeCsv(headers, p.getResult(), outPath);
		
	}

	public static void Test() {
		// 创建文档.
		Document doc = new Document("add your text here! It can contain multiple sentences.");
		// 遍历文档，自动得到句子
		for (Sentence sent : doc.sentences()) {
			System.out.println("The second word of the sentence '" + sent + "' is " + sent.word(1));
			// When we ask for the lemma, it will load and run the part of speech tagger
			System.out.println("The third lemma of the sentence '" + sent + "' is " + sent.lemma(2));
			// 使用的解析规则
			System.out.println("The parse of the sentence '" + sent + "' is " + sent.parse());
		}
	}

	public static void Extract() {
		// Create a CoreNLP document
		Document doc = new Document("BYD debuted its E-SEED GT concept car and Song Pro SUV alongside its all-new e-series models at the Shanghai International Automobile Industry Exhibition. The company also showcased its latest Dynasty series of vehicles, which were recently unveiled at the company’s spring product launch in Beijing. A total of 23 new car models were exhibited at the event, held at Shanghai’s National Convention and Exhibition Center, fully demonstrating the BYD New Architecture (BNA) design, the 3rd generation of Dual Mode technology, plus the e-platform framework. Today, China’s new energy vehicles have entered the ‘fast lane’, ushering in an even larger market outbreak. Presently, we stand at the intersection of old and new kinetic energy conversion for mobility, but also a new starting point for high-quality development. To meet the arrival of complete electrification, BYD has formulated a series of strategies, and is well prepared.");

		// Iterate over the sentences in the document
		for (Sentence sent : doc.sentences()) {
//					System.out.println(sent.text());
			// Iterate over the triples in the sentence
			for (RelationTriple triple : sent.openieTriples()) {
				// Print the triple
				System.out.println(triple.confidence + "\t" + triple.subjectLemmaGloss() + "\t" + triple.relationLemmaGloss() + "\t"
						+ triple.objectLemmaGloss());
			}
		}
	}
	
	public static List<String[]> Extract (Article article) {
		Annotation doc;
		if (article == null) {
			doc = new Annotation("Multiferroic materials refer to a special class of materials which simultaneously possess more than one primary ferroic order\r\n" + 
					"parameters such as ferromagnetic (FM), ferroelectric (FE), and ferroelastic (FA) orders in a single phase.1 In general, magnetism usually originates from the ordered spins of electrons in the partially filled d/f orbitals of transition metals and breaks the\r\n" + 
					"time-reversal symmetry, while ferroelectricity often results from residual polarization due to stable off-centered ion with\r\n" + 
					"empty d/f orbitals, which breaks the space-inversion symmetry. Ferroelasticity comes from the lattice distortions and breaks\r\n" + 
					"neither the time-reversal symmetry nor the space-inversion symmetry. All these ferroic materials with switchable magnetization, electrical polarizations, or strain have been widely exploited for applications in nonvolatile memories.2–4 And the multiferroics with coexistent or even strongly coupled ferroic orders can give rise to new applications including high-density\r\n" + 
					"multistate data storage, energy transformation, signal generation and so on, which therefore have attracted enormous attention\r\n" + 
					"in the past decade. It is known that in commercial random access memories (RAMs), data writing in FM RAMs is energy consuming while reading operation in FE RAMs is destructive, therefore, the magnetoelectric FM–FE multiferroic materials with\r\n" + 
					"a combination of efficient writing and low-cost reading are highly desirable. However, the mutual exclusion between magnetism and electric dipole make it particularly challenging to incorporate them in the same compound and the investigation on\r\n" + 
					"the essence of multiferroics is also of fundamental scientific importance.");
		}else {
			doc = new Annotation(article.getSummary());
		}
		pipeline.annotate(doc);
		
		List<String[]> data = new ArrayList<String[]>();
		long turpleBeginTime = System.currentTimeMillis(); // 三元组提取执行开始时间
		// 遍历文档中的句子
		for (CoreMap sentence : doc.get(CoreAnnotations.SentencesAnnotation.class)) {

			// 提取三元组
			Collection<RelationTriple> triples = sentence.get(NaturalLogicAnnotations.RelationTriplesAnnotation.class);
			// 打印三元组
			for (RelationTriple triple : triples) {
				System.out.println(triple.confidence + "\t" + triple.subjectLemmaGloss() + "\t" + triple.relationLemmaGloss() + "\t"
						+ triple.objectLemmaGloss());
				// 输出置信度为1的
				if (triple.confidence == 1.0) {
					data.add(new String[] { triple.subjectLemmaGloss().toString(), triple.relationLemmaGloss().toString(),
							triple.objectLemmaGloss().toString() });
				}

			}
		}
//		article.setTriples(data);
		return data;
	}

	public static void Extract(String filePath, String fileName) throws IOException {

		long beginTime = System.currentTimeMillis(); // 开始时间
		
		// 准备输入文件
		String dataset = FileUtil.readTxt(filePath + fileName + ".txt");
		// 注解文档
		Annotation doc = new Annotation(dataset);
		pipeline.annotate(doc);

		// 设置csv列名
		String[] headers = new String[] { "subject", "relation", "object" };
		List<String[]> data = new ArrayList<String[]>();
		long turpleBeginTime = System.currentTimeMillis(); // 三元组提取执行开始时间
		// 遍历文档中的句子
		for (CoreMap sentence : doc.get(CoreAnnotations.SentencesAnnotation.class)) {

			// 提取三元组
			Collection<RelationTriple> triples = sentence.get(NaturalLogicAnnotations.RelationTriplesAnnotation.class);
			// 打印三元组
			for (RelationTriple triple : triples) {
//				System.out.println(triple.confidence + "\t" + triple.subjectLemmaGloss() + "\t" + triple.relationLemmaGloss() + "\t"
//						+ triple.objectLemmaGloss());
				// 输出置信度为1的
				if (triple.confidence == 1.0) {
					data.add(new String[] { triple.subjectLemmaGloss().toString(), triple.relationLemmaGloss().toString(),
							triple.objectLemmaGloss().toString() });
				}

			}
		}
		long turpleEndTime = System.currentTimeMillis(); // 三元组提取执行完时间
		System.out.println("当前文件提取三元组消耗时间:" + (turpleEndTime - turpleBeginTime) + "ms");
		// 指定输出路径
		String outPath = filePath + fileName + ".csv";
		CsvUtil.writeCsv(headers, data, outPath);
		long endTime = System.currentTimeMillis(); // 结束时间
		System.out.println("当前文件执行消耗时间:" + (endTime - beginTime) + "ms");
	}
}