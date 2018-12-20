package python;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
/**
 * 第一步
 * 列出所有的选项包括答案
 * @author Administrator
 *
 */
public class ConvertAnswer1 {
	
	public static void main(String[] args) throws IOException {
		//将网页文件装换成Doc对象
		//答案的网页
		File file = new File("C:\\Users\\Administrator\\Desktop\\homeworkPage.html");
		//爬取出数据的文本
		File outFile = new File("C:\\Users\\Administrator\\Desktop\\EnglishAnswer.txt");
		FileOutputStream fos = new FileOutputStream(outFile);
		PrintWriter pw = new PrintWriter(fos,true);
		Document doc = Jsoup.parse(file,"utf-8");
		
		Elements es = doc.getElementsByAttributeValue("class","ex_text");
		String [] strs = new String[6];
		
		int i = 0;
		for(Element e:es) {
			if(i>4) {
				i=0;
			}
			if(e.text().contains("(A)")||e.text().contains("(B)")||e.text().contains("(C)")||e.text().contains("(D)")) {
				System.out.println("选项:"+e.text());
				/*pw.println(e.text());*/
				strs[i] = e.text();
				i++;
			}
			if(e.text().contains("正确答案：")) {
				System.out.println("正确答案："+e.text());
				/*pw.println(e.text());*/
				strs[i] = e.text();
				i++;
			}			
		}
		pw.close();
		System.out.println("1完成！");
		//第一步结束
		
	
	}
}
