package AutoDoHomeWork;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.Connection.Method;
import org.jsoup.Connection.Response;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class Util {


	/**��ȡ���е�Value��ֵ*/
	public static Map<String,String> getAllValue(Response res4,List<Answer> allRightList) {
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
			
			for(Answer answer:allRightList) {
				for(String str:answer.getAnswer()) {
					if(str.contains(text)&&str.endsWith(text)) {
						//���key��value������
						strs  = getAllValue(e);
						if(strs.length>0) {
							//�Ķ���⼰���������
							if(map.get(strs[0])!=null) {
								value = map.get(strs[0]) + "|" +strs[1];
								map.put(strs[0], value);
								value = "";
							}else {//��ѡ��
								map.put(strs[0], strs[1]);
							}
							
						}
						
					}
				}
				
			}
		}
		return map;
		
	}
	
	
	/**��ȡ��ȷ�𰸵�value*/
	public static String[]  getAllValue(Element e) {
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
		
	/**�õ����е���ȷ��*/
	public static List <Answer> getAllRightAnswer(List<AQuest> allAQuest) {
		//��Ŵ𰸶���ļ���
		List <Answer> Answerlist = new ArrayList<Answer>();
		for(AQuest a:allAQuest) {
			//Answer����
			Answer answer = new Answer();
			List<String> list = new ArrayList<String>();
			String answerStr = null;
			try {
				answerStr = a.getAnswer().split("��")[1];
			}catch(Exception e){
				System.out.println(e.getMessage());
			}
			char [] chars = answerStr.toCharArray();
			for(int i=0;i<a.getAllQuest().size();i++) {
				for(char c:chars) {
					if(a.getAllQuest().get(i).contains("("+c+")")) {
						list.add(a.getAllQuest().get(i).substring(6));
					}
				}
				
			}
			answer.setAnswer(list);
			Answerlist.add(answer);
		}
		return Answerlist;
	}

	/**�����ļ���ȡ���е�ABCD
	 * @throws IOException */
	public static List<AQuest> getAllAQuestListByFile(File answerFile) throws IOException {
		//���ڷ��صļ��ϣ��������AQuest
				List <AQuest> allAQuest = new ArrayList<AQuest>();
				//��ȡ��Ӧ��body
				Document doc = Jsoup.parse(answerFile,"utf-8");
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

	/**��ȡ���е���Ŀ�������AQuest�ļ�����*/
	 static List<AQuest> getAllAQuestList(Response response) {
		//���ڷ��صļ��ϣ��������AQuest
		List <AQuest> allAQuest = new ArrayList<AQuest>();
		//��ȡ��Ӧ��body
		Document doc = Jsoup.parse(response.body());
		Elements es = doc.getElementsByAttributeValue("class","ex_text");
		//�����ʱ���ݵ�����
		String answer = "";
		String head = "";
		List<String> optionList = null;
		String [] strs = new String[6];
		int i = 0;
		for(Element e:es) {
			//��ʼ��
			if(i==0) {
				optionList = new ArrayList<String>();
				answer = "";
				head = "";
				i++;
			}
			//Ѱ��������
			if((e.text().startsWith("1")&&e.text().length()<50)||
					(e.text().startsWith("2")&&e.text().length()<50)||
					(e.text().startsWith("3")&&e.text().length()<50)||
					(e.text().startsWith("4")&&e.text().length()<50)||
					(e.text().startsWith("5")&&e.text().length()<50)||
					(e.text().startsWith("6")&&e.text().length()<50)||
					(e.text().startsWith("7")&&e.text().length()<50)||
					(e.text().startsWith("8")&&e.text().length()<50)||
					(e.text().startsWith("9")&&e.text().length()<50)||
					(e.text().startsWith("(")&&e.text().length()<50)) {
				
				System.out.println(e.text());
				if(e.text().length()<60) {
					System.out.println("111");
					head = e.text();
				}
			}
			//ѡ��
			if(e.text().contains("(A)")||e.text().contains("(B)")||e.text().contains("(C)")||e.text().contains("(D)")) {
				optionList.add(e.text());
			}
			//��
			if(e.text().contains("��ȷ�𰸣�")) {
				answer = e.text();
				AQuest aq = new AQuest();
				aq.setAllQuest(optionList);
				aq.setAnswer(answer);
				aq.setHead(head);
				allAQuest.add(aq);
				System.out.println(aq);
				i=0;
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

	
	/**��ҵ�ύ
	 * @throws IOException */
	public static Response commitHomework(String commitPath, Map datas, Response login,Map<String,String>dataMap) throws IOException {
		
		Set set = dataMap.entrySet();
		Iterator<Entry> i = set.iterator();
		//�����ַ������
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
