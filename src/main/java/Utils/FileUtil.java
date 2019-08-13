package Utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

/*
 * 文件工具类
 */

public class FileUtil {

	// 读csv文件
	public static List<String> readCsv(File file) {
		List<String> dataList = new ArrayList<String>();
		BufferedReader br = null;
		try {
			br = new BufferedReader(new FileReader(file));
			String line = "";
			while ((line = br.readLine()) != null) {
				dataList.add(line);
			}
		} catch (Exception e) {
		} finally {
			if (br != null) {
				try {
					br.close();
					br = null;
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return dataList;
	}

	// 读txt文件
	public static String readTxt(String path) {
		String data = "";
		try (FileReader reader = new FileReader(path);
	             BufferedReader br = new BufferedReader(reader) // 建立一个对象，它把文件内容转成计算机能读懂的语言
	        ) {
	            String line;
	            //网友推荐更加简洁的写法
	            while ((line = br.readLine()) != null) {
	                // 一次读入一行数据
	                data += line;
	            }
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
		return data;
	}

	// 输出txt文件
	private static void writeTxt(String outPath) {

		try {
			PrintStream re = new PrintStream(new File(outPath));
			System.setOut(re);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	// 获取目标文件下所有的txt文件
	public static ArrayList<String> readFiles(String path, ArrayList<String> fileNameList) {
		File file = new File(path);
		if (file.isDirectory()) {
			File[] files = file.listFiles();
			for (int i = 0; i < files.length; i++) {
				if (files[i].isDirectory()) {
					readFiles(files[i].getPath(), fileNameList);
				} else {
					String path1 = files[i].getPath();
					String fileName = path1.substring(path1.lastIndexOf("\\") + 1);
					if (fileName.contains(".txt")) {
						fileNameList.add(fileName);
					}
				}
			}
		} else {
			String path1 = file.getPath();
			String fileName = path1.substring(path1.lastIndexOf("\\") + 1);
			if (fileName.contains(".txt")) {
				fileNameList.add(fileName);
			}

		}
		return fileNameList;
	}

	public static void main(String[] args) {
//		String filePath = "D:\\study\\铁电材料文献分析\\ferroelectric\\ferroelectric keyword.txt";
// 测试读文件
//		String data = readTxt(new File(filePath));
//		System.out.print(data);
//		List<String> dataList = readCsv(new File(filePath));
//		for (String data:dataList) {
//			System.out.println(data);
//		}
		// 测试读取文件列表
		String filePath = "D:\\study\\铁电材料文献分析\\NLP\\code\\ICDM\\data\\inputs";
		ArrayList<String> fileNameList = readFiles(filePath, new ArrayList<String>());
		for (String fileName : fileNameList) {
			System.out.println(fileName);
		}

	}

}
