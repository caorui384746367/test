package python;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Random;

import Entity.Quest;
/**
 * 2
 * �ҳ���ȷ�Ĵ�
 * @author Administrator
 *
 */
public class LockForAnswer2 {
	public static void main(String[] args) throws Exception {
		File file = 
			new File("C:\\Users\\Administrator\\Desktop\\EnglishAnswer.txt");
		
		File outFile = 
			new File("C:\\Users\\Administrator\\Desktop\\allAnswer.txt");
		//�ļ�������
		FileInputStream fis = new FileInputStream(file);
		InputStreamReader isr = new InputStreamReader(fis);
		BufferedReader br = new BufferedReader(isr);
		
		//�ļ������
		FileOutputStream fos = new FileOutputStream(outFile);
		PrintWriter pw = new PrintWriter(fos,true);
		
		
		String str = null;
		//����𰸼���
		String [] strs = new String[5];
		int i=0;
		while((str=br.readLine())!=null) {
			if(i>4) {
				LockFroString(strs,pw);
				i=0;
			}
			strs[i] = str;
			i++;
		}
		pw.close();
		br.close();
		System.out.println("2���!");
		String username = "17932575";
		String password = "945631";
		String homeworkId ="65";
		String courseId = "7";
		FindRightAnwer.execute(username, password, courseId, homeworkId);
		
		
	}
	/**�ҵ����е���ȷѡ��*/
	public static void LockFroString(String [] strs,PrintWriter pw) {
		
		for(String str:strs) {
			System.out.println(str);
		}
		
		//��ȡ�𰸵�ֵ
		String answer = strs[4];
		System.out.println("--------------------------------------------");
		//����ѡ����µļ������ҳ�
		char [] chars = answer.toCharArray();
		//�ҵ���ȷ�����е�ѡ��
		for(int i=0;i<4;i++) {
			for(int j=0;j<chars.length;j++) {
				if(strs[i].contains("("+chars[j]+")")) {
					pw.println(strs[i]);
				}
			}
		}
		
		
	}
}
