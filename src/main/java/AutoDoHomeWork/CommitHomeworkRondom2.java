package AutoDoHomeWork;

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
public class CommitHomeworkRondom2 {
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
		/** 登录 */
		Connection con2 = Jsoup
				.connect("http://auth.xnjd.cn/login?service=http%3A%2F%2Fstudy.xnjd.cn%2FIndex_index.action");
		con2.header(USER_AGENT, USER_AGENT_VALUE);		
		// 设置cookie和post上面的map数据
		Response login = con2.ignoreContentType(true).followRedirects(true).method(Method.POST).data(dates)
				.cookies(res.cookies()).execute();
		
		//文件输出map
		File mapFile = new File("E:\\eclipse\\eclipse-workspace\\python\\src\\main\\resources\\answer.txt");
		String suffix = mapFile.getName().split("\\.")[0];

		FileInputStream fis = new FileInputStream(mapFile);
		InputStreamReader isr = new InputStreamReader(fis);
		BufferedReader br = new BufferedReader(isr);
		Map <String,String> map2 = new HashMap<String,String>();
		String str =null;
		while((str=br.readLine())!=null) {
			map2.put(str.split(":")[0], str.split(":")[1]);
		}
		String commitPath = 
				"http://cs.xnjd.cn/course/exercise/Ajax_stusavetmp.action?";
		Response response = commitHomework(commitPath,dates,login, map2);
		System.out.println(username+"的"+suffix+"作业提交:"+response.body());
		
	}
	


	/**获取所有的Value的值*/
	private static Map<String,String> getAllValue(Response res4,List<String> allRightList) {
		
		//返回的Map
		Map<String,String> map = new HashMap();
		String value = "";
		String [] strs = new String[2];
		Document doc = Jsoup.parse(res4.body());
		//获取页面的部分参数
		String glo_homework_id = doc.getElementById("glo_homework_id").val();
		String glo_allExerciseId = doc.getElementById("glo_allExerciseId").val();
		String glo_allType = doc.getElementById("glo_allType").val();
		String student_id  = doc.getElementById("glo_student_id").val();
		String course_code  = doc.getElementById("glo_course_code").val();
		String center_code  = doc.getElementById("center_code").val();
		String class_code  = doc.getElementById("class_code").val();
		String course_url = doc.getElementById("course_url").val();
		String repeat_type = doc.getElementById("repeat_type").val();
		String homework_type = doc.getElementById("homework_type").val();
		map.put("method", "savetmpontime");
		map.put("all_ex", glo_allExerciseId);
		map.put("course_code", course_code);
		map.put("homework_id", glo_homework_id);
		map.put("student_id", student_id);
		map.put("all_type", glo_allType);
		map.put("center_code", center_code);
		map.put("class_code", class_code);
		map.put("course_url", course_url);
		map.put("timestamp", new Date().toString());
		map.put("lefttime", "|0");
		map.put("repeat_type", repeat_type);
		
		Elements es = doc.getElementsByAttributeValue("class","stu_homework_list");
		for(Element e:es) {
			//每一个class的stu_homework_list的文本
			String text = e.text();
			if(text.length()>60||text.startsWith("(")) {
				continue;
			}
			text = text.substring(5);
			
			for(String str:allRightList) {
				if(str.contains(text)&&str.endsWith(text)) {
					//存放key及value的数组
					strs  = getAllValue(e);
					if(strs.length>0) {
						//阅读理解及多个提问题
						if(map.get(strs[0])!=null) {
							value = map.get(strs[0]) + "|" +strs[1];
							map.put(strs[0], value);
							value = "";
						}else {//单选题
							map.put(strs[0], strs[1]);
						}
						
					}
					
				}
			}
		}
		return map;
		
	}
	
	
	/**获取正确答案的value*/
	private static String[]  getAllValue(Element e) {
		String [] strs = new String[2];
		Elements inputs = e.getElementsByTag("input");
		if(inputs.size()>0) {
			String name = inputs.get(0).attr("name").split("_")[0]+"_"+
					inputs.get(0).attr("name").split("_")[1];
			String value = inputs.get(0).attr("value");
			strs[0] = name;
			strs[1] = value;
		}
		return strs;
	}
		
	/**得到所有的正确答案*/
	private static List <String> getAllRightAnswer(List<AQuest> allAQuest) {
		List <String> list = new ArrayList<String>();
		for(AQuest a:allAQuest) {
			String answer = a.getAnswer().split("：")[1];
			char [] chars = answer.toCharArray();
			for(int i=0;i<a.getAllQuest().size();i++) {
				for(char c:chars) {
					if(a.getAllQuest().get(i).contains("("+c+")")) {
						list.add(a.getAllQuest().get(i));
					}
				}
				
			}
		}
		return list;
	}

	/**根据文件获取所有的ABCD
	 * @throws IOException */
	private static List<AQuest> getAllAQuestListByFile(File answerFile) throws IOException {
		//用于返回的集合，存放所有AQuest
				List <AQuest> allAQuest = new ArrayList<AQuest>();
				//获取响应的body
				Document doc = Jsoup.parse(answerFile,"utf-8");
				Elements es = doc.getElementsByAttributeValue("class","ex_text");
				//存放临时数据的数组
				String [] strs = new String[6];
				int i = 0;
				for(Element e:es) {
					if(i>4) {
						AQuest aq = new AQuest();
						//设置选项
						//所有选项的集合
						List <String> allOption = new ArrayList<>();
						for(int j=0;j<4;j++) {
							allOption.add(strs[j]);
						}
						aq.setAllQuest(allOption);
						//设置答案
						aq.setAnswer(strs[4]);
						allAQuest.add(aq);
						i=0;
					}
					if(e.text().contains("(A)")||e.text().contains("(B)")||e.text().contains("(C)")||e.text().contains("(D)")) {
						strs[i] = e.text();
						i++;
					}
					if(e.text().contains("正确答案：")) {
						strs[i] = e.text();
						i++;
					}			
				}
				
				return allAQuest;
		
	}

	/**获取所有的题目，存放在AQuest的集合中*/
	private static List getAllAQuestList(Response res4) {
		//用于返回的集合，存放所有AQuest
		List <AQuest> allAQuest = new ArrayList<AQuest>();
		//获取响应的body
		Document doc = Jsoup.parse(res4.body());
		Elements es = doc.getElementsByAttributeValue("class","ex_text");
		//存放临时数据的数组
		String [] strs = new String[6];
		int i = 0;
		for(Element e:es) {
			if(i>4) {
				AQuest aq = new AQuest();
				//设置选项
				//所有选项的集合
				List <String> allOption = new ArrayList<>();
				for(int j=0;j<4;j++) {
					System.out.println(strs[j]);
					allOption.add(strs[j]);
				}
				aq.setAllQuest(allOption);
				//设置答案
				aq.setAnswer(strs[4]);
				allAQuest.add(aq);
				i=0;
			}
			if(e.text().contains("(A)")||e.text().contains("(B)")||e.text().contains("(C)")||e.text().contains("(D)")) {
				strs[i] = e.text();
				i++;
			}
			if(e.text().contains("正确答案：")) {
				strs[i] = e.text();
				i++;
			}			
		}
		
		return allAQuest;
		
		
	}

	/**作业列表里面需要作业的连接*/
	public static List<String> getAllHomeworkURL(Response res) {
		//存放所有的URL的集合
		List <String>allUrlList = new ArrayList<String>();
		Document doc = Jsoup.parse(res.body());
		//所有的a标签
		Elements elements = doc.getElementsByTag("a");
		for(Element e:elements) {
			if(e.text().contains("开始做作业")) {
				String url = e.attr("href");
				allUrlList.add(url);
			}
		}
		return allUrlList;
		
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
	
	/**作业提交
	 * @throws IOException */
	public static Response commitHomework(String commitPath, Map datas, Response login,Map<String,String>dataMap) throws IOException {
		
		Set set = dataMap.entrySet();
		Iterator<Entry> i = set.iterator();
		//请求地址的设置
		while(i.hasNext()) {
			Entry en = i.next();				
			commitPath +="&"+en.getKey()+"="+en.getValue();
		}
		System.out.println("commitPath"+commitPath);
		Connection con5 = Jsoup.connect(commitPath);
		Response response = con5.ignoreContentType(true).followRedirects(true)
				.method(Method.POST).maxBodySize(0).cookies(login.cookies()).execute();
		return response;
		
	}

}
