package com.fewave.util.common;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * ×Ö·û´®¹¤¾ßÀà
 * @author fewave
 *
 */
public class StringUtil {


	public static String[] split(String value, String splitChar) {
		if (isEmpty(value)) {
			return new String[0];
		}
		
		Pattern pattern = Pattern.compile("([^" + splitChar + "]*)(" + splitChar + ")");
		Matcher t = pattern.matcher(value);

		List<String> values = new ArrayList<String>();
		while (t.find()) {
			values.add(t.group(1));
		}
		return (String[]) values.toArray(new String[values.size()]);
	}

	/**
	 * @param value
	 * @param length
	 * @param fillcode
	 * @return
	 */
	public static String format(String value, int length, char fillcode) {
		if (value == null) {
			value = "";
		}
		int len = value.length();
		if (len > length) {
			return value.substring(0, length);
		} else if (len < length) {
			StringBuffer buffer = new StringBuffer(value);
			int sublen = length - len;
			for (int i = 0; i < sublen; i++) {
				buffer.append(fillcode);
			}
			return buffer.toString();
		}
		return value;
	}

	public static byte[] take(byte[] body, int length) {
		try {
			byte[] temp = new byte[length];
			System.arraycopy(body, 0, temp, 0, length);
			return temp;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public static byte[] remove(byte[] body, int headerLength) {
		byte[] temp = new byte[body.length - headerLength];
		System.arraycopy(body, headerLength, temp, 0, temp.length);
		return temp;
	}

	public static byte[] remove(byte[] body, byte[] removed) {
		byte[] temp = new byte[body.length - removed.length];
		System.arraycopy(body, removed.length, temp, 0, temp.length);
		return temp;
	}

	public static byte[] join(byte[] left, byte[] right) {
		byte[] temp = new byte[left.length + right.length];
		System.arraycopy(left, 0, temp, 0, left.length);
		System.arraycopy(right, 0, temp, left.length, right.length);
		return temp;
	}

	public static String format(String[] array) {
		StringBuilder builder = new StringBuilder();
		for (int i = 0; i < array.length; i++) {
			builder.append("field[" + i + "]:" + array[i] + "\n");
		}
		return builder.toString();
	}

	public static String format(String str, int maxLength) {
		if (str == null) {
			return "";
		}
		int length = str.length();
		if (length <= maxLength) {
			return str;
		} else {
			return str.substring(length - maxLength);
		}
	}

	public static String format(String str, int maxLength, int minLength, char fill) {
		if (str == null) {
			str = "";
		}

		int length = str.length();
		if (length <= maxLength && length >= minLength) {
			return str;
		} else if (length > maxLength) {
			return str.substring(length - maxLength);
		} else { // length < minLength
			for (int i = 0; i < minLength - length; i++) {
				str = fill + str;
			}
			return str;
		}
	}

	public static String removeSpace(String str) {
		if (str == null) {
			return null;
		}
		char[] chars = str.toCharArray();
		StringBuffer buffer = new StringBuffer();
		for (int i = 0; i < chars.length; i++) {
			if ((chars[i] == '\n') || chars[i] == '\t' || chars[i] == '\r' || chars[i] == ' ') {
				continue;
			} else {
				buffer.append(chars[i]);
			}
		}
		return buffer.toString();

	}

	private static final Pattern pattern = Pattern.compile("<(\\w+?)>(.*?)</\\1>");

	public static Map<String, String> readXml(String xmlStr) {
		Matcher matcher = pattern.matcher(xmlStr);

		Map<String, String> map = new HashMap<String, String>();

		while (matcher.find()) {
			map.put(matcher.group(1), matcher.group(2));
		}

		return map;
	}

	public static String writeXml(Map<String, String> map) {
		StringBuilder buffer = new StringBuilder(1024);
		for (String key : map.keySet()) {
			buffer.append("<").append(key).append(">").append(map.get(key)).append("</")
					.append(key).append(">");
		}
		return buffer.toString();
	}

	public static String join(String[] values, String split) {
		if (values == null || values.length == 0) {
			return "";
		}
		StringBuffer buffer = new StringBuffer(values[0]);

		for (int i = 1; i < values.length; i++) {
			buffer.append(split).append(values[i]);
		}

		return buffer.toString();
	}

	public static String upperFirstCharactor(String str) {
		if (str == null || str.length() == 0 || str.trim().length() == 0) {
			return "";
		}

		char[] chars = str.trim().toCharArray();

		if (Character.isLetter(chars[0])) {
			chars[0] = Character.toUpperCase(chars[0]);
			return new String(chars);
		} else {
			throw new java.lang.UnsupportedOperationException("ï¿½ï¿½ï¿½Ö·ï¿½ï¿½ï¿½Letter");
		}
	}

	public static String replace(String src, String oldStr, String newStr) {
		StringBuffer buffer = new StringBuffer(src);

		int index = 0;
		while ((index = buffer.indexOf(oldStr, index)) != -1) {
			buffer.replace(index, index + oldStr.length(), newStr);
			index = index + newStr.length();
		}
		return buffer.toString();
	}

	public static int match(String src, String mStr) {
		StringBuffer buffer = new StringBuffer(src);

		int num = 0;

		int index = 0;

		while ((index = buffer.indexOf(mStr, index)) != -1) {
			num++;
			index = index + mStr.length();
		}
		return num;
	}

	public static String xor(String str1, String str2) {
		char[] c1 = str1.toCharArray();
		char[] c2 = str2.toCharArray();
		int maxLength = c1.length > c2.length ? c1.length : c2.length;
		int minLength = c1.length < c2.length ? c1.length : c2.length;

		char[] retVal = new char[maxLength];

		for (int i = 0; i < minLength; i++) {
			retVal[i] = xor(c1[i], c2[i]);
		}
		if (maxLength == c1.length) {
			System.arraycopy(c1, minLength - 1, retVal, minLength - 1, maxLength - minLength);
		} else {
			System.arraycopy(c2, minLength - 1, retVal, minLength - 1, maxLength - minLength);
		}
		return new String(retVal);
	}

	public static char xor(char c1, char c2) {
		int i = c1 ^ c2;

		return (char) i;
	}

	public static String reverse(String str) {
		StringBuffer buffer = new StringBuffer(str);
		buffer.reverse();
		return buffer.toString();
	}

	public static char[] reverse(char[] characters) {
		return reverse(new String(characters)).toCharArray();
	}

	public static char[] align(char[] ch, char c, int length, int flag) {
		char[] chars = new char[length];

		for (int i = 0; i < length; i++) {
			chars[i] = c;
		}
		if (flag == 0) {
			System.arraycopy(ch, 0, chars, 0, ch.length);
		} else if (flag == 1) {
			System.arraycopy(ch, 0, chars, length - ch.length, ch.length);
		}
		return chars;
	}

	public static char[] erase(char[] ch, char c, int flag) {
		if (flag == 0) {
			int i = ch.length - 1;
			for (; i >= 0; i--) {
				if (ch[i] == c) {
					continue;
				} else {
					break;
				}
			}
			char[] temp = new char[i + 1];
			System.arraycopy(ch, 0, temp, 0, i + 1);
			return temp;
		} else if (flag == 1) {
			int i = 0;
			for (; i < ch.length; i++) {
				if (ch[i] == c) {
					continue;
				} else {
					break;
				}
			}
			char[] temp = new char[ch.length - i];
			System.arraycopy(ch, i, temp, 0, ch.length - i);
			return temp;
		}
		return ch;
	}

	public static boolean isEmpty(String str) {
		if (null == str || "null".equals(str.trim()) || "".equals(str.trim())) {
			return true;
		} else {
			return false;
		}
	}

	public static String strNull(String str, String rpt) {
		if (isEmpty(str)) {
			return rpt;
		} else {
			return str.trim();
		}
	}

	public static String strNull(String str) {
		return strNull(str, "");
	}
	
	/**
	 * ×Ö·û³¤Ê××ÖÄ¸´óÐ´
	 * @param str
	 * @return
	 */
	public static String strFirstToUpper(String str){
		if (str == null || "".equals(str.trim())){
			return "";
		}
		return str.replaceFirst(str.substring(0, 1), str.substring(0, 1).toUpperCase());
	}
	
	public static String exchangeStr(String str, int num){
		if (str == null || "".equals(str.trim())){
			return "";
		}
		String[] temps = str.split("_");
		str = "";
		if (num == 1){
			str = temps[0].toLowerCase();
		}
		for (int i = num; i < temps.length; i++) {
			//µÚÒ»¸ö×Ö·û´óÐ´
			str += StringUtil.strFirstToUpper(temps[i].toLowerCase());
		}
		return str;
	}
	
	public static void main(String[] args) {
		String temp=new String(new byte[]{});
		String[] values=temp.split("\\$");
		System.out.println(values.length);
		System.out.println(values[0]);
		//String temp="1$700011411601010002$1$14$99.99$$$2010-01-17$test$2$700011411601010001$2$14$99.99$$$$";
		//String[] values=temp.split("\\$");
		//String[] tt=StringUtil.split(temp, "\\$");
	}

}
