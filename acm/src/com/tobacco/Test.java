package com.tobacco;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Test {
	public static void main(String[] args) {
		/*Date date = new Date();//2013年8月27日 下午03时09分59秒
		Calendar cal=Calendar.getInstance(); 
		cal.setTime(date);
		SimpleDateFormat sdf = null;
		int hours = cal.get(Calendar.HOUR_OF_DAY);
		if(hours > 12) {
			sdf = new SimpleDateFormat("yyyy年MM月dd日 下午hh时mm分ss秒");
		} else {
			sdf = new SimpleDateFormat("yyyy年MM月dd日 上午hh时mm分ss秒");
		}
		System.out.println(sdf.format(new Date()));*/
		
		/*String key = "@acmd@搜的士大夫";
		int index = key.indexOf("@", 1);
		String[] d = key.split("@");
		System.out.println(key.substring(1).split("@")[0]);
		System.out.println(d.length);
		System.out.println(d[0]);
		System.out.println(d[1]);
		System.out.println(d[2]);*/
		//Integer.parseInt("ads");
		
		String regEx="[]+"; //表示一个或多个@  
		Pattern pat=Pattern.compile(regEx);  
		String test = "@safd@21";
		Matcher mat=pat.matcher("@@aa@b cc@@");  
		String s=mat.replaceAll("#");
		
		System.out.println(test.matches(regEx));
	}
}
