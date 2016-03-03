package autotest;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.regex.Pattern;


























import autotest.utils.ReUtils;
import autotest.utils.ShellUtils;
import autotest.element.Element;
import autotest.impl.AndroidDeviceImpl;

/**
 * 
 * @author peter, 4653476@qq.com
 *
 */
public interface AdbDevice {
	
	int size();
	boolean isDeviceReady();
	boolean isScreenOn();
	void unlock();
	void innerUnlock(String pkg, String activity);
	void killApp(String pkgname);
	String getDeviceId();
	String getExternalStoragePath();
	String getCrashLog();
	boolean  isWifiOff();
	String getAndroidVersion();
	int getSdkVersion();
	
	int[] getScreenResolution();
	String getScreenResolutionByAdb();
	
	int getBatteryLevel();
	double getBatteryTemp();
	int getBatteryStatus();
	String getFocusedPackageAndActivity();
	String getCurrentPackageName();
	String getCurrentActivity();
	int getPid(String packageName);
	boolean killProcess(int pid);
	void quitCurrentApp();
	void resetApp();
	public ArrayList<String> getSystemAppList();
	ArrayList<String> getThirdAppList();
	int getAppStartTotalTime(String component);
	void pullFile(String remotePath, String localPath);
	void pushFile(String localPath, String remotePath);
	void installApp(String appPath);
	boolean isInstalled(String packageName);
	void uninstallApp(String packageName);
	boolean clearAppDate(String packageName);
	void reboot();
	void fastboot();
	void startActivity(String component);
	void startWebpage(String url);
	void callPhone(int number);
	void sendKeyEvent(int keycode);
	void longPressKey(int keycode);
	void tap(int x, int y);
	void tap(double x, double y);
	void tap(Element e);
	void swipe(int startX, int startY, int endX, int endY, long ms);
	void swipe(double startX, double startY, double endX, double endY,
			long ms);
	void swipe(Element e1, Element e2, long ms);
	void swipeToLeft();
	void swipeToRight();
	void swipeToUp();
	void swipeToDown();
	void longPress(int x, int y);
	void longPress(double x, double y);
	void longPress(Element e);
	void sendText(String text);
	public void clearText(String text);
	double[] ratio(double x, double y);
	double[] ratio(double startX, double startY, double endX,
			double endY);
	void sleep(long millis);
	
	
	
}
