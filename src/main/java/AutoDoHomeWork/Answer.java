package AutoDoHomeWork;

import java.util.List;

/**
 * һ����Ĵ�
 * @author Administrator
 *
 */
public class Answer {
	
	//��
		public List<String> answer;
		//�����
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
