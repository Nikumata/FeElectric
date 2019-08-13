package FeElectric.FeElectric;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Properties;
import java.util.Set;

import edu.stanford.nlp.ie.util.RelationTriple;
import edu.stanford.nlp.ling.CoreAnnotations;
import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.naturalli.NaturalLogicAnnotations;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.util.CoreMap;

/**
 * 根据OpenIE提取的三元组构建问题
 * 
 * @author denggeng
 *
 */

public class BuildQuestion {

	private static Properties props;
	private static StanfordCoreNLP pipeline;
	
	public BuildQuestion() {
		props = new Properties();
		// 设置用到的模块属性
		props.setProperty("annotators", "tokenize,ssplit,pos,lemma,depparse,natlog,openie");
		pipeline = new StanfordCoreNLP(props);
	}

	public static void main(String[] args) {
		props = new Properties();
		// 设置用到的模块属性
		props.setProperty("annotators", "tokenize,ssplit,pos,lemma,depparse,natlog,openie");
		pipeline = new StanfordCoreNLP(props);
		String sentence = "Multiferroic materials refer to a special class of materials which simultaneously possess more than one primary ferroic order\r\n"
				+ "parameters such as ferromagnetic (FM), ferroelectric (FE), and ferroelastic (FA) orders in a single phase.";
		Set<String> questions = getQuestion(sentence);
		System.out.println("构建的问题如下:");
		for (String question : questions) {
			System.out.println("question : " + question);
		}

	}

	// 给定一个句子，得到介词部分前
	public static void getPos(String sentence) {
		Annotation doc = new Annotation(sentence);
		pipeline.annotate(doc);
//		pipeline.prettyPrint(doc, System.out);
		StringBuffer sb = new StringBuffer();
		sb.append("what ");
		List<CoreMap> words = doc.get(CoreAnnotations.SentencesAnnotation.class);
		for (CoreMap word_temp : words) {
			for (CoreLabel token : word_temp.get(CoreAnnotations.TokensAnnotation.class)) {
				String word = token.get(CoreAnnotations.TextAnnotation.class); // 获取单词信息
				String pos = token.get(CoreAnnotations.PartOfSpeechAnnotation.class); // 获取词性标注信息
				sb.append(word + " ");
				if (pos.equals("IN")) break;
			}
		}
		sb.append("?");
		System.out.println(sb.toString());
	}

	// 给定文本，返回构建的问题集合
	public static Set<String> getQuestion(String text) {
		Set<String> questions = new HashSet<>();
		Annotation doc = new Annotation(text);
		pipeline.annotate(doc);
		// 遍历文档中的句子
		for (CoreMap sentence : doc.get(CoreAnnotations.SentencesAnnotation.class)) {
			// 提取三元组
			Collection<RelationTriple> triples = sentence.get(NaturalLogicAnnotations.RelationTriplesAnnotation.class);
			// 打印三元组
			for (RelationTriple triple : triples) {

				System.out.println("triple : " + triple.subjectLemmaGloss().toString() + ", " + triple.relationLemmaGloss().toString() + ", " +
						triple.objectLemmaGloss().toString());
				
				boolean inFlag = false; // 介词标识
				StringBuffer question = new StringBuffer("what ");
				question.append(triple.subjectLemmaGloss().toString() + " "); // 添加问题
				// 识别relation中是否有介词
				Annotation relationDoc = new Annotation(triple.relationLemmaGloss().toString());
				pipeline.annotate(relationDoc);
				List<CoreMap> words = relationDoc.get(CoreAnnotations.SentencesAnnotation.class);
				for (CoreMap word_temp : words) {
					for (CoreLabel token : word_temp.get(CoreAnnotations.TokensAnnotation.class)) {
						String word = token.get(CoreAnnotations.TextAnnotation.class); // 获取单词信息
						String pos = token.get(CoreAnnotations.PartOfSpeechAnnotation.class); // 获取词性标注信息
						question.append(word + " ");
						if (pos.equals("IN") || pos.equals("TO")) {
							inFlag = true;
							break;
						}
					}
					if (inFlag) break;
				}
				if (inFlag) {
					question.append("?");
				} else {
					// 识别object中是否有介词
					Annotation objectDoc = new Annotation(triple.objectLemmaGloss().toString());
					pipeline.annotate(objectDoc);
					List<CoreMap> words2 = objectDoc.get(CoreAnnotations.SentencesAnnotation.class);
					for (CoreMap word_temp : words2) {
						for (CoreLabel token : word_temp.get(CoreAnnotations.TokensAnnotation.class)) {
							String word = token.get(CoreAnnotations.TextAnnotation.class); // 获取单词信息
							String pos = token.get(CoreAnnotations.PartOfSpeechAnnotation.class); // 获取词性标注信息
							question.append(word + " ");
							if (pos.equals("IN") || pos.equals("TO")) {
								inFlag = true;
								break;
							}

						}
						if (inFlag) break;
					}
					question.append("?");
				}

				questions.add(question.toString());
				System.out.println("question: " + question.toString());
				System.out.println("***********************************");

			}
		}
		return questions;

	}

	// 给定文本，返回构建的问题集合
	public static List<String[]> getAllenInput(String text) {
		List<String[]> data = new ArrayList<>();
		Set<String> questions = new HashSet<>();
		Annotation doc = new Annotation(text);
		pipeline.annotate(doc);
		int index = 0;
		// 遍历文档中的句子
		for (CoreMap sentence : doc.get(CoreAnnotations.SentencesAnnotation.class)) {
			index++ ;
			// 提取三元组
			Collection<RelationTriple> triples = sentence.get(NaturalLogicAnnotations.RelationTriplesAnnotation.class);
			System.out.println("openIE提取的三元组个数: " + triples.size());
			// 打印三元组
			for (RelationTriple triple : triples) {

				String tripleStr = triple.subjectLemmaGloss().toString() + "|" + triple.relationLemmaGloss().toString() + "|" +
						triple.objectLemmaGloss().toString();
//				System.out.println("triple : " + triple.subjectLemmaGloss().toString() + ", " + triple.relationLemmaGloss().toString() + ", " +
//						triple.objectLemmaGloss().toString());
				
				boolean inFlag = false; // 介词标识
				StringBuffer question = new StringBuffer("what ");
				question.append(triple.subjectLemmaGloss().toString() + " "); // 添加问题
				// 识别relation中是否有介词
				Annotation relationDoc = new Annotation(triple.relationLemmaGloss().toString());
				pipeline.annotate(relationDoc);
				List<CoreMap> words = relationDoc.get(CoreAnnotations.SentencesAnnotation.class);
				for (CoreMap word_temp : words) {
					for (CoreLabel token : word_temp.get(CoreAnnotations.TokensAnnotation.class)) {
						String word = token.get(CoreAnnotations.TextAnnotation.class); // 获取单词信息
						String pos = token.get(CoreAnnotations.PartOfSpeechAnnotation.class); // 获取词性标注信息
						question.append(word + " ");
						if (pos.equals("IN") || pos.equals("TO")) {
							inFlag = true;
							break;
						}
					}
					if (inFlag) break;
				}
				if (inFlag) {
					question.append("?");
				} else {
					// 识别object中是否有介词
					Annotation objectDoc = new Annotation(triple.objectLemmaGloss().toString());
					pipeline.annotate(objectDoc);
					List<CoreMap> words2 = objectDoc.get(CoreAnnotations.SentencesAnnotation.class);
					for (CoreMap word_temp : words2) {
						for (CoreLabel token : word_temp.get(CoreAnnotations.TokensAnnotation.class)) {
							String word = token.get(CoreAnnotations.TextAnnotation.class); // 获取单词信息
							String pos = token.get(CoreAnnotations.PartOfSpeechAnnotation.class); // 获取词性标注信息
							question.append(word + " ");
							if (pos.equals("IN") || pos.equals("TO")) {
								inFlag = true;
								break;
							}

						}
						if (inFlag) break;
					}
					question.append("?");
				}

				questions.add(question.toString());
//				System.out.println("question: " + question.toString());
//				System.out.println("***********************************");
				data.add(new String[] {index + "", sentence.toString(), tripleStr, question.toString()});

			}
		}
		System.out.println("AllenNLPinput个数" + data.size());
		return data;

	}
	
}
