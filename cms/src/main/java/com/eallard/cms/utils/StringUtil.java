package com.eallard.cms.utils;

/*jadclipse*/// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
//Jad home page: http://www.kpdus.com/jad.html
//Decompiler options: packimports(3) radix(10) lradix(10) 
//Source File Name:   StringUtil.java

import java.math.BigDecimal;
import java.security.MessageDigest;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.*;

import org.springframework.util.StringUtils;

public class StringUtil {

	public StringUtil() {
	}

	public static boolean isEmpty(Object str) {
		return str == null || String.valueOf(str).trim().length() < 1;
	}

	public static boolean isNotEmpty(Object str) {
		return !isEmpty(str);
	}

	public static boolean isEmpty(String str) {
		return str == null || str.trim().length() < 1;
	}

	public static boolean isNotEmpty(String str) {
		return !isEmpty(str);
	}

	public static String obj2Str(Object obj) {
		return obj != null ? obj.toString().trim() : "";
	}

	public static String obj2Str(Object obj, String defaultValue) {
		return obj != null ? obj.toString().trim() : defaultValue;
	}

	public static Integer string2Integer(String str) {
		if (isNotEmpty(str))
			try {
				return new Integer(str.trim());
			} catch (NumberFormatException e) {
				e.printStackTrace();
			}
		return null;
	}

	public static Integer string2Integer(String str, Integer defaultValue) {
		if (isNotEmpty(str)) {
			try {
				return new Integer(str.trim());
			} catch (NumberFormatException e) {
				e.printStackTrace();
			}
			return defaultValue;
		} else {
			return defaultValue;
		}
	}

	public static Long string2Long(String str) {
		if (isNotEmpty(str))
			try {
				return new Long(str.trim());
			} catch (NumberFormatException e) {
				e.printStackTrace();
			}
		return null;
	}

	public static Long string2Long(String str, Long defaultValue) {
		if (isNotEmpty(str)) {
			try {
				return new Long(str.trim());
			} catch (NumberFormatException e) {
				e.printStackTrace();
			}
			return defaultValue;
		} else {
			return defaultValue;
		}
	}

	public static Double stringToDouble(String str) {
		if (isNotEmpty(str))
			try {
				return new Double(str.trim());
			} catch (NumberFormatException e) {
				e.printStackTrace();
			}
		return null;
	}

	public static Double stringToDouble(String str, Double defaultValue) {
		if (isNotEmpty(str)) {
			try {
				return new Double(str.trim());
			} catch (NumberFormatException e) {
				e.printStackTrace();
			}
			return defaultValue;
		} else {
			return defaultValue;
		}
	}

	public static BigDecimal string2BigDecimal(String str) {
		if (isNotEmpty(str))
			try {
				return new BigDecimal(str);
			} catch (NumberFormatException e) {
				e.printStackTrace();
			}
		return null;
	}

	public static BigDecimal string2BigDecimal(String str,
			BigDecimal defaultValue) {
		if (isNotEmpty(str)) {
			try {
				return new BigDecimal(str);
			} catch (NumberFormatException e) {
				e.printStackTrace();
			}
			return defaultValue;
		} else {
			return defaultValue;
		}
	}

	public static boolean isDecimal(String str) {
		boolean res = true;
		if (isEmpty(str))
			return false;
		try {
			new BigDecimal(str);
		} catch (NumberFormatException e) {
			res = false;
		}
		return res;
	}

	public static String numberFormat(String value) {
		if (!isEmpty(value)) {
			NumberFormat format = NumberFormat.getInstance();
			return format.format(Double.parseDouble(value));
		} else {
			return null;
		}
	}

	public static boolean isChinese(char c) {
		Character.UnicodeBlock ub = Character.UnicodeBlock.of(c);
		return ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS
				|| ub == Character.UnicodeBlock.CJK_COMPATIBILITY_IDEOGRAPHS
				|| ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_A
				|| ub == Character.UnicodeBlock.GENERAL_PUNCTUATION
				|| ub == Character.UnicodeBlock.CJK_SYMBOLS_AND_PUNCTUATION
				|| ub == Character.UnicodeBlock.HALFWIDTH_AND_FULLWIDTH_FORMS;
	}

	public static boolean isChinese(String str) {
		if (isEmpty(str))
			return false;
		char ch[] = str.toCharArray();
		for (int i = 0; i < ch.length; i++) {
			char c = ch[i];
			if (isChinese(c))
				return true;
		}

		return false;
	}

	public static boolean stringEquals(String str1, String str2) {
		if (isEmpty(str1) && isEmpty(str2))
			return true;
		if (isNotEmpty(str1))
			return str1.equals(str2);
		else
			return false;
	}

	public static String array2Str(Object strArray[]) {
		String str = "";
		for (int i = 0; i < strArray.length; i++)
			str = (new StringBuilder()).append(str)
					.append(strArray[i].toString()).append(",").toString();

		if (str.length() > 0)
			str = str.substring(0, str.length() - 1);
		return str;
	}

	public static String[] str2Array(String str) {
		Vector<String> vect = getVector(str, ",");
		int num = vect.size();
		String strArray[] = new String[num];
		for (int i = 0; i < num; i++)
			strArray[i] = (String) vect.elementAt(i);

		return strArray;
	}

	public static Set<String> commaString2Set(String commaString, String split) {
		Set<String> s = new HashSet<String>();
		if (isNotEmpty(commaString)) {
			String arr[] = commaString.split(split);
			for (int i = 0; i < arr.length; i++)
				s.add(arr[i].trim());

		}
		return s;
	}

	public static Set<String> commaString2Set(String commaString) {
		return commaString2Set(commaString, ",");
	}

	public static List<String> string2List(String str, String splitStr,
			boolean removeComma) {
		List<String> list = new ArrayList<String>();
		int pos = str.indexOf(splitStr);
		boolean hasSplit = false;
		if (pos >= 0)
			hasSplit = true;
		for (; pos >= 0; pos = str.indexOf(splitStr)) {
			String obj = str.substring(0, pos);
			if (removeComma)
				obj = obj.substring(1, obj.length() - 1);
			list.add(obj);
			str = str.substring(pos + 1, str.length());
		}

		if (hasSplit) {
			if (removeComma)
				str = str.substring(1, str.length() - 1);
			list.add(str);
		}
		return list;
	}

	public static boolean contains(String str, String target, int pos,
			char begin, char end) throws Exception {
		int b = str.indexOf(begin);
		int e = str.indexOf(end);
		if (b < 0 || e < 0)
			return false;
		int len = target.length();
		System.out.println((new StringBuilder()).append(str.length())
				.append(":").append(pos).append(":").append(len).toString());
		String s = str.substring(pos, pos + len);
		if (!s.equalsIgnoreCase(target))
			throw new Exception((new StringBuilder()).append("string['")
					.append(target).append("]location:[").append(pos)
					.append("]Unspecified error").toString());
		String frontStr = str.substring(0, pos);
		String backStr = str.substring(pos + len + 1);
		int endCount = 0;
		int beginCount = 0;
		boolean existBegin = false;
		for (int i = 0; i < frontStr.length(); i++) {
			char c = frontStr.charAt(i);
			if (c == begin)
				beginCount++;
			if (c == end)
				endCount++;
		}

		if (beginCount - endCount > 0)
			existBegin = true;
		endCount = 0;
		beginCount = 0;
		boolean existEnd = false;
		for (int i = 0; i < backStr.length(); i++) {
			char c = backStr.charAt(i);
			if (c == begin)
				beginCount++;
			if (c == end)
				endCount++;
		}

		if (endCount - beginCount > 0)
			existEnd = true;
		return existBegin && existEnd;
	}

	public static int getPosNotIn(String str, String target, char begin,
			char end) throws Exception {
		String opStr = str;
		int pos = 0;
		int modify = 0;
		boolean inIf = false;
		do {
			if (opStr.toLowerCase().indexOf(target.toLowerCase()) < 0)
				break;
			System.out.println((new StringBuilder()).append("opStr=[")
					.append(opStr).append("]").toString());
			pos = opStr.toLowerCase().indexOf(target.toLowerCase());
			if (!contains(str, target, pos + modify, begin, end)) {
				inIf = true;
				break;
			}
			opStr = opStr.substring(pos + target.length());
			modify += pos + target.length();
			System.out.println((new StringBuilder()).append("modify=")
					.append(modify).toString());
		} while (true);
		if (!inIf) {
			return -1;
		} else {
			pos = modify + pos;
			System.out.println((new StringBuilder())
					.append(str.substring(pos, pos + target.length()))
					.append(" found,pos=[").append(pos).append("],modify=[")
					.append(modify).append("]").toString());
			return pos;
		}
	}

	public static String replaceAllBlank(String s) {
		if (isEmpty(s))
			return "";
		else
			return s.replaceAll("\\s", "");
	}

	public static String replaceAll(String source, String oldString,
			String newString) {
		if (source == null || source.equals(""))
			return "";
		StringBuffer output = new StringBuffer();
		int lengthOfSource = source.length();
		int lengthOfOld = oldString.length();
		int posStart;
		int pos;
		for (posStart = 0; (pos = source.indexOf(oldString, posStart)) >= 0; posStart = pos
				+ lengthOfOld) {
			output.append(source.substring(posStart, pos));
			output.append(newString);
		}

		if (posStart < lengthOfSource)
			output.append(source.substring(posStart));
		return output.toString();
	}

	public static String arr2CommaString(String s[]) {
		if (s == null || s.length < 1)
			return "";
		String result = s[0];
		if (s.length > 1) {
			for (int i = 1; i < s.length; i++)
				result = (new StringBuilder()).append(result).append(",")
						.append(s[i]).toString();

		}
		return result;
	}

	public static String list2StringWithSplit(List<String> list, String splitStr) {
		if (list == null || list.size() < 1)
			return null;
		StringBuffer buf = new StringBuffer();
		for (Iterator<String> iter = list.iterator(); iter.hasNext(); buf
				.append((String) iter.next()))
			buf.append(splitStr);

		return buf.toString().substring(1);
	}

	public static String list2StringWithComma(Collection<String> list, String splitStr) {
		if (list == null || list.size() < 1)
			return null;
		StringBuffer buf = new StringBuffer();
		for (Iterator<String> iter = list.iterator(); iter.hasNext(); buf.append("'")) {
			buf.append(splitStr);
			buf.append("'");
			buf.append((String) iter.next());
		}

		return buf.toString().substring(1);
	}

	public static String filterStr(String src, String filterChar) {
		if (isEmpty(src))
			return "";
		src = src.trim();
		if (filterChar == null || filterChar.length() < 0)
			filterChar = "`~\\:;\"'<,>./";
		int len = filterChar.length();
		for (int i = 0; i < len; i++)
			src = src.replaceAll(
					(new StringBuilder()).append("\\")
							.append(String.valueOf(filterChar.charAt(i)))
							.toString(), "");

		return src;
	}

	public static String convertComma2Db(String str) {
		int pos = str.indexOf("'");
		int pos1 = 0;
		for (; pos != -1; pos = str.substring(pos1, str.length()).indexOf("'")) {
			str = (new StringBuilder()).append(str.substring(0, pos1 + pos))
					.append("'")
					.append(str.substring(pos + pos1, str.length())).toString();
			pos1 = pos1 + pos + 2;
		}

		return str;
	}

	public static String addInvertedComma(String source) {
		if (isEmpty(source))
			return null;
		source = (new StringBuilder()).append("'").append(source.trim())
				.toString();
		int pos = source.indexOf(",");
		int len = source.length();
		do {
			if (pos == -1)
				break;
			source = (new StringBuilder()).append(source.substring(0, pos))
					.append("','").append(source.substring(pos + 1, len))
					.toString();
			len += 2;
			pos += 2;
			if (source.substring(pos).indexOf(",") == -1)
				break;
			pos = source.substring(pos).indexOf(",") + pos;
		} while (true);
		source = (new StringBuilder()).append(source).append("'").toString();
		return source;
	}


	public static Vector<String> getVector(String str, String splitStr) {
		Vector<String> vect = new Vector<String>();
		int pos = str.indexOf(splitStr);
		int len = splitStr.length();
		for (; pos >= 0; pos = str.indexOf(splitStr)) {
			vect.addElement(str.substring(0, pos));
			str = str.substring(pos + len, str.length());
		}

		vect.addElement(str.substring(0, str.length()));
		return vect;
	}

	public static String addChar4Len(String str, String strspace, int strlen) {
		if (str == null || str.length() < 1 || strspace == null
				|| strspace.length() < 1)
			return str;
		for (; str.length() < strlen; str = (new StringBuilder())
				.append(strspace).append(str).toString())
			if (str.length() + strspace.length() > strlen)
				return str;

		return str;
	}

	public static String getGender(String iDCard) {
		int gender = 3;
		if (iDCard.length() == 15)
			gender = (new Integer(iDCard.substring(14, 15))).intValue() % 2;
		else if (iDCard.length() == 18) {
			int number17 = (new Integer(iDCard.substring(16, 17))).intValue();
			gender = number17 % 2;
		}
		if (gender == 1)
			return "1";
		if (gender == 0)
			return "2";
		else
			return "";
	}

	public static boolean checkEmail(String emailStr) {
		if (isEmpty(emailStr))
			return false;
		else
			return emailStr.matches("[-_.a-zA-Z0-9]+@[-_a-zA-Z0-9]+.[a-zA-Z]+");
	}

	public static boolean checkStr(String v) {
		if (isEmpty(v))
			return false;
		else
			return v.matches("[a-zA-Z0-9_]*");
	}

	public static String encryptMD5(String value) throws Exception {
		String result = "";
		if (isEmpty(value))
			return "";
		try {
			MessageDigest messageDigest = MessageDigest.getInstance("MD5");
			messageDigest.update(value.getBytes());
			result = byte2hex(messageDigest.digest());
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception(e.getMessage());
		}
		return result;
	}

	private static String byte2hex(byte bytes[]) {
		String result = "";
		String stmp = "";
		for (int n = 0; n < bytes.length; n++) {
			stmp = Integer.toHexString(bytes[n] & 255);
			if (stmp.length() == 1)
				result = (new StringBuilder()).append(result).append("0")
						.append(stmp).toString();
			else
				result = (new StringBuilder()).append(result).append(stmp)
						.toString();
		}

		return result.toUpperCase();
	}

	public static String formatString(String value, String mask) {
		String result = value;
		try {
			if (StringUtils.hasText(value) && StringUtils.hasText(mask))
				if (mask.indexOf("yyyy") >= 0 || mask.indexOf("MM") >= 0
						|| mask.indexOf("dd") >= 0 || mask.indexOf("HH") >= 0
						|| mask.indexOf("mm") >= 0) {
					java.util.Date date = DateUtil.string2Date(value, mask);
					result = DateUtil.date2String(date, mask);
				} else if (mask.indexOf('.') > 0 || mask.indexOf('#') >= 0
						|| mask.indexOf('0') >= 0) {
					Double d = Double.valueOf(value);
					DecimalFormat df = new DecimalFormat(mask);
					result = df.format(d);
				}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	public static String format4InvertedComma(String sourceString) {
		return sourceString.replaceAll("'", "''");
	}

	public static void main(String args[]) {
		/*String testStr = "aa;bb;cc|dd;ee;ff";
		Vector v = getVector(testStr, "|");
		for (int i = 0; i < v.size(); i++)
			System.out.println(v.get(i));

		String strPath = "file:/crmtest/product/suite400/webapp/WEB-INF/lib/aibi-waterMark-1.2.0-SNAPSHOT.jar!/META-INF/MANIFEST.MF";
		strPath = strPath.substring(5, strPath.indexOf("!"));
		System.out.println(strPath);
		strPath = strPath.substring(0, strPath.lastIndexOf("/") + 1);
		System.out.println(strPath);*/
	}

}

