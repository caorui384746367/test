package AutoDoHomeWork;

import java.util.List;
/**
 * һ����Ŀ��ѡ���
 * @author Administrator
 *
 */
public class AQuest {
	//������
	public String head;
	//���е�ѡ��
	public List<String> allQuest;
	//���еĴ�
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
