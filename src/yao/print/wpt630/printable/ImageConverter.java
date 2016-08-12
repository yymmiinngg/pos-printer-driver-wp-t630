package yao.print.wpt630.printable;

import yao.print.api.Converter;
import yao.print.api.DeviceSetting;
import yao.print.api.content.printable.Image;
import yao.print.wpt630.ESCPOSCode;
import yao.print.wpt630.WPConsts;
import yao.print.wpt630.util.ArrayUtil;
import yao.print.wpt630.util.BytesBuffer;

/**
 * 图片转换器
 */
public class ImageConverter extends Converter<Image> {

	@Override
	public byte[] toBytes(DeviceSetting deviceSetting) {

		int drawablePixel = deviceSetting.getDrawablePixel();
		int margin = getConvertable().getMargin();
		if (0 == margin) {
			margin = WPConsts.DEFAULT_IMAGE_MARGIN;
		}

		Image image = getConvertable();
		if (image.getWidth() > drawablePixel || image.getWidth() < 200) {
			image.scaleWidth(drawablePixel);
		}

		int width = image.getWidth();
		int height = image.getHeight();

		int[] pixels = image.getPixels();
		// 由于打印机打印图片的时候总是从一个固定的位置开始打印，无法用命令设置打印位置，所以需要进行填充调整
		if (width < drawablePixel) {
			Image adjust_image = getAdjustPixls(drawablePixel, image);
			height = adjust_image.getHeight();
			width = adjust_image.getWidth();
			pixels = adjust_image.getPixels();
		}

		int bytes_number_of_width = width % 8 == 0 ? width / 8 : width / 8 + 1;
		byte xL = (byte) (bytes_number_of_width % 256);
		byte xH = (byte) (bytes_number_of_width / 256);
		byte yL = (byte) (height % 256);
		byte yH = (byte) (height / 256);

		byte[] dest_bit_data = new byte[bytes_number_of_width * height];

		convertMulityBytesToSingleBit(width, height, pixels, dest_bit_data);
		BytesBuffer out = new BytesBuffer();
		out.write(ESCPOSCode.ESC_FORWARD_DOT(margin));
		out.write(ESCPOSCode.IMAGE_PRINT((byte) 0x00, xL, xH, yL, yH, dest_bit_data)); // 该指令有打印功能
		out.write(ESCPOSCode.ESC_FORWARD_DOT(margin));
		return out.toByteArray();
	}

	/**
	 * 将24位色图转化为单色位图
	 * 
	 * @param image_width
	 * @param image_height
	 * @param src_bitData
	 * @param dest_bit_data
	 */
	private void convertMulityBytesToSingleBit(int image_width, int image_height, int[] src_bitData, byte[] dest_bit_data) {
		int bit_index = 0;
		for (int y = 0; y < image_height; y++) {
			byte one_byte_with_eight_dot = 0x00;
			int bit_position = 7;
			for (int x = 0; x < image_width; x++) {
				int one_pixel_with_mulity_bit = src_bitData[y * image_width + x];
				if (assertBlack(one_pixel_with_mulity_bit)) {
					one_byte_with_eight_dot |= 1 << bit_position;// 把该位置1
				}
				bit_position--;
				if (bit_position < 0) {
					dest_bit_data[bit_index++] = one_byte_with_eight_dot;
					one_byte_with_eight_dot = 0x00;
					bit_position = 7;
				}
			}
			if (image_width % 8 > 0) {
				dest_bit_data[bit_index++] = one_byte_with_eight_dot;

			}
		}
	}

	/**
	 * 判断一个像素点是黑色还是白色，这里用了简化计算，没有采用灰度公式
	 * 
	 * @param one_pixel
	 * @return
	 */
	private boolean assertBlack(int one_pixel) {
		int red = (one_pixel >> 16) & 0xFF;
		int green = (one_pixel >> 8) & 0xFF;
		int blue = one_pixel & 0xFF;
		int gray = (red * 306 + green * 601 + blue * 117) >> 10;
		if (gray < 160) {
			return true;
		}
		return false;
	}

	/**
	 * 通过留白的方式设置打印位置
	 * 
	 * @param drawablePixel
	 *            可打印的宽度
	 * @return
	 */
	public Image getAdjustPixls(int drawablePixel, Image image) {
		int WHITE = 0XFFFFFFFF;
		int image_width = image.getWidth();
		int image_height = image.getHeight();
		int left_fill_num = (drawablePixel - image_width) / 2;
		int new_width = image_width + left_fill_num;

		int[] src_bitData = image.getPixels();
		int[][] new_data = new int[image_height][new_width];

		for (int height = 0; height < image_height; height++) {
			for (int fill_temp = 0; fill_temp < left_fill_num; fill_temp++) {
				new_data[height][fill_temp] = WHITE;
			}
			for (int width = 0; width < image_width; width++) {
				new_data[height][width + left_fill_num] = src_bitData[height * image_width + width];
			}
		}

		return new Image(new_width, image_height, ArrayUtil.convertTwoToOne(new_data));
	}
}
