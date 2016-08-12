package yao.print.wpt630.convertable;

import java.io.UnsupportedEncodingException;

import yao.print.api.Converter;
import yao.print.api.DeviceSetting;
import yao.print.api.content.convertable.Font;
import yao.print.api.content.convertable.Text;
import yao.print.api.exception.PrinterException;
import yao.print.wpt630.util.BytesBuffer;

/**
 * 文本转换器
 */
public class TextConverter extends Converter<Text> {

	@Override
	protected byte[] toBytes(DeviceSetting setting) throws PrinterException {
		Font font = getConvertable().getFont();
		String content = getConvertable().getText();
		BytesBuffer out = new BytesBuffer();
		out.write(getConverterKit().fromConverter(font, setting));
		try {
			out.write(content.getBytes(setting.getCharset()));
		} catch (UnsupportedEncodingException e) {
			throw new PrinterException(e);
		}
		return out.toByteArray();
	}
}
