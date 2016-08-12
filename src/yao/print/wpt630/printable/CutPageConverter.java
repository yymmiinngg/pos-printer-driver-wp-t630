package yao.print.wpt630.printable;

import yao.print.api.Converter;
import yao.print.api.DeviceSetting;
import yao.print.api.content.printable.CutPage;
import yao.print.wpt630.ESCPOSCode;
import yao.print.wpt630.util.BytesBuffer;

/**
 * 切纸转换器
 */
public class CutPageConverter extends Converter<CutPage> {

	public byte[] toBytes(DeviceSetting deviceSetting) {
		BytesBuffer out = new BytesBuffer();
		out.write(ESCPOSCode.ESC_FORWARD_LINE(6));
		return out.toByteArray();
	}

}
