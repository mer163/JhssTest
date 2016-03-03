package autotest.element;

import autotest.AdbDevice;
import autotest.impl.AndroidAppImpl;
import autotest.impl.AndroidDeviceImpl;
import autotest.utils.ShellUtils;

//坐标
public class Element  {
	private int x;
	private int y;
	private String device;
	
	public void Element(){
		x = getX();
		y = getY();
		
	}
	
	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}
	
	public void tap() throws InterruptedException {
		ShellUtils.shell("input tap " + x + " " + y);
		Thread.sleep(500);
	}
	
	public void swipe(Element e, long ms) throws InterruptedException {
		AdbDevice adbDevice = new AndroidDeviceImpl(0);
		if (adbDevice.getSdkVersion() < 19) {
			ShellUtils.shell("input swipe " + x + " " +y + " "
					+ e.getX() + " " + e.getY());
		} else {
			ShellUtils.shell("input swipe " + x + " " +y + " "
					+ e.getX() + " " + e.getY() + " " + ms);
		}

		Thread.sleep(500);
	}
	
	public void longPress(Element e) throws InterruptedException {
		swipe(e,1500);
	}
}
