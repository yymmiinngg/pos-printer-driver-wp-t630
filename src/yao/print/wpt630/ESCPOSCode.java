package yao.print.wpt630;

import yao.print.wpt630.util.ArrayUtil;
import yao.print.wpt630.util.BytesBuffer;

public class ESCPOSCode {

	public static final byte HT = 0x9; // 水平制表
	public static final byte LF = 0x0A; // 打印并换行
	public static final byte ESC = 0x1B; // ESC码
	public static final byte GS = 0x1D;
	public static final byte FS = 0x1C;

	public static final byte[] BEL = new byte[] { ESC, 0x7 };

	public static final byte[] RESET = new byte[] { ESC, '@' };

	public static final byte[] STATUS = new byte[] { ESC, 'v' };

	/* 初始化打印机 ECS @ */
	public static final byte[] ESC_RESETPRINTER = new byte[] { ESC, 0x40 };

	/* 靠左打印命令 */
	public static final byte[] ESC_ALIGN_LEFT = new byte[] { ESC, 'a', 0x00 };

	/* 靠右打印命令 */
	public static final byte[] ESC_ALIGN_RIGHT = new byte[] { ESC, 'a', 0x02 };

	/* 居中打印命令 */
	public static final byte[] ESC_ALIGN_CENTER = new byte[] { ESC, 'a', 0x01 };

	/* 取消字体加粗 */
	public static final byte[] ESC_CANCEL_BOLD = new byte[] { ESC, 0x45, 0 };

	/* 设置字体加粗 */
	public static final byte[] ESC_SET_BOLD = new byte[] { ESC, 0x45, 0x01 };

	/* 取消字体下划线 */
	public static final byte[] FS_CANCEL_UNDERLINE = new byte[] { FS, 0x2D, 0x00 };

	/* 设置字体下划线 */
	public static final byte[] FS_SET_UNDERLINE = new byte[] { FS, 0x2D, 0x01 };

	/** 字体大小 */
	public static final byte[] ESC_FONT_SIZE_MAX = new byte[] { GS, '!', 0x22 };
	public static final byte[] ESC_FONT_SIZE_MID = new byte[] { GS, '!', 0x11 };
	public static final byte[] ESC_FONT_SIZE_MIN = new byte[] { GS, '!', 0x00 };

	// // 走纸n行
	public static final byte[] ESC_FORWARD_LINE(int n) {
		return new byte[] { ESC, 'd', (byte) n };
	}

	// 切纸
	public static final byte[] ESC_CUTPAPER = new byte[] { ESC, 0x6D };

	// 走纸n点，一般8点一个像素，本指令执行后走纸后将移动到下一行的起始位置
	public static final byte[] ESC_FORWARD_DOT(int n) {
		return new byte[] { ESC, 'J', (byte) n };
	}

	/**
	 * 打印图片命令
	 * 
	 * @param modole
	 * @param xL
	 * @param xH
	 * @param yL
	 * @param yH
	 * @param bit_image
	 * @return
	 */
	public static final byte[] IMAGE_PRINT(byte modole, byte xL, byte xH, byte yL, byte yH, byte[] bit_image) {
		BytesBuffer out = new BytesBuffer();
		out.write(new byte[] { 0x1d, 0x76, 0x30 }); // 基本指令
		out.write(new byte[] { modole, xL, xH, yL, yH });// 打印设置
		out.write(bit_image);// 位图信息
		return out.toByteArray();
	}

	/**
	 * 设置制表位置
	 * 
	 * @param positions
	 * @return
	 */
	public static final byte[] SET_TABLE_CELL_POSITION(byte[] positions) {
		byte[] command = new byte[] { 0x1b, 0x44 };
		byte[] end_mark = new byte[] { 0x00 };
		if (null != positions) {
			return ArrayUtil.contact(command, positions, end_mark);
		} else {
			return ArrayUtil.contact(command, end_mark);
		}
	}

	/**
	 * 取消制表位置
	 * 
	 * @return
	 */
	public static final byte[] CANCLE_TABLE_CELL_POSITION_SETTING() {
		return SET_TABLE_CELL_POSITION(null);
	}
}