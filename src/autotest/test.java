package autotest;

import java.io.File;

import autotest.element.Element;
import autotest.element.Position;
import autotest.impl.AndroidAppImpl;
import autotest.impl.AndroidDeviceImpl;
import autotest.impl.DefaultAndroidAppImpl;
import autotest.impl.SoloImpl;
import autotest.model.AppInfo;

public class test {

	static AdbDevice device = new AndroidDeviceImpl(0);
	AppInfo appInfo = new AppInfo();
	static AndroidApp app = new DefaultAndroidAppImpl(new File("d:\\yglc.apk"));
	static AppInfo appinfo = app.toAppInfo();
	static Solo solo = new SoloImpl();
		
	public static void main(String[] args) throws InterruptedException {
		// TODO Auto-generated method stub
		
		testDevice();
		testapp();
		testSolo();
		testPosition();
	}
	
	public static void testDevice(){
		System.out.println("开始测试Device");
		System.out.println("获取设备数量，设备数量："+device.size());
		System.out.println("appinfo对象数据："+appinfo.toString());
		System.out.println("当前屏幕是否点亮："+device.isScreenOn());
		System.out.println("当前包名是否是“com.jhss.youguu:”"+("com.jhss.youguu".equals(app.getBasePackage())));
		System.out.println("isdeviceready:"+device.isDeviceReady());
		System.out.println("android Versin:"+device.getAndroidVersion());
		System.out.println("SDK version:"+device.getSdkVersion());
		System.out.println("屏幕分辨率："+device.getScreenResolution()[0]+"X"+device.getScreenResolution()[1]);
		System.out.println("batteryLevel:"+device.getBatteryLevel());
		System.out.println("batteryStatus:"+device.getBatteryStatus());
		System.out.println("系统app:"+device.getSystemAppList());
		System.out.println("三方app:"+device.getThirdAppList());
		System.out.println("启动时间："+device.getAppStartTotalTime("jhss.youguu.finance/jhss.youguu.finance.GuideActivity"));
		device.killApp("jhss.youguu.finance");
		device.unlock();
	}
	
	public static void testapp(){
		System.out.println("开始测试app");
		System.out.println("mainActivity:"+app.getMainActivity());
		System.out.println("appVersion:"+app.getVersionName());
		System.out.println("appId"+app.getAppId());
		System.out.println("包名："+app.getBasePackage());
	}
	
	public static void testSolo(){
		System.out.println("开始测试Solo");
		System.out.println("右滑屏幕");
		solo.swipeToRight();
		solo.swipeToLeft();
		System.out.println("左滑屏幕");
	}
	
	public static void testPosition() throws InterruptedException{
		System.out.println("启动优顾理财app");
		Position position = new Position();
		Element e_text = position.findElementByText("优顾理财");
		device.tap(e_text);
		System.out.println("FocusedPackageAndActivity:"+device.getFocusedPackageAndActivity());
		System.out.println("package:"+device.getCurrentPackageName());
		System.out.println("activity:"+device.getCurrentActivity());
		System.out.println("pid:"+device.getPid(device.getCurrentPackageName()));
		
		Thread.sleep(5000);
//		device.killApp("jhss.youguu.finance");
		device.killProcess(device.getPid(device.getCurrentPackageName()));
		System.out.println("关闭优顾炒股成功。");
	}
}
