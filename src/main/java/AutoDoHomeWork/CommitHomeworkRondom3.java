package AutoDoHomeWork;

import java.beans.Encoder;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
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
import org.jsoup.select.Elements;
/**
 * �ύ��ҵ����
 * @author Administrator
 *
 */
public class CommitHomeworkRondom3 {
	public static String USER_AGENT = "User-Agent";
	public static String USER_AGENT_VALUE = "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:52.0) Gecko/20100101 Firefox/52.0";

	public static void main(String[] args) throws Exception {
		String username = "17932575";
		String password = "945631";
		//����ģ���¼����ȡCookie����ص�����
		//��¼��ҳ��ַ
		String path1 = "http://auth.xnjd.cn/login?service=http%3A%2F%2Fstudy.xnjd.cn%2FIndex_index.action";
		Connection con1 = Jsoup
				.connect(path1);
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
		//��¼ 
		//��¼������
		String path2 ="http://auth.xnjd.cn/login?service=http%3A%2F%2Fstudy.xnjd.cn%2FIndex_index.action";
		Connection con2 = Jsoup
				.connect(path2);
		con2.header(USER_AGENT, USER_AGENT_VALUE);
		// ����cookie��post�����map����
		Response login = con2.ignoreContentType(true).followRedirects(true)
				.method(Method.POST).data(dates).cookies(res.cookies())
				.execute();
		
		/**��ҵҳ��*/
		//��ҵ��·��
		String homeWorkPage = "http://cs.xnjd.cn/course/exercise/Student_doIt.action?courseId=566&amp&homeworkId=31723";
		Connection conh = Jsoup
				.connect(homeWorkPage);
		conh.header(USER_AGENT, USER_AGENT_VALUE);
		Response hres = conh.ignoreContentType(true).followRedirects(true)	
		.method(Method.POST).data(dates).cookies(login.cookies())
		.execute();
		System.out.println(hres.body());
		System.out.println("��������֤�룡");
		Scanner scan = new Scanner(System.in);
		String code = scan.nextLine();
		//��ȡ����ҳ����Ҫ�ύ������
		Map<String,String> map = new HashMap();
		Document document = Jsoup.parse(hres.body());
		//��ȡҳ��Ĳ��ֲ���
		String glo_homework_id = document.getElementById("glo_homework_id").val();
		String glo_allExerciseId = document.getElementById("glo_allExerciseId").val();
		String glo_allType = document.getElementById("glo_allType").val();
		String student_id  = document.getElementById("glo_student_id").val();
		String course_code  = document.getElementById("glo_course_code").val();
		String course_url = document.getElementById("course_url").val();
		String repeat_type = document.getElementById("repeat_type").val();
		String homework_type = document.getElementById("homework_type").val();
		map.put("method", "submithomework");
		map.put("all_ex", glo_allExerciseId);
		map.put("course_code", course_code);
		map.put("homework_id", glo_homework_id);
		map.put("student_id", student_id);
		map.put("all_type", glo_allType);
		map.put("center_code", "");
		map.put("class_code", "");
		map.put("course_url", course_url);
		map.put("timestamp", new Date().toString());
		map.put("lefttime", "|0");
		map.put("repeat_type", repeat_type);
		map.put("qulifyCode", code);
		map.put("homework_type",homework_type);
		
		/**�ύ��ҵ*/
		//�����ύ��ҵ��·��
		int i = 0;
		String path = "http://cs.xnjd.cn/course/exercise/Ajax_stusavetmp.action?";
		Set set = map.entrySet();
		Iterator<Entry> iterator = set.iterator();
		//�����ַ������
		while(iterator.hasNext()) {
			Entry en = iterator.next();		
			if(i==0){
				path += en.getKey()+"="+en.getValue();
				i++;
			}else {
			path +="&"+en.getKey()+"="+en.getValue();
			}
		}
		System.out.println("path��"+path);
		
		Connection conx = Jsoup
				.connect(path);
		conx.header(USER_AGENT, USER_AGENT_VALUE);
		Response xres = conx.ignoreContentType(true).followRedirects(true)	
		.method(Method.POST).cookies(login.cookies()).execute();
		System.out.println(xres.body());
		
		//����ҵ�б�ҳ��
		String path3 ="http://study.xnjd.cn/study/Homework_list.action";
		Response res3 = getResponse(path3,dates,login);
		//��ȡ��������Ҫ����ҵ������
		List<String> allUrl =  Util.getAllHomeworkURL(res3);
		//��ȡ��ҵ���𰸵�ҳ��
		//����ĵ�ַ
		/**��ҵ��ַ����ҵ���ɿ��� �쳣*/
		/*for(String url:allUrl) {
			//��ȡ��ҵ��׺���γ�Id����ҵId
			System.out.println(url);
			String suffix = url.split("\\?")[1];
			System.out.println("���ύ��ҵ!");
			Scanner scan = new Scanner(System.in);
			String start = scan.nextLine();
			//����ҳ�����ַ
			String path4 ="http://cs.xnjd.cn/course/exercise/"
					+ "Student_history.action?"+suffix;
			Response res4 = getResponse(path4,dates,login);
			//��ȡABCDѡ���ȷ�Ĵ�AQuest���󣬴���ڼ���
			List <AQuest> allAQuest = Util.getAllAQuestList(res4);
			System.out.println(allAQuest);
			//��ȡ���е���ȷѡ��
			List <Answer> answerList = Util.getAllRightAnswer(allAQuest);
			//��ȡ���е�Value
			String path5 = "http://cs.xnjd.cn/course/exercise"
					+ "/Student_doIt.action?"+suffix;
			Util.getAllValue(res4, answerList);
			
			break;
			
			
			
			
		}*/
		
	}
	/**�������󷵻���Ӧ
	 * @throws IOException */
	public static Response getResponse(String path,Map datas,Response login) throws IOException {
		Connection con = Jsoup
				.connect(path);
		con.header(USER_AGENT, USER_AGENT_VALUE);
		return con.ignoreContentType(true).followRedirects(true)
				.method(Method.POST).data(datas).cookies(login.cookies())
				.execute();
	}



}
