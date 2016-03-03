package autotest.impl;

import autotest.AdbDevice;

public class test {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		AdbDevice device = new AndroidDeviceImpl();
		System.out.println("分辨率"+device.getScreenResolution()[0]+"--"+device.getScreenResolution()[1]);
		System.out.println(device.getScreenResolutionByAdb());
		System.out.println(device.getCurrentActivity());
	}

}
