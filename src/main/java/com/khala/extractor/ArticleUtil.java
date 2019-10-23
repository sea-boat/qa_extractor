package com.khala.extractor;

import com.aliasi.sentences.IndoEuropeanSentenceModel;
import com.aliasi.sentences.SentenceModel;
import com.aliasi.tokenizer.IndoEuropeanTokenizerFactory;
import com.aliasi.tokenizer.Tokenizer;
import com.aliasi.tokenizer.TokenizerFactory;

import java.util.ArrayList;
import java.util.List;

public class ArticleUtil {

	static final TokenizerFactory TOKENIZER_FACTORY = IndoEuropeanTokenizerFactory.INSTANCE;
	static final SentenceModel SENTENCE_MODEL = new IndoEuropeanSentenceModel();
	static String[] ChineseInterpunction = { "。", "，", "；", "：", "？", "！", "……", "—", "～", "（", "）", "《", "》" };
	static String[] EnglishInterpunction = { ".", ",", ";", ":", "?", "!", "…", "-", "~", "(", ")", "<", ">" };

	public static void main(String[] args) {
		String str = "泼水节是世界上最重要节日之一，深受中国傣族和东南亚人民的喜爱。七百多年来，人们一直在庆祝这个节日，现在这个节日是促进国家间合作和交流的必要方式。";
		List<String> sl = getSentences(str);
		if (sl.isEmpty()) {
			System.out.println("没有识别到句子");
		}
		for (String row : sl) {
			System.out.println(row);
		}
	}

	public static List<String> getSentences(String text) {
		for (int j = 0; j < ChineseInterpunction.length; j++) {
			text = text.replace(ChineseInterpunction[j], EnglishInterpunction[j] + " ");
		}
		List<String> result = new ArrayList<String>();
		List<String> tokenList = new ArrayList<String>();
		List<String> whiteList = new ArrayList<String>();
		Tokenizer tokenizer = TOKENIZER_FACTORY.tokenizer(text.toCharArray(), 0, text.length());
		tokenizer.tokenize(tokenList, whiteList);
		String[] tokens = new String[tokenList.size()];
		String[] whites = new String[whiteList.size()];
		tokenList.toArray(tokens);
		whiteList.toArray(whites);
		int[] sentenceBoundaries = SENTENCE_MODEL.boundaryIndices(tokens, whites);
		int sentStartTok = 0;
		int sentEndTok = 0;

		for (int i = 0; i < sentenceBoundaries.length; ++i) {
			StringBuilder sb = new StringBuilder();
			sentEndTok = sentenceBoundaries[i];
			for (int j = sentStartTok; j <= sentEndTok; j++) {
				sb.append(tokens[j]).append(whites[j + 1]);
			}
			sentStartTok = sentEndTok + 1;
			String temp = sb.toString();
			for (int j = 0; j < ChineseInterpunction.length; j++) {
				temp = temp.replace(EnglishInterpunction[j], ChineseInterpunction[j]);
			}
			result.add(temp);
		}
		return result;
	}

}
