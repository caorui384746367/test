package python;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.Connection.Method;
import org.jsoup.Connection.Response;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
/**
 * �ҵ���ȷѡ��Valueֵ
 * @author Administrator
 *
 */
public class FindRightAnwer {
	public static String USER_AGENT = "User-Agent";
	public static String USER_AGENT_VALUE = "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:52.0) Gecko/20100101 Firefox/52.0";
	public static void main(String[] args) {
		String username = "17932575";
		String password = "945631";
		String homeworkId ="65";
		String courseId = "7";
		try {
			execute(username, password,courseId,homeworkId);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void execute(String username,String password,String courseId,String homeworkId) throws Exception {
		/** index��ҳչʾ */
		// ������ҳ������doc�ĵ�
		Connection con1 = Jsoup
				.connect("http://auth.xnjd.cn/login?service=http%3A%2F%2Fstudy.xnjd.cn%2FIndex_index.action");
		con1.header(USER_AGENT, USER_AGENT_VALUE);
		Response res = con1.execute();
		Document doc = Jsoup.parse(res.body());
		// ��ȡform��
		Element list = doc.getElementById("fm1");
		//ģ���ύ������
		Map dates = new HashMap<>();
		for (Element e : list.getAllElements()) {
			// �˺�����
			if (e.attr("name").equals("username")) {
				e.val(username);
			}
			// ��������
			if (e.attr("name").equals("password")) {
				e.val(password);
			}
			// ��������
			if (e.attr("name").length() > 0) {
				dates.put(e.attr("name"), e.attr("value"));
			}
		}

		/** ��¼ */
		Connection con2 = Jsoup
				.connect("http://auth.xnjd.cn/login?service=http%3A%2F%2Fstudy.xnjd.cn%2FIndex_index.action");
		con2.header(USER_AGENT, USER_AGENT_VALUE);
		// ����cookie��post�����map����
		Response login = con2.ignoreContentType(true).followRedirects(true).method(Method.POST).data(dates)
				.cookies(res.cookies()).execute();			
		/** Ӣ����ҵҳ�� */
		/*File EnglisFile = new File("C:\\Users\\Administrator\\Desktop\\EnglisFile.html");
		Document doc4 = Jsoup.parse(EnglisFile, "utf-8");*/
		Connection con4 = Jsoup				
	.connect("http://cs.xnjd.cn/course/exercise/Student_doIt.action;jsessionid="+login.cookie("JSESSIONID")+"?courseId="+courseId+"&homeworkId="+homeworkId);
		Response res4 = con4.ignoreContentType(true).followRedirects(true).method(Method.GET).maxBodySize(0)
				.cookies(login.cookies()).execute();
		Document doc4 = Jsoup.parse(res4.body());
		System.out.println("******************************");
		System.out.println(doc4.body());
		
		File file = new File("C:\\Users\\Administrator\\Desktop\\allAnswer.txt");//�𰸵�λ��
		HomeworkPage.lockForAnswerValue(doc4,file);
		
	}

}
