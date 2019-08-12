package FeElectric.FeElectric;

import edu.stanford.nlp.coref.data.CorefChain;
import edu.stanford.nlp.ling.*;
import edu.stanford.nlp.ie.util.*;
import edu.stanford.nlp.pipeline.*;
import edu.stanford.nlp.semgraph.*;
import edu.stanford.nlp.trees.*;
import java.util.*;

public class BasicPipelineExample {

	// 输入
	public static String text = "Joe Smith was born in California. " + "In 2017, he went to Paris, France in the summer. "
			+ "His flight left at 3:00pm on July 10th, 2017. "
			+ "After eating some escargot for the first time, Joe said, \"That was delicious!\" "
			+ "He sent a postcard to his sister Jane Smith. "
			+ "After hearing about Joe's trip, Jane decided she might go to France one day.";

	public static void main(String[] args) {
		// 设置管道属性
		Properties props = new Properties();
		// 设置注解
//		props.setProperty("annotators", "tokenize,ssplit,pos,lemma,ner,parse,depparse,coref,kbp,quote");
//		props.setProperty("annotators", "tokenize");		// tokenize 分词
//		props.setProperty("annotators", "ssplit");			// ssplit 分句
//		props.setProperty("annotators", "tokenize,ssplit,pos,lemma,ner");			// pos,lemma,ner 词性标注
//		props.setProperty("annotators", "tokenize,ssplit,parse,depparse");			// parse, depparse 解析
		props.setProperty("annotators", "tokenize,ssplit,pos,lemma,ner,parse,depparse,coref,kbp");			// parse, depparse 解析
		// set a property for an annotator, in this case the coref annotator is being
		// 设置神经元算法
		props.setProperty("coref.algorithm", "neural");
		// 构建管道
		StanfordCoreNLP pipeline = new StanfordCoreNLP(props);
		// 根据字符得到文档
		CoreDocument document = new CoreDocument(text);
		// 标注文档
		pipeline.annotate(document);
		// examples

//		// 文档中index为10的词
//		CoreLabel token = document.tokens().get(10);
//		System.out.println("Example: token");
//		System.out.println(token);
//		System.out.println();

//		// 第一句的文本
//		String sentenceText = document.sentences().get(0).text();
//		System.out.println("Example: sentence");
//		System.out.println(sentenceText);
//		System.out.println();
//
		// 第二句的文本
		CoreSentence sentence = document.sentences().get(1);

//		// 第二句的词性标记列表
//		List<String> posTags = sentence.posTags();
//		System.out.println("Example: pos tags");
//		System.out.println(posTags);
//		System.out.println();
//
//		// 第二句的ner标记列表
//		List<String> nerTags = sentence.nerTags();
//		System.out.println("Example: ner tags");
//		System.out.println(nerTags);
//		System.out.println();

//		// constituency parse for the second sentence
//		Tree constituencyParse = sentence.constituencyParse();
//		System.out.println("Example: constituency parse");
//		System.out.println(constituencyParse);
//		System.out.println();
//
//		// dependency parse for the second sentence
//		SemanticGraph dependencyParse = sentence.dependencyParse();
//		System.out.println("Example: dependency parse");
//		System.out.println(dependencyParse);
//		System.out.println();

		// 目前会报内存异常错
		// kbp relations found in fifth sentence
		// 在第五句里发现的kbp关系
		List<RelationTriple> relations = document.sentences().get(4).relations();
		System.out.println("Example: relation");
		System.out.println(relations.get(0));
		System.out.println();
//
//		// entity mentions in the second sentence
//		// 在第二句里提到的实体
//		List<CoreEntityMention> entityMentions = sentence.entityMentions();
//		System.out.println("Example: entity mentions");
//		System.out.println(entityMentions);
//		System.out.println();
//
//		// coreference between entity mentions
//		// 提到的实体中的相同实体
//		CoreEntityMention originalEntityMention = document.sentences().get(3).entityMentions().get(1);
//		System.out.println("Example: original entity mention");
//		System.out.println(originalEntityMention);
//		System.out.println("Example: canonical entity mention");
//		System.out.println(originalEntityMention.canonicalEntityMention().get());
//		System.out.println();
//
//		// get document wide coref info
//		Map<Integer, CorefChain> corefChains = document.corefChains();
//		System.out.println("Example: coref chains for document");
//		System.out.println(corefChains);
//		System.out.println();
//
//		// get quotes in document
//		List<CoreQuote> quotes = document.quotes();
//		CoreQuote quote = quotes.get(0);
//		System.out.println("Example: quote");
//		System.out.println(quote);
//		System.out.println();
//
//		// original speaker of quote
//		// note that quote.speaker() returns an Optional
//		System.out.println("Example: original speaker of quote");
//		System.out.println(quote.speaker().get());
//		System.out.println();
//
//		// canonical speaker of quote
//		System.out.println("Example: canonical speaker of quote");
//		System.out.println(quote.canonicalSpeaker().get());
//		System.out.println();

	}

}
