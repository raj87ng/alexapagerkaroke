package com.alexa.pager.karoke.servlet;

import java.io.File;
import java.io.FileNotFoundException;

import org.springframework.util.ResourceUtils;

public class TestFile {
public static void main(String[] args) {
	try {
		File file = ResourceUtils.getFile("classpath:json/devices.json");
		if(file.exists()) {
			System.out.println(" File exists");
		}else {
			System.out.println(" File not exists");
		}
	} catch (FileNotFoundException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
}
}
