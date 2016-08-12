package yao.print.wpt630.util;

import java.util.Hashtable;

import yao.print.api.content.printable.Image;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;

/**
 * 图片生成器
 */
@SuppressWarnings("serial")
public class ImageCreater {

	/**
	 * 条形码的编码类型
	 */
	private static BarcodeFormat barcodeFormat = BarcodeFormat.CODE_128;

	/** 二维码的编码类型 */
	private static BarcodeFormat QRcodeFormat = BarcodeFormat.QR_CODE;

	private static final String content_charset = "utf-8";

	private static final int BLACK = 0xff000000;

	private static final int WHITE = 0Xffffffff;

	/**
	 * 生成条形码
	 *
	 * @param content
	 *            需要生成的内容
	 * @param width
	 *            生成条形码的宽带
	 * @param height
	 *            生成条形码的高度
	 * @return
	 */
	public static Image creatBarcodeImage(Integer width, Integer height, String content) {
		Hashtable<EncodeHintType, String> hints = new Hashtable<EncodeHintType, String>() {
			{
				put(EncodeHintType.CHARACTER_SET, content_charset);
			}
		};
		BitMatrix bitMatrix = null;
		try {
			bitMatrix = new MultiFormatWriter().encode(content, barcodeFormat, width, height, hints);
			return convertToPrintableImage(bitMatrix);
		} catch (WriterException e) {
			throw new RuntimeException(e);
		}

	}

	/**
	 * 产生线图
	 * 
	 * @param width
	 * @param height
	 * @param dotted
	 * @return
	 */
	public static Image createLine(int width, int height, boolean dotted) {
		int[] pixels = new int[width * height];
		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				if (dotted && (x / 8 % 2) == 1) {
					pixels[(y * width) + x] = WHITE;
				} else {
					pixels[(y * width) + x] = BLACK;
				}
			}
		}
		return new Image(width, height, pixels);

	}

	/**
	 * 生成二维码
	 *
	 * @param content
	 *            需要生成的内容
	 * @param width
	 *            生成条形码的宽度mm
	 * @param height
	 *            生成条形码的高度mm
	 * @return
	 */
	public static Image creatQRcodeImage(String content, Integer width) {
		Hashtable<EncodeHintType, String> hints = new Hashtable<EncodeHintType, String>() {
			{
				put(EncodeHintType.CHARACTER_SET, content_charset);
			}
		};
		BitMatrix bitMatrix = null;
		try {
			bitMatrix = new MultiFormatWriter().encode(content, QRcodeFormat, width, width, hints);
			return convertToPrintableImage(bitMatrix);
		} catch (WriterException e) {
			throw new RuntimeException(e);
		}

	}

	private static Image convertToPrintableImage(BitMatrix bitMatrix) {
		int picture_width = bitMatrix.getWidth();
		int picture_height = bitMatrix.getHeight();
		int[] pixels = new int[picture_width * picture_height];
		for (int y = 0; y < picture_height; y++) {
			for (int x = 0; x < picture_width; x++) {
				if (bitMatrix.get(x, y)) {
					pixels[y * picture_width + x] = BLACK;
				} else {
					pixels[y * picture_width + x] = WHITE;
				}
			}
		}
		return new Image(picture_width, picture_height, pixels);
	}
}
