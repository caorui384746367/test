package AutoDoHomeWork;

import java.util.List;
/**
 * 一个题目的选项及答案
 * @author Administrator
 *
 */
public class AQuest {
	//题的序号
	public String head;
	//所有的选项
	public List<String> allQuest;
	//所有的答案
	public String answer;
		
	
	public String getHead() {
		return head;
	}
	public void setHead(String head) {
		this.head = head;
	}
	public List<String> getAllQuest() {
		return allQuest;
	}
	public void setAllQuest(List<String> allQuest) {
		this.allQuest = allQuest;
	}
	public String getAnswer() {
		return answer;
	}
	public void setAnswer(String answer) {
		this.answer = answer;
	}
	
	@Override
	public String toString() {
		return "AQuest [head=" + head + ", allQuest=" + allQuest + ", answer=" + answer + "]";
	}
	
	
	
	
	
}
