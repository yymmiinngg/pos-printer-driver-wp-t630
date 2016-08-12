package yao.print.wpt630.printable;

import yao.print.api.Converter;
import yao.print.api.DeviceSetting;
import yao.print.api.content.printable.Barcode;
import yao.print.api.content.printable.Image;
import yao.print.api.exception.PrinterException;
import yao.print.wpt630.WPConsts;
import yao.print.wpt630.util.BytesBuffer;
import yao.print.wpt630.util.ImageCreater;

/**
 * 条形码转换器
 */
public class BarcodeConverter extends Converter<Barcode> {

	@Override
	public byte[] toBytes(DeviceSetting deviceSetting) throws PrinterException {

		String content = getConvertable().getContent();
		int margin = getConvertable().getMargin();
		int height = getConvertable().getHeight();
		if (0 == height) {
			height = WPConsts.DEFAULT_BARCODE_HEIGHT;
		}

		Image image = ImageCreater.creatBarcodeImage(deviceSetting.getDrawablePixel(), height, content);
		image.setMargin(margin);

		BytesBuffer out = new BytesBuffer();
		out.write(getConverterKit().fromConverter(image, deviceSetting));
		return out.toByteArray();
	}
}
