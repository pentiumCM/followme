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
		String inputPath = "mp3D:/upload/follow_me/vedio/WeChat_20190603230941.mp4";
		String outPath = "D:/upload/follow_me/gif/4.gif";
		System.out.println(inputPath.indexOf("mp3"));
		System.out.println(outPath.indexOf(".mp4"));
	}
}
