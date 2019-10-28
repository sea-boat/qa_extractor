package com.khala.extractor;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

/**
 * QA Extractor.
 *
 */
public class QAExtractor {
	public static void main(String[] args) throws IOException {
		QAExtractor extractor = new QAExtractor();
		List<Map<String, String>> list = extractor
				.extract(new File("C:\\Users\\84958\\Desktop\\智能问答文档1012\\article\\0a6a38e1974d.html"));
		for (Map<String, String> map : list) {
			System.out.println("Q：" + map.get("Q"));
			System.out.println("A：" + map.get("A"));
		}
	}

	public List<Map<String, String>> extract(File file) {
		List<Map<String, String>> list = new ArrayList<Map<String, String>>();
		StringBuffer buffer = new StringBuffer();
		BufferedReader bf;
		try {
			bf = new BufferedReader(new FileReader(file));
			String s = null;
			while ((s = bf.readLine()) != null)
				buffer.append(s.trim());
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		String html = buffer.toString();
		Document doc = Jsoup.parse(html);
		BasicParser parser = new BasicParser();
		String title = parser.getTitle(doc);
		String content = parser.getContent(doc);
		String _content = HTMLUtil.getTextFromHtml(content).replaceAll(" ", "");
		//		System.out.println(_content);
		List<String> sentences = ArticleUtil.getSentences(_content);
		for (String s : sentences) {
			if (RegulationMatcher.match(s.trim())) {
				System.out.println(s);
				Map<String, String> map = new HashMap<String, String>();
				map.put("Q", s);
				map.put("A", content);
				list.add(map);
			}
		}
		Map<String, String> map = new HashMap<String, String>();
		if (!Util.isContainedChinese(title) || !Util.isContainedChinese(content)) {
			map.put("Q", "");
			map.put("A", "");
		} else {
			map.put("Q", title);
			map.put("A", content);
		}
		list.add(map);
		return list;
	}
}
