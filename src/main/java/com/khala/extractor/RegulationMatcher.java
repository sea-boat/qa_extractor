package com.khala.extractor;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class RegulationMatcher {

	public static List<String> regulationList = new ArrayList<String>();
	static {
		InputStream input = RegulationMatcher.class.getResourceAsStream("/regulation.txt");
		String str;
		try {
			BufferedReader bf = new BufferedReader(new InputStreamReader(input));
			while ((str = bf.readLine()) != null) {
				regulationList.add(str);
			}
			bf.close();
			input.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static boolean match(String s) {
		for (String rule : regulationList) {
			if (Pattern.matches(rule, s)) {
//				System.out.println(rule);
				return true;
			}
		}
		return false;
	}

	public static void main(String[] args) {
		System.out.println(RegulationMatcher.match("这本是额什么书呢"));
	}

}
