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
 * �ύ��ҵ����
 * @author Administrator
 *
 */
public class CommitHomeworkRondom {
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
		//����ҵ�б�ҳ��
		String path3 ="http://study.xnjd.cn/study/Homework_list.action";
		Response res3 = getResponse(path3,dates,login);
		//��ȡ��������Ҫ����ҵ������
		List<String> allUrl = getAllHomeworkURL(res3);
		//��ȡ��ҵ���𰸵�ҳ��
		//����ĵ�ַ
		/**��ҵ��ַ����ҵ���ɿ��� �쳣*/
		/*for(String url:allUrl) {
			//��ȡ��ҵ��׺���γ�Id����ҵId
			System.out.println(url);
			String suffix = url.split("\\?")[1];
			String path4 ="http://cs.xnjd.cn/course/exercise/Student_history.action?"+suffix;
			Response res4 = getResponse(path4,dates,login);
			//��ȡABCDѡ���ȷ�Ĵ�AQuest���󣬴���ڼ���
			
			List <AQuest> allAQuest = getAllAQuestList(res4);
			System.out.println(allAQuest);
			
		}*/
		//�����ļ���ȡ���е�ABCD
		File answerFile = new File("E:\\eclipse\\eclipse-workspace\\python\\src\\main\\resources\\"+"courseId=6&homeworkId=2724"+".html");
		List <AQuest> allAQuest = getAllAQuestListByFile(answerFile);
		//��ȡ���е���ȷ��
		List <String> rightAnswerlist = getAllRightAnswer(allAQuest);
		//������ҵҳ�棬��ȡ�����е�Value
		//�����·��
		String suffix = answerFile.getName().split("\\.")[0];
		String path4 = "http://cs.xnjd.cn/course/exercise/Student_doIt.action?"+suffix;
		Response res4 = getResponse(path4,dates,login);
		
		//����input��value��key
		Map<String,String> map = getAllValue(res4,rightAnswerlist);
		System.out.println(map);
		//�ļ����map
		File mapFile = new File("E:\\eclipse\\eclipse-workspace\\python\\src\\main\\resources\\answer.txt");
		FileOutputStream fos = new FileOutputStream(mapFile);
		PrintWriter pw = new PrintWriter(fos,true);
		Set set = map.entrySet();
		//����map
		Iterator<Entry> i = set.iterator();
		//�����ַ������
		while(i.hasNext()) {
			Entry en = i.next();				
			pw.println(en.getKey()+":"+en.getValue());
		}
		pw.close();				
		//�ύ��ҵ
		String commitPath = 
				"http://cs.xnjd.cn/course/exercise/Ajax_stusavetmp.action?";
		//�ύ��ҵ���ҳ���
		Response response = commitHomework(commitPath,dates,login,map);
		System.out.println(username+"��"+suffix+"��ҵ�ύ:"+response.body());
		
		System.out.println("�Ƿ������");
		Scanner scan = new Scanner(System.in);
		scan.nextLine();
		
		FileInputStream fis = new FileInputStream(mapFile);
		InputStreamReader isr = new InputStreamReader(fis);
		BufferedReader br = new BufferedReader(isr);
		Map <String,String> map2 = new HashMap<String,String>();
		String str =null;
		while((str=br.readLine())!=null) {
			map2.put(str.split(":")[0], str.split(":")[1]);
		}
		response = commitHomework(commitPath,dates,login, map2);
		System.out.println(username+"��"+suffix+"��ҵ�ύ:"+response.body());
		
	}
	


	/**��ȡ���е�Value��ֵ*/
	private static Map<String,String> getAllValue(Response res4,List<String> allRightList) {
		
		//���ص�Map
		Map<String,String> map = new HashMap();
		String value = "";
		String [] strs = new String[2];
		Document doc = Jsoup.parse(res4.body());
		//��ȡҳ��Ĳ��ֲ���
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
			//ÿһ��class��stu_homework_list���ı�
			String text = e.text();
			if(text.length()>60||text.startsWith("(")) {
				continue;
			}
			text = text.substring(5);
			//�ж���
			if(e.text().contains("˵������")) {
				Elements input = e.getElementsByTag("input");
				if(input.size()>0) {
					String key = input.get(0).attr("name");
					map.put(key,"x");
					System.out.println("entry:"+key+"|"+"x");
				}
			}
			if(e.text().contains("˵����ȷ")) {
				Elements input = e.getElementsByTag("input");
				if(input.size()>0) {
					String key = input.get(0).attr("name");
					map.put(key,"1");
				}
			}
			for(String str:allRightList) {
				if(str.contains(text)||str.endsWith(text)) {
					//���key��value������
					strs = getAllValue(e);
					if(strs.length>1) {
						//�Ķ���⼰���������
						if(map.get(strs[0])!=null) {
							value = map.get(strs[0]) + "|" +strs[1];
							map.put(strs[0],value);
							value = "";
						}else {//��ѡ��
								map.put(strs[0], strs[1]);
						}
						
					}
					
				}
			}
		}
		return map;
		
	}
	
	
	/**��ȡ��ȷ�𰸵�value*/
	private static String[]  getAllValue(Element e) {
		String [] strs = new String[2];
		Elements inputs = e.getElementsByTag("input");
		if(inputs.size()>0) {
			String name = inputs.get(0).attr("name").split("_")[0]+"_"+
					inputs.get(0).attr("name").split("_")[1];
			String value = inputs.get(0).attr("value");
			if(value.split("_").length>=3) {
				strs[1] = value.split("_")[2];
			}else {
				strs[1] = value;

			}
			strs[0] = name;
		}
		return strs;
	}
		
	/**�õ����е���ȷ��*/
	private static List <String> getAllRightAnswer(List<AQuest> allAQuest) {
		List <String> list = new ArrayList<String>();
		for(AQuest a:allAQuest) {
			String answer = a.getAnswer().split("��")[1];
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

	/**�����ļ���ȡ���е�ABCD
	 * @throws IOException */
	private static List<AQuest> getAllAQuestListByFile(File answerFile) throws IOException {
		//���ڷ��صļ��ϣ��������AQuest
				List <AQuest> allAQuest = new ArrayList<AQuest>();
				//��ȡ��Ӧ��body
				Document doc = Jsoup.parse(answerFile,"utf-8");
				Elements es = doc.getElementsByAttributeValue("class","ex_text");
				//�����ʱ���ݵ�����
				List<String> quests = null;
				List<String> answer = null;
				int i = 0;
				for(Element e:es) {
					/*if(i>4) {
						AQuest aq = new AQuest();
						//����ѡ��
						//����ѡ��ļ���
						List <String> allOption = new ArrayList<>();
						for(int j=0;j<4;j++) {
							allOption.add(strs[j]);
						}
						aq.setAllQuest(allOption);
						//���ô�
						aq.setAnswer(strs[4]);
						allAQuest.add(aq);
						i=0;
					}*/
					if(i==0) {
						quests = new ArrayList<String>();
						answer = new ArrayList<String>();
						quests.clear();
						answer.clear();
						
						i++;
					}
					if(e.text().contains("(A)")||e.text().contains("(B)")||e.text().contains("(C)")||e.text().contains("(D)")) {
						quests.add(e.text());
					}
					if(e.text().contains("��ȷ�𰸣�")) {
						
						answer.add(e.text());
						AQuest aq = new AQuest();
						aq.setAllQuest(quests);
						aq.setAnswer(answer.get(0));
						allAQuest.add(aq);
						quests = new ArrayList<String>();
						i = 0;
					}
					
				}
			
				return allAQuest;
		
	}

	/**��ȡ���е���Ŀ�������AQuest�ļ�����*/
	private static List getAllAQuestList(Response res4) {
		//���ڷ��صļ��ϣ��������AQuest
		List <AQuest> allAQuest = new ArrayList<AQuest>();
		//��ȡ��Ӧ��body
		Document doc = Jsoup.parse(res4.body());
		Elements es = doc.getElementsByAttributeValue("class","ex_text");
		//�����ʱ���ݵ�����
		String [] strs = new String[6];
		int i = 0;
		for(Element e:es) {
			if(i>4) {
				AQuest aq = new AQuest();
				//����ѡ��
				//����ѡ��ļ���
				List <String> allOption = new ArrayList<>();
				for(int j=0;j<4;j++) {
					allOption.add(strs[j]);
				}
				aq.setAllQuest(allOption);
				//���ô�
				aq.setAnswer(strs[4]);
				allAQuest.add(aq);
				i=0;
			}
			if(e.text().contains("(A)")||e.text().contains("(B)")||e.text().contains("(C)")||e.text().contains("(D)")) {
				strs[i] = e.text();
				i++;
			}
			if(e.text().contains("��ȷ�𰸣�")) {
				strs[i] = e.text();
				i++;
			}			
		}
		
		return allAQuest;
		
		
	}

	/**��ҵ�б�������Ҫ��ҵ������*/
	public static List<String> getAllHomeworkURL(Response res) {
		//������е�URL�ļ���
		List <String>allUrlList = new ArrayList<String>();
		Document doc = Jsoup.parse(res.body());
		//���е�a��ǩ
		Elements elements = doc.getElementsByTag("a");
		for(Element e:elements) {
			if(e.text().contains("��ʼ����ҵ")) {
				String url = e.attr("href");
				allUrlList.add(url);
			}
		}
		return allUrlList;
		
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
	
	/**��ҵ�ύ
	 * @throws IOException */
	public static Response commitHomework(String commitPath, Map datas, Response login,Map<String,String>dataMap) throws IOException {
		
		Set set = dataMap.entrySet();
		Iterator<Entry> i = set.iterator();
		//�����ַ������
		int idex=0;
		while(i.hasNext()) {
			Entry en = i.next();
			if(en.getValue()!=null) {
				if(idex==0) {
					commitPath += en.getKey()+"=0";
					idex++;
				}
				if(en.getValue().equals("x")) {
					System.out.println("x");
					commitPath +="&"+en.getKey()+"=0";
				}else {
					commitPath +="&"+en.getKey()+"="+en.getValue();
					
				}
			}
		}
		System.out.println("commitPath: "+commitPath);
		Connection con5 = Jsoup.connect(commitPath);
		Response response = con5.ignoreContentType(true).followRedirects(true)
				.method(Method.POST).maxBodySize(0).cookies(login.cookies()).execute();
		return response;
		
	}

}
