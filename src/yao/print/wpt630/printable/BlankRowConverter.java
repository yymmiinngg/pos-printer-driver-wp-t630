package yao.print.wpt630.printable;

import yao.print.api.Converter;
import yao.print.api.DeviceSetting;
import yao.print.api.content.printable.BlankRow;
import yao.print.wpt630.ESCPOSCode;

/**
 * 空行转换器
 */
public class BlankRowConverter extends Converter<BlankRow> {

	@Override
	public byte[] toBytes(DeviceSetting deviceSetting) {
		return ESCPOSCode.ESC_FORWARD_LINE(getConvertable().getLineNumber());
	}

}
