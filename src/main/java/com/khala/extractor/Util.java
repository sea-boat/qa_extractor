package com.khala.extractor;

public class Util {

	public static boolean isContainedChinese(String s) {
		s.trim();
		char[] chars = s.toCharArray();

		for (int i = 0; i < chars.length; i++) {
			if (isChinese(chars[i])) {
				return true;
			}
		}
		return false;
	}

	private static boolean isChinese(char ch) {
		// 获取此字符的UniCodeBlock
		Character.UnicodeBlock ub = Character.UnicodeBlock.of(ch);
		/*
		 * CJK_UNIFIED_IDEOGRAPHS // 中日韩统一表意文字 CJK_COMPATIBILITY_IDEOGRAPHS //
		 * 中日韩兼容字符 CJK_UNIFIED_IDEOGRAPHS_EXTENSION_A // 中日韩统一表意文字扩充A
		 * GENERAL_PUNCTUATION // 一般标点符号, 判断中文的“号 CJK_SYMBOLS_AND_PUNCTUATION //
		 * 符号和标点, 判断中文的。号 HALFWIDTH_AND_FULLWIDTH_FORMS // 半角及全角字符, 判断中文的，号
		 */
		if (ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS
				|| ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_A
				|| ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_B) {
			return true;
		}
		return false;
	}

}
