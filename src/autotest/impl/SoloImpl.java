package autotest.impl;

import java.util.ArrayList;
import java.util.regex.Pattern;

import autotest.AdbDevice;
import autotest.KeyEvent;
import autotest.Solo;
import autotest.element.Element;
import autotest.utils.ReUtils;
import autotest.utils.ShellUtils;

public class SoloImpl implements Solo {
	
	
	String device;
	int sdkVersion;
	int[] ScreenResolution;
	
		
	public SoloImpl(){
		this.sdkVersion = this.getSdkVersion();
		this.ScreenResolution = this.getScreenResolution();
	}
	
	
	/**
	 * 发送一个按键事件
	 * 
	 * @param keycode
	 *            键值
	 */
	public void sendKeyEvent(int keycode) {
		ShellUtils.shell("input keyevent " + keycode);
		sleep(500);
	}
	
	
	/**
	 * 长按物理键，需要android 4.4以上
	 * 
	 * @param keycode
	 *            键值
	 */
	public void longPressKey(int keycode) {
		ShellUtils.shell("input keyevent --longpress " + keycode);
		sleep(500);
	}

	/**
	 * 发送一个点击事件
	 * 
	 * @param x
	 *            x坐标
	 * @param y
	 *            y坐标
	 */
	public void clickOn(int x, int y) {
		ShellUtils.shell("input tap " + x + " " + y);
		sleep(500);
	}

	/**
	 * 发送一个点击事件
	 * 
	 * @param x
	 *            x小于1，自动乘以分辨率转换为实际坐标，大于1，当做实际坐标处理
	 * @param y
	 *            y小于1，自动乘以分辨率转换为实际坐标，大于1，当做实际坐标处理
	 */
	public void clickOn(double x, double y) {
		double[] coords = ratio(x, y);
		ShellUtils.shell("input tap " + coords[0] + " " + coords[1]);
		sleep(500);
	}

	/**
	 * 发送一个点击事件
	 * 
	 * @param e
	 *            元素对象
	 */
	public void ClickOnElement(Element e) {
		ShellUtils.shell("input tap " + e.getX() + " " + e.getY());
		sleep(500);
	}

	/**
	 * 发送一个滑动事件
	 * 
	 * @param startX
	 *            起始x坐标
	 * @param startY
	 *            起始y坐标
	 * @param endX
	 *            结束x坐标
	 * @param endY
	 *            结束y坐标
	 * @param ms
	 *            持续时间
	 */
	public void swipe(int startX, int startY, int endX, int endY, long ms) {
		int i;
		if (sdkVersion < 19) {
			ShellUtils.shell("input swipe " + startX + " " + startY + " "
					+ endX + " " + endY,device);
		} else {
			ShellUtils.shell("input swipe " + startX + " " + startY + " "
					+ endX + " " + endY + " " + ms);
		}

		sleep(500);
	}

	/**
	 * 发送一个滑动事件
	 * 
	 * @param startX
	 *            起始x坐标
	 * @param startY
	 *            起始y坐标
	 * @param endX
	 *            结束x坐标
	 * @param endY
	 *            结束y坐标
	 * @param ms
	 *            持续时间
	 */
	public void swipe(double startX, double startY, double endX, double endY,
			long ms) {
		double[] coords = ratio(startX, startY, endX, endY);
		if (sdkVersion < 19) {
			ShellUtils.shell("input swipe " + coords[0] + " " + coords[1] + " "
					+ coords[2] + " " + coords[3]);
		} else {
			ShellUtils.shell("input swipe " + coords[0] + " " + coords[1] + " "
					+ coords[2] + " " + coords[3] + " " + ms);
		}

		sleep(500);
	}

	/**
	 * 发送一个滑动事件
	 * 
	 * @param e1
	 *            起始元素
	 * @param e2
	 *            终点元素
	 * @param ms
	 *            持续时间
	 */
	public void swipeElementTo(Element e1, Element e2, long ms) {
		if (sdkVersion < 19) {
			ShellUtils.shell("input swipe " + e1.getX() + " " + e1.getY() + " "
					+ e2.getX() + " " + e2.getY());
		} else {
			ShellUtils.shell("input swipe " + e1.getX() + " " + e1.getY() + " "
					+ e2.getX() + " " + e2.getY() + " " + ms);
		}

		sleep(500);
	}

	/**
	 * 左滑屏幕
	 */
	public void swipeToLeft() {
		swipe(0.8, 0.5, 0.2, 0.5, 500);
	}

	/**
	 * 右滑屏幕
	 */
	public void swipeToRight() {
		swipe(0.2, 0.5, 0.8, 0.5, 500);
	}

	/**
	 * 上滑屏幕
	 */
	public void swipeToUp() {
		swipe(0.5, 0.7, 0.5, 0.3, 500);
	}

	/**
	 * 下滑屏幕
	 */
	public void swipeToDown() {
		swipe(0.5, 0.3, 0.5, 0.7, 500);
	}

	/**
	 * 发送一个长按事件
	 * 
	 * @param x
	 *            x坐标
	 * @param y
	 *            y坐标
	 */
	public void longPress(int x, int y) {
		swipe(x, y, x, y, 1500);
	}

	/**
	 * 发送一个长按事件
	 * 
	 * @param x
	 *            x坐标
	 * @param y
	 *            y坐标
	 */
	public void longPress(double x, double y) {
		swipe(x, y, x, y, 1500);
	}

	/**
	 * 发送一个长按事件
	 * 
	 * @param e
	 *            元素对象
	 */
	public void longPress(Element e) {
		swipeElementTo(e, e, 1500);
	}

	/**
	 * 发送一段文本，只支持英文，多个空格视为一个空格
	 * 
	 * @param text
	 *            英文文本
	 */
	public void sendText(String text) {
		String[] str = text.split(" ");
		ArrayList<String> out = new ArrayList<String>();
		for (String string : str) {
			if (!string.equals("")) {
				out.add(string);
			}
		}

		int length = out.size();
		for (int i = 0; i < length; i++) {
			ShellUtils.shell("input text " + out.get(i));
			sleep(100);
			if (i != length - 1) {
				sendKeyEvent(KeyEvent.KEYCODE_DEL);
			}
		}
	}

	/**
	 * 清除文本
	 * 
	 * @param text
	 *            获取到的文本框中的text
	 */
	public void clearText(String text) {
		int length = text.length();
		for (int i = length; i > 0; i--) {
			sendKeyEvent(KeyEvent.KEYCODE_DEL);
		}
	}

	public double[] ratio(double x, double y) {
		int[] display = ScreenResolution;
		double[] coords = new double[2];

		if (x < 1) {
			coords[0] = display[0] * x;
		} else {
			coords[0] = x;
		}

		if (y < 1) {
			coords[1] = display[1] * y;
		} else {
			coords[1] = y;
		}

		return coords;
	}

	public double[] ratio(double startX, double startY, double endX,
			double endY) {
		int[] display = ScreenResolution;
		double[] coords = new double[4];

		if (startX < 1) {
			coords[0] = display[0] * startX;
		} else {
			coords[0] = startX;
		}

		if (startY < 1) {
			coords[1] = display[1] * startY;
		} else {
			coords[1] = startY;
		}

		if (endX < 1) {
			coords[2] = display[0] * endX;
		} else {
			coords[2] = endX;
		}

		if (endY < 1) {
			coords[3] = display[1] * endY;
		} else {
			coords[3] = endY;
		}

		return coords;
	}
	
	/**
	 * 获取设备屏幕的分辨率
	 * 
	 * @return 返回分辨率数组
	 */
	public int[] getScreenResolution() {
		Pattern pattern = Pattern.compile("([0-9]+)");
		int[] resolution;
		
		Process ps = ShellUtils
				.shell("dumpsys display | grep PhysicalDisplayInfo");
		ArrayList<Integer> out = ReUtils.matchInteger(pattern,
				ShellUtils.getShellOut(ps));
		resolution = new int[] { out.get(0), out.get(1) };
		
		return resolution;
	}
	
	/**
	 * 获取设备中SDK的版本号
	 * 
	 * @return 返回SDK版本号
	 */
	public int getSdkVersion() {
		String sdkVersion;
		Process ps = ShellUtils.shell("getprop ro.build.version.sdk");
		sdkVersion = ShellUtils.getShellOut(ps);
		return new Integer(sdkVersion);
	}
	

	public void sleep(long millis) {
		try {
			Thread.sleep(millis);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	
}
