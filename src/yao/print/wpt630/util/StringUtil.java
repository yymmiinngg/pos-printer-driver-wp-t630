package yao.print.wpt630.util;

import yao.print.api.DeviceSetting;

public class StringUtil {

	/**
	 * 判断一个字符占用的字符位置数（中文2位）
	 *
	 * @param value
	 * @return
	 */
	public static int lengthOfGBK(String value) {
		if (value == null)
			return 0;
		StringBuffer buff = new StringBuffer(value);
		int length = 0;
		String stmp;
		for (int i = 0; i < buff.length(); i++) {
			stmp = buff.substring(i, i + 1);
			try {
				stmp = new String(stmp.getBytes("utf8"));
			} catch (Exception e) {
				e.printStackTrace();
			}
			if (stmp.getBytes().length > 1) {
				length += 2;
			} else {
				length += 1;
			}
		}
		return length;
	}

	/**
	 * 将字符串变成指定长度，删除末尾或填充末尾(空格)
	 * 
	 * @param src
	 * @param destLen
	 * @return
	 */
	public static String toGBKLength(String src, int destLen) {
		StringBuffer sb = new StringBuffer(src);
		int i = lengthOfGBK(sb.toString());
		if (i > destLen) {
			do {
				sb.deleteCharAt(sb.length() - 1);
				i = lengthOfGBK(sb.toString());
			} while (i > destLen);
		}
		if (i < destLen) {
			do {
				sb.append(' ');
				i = lengthOfGBK(sb.toString());
			} while (i < destLen);
		}
		return sb.toString();
	}

	/**
	 * 将字符串变成指定长度，删除末尾，如果长度不够不填充末尾
	 * 
	 * @param src
	 * @param destLen
	 * @return
	 */
	public static String toGBKLengthNoAdd(String src, int destLen) {
		StringBuffer sb = new StringBuffer(src);
		int i = lengthOfGBK(sb.toString());
		if (i > destLen) {
			do {
				sb.deleteCharAt(sb.length() - 1);
				i = lengthOfGBK(sb.toString());
			} while (i > destLen);
		}
		return sb.toString();
	}

	/**
	 * 把长度不够的字符串填充指定字符，直到指定长度
	 * 
	 * @param src
	 * @param fill
	 *            填充合作的字符串
	 * @param destLen
	 * @return
	 */
	public static String fillLeft2GBKLength(String src, char fill, int destLen) {
		StringBuffer sb = new StringBuffer(src);
		int i = lengthOfGBK(sb.toString());
		if (i < destLen) {
			do {
				sb.insert(0, fill);
				i = lengthOfGBK(sb.toString());
			} while (i < destLen);
		}
		return sb.toString();
	}

	public static int getCharEachLine(DeviceSetting setting) {
		return setting.getDrawablePixel() / 12;
	}

}
