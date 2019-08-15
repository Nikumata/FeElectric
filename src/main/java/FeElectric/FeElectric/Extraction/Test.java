package FeElectric.FeElectric.Extraction;

import java.io.File;
import java.io.IOException;
import java.util.List;

import FeElectric.FeElectric.BuildQuestion;
import Utils.CsvUtil;
import Utils.FileUtil;

public class Test {
	
	public static void main(String[] args) throws IOException {
		
		// 读取文件得到输入
		String path = "D:\\study\\铁电材料文献分析\\人工提取三元组\\20190815实验\\文献输入人工标注部分.txt";
		// 测试
		String text = FileUtil.readTxt(path);
		
		// 设置csv列名
		String[] headers = new String[] { "index", "sentence", "triple", "question" };
		
		long startBq = System.currentTimeMillis();
		// 构建问题
		BuildQuestion bq = new BuildQuestion();
		List<String[]> data = bq.getAllenInput(text);
		long endBq = System.currentTimeMillis();
		System.out.println("构建问题花费时间:" + (endBq - startBq) + "ms");
		
		// 输出到csv
		// 指定输出路径
		String outPath = "D:\\study\\铁电材料文献分析\\人工提取三元组\\20190815实验\\allenInput1227.csv";
		long startOut = System.currentTimeMillis();
		CsvUtil.writeCsv(headers, data, outPath);
		long endOut = System.currentTimeMillis();
		System.out.println("文件输出花费时间:" + (endOut - startOut) + "ms");
	}
	
	
}
