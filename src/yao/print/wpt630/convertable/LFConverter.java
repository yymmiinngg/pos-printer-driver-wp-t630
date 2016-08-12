package yao.print.wpt630.convertable;

import yao.print.api.Converter;
import yao.print.api.DeviceSetting;
import yao.print.api.content.convertable.LF;
import yao.print.wpt630.ESCPOSCode;

/**
 * 换行转换器
 */
public class LFConverter extends Converter<LF> {

	protected byte[] toBytes(DeviceSetting deviceSetting) {
		return new byte[] { ESCPOSCode.LF };
	}
}
