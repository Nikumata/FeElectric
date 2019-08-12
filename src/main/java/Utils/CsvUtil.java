package Utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;

import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;

import FeElectric.FeElectric.Article;

public class CsvUtil {

	// 初始化csv文件
	// CSV文件分隔符
	private final static String NEW_LINE_SEPARATOR = "\n";
	// 初始化csvformat
	CSVFormat formator = CSVFormat.DEFAULT.withRecordSeparator(NEW_LINE_SEPARATOR);

//	public static void main(String[] args) throws IOException {
////		String[] headers = new String[] { "name", "age", "birth" };
////		List<String[]> data = new ArrayList<String[]>();
////		data.add(new String[] { "Tom", "15", "2001-1-23" });
////		data.add(new String[] { "Tom", "15", "2001-1-23" });
////		data.add(new String[] { "Tom", "15", "2001-1-23" });
////		String filePath = "D:\\study\\铁电材料文献分析\\NLP\\code\\turples\\a.csv";
////		writeCsv(headers, data, filePath);
//		String filePath = "D:\\study\\铁电材料文献分析\\NLP\\code\\ICDM\\data\\icdm_contest_data.csv";
//		readCsv(filePath);
//
//	}

	/**
	 * 写入csv文件
	 * 
	 * @param headers
	 *            列头
	 * @param data
	 *            数据内容
	 * @param filePath
	 *            创建的csv文件路径
	 * @throws IOException
	 **/
	public static void writeCsv(String[] headers, List<String[]> data, String filePath) throws IOException {

		// 初始化csvformat
		CSVFormat formator = CSVFormat.DEFAULT.withRecordSeparator(NEW_LINE_SEPARATOR);

		// 创建FileWriter对象
		FileWriter fileWriter = new FileWriter(filePath);

		// 创建CSVPrinter对象
		CSVPrinter printer = new CSVPrinter(fileWriter, formator);

		// 写入列头数据
		printer.printRecord(headers);

		if (null != data) {
			// 循环写入数据
			for (String[] lineData : data) {
				printer.printRecord(lineData);

			}
		}

		printer.close();
		System.out.println("CSV文件创建成功,文件路径:" + filePath);

	}

//	public static void readCsv(String filePath) {
//		try {    
//            BufferedReader reader = new BufferedReader(new FileReader(filePath));//换成你的文件名   
//            reader.readLine();//第一行信息，为标题信息，不用,如果需要，注释掉   
//            String line = null;    
//            while((line=reader.readLine())!=null){    
//                String item[] = line.split(","); //CSV格式文件为逗号分隔符文件，这里根据逗号切分   
//                String index = item[0];
//                String content = item[1];
//                String industry = item[2];
////                String last = item[item.length-1];//这就是你要的数据了   
//                //int value = Integer.parseInt(last);//如果是数值，可以转化为数值   
//                System.out.println("index :" + index + " content:"  + content + " industry:" + industry);    
////            System.out.println(line);
//            }    
//        } catch (Exception e) {    
//            e.printStackTrace();    
//        }
//	}

	public static List<Article> readCsv(String filePath) {
		List<Article> articles = new ArrayList<>();
		String charset = "utf-8";
		try (CSVReader csvReader = new CSVReaderBuilder(
				new BufferedReader(new InputStreamReader(new FileInputStream(new File(filePath)), charset))).build()) {
			Iterator<String[]> iterator = csvReader.iterator();
			while (iterator.hasNext()) {
				String[] record = iterator.next();
				String category = record[0] == null ? "FeElectric" : record[0];
				String index = record[1] == null ? "WOS:0" : record[1];
				String monthDay = record[2] == null ? "unknown" : record[2];
				String year = record[3] == null ? "unknown" : record[3];
				String title = record[4] == null ? "unknown" : record[4];
				String summary = record[5] == null ? "unknown" : record[5];					
//					System.out.println("index :" + index + " content:" + content + " industry:" + industry);
				articles.add(new Article(category, index, monthDay, year, title, summary));

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return articles;
	}

}
