package com.cl.test;

import com.cl.util.FFMpegUtil;

public class Test {
	public static void main(String[] args) throws Exception {
		test2();
	}
	
	public static void test1() {
		String string1 = "D:/upload/follow_me/vedio/WeChat_20190603230941.mp4";
		String string2 = "D:/upload/follow_me";
		string1 = string1.replace(string2, "123");
		System.out.println(string1);
	}
	
	public static void test2() throws Exception {
		String inputPath = "D:/upload/follow_me/vedio/WeChat_20190603230941.mp4";
		String outPath = "D:/upload/follow_me/gif/4.gif";
		FFMpegUtil.convetor(5, "00:00:00", inputPath, outPath);
	}
}
