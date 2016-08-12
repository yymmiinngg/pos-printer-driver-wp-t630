package yao.print.wpt630.printable;

import yao.print.api.Converter;
import yao.print.api.DeviceSetting;
import yao.print.api.content.printable.Image;
import yao.print.api.content.printable.Line;
import yao.print.api.exception.PrinterException;
import yao.print.wpt630.util.BytesBuffer;
import yao.print.wpt630.util.ImageCreater;

/**
 * 横线转换器
 */
public class LineConverter extends Converter<Line> {

	@Override
	public byte[] toBytes(DeviceSetting deviceSetting) throws PrinterException {

		int width = deviceSetting.getDrawablePixel();
		int hegiht = getConvertable().getHeight();
		boolean dotted = getConvertable().isDotted();

		Image image = ImageCreater.createLine(width, hegiht, dotted);
		image.setMargin(0);

		BytesBuffer out = new BytesBuffer();
		out.write(getConverterKit().fromConverter(image, deviceSetting));
		return out.toByteArray();

	}
}
