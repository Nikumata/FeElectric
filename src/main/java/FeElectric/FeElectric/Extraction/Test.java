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
		String path = "D:\\study\\铁电材料文献分析\\人工提取三元组\\test.txt";
		String text = FileUtil.readTxt(path);
		
		// 设置csv列名
		String[] headers = new String[] { "index", "sentence", "triple", "question" };
		
		// 构建问题
		BuildQuestion bq = new BuildQuestion();
		List<String[]> data = bq.getAllenInput(text);
		
		// 输出到csv
		// 指定输出路径
		String outPath = "D:\\study\\铁电材料文献分析\\人工提取三元组\\allenInput.csv";
		CsvUtil.writeCsv(headers, data, outPath);
	}
	
	
}
