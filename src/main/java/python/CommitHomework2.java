package python;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Scanner;
import java.util.Set;

import org.jsoup.Connection;
import org.jsoup.Connection.Method;
import org.jsoup.Connection.Response;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
/**
 * �ύ��ҵ����
 * @author Administrator
 *
 */
public class CommitHomework2 {
	public static String USER_AGENT = "User-Agent";
	public static String USER_AGENT_VALUE = "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:52.0) Gecko/20100101 Firefox/52.0";

	public static void main(String[] args) throws Exception {
		File userFile = new File("C:\\Users\\Administrator\\Desktop\\userFile.txt");
		try {
			FileInputStream fis = new FileInputStream(userFile);
			BufferedReader br = new BufferedReader(new InputStreamReader(fis,"utf-8"));
			String user = null;
			while((user=br.readLine())!=null) {
				String username = user.split("----")[0];
				String password = user.split("----")[1];
				for(int i=32046;i<32049;i++) {				
					commitHomework(username,password,String.valueOf(i));
					Thread thread = Thread.currentThread();
					thread.sleep(3000);
				}
			}
		
		
		
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		

	}
	public static void commitHomework(String username,String password,String homeworkId) {
		try {
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
			Connection con4 = Jsoup
					.connect("http://cs.xnjd.cn/course/exercise/Student_doIt.action;jsessionid="+login.cookie("JSESSIONID")+"?courseId=356&homeworkId="+homeworkId);
			Response res4 = con4.ignoreContentType(true).followRedirects(true).method(Method.GET).maxBodySize(0)
					.cookies(login.cookies()).execute();
			Document doc4 = Jsoup.parse(res4.body());
			/**��ҵ�ύ*/
			//��ȡ�ύ�Ĳ���
			String glo_homework_id = homeworkId;
			String glo_allExerciseId = doc4.getElementById("glo_allExerciseId").val();
			String glo_allType = doc4.getElementById("glo_allType").val();
			String student_id  = doc4.getElementById("glo_student_id").val();
			String course_code  = doc4.getElementById("glo_course_code").val();
			String center_code  = doc4.getElementById("center_code").val();
			String class_code  = doc4.getElementById("class_code").val();
			String course_url = doc4.getElementById("course_url").val();
			String repeat_type = doc4.getElementById("repeat_type").val();
			String homework_type = doc4.getElementById("homework_type").val();
			//�ύ�Ĳ���
			Map datas = new HashMap<>();			
			//�����������
			datas.put("method", "savetmpontime");
			datas.put("all_ex", glo_allExerciseId);
			datas.put("course_code", course_code);
			datas.put("homework_id", glo_homework_id);
			datas.put("student_id", student_id);
			datas.put("all_type", glo_allType);
			datas.put("center_code", center_code);
			datas.put("class_code", class_code);
			datas.put("course_url", course_url);
			datas.put("timestamp", new Date().toString());
			datas.put("lefttime", "|0");
			datas.put("repeat_type", repeat_type);
			datas.put("method", "savetmpontime");
			//��ȡ�ļ�������answer
			/**��Ҫ�޸�*/
			File englisfile = new File("C:\\Users\\Administrator\\Desktop\\answer2\\answer"+glo_homework_id+".txt");
			FileInputStream fis = new FileInputStream(englisfile);
			BufferedReader br = new BufferedReader(new InputStreamReader(fis, "utf-8"));
			String answer = null;
			while((answer=br.readLine())!=null) {
				String [] as = answer.split(":");
				datas.put(as[0],as[1]);				
			}
			//�ύ��ҵ��ַ
			/**��Ҫ�޸�*/
			String pathUrl = 
			"http://cs.xnjd.cn/course/exercise/Ajax_stusavetmp.action?";
			
			//ͼƬ
			/*System.out.println("��������֤�룡");
			Scanner scan = new Scanner(System.in);
			String str = scan.nextLine();
			datas.put("qulifyCode", str);*/
			//���date��ֵ
			
			Set set = datas.entrySet();
			Iterator<Entry> i = set.iterator();
			//�����ַ������
			while(i.hasNext()) {
				Entry en = i.next();				
				pathUrl +="&"+en.getKey()+"="+en.getValue();
			}
			
			Connection con5 = Jsoup.connect(pathUrl);
			Response res5 = con5.ignoreContentType(true).followRedirects(true).method(Method.POST).maxBodySize(0)
			.cookies(login.cookies()).execute();
			System.out.println(username+"|"+homeworkId+":"+res5.body());
			
			

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
