package yao.print.wpt630.printable;

import yao.print.api.Converter;
import yao.print.api.DeviceSetting;
import yao.print.api.content.printable.Image;
import yao.print.api.content.printable.QRcode;
import yao.print.api.exception.PrinterException;
import yao.print.wpt630.util.BytesBuffer;
import yao.print.wpt630.util.ImageCreater;

/**
 * 二维码转换器
 */
public class QRcodeConverter extends Converter<QRcode> {

	@Override
	public byte[] toBytes(DeviceSetting deviceSetting) throws PrinterException {

		String content = getConvertable().getContent();
		int margin = getConvertable().getMargin();
		int width = getConvertable().getWidth();
		if (0 == width) {
			width = deviceSetting.getDrawablePixel();
		}

		Image image = ImageCreater.creatQRcodeImage(content, width);
		image.setMargin(margin);

		BytesBuffer out = new BytesBuffer();
		out.write(getConverterKit().fromConverter(image, deviceSetting)); // 该指令有打印功能
		return out.toByteArray();

	}
}
