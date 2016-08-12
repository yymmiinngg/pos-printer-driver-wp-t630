package yao.print.wpt630;

import java.util.Set;

import yao.print.api.Converter;
import yao.print.api.ConverterKit;
import yao.print.wpt630.convertable.FontConverter;
import yao.print.wpt630.convertable.LFConverter;
import yao.print.wpt630.convertable.TextConverter;
import yao.print.wpt630.printable.BarcodeConverter;
import yao.print.wpt630.printable.BlankRowConverter;
import yao.print.wpt630.printable.CutPageConverter;
import yao.print.wpt630.printable.ImageConverter;
import yao.print.wpt630.printable.KeyValueConverter;
import yao.print.wpt630.printable.LineConverter;
import yao.print.wpt630.printable.QRcodeConverter;
import yao.print.wpt630.printable.SectionConverter;
import yao.print.wpt630.printable.TableConverter;

public class WPConverterKit extends ConverterKit {

	@Override
	public void registConverter(Set<Class<? extends Converter<?>>> set) {
		set.add(BarcodeConverter.class);
		set.add(CutPageConverter.class);
		set.add(BlankRowConverter.class);
		set.add(KeyValueConverter.class);
		set.add(ImageConverter.class);
		set.add(LFConverter.class);
		set.add(LineConverter.class);
		set.add(QRcodeConverter.class);
		set.add(TextConverter.class);
		set.add(TableConverter.class);
		set.add(SectionConverter.class);
		set.add(FontConverter.class);
	}

	@Override
	public boolean noMatchConverterException() {
		return true;
	}

}
