package python;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.zip.InflaterOutputStream;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class HomeworkPage {

	public static void main(String[] args) {

	}
	public static void lockForAnswerValue(Document doc,File file) throws Exception {
		Elements elements = doc.getElementsByAttributeValue("class","stu_homework_list");
		
		//输入流
		FileInputStream fis = new FileInputStream(file);
		InputStreamReader isr = new InputStreamReader(fis);
		BufferedReader br = new BufferedReader(isr);
		
		//保存answerValue值的文件
		File valueFile = new File("C:\\Users\\Administrator\\Desktop\\answer.txt");
		FileOutputStream fos = new FileOutputStream(valueFile);
		PrintWriter pw = new PrintWriter(fos,true);
		
		//字符串集合
		List <String> list = new ArrayList<String>();
		String answerString = null;
		while((answerString=br.readLine())!=null) {
			list.add(answerString);
		}

		String str = null;
		//遍历出每个元素
		for(Element e:elements) {
			try {
				System.out.println(e.text());
				str = e.text().substring(5).trim();
				for(String s:list) {
					if(beatchString(s,str)&&str!=null&&!"".equals(str)) {//找到答案						
						System.out.println(e.val());
						findInputValue(e,pw);
					}
				}
			}catch(Exception exception) {
				System.out.println("下标越界");
			}
					
		}
		br.close();
		pw.close();
	}
	/**答案对应的input的相关值
	 * @throws Exception */
	public static void findInputValue(Element e,PrintWriter pw) throws Exception {
	
		Elements es =e.getElementsByTag("input");//包围td的标签
		if(es.size()>0) {
			Elements ename = es.get(0).getElementsByAttribute("name");
			if(ename.size()>0) {
				String name = ename.get(0).attr("name");
				String value = ename.get(0).val();
				pw.println(name+":"+value);
			}
		}
		
	}
	/**两字符串的比较*/
	public static boolean beatchString(String s1,String s2) {
		return s1.contains(s2);
	}
}
