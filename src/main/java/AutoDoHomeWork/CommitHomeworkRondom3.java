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
 * 提交作业数据
 * @author Administrator
 *
 */
public class CommitHomeworkRondom3 {
	public static String USER_AGENT = "User-Agent";
	public static String USER_AGENT_VALUE = "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:52.0) Gecko/20100101 Firefox/52.0";

	public static void main(String[] args) throws Exception {
		String username = "17932575";
		String password = "945631";
		//首先模拟登录，获取Cookie等相关的数据
		//登录网页地址
		String path1 = "http://auth.xnjd.cn/login?service=http%3A%2F%2Fstudy.xnjd.cn%2FIndex_index.action";
		Connection con1 = Jsoup
				.connect(path1);
		con1.header(USER_AGENT, USER_AGENT_VALUE);
		Response res = con1.execute();
		Document doc = Jsoup.parse(res.body());
		// 获取form表单
		Element list = doc.getElementById("fm1");
		//模拟提交的数据
		Map dates = new HashMap<>();
		for (Element e : list.getAllElements()) {
			// 账号设置
			if (e.attr("name").equals("username")) {
				e.val(username);
			}
			// 密码设置
			if (e.attr("name").equals("password")) {
				e.val(password);
			}
			// 其他参数
			if (e.attr("name").length() > 0) {
				dates.put(e.attr("name"), e.attr("value"));
			}
		}
		//登录 
		//登录的请求
		String path2 ="http://auth.xnjd.cn/login?service=http%3A%2F%2Fstudy.xnjd.cn%2FIndex_index.action";
		Connection con2 = Jsoup
				.connect(path2);
		con2.header(USER_AGENT, USER_AGENT_VALUE);
		// 设置cookie和post上面的map数据
		Response login = con2.ignoreContentType(true).followRedirects(true)
				.method(Method.POST).data(dates).cookies(res.cookies())
				.execute();
		
		/**作业页面*/
		//作业的路径
		String homeWorkPage = "http://cs.xnjd.cn/course/exercise/Student_doIt.action?courseId=566&amp&homeworkId=31723";
		Connection conh = Jsoup
				.connect(homeWorkPage);
		conh.header(USER_AGENT, USER_AGENT_VALUE);
		Response hres = conh.ignoreContentType(true).followRedirects(true)	
		.method(Method.POST).data(dates).cookies(login.cookies())
		.execute();
		System.out.println(hres.body());
		System.out.println("请输入验证码！");
		Scanner scan = new Scanner(System.in);
		String code = scan.nextLine();
		//获取请求页面需要提交的数据
		Map<String,String> map = new HashMap();
		Document document = Jsoup.parse(hres.body());
		//获取页面的部分参数
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
		
		/**提交作业*/
		//用于提交作业的路径
		int i = 0;
		String path = "http://cs.xnjd.cn/course/exercise/Ajax_stusavetmp.action?";
		Set set = map.entrySet();
		Iterator<Entry> iterator = set.iterator();
		//请求地址的设置
		while(iterator.hasNext()) {
			Entry en = iterator.next();		
			if(i==0){
				path += en.getKey()+"="+en.getValue();
				i++;
			}else {
			path +="&"+en.getKey()+"="+en.getValue();
			}
		}
		System.out.println("path："+path);
		
		Connection conx = Jsoup
				.connect(path);
		conx.header(USER_AGENT, USER_AGENT_VALUE);
		Response xres = conx.ignoreContentType(true).followRedirects(true)	
		.method(Method.POST).cookies(login.cookies()).execute();
		System.out.println(xres.body());
		
		//到作业列表页面
		String path3 ="http://study.xnjd.cn/study/Homework_list.action";
		Response res3 = getResponse(path3,dates,login);
		//获取出所有需要做作业的连接
		List<String> allUrl =  Util.getAllHomeworkURL(res3);
		//获取作业及答案的页面
		//请求的地址
		/**作业地址做作业不可看答案 异常*/
		/*for(String url:allUrl) {
			//获取作业后缀，课程Id及作业Id
			System.out.println(url);
			String suffix = url.split("\\?")[1];
			System.out.println("请提交作业!");
			Scanner scan = new Scanner(System.in);
			String start = scan.nextLine();
			//答案网页请求地址
			String path4 ="http://cs.xnjd.cn/course/exercise/"
					+ "Student_history.action?"+suffix;
			Response res4 = getResponse(path4,dates,login);
			//获取ABCD选项及正确的答案AQuest对象，存放在集合
			List <AQuest> allAQuest = Util.getAllAQuestList(res4);
			System.out.println(allAQuest);
			//获取所有的真确选项
			List <Answer> answerList = Util.getAllRightAnswer(allAQuest);
			//获取所有的Value
			String path5 = "http://cs.xnjd.cn/course/exercise"
					+ "/Student_doIt.action?"+suffix;
			Util.getAllValue(res4, answerList);
			
			break;
			
			
			
			
		}*/
		
	}
	/**根据请求返回响应
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
