package FeElectric.FeElectric;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;

public class Process {

	private List<String[]> source;
	private List<String[]> result;

	public void init(List<String[]> inData, int maxOrmin) throws Exception {
		source = inData;
		result = new ArrayList<String[]>();
		filter(maxOrmin);
	}

	public List<String[]> getResult() {
		return result;
	}

	public void setResult(List<String[]> result) {
		this.result = result;
	}

	public void readCsv(String fileName) throws Exception {
		FileReader file = new FileReader(fileName);
		BufferedReader bf = new BufferedReader(file);
		String str;
		while ((str = bf.readLine()) != null) {
			String[] strList = str.split(",");
			source.add(strList);
		}
		bf.close();
	}

	public static int findLCS(String A, String B) {
		int n = A.length();
		int m = B.length();
		int[][] dp = new int[n + 1][m + 1];
		for (int i = 0; i <= n; i++) {
			for (int j = 0; j <= m; j++) {
				dp[i][j] = 0;
			}
		}
		for (int i = 1; i <= n; i++) {
			for (int j = 1; j <= m; j++) {
				if (A.charAt(i - 1) == B.charAt(j - 1)) {
					dp[i][j] = dp[i - 1][j - 1] + 1;
				} else {
					dp[i][j] = dp[i - 1][j] > dp[i][j - 1] ? dp[i - 1][j] : dp[i][j - 1];
				}
			}
		}
		return dp[n][m];
	}

	public int match(String[] a, String[] b) {
		if (findLCS(a[0], b[0]) == b[0].length() && findLCS(a[1], b[1]) == b[1].length() && findLCS(a[2], b[2]) == b[2].length()) return 1; // b为a的子三元组
		if (findLCS(a[0], b[0]) == a[0].length() && findLCS(a[1], b[1]) == a[1].length() && findLCS(a[2], b[2]) == a[2].length()) return -1; // a为b的子三元组
		return 0; // a与b互不为子三元组
	}

	// 将冗余的三元组过滤掉，只保留最长的三元组
	public void filter(int maxOrmin) throws Exception {
		String[] item = source.get(0);
		result.add(item);
		for (String[] a : source) {
			int flag = 0;
			for (int i = result.size() - 1; i >= 0; i--) {
				int m = match(a, result.get(i));
				if (m == -1*maxOrmin)
					break;
				else if (m == 1*maxOrmin) {
					if (flag == 0) {
						result.remove(i);
						result.add(a);
						flag = 1;
					} else {
						result.remove(i);
					}
				}
				if (i == 0 && flag == 0) result.add(a);
			}
		}

	}

	public void writeToCsv(String fileName) throws Exception {
		File file = new File(fileName);
		FileOutputStream fos = null;
		if (!file.exists()) {
			file.createNewFile();// 如果文件不存在，就创建该文件
			fos = new FileOutputStream(file);// 首次写入获取
		} else {
			// 如果文件已存在，那么就在文件末尾追加写入
			fos = new FileOutputStream(file, false);// 这里构造方法多了一个参数true,表示在文件末尾追加写入
		}
		OutputStreamWriter osw = new OutputStreamWriter(fos, "UTF-8");// 指定以UTF-8格式写入文件
		for (String[] i : result) {
			osw.write(i[0] + "," + i[1] + "," + i[2] + "\n");
		}
		osw.close();
		fos.close();
	}
}
