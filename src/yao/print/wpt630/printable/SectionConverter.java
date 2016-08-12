package yao.print.wpt630.printable;

import java.util.List;

import yao.print.api.Converter;
import yao.print.api.DeviceSetting;
import yao.print.api.content.Convertable;
import yao.print.api.content.printable.Section;
import yao.print.api.content.printable.Section.TextAlign;
import yao.print.api.exception.PrinterException;
import yao.print.wpt630.ESCPOSCode;
import yao.print.wpt630.util.BytesBuffer;
import yao.print.wpt630.util.TextAlignUtil;

/**
 * 打印文字段落命令
 */
public class SectionConverter extends Converter<Section> {

	@Override
	public byte[] toBytes(DeviceSetting deviceSetting) throws PrinterException {
		List<Convertable> contents = getConvertable().getConvertables();
		TextAlign align = getConvertable().getTextAlign();
		BytesBuffer out = new BytesBuffer();
		out.write(TextAlignUtil.toBytes(align));
		for (Convertable text : contents) {
			out.write(getConverterKit().fromConverter(text, deviceSetting));
		}
		out.write(ESCPOSCode.LF);
		return out.toByteArray();
	}
}
