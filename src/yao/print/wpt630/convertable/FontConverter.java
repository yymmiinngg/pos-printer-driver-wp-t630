package yao.print.wpt630.convertable;

import yao.print.api.Converter;
import yao.print.api.DeviceSetting;
import yao.print.api.content.convertable.Font;
import yao.print.wpt630.ESCPOSCode;
import yao.print.wpt630.util.BytesBuffer;

/**
 * 文档格式
 */
public class FontConverter extends Converter<Font> {

	@Override
	protected byte[] toBytes(DeviceSetting deviceSetting) {

		boolean font_bold = getConvertable().isBold();
		boolean under_line = getConvertable().isUnderline();
		Font.FontSize fontSize = getConvertable().getFontSize();

		BytesBuffer out = new BytesBuffer();

		if (font_bold) {
			out.write(ESCPOSCode.ESC_SET_BOLD);
		} else {
			out.write(ESCPOSCode.ESC_CANCEL_BOLD);
		}

		if (under_line) {
			out.write(ESCPOSCode.FS_SET_UNDERLINE);
		} else {
			out.write(ESCPOSCode.FS_CANCEL_UNDERLINE);
		}

		if (fontSize == Font.FontSize.Big) {
			out.write(ESCPOSCode.ESC_FONT_SIZE_MAX);
		} else if (fontSize == Font.FontSize.Small) {
			out.write(ESCPOSCode.ESC_FONT_SIZE_MIN);
		} else {
			out.write(ESCPOSCode.ESC_FONT_SIZE_MID);
		}

		return out.toByteArray();

	}
}
