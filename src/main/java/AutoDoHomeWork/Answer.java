package AutoDoHomeWork;

import java.util.List;

/**
 * 一个题的答案
 * @author Administrator
 *
 */
public class Answer {
	
	//答案
		public List<String> answer;
		//题序号
		public String head;
		
		
	@Override
	public String toString() {
		return "Answer [answer=" + answer + "]";
	}
	
	
	public String getHead() {
		return head;
	}


	public void setHead(String head) {
		this.head = head;
	}


	public List<String> getAnswer() {
		return answer;
	}

	public void setAnswer(List<String> answer) {
		this.answer = answer;
	}
	
}
