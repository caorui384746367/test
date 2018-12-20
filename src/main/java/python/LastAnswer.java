package python;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

public class LastAnswer {
	public static void main(String[] args) throws Exception {
		execute();
	}
	public static void execute() throws Exception {
		//答案的文件
		File file = new File("C:\\Users\\Administrator\\Desktop\\answer.txt");
		//最终答案的文件夹
		File lastFile = new File("C:\\Users\\Administrator\\Desktop\\lastAnswer.txt");
		
		Map <String,String>map = new HashMap();
		
		//创建流
		FileInputStream fis = new FileInputStream(file);
		InputStreamReader isr = new InputStreamReader(fis,"utf-8");
		BufferedReader br = new BufferedReader(isr);
		
		FileOutputStream fos = new FileOutputStream(lastFile);
		PrintWriter pw = new PrintWriter(fos,true);
		
		//将文件中数据放入Map
		String str = null;
		String key= null;
		String value = null;
		while((str=br.readLine())!=null) {
			key = str.split(":")[0].split("_")[0]+"_"+str.split(":")[0].split("_")[1];
			if(map.get(key)!=null) {
				value = map.get(key)+"|"+str.split(":")[1];
				map.put(key, value);
				value = "";
				continue;
			}
			map.put(key, str.split(":")[1]);
		}
		Set<Entry<String, String>> set = map.entrySet();
		for(Entry e:set) {
			value = e.getKey()+":"+e.getValue();
			pw.println(value);
		}
		pw.close();
		br.close();
		System.out.println("成功");
	}

}
