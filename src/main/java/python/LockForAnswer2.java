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
 * 找出正确的答案
 * @author Administrator
 *
 */
public class LockForAnswer2 {
	public static void main(String[] args) throws Exception {
		File file = 
			new File("C:\\Users\\Administrator\\Desktop\\EnglishAnswer.txt");
		
		File outFile = 
			new File("C:\\Users\\Administrator\\Desktop\\allAnswer.txt");
		//文件输入流
		FileInputStream fis = new FileInputStream(file);
		InputStreamReader isr = new InputStreamReader(fis);
		BufferedReader br = new BufferedReader(isr);
		
		//文件输出流
		FileOutputStream fos = new FileOutputStream(outFile);
		PrintWriter pw = new PrintWriter(fos,true);
		
		
		String str = null;
		//问题答案集合
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
		System.out.println("2完成!");
		String username = "17932575";
		String password = "945631";
		String homeworkId ="65";
		String courseId = "7";
		FindRightAnwer.execute(username, password, courseId, homeworkId);
		
		
	}
	/**找到所有的正确选项*/
	public static void LockFroString(String [] strs,PrintWriter pw) {
		
		for(String str:strs) {
			System.out.println(str);
		}
		
		//获取答案的值
		String answer = strs[4];
		System.out.println("--------------------------------------------");
		//将多选情况下的几个答案找出
		char [] chars = answer.toCharArray();
		//找到正确的所有的选项
		for(int i=0;i<4;i++) {
			for(int j=0;j<chars.length;j++) {
				if(strs[i].contains("("+chars[j]+")")) {
					pw.println(strs[i]);
				}
			}
		}
		
		
	}
}
