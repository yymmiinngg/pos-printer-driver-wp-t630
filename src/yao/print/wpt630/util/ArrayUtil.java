package yao.print.wpt630.util;

import java.util.Arrays;

public class ArrayUtil {

	/**
	 * 连接多个数组
	 * 
	 * @param first
	 * @param rest
	 * @return
	 */
	public static byte[] contact(byte[] first, byte[]... rest) {

		int length = first.length;

		for (byte[] one_memb : rest) {
			length += one_memb.length;
		}
		int offset = first.length;
		byte[] dest_array = Arrays.copyOf(first, length);
		for (byte[] one_array : rest) {

			System.arraycopy(one_array, 0, dest_array, offset, one_array.length);
			offset += one_array.length;

		}
		return dest_array;
	}

	/**
	 * 二维数组转化成一维
	 * 
	 * @param src
	 * @return
	 */
	public static int[] convertTwoToOne(int[][] src) {

		int height = src.length;
		int width = src[0].length;

		int[] dest = new int[height * width];
		int index = 0;
		for (int h = 0; h < height; h++) {

			int[] aline = src[h];
			for (int w = 0; w < width; w++) {
				dest[index++] = aline[w];
			}
		}

		return dest;

	}

}
