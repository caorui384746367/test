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
 * ��һ��
 * �г����е�ѡ�������
 * @author Administrator
 *
 */
public class ConvertAnswer1 {
	
	public static void main(String[] args) throws IOException {
		//����ҳ�ļ�װ����Doc����
		//�𰸵���ҳ
		File file = new File("C:\\Users\\Administrator\\Desktop\\homeworkPage.html");
		//��ȡ�����ݵ��ı�
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
				System.out.println("ѡ��:"+e.text());
				/*pw.println(e.text());*/
				strs[i] = e.text();
				i++;
			}
			if(e.text().contains("��ȷ�𰸣�")) {
				System.out.println("��ȷ�𰸣�"+e.text());
				/*pw.println(e.text());*/
				strs[i] = e.text();
				i++;
			}			
		}
		pw.close();
		System.out.println("1��ɣ�");
		//��һ������
		
	
	}
}
