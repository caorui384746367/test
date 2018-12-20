package Entity;

import java.util.ArrayList;
import java.util.List;

public class Quest {
	public String quest;
	public List<String> allABCD = new ArrayList<String>();
	public String answer;
	public List <String>answerText = new ArrayList<String>();//所有的答案选项
	
	@Override
	public String toString() {
		return "Quest [quest=" + quest + ", allABCD=" + allABCD + ", answer=" + answer + ", answerText=" + answerText
				+ "]";
	}

	public String getQuest() {
		return quest;
	}
	
	public List getAllABCD() {
		return allABCD;
	}

	public void setAllABCD(List allABCD) {
		this.allABCD = allABCD;
	}

	public List getAnswerText() {
		return answerText;
	}

	public void setAnswerText(List answerText) {
		this.answerText = answerText;
	}

	public void setQuest(String quest) {
		this.quest = quest;
	}
	
	public String getAnswer() {
		return answer;
	}
	public void setAnswer(String answer) {
		this.answer = answer;
	}
	
}
