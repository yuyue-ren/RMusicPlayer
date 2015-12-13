package com.example.utils;

public class MyTimeUtils {

	/** 
     * 格式化时间，将毫秒转换为分:秒格式 
     * @param time 
     * @return 
     */  
	public static String ms2s(long time){
		long stime=time/1000;
		String min=stime/60+"";
		String sec=stime%60+"";
		if(sec.length()==1){
			sec="0"+sec;
		}else if(sec.length()==0){
			sec="00";
		}
		return min+":"+sec;
	}
	
}
