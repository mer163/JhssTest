package autotest.monkey;

import java.util.Random;

import autotest.utils.ShellUtils;

import com.android.chimpchat.adb.AdbChimpDevice;
import com.android.ddmlib.IDevice;

public class MonkeyTest {

	private AdbChimpDevice mACDevice = null;
	private IDevice mDevice = null;
	float[] factors;
	public String mPackage=null;
	public String mActivity=null;
	Monkey monkey =null;
	private Rectangle mRectangle = null;
	
	
	public MonkeyTest(String Package,String mActivity){
		this.mPackage = Package;
		this.mActivity = mActivity;
		this.monkey = new Monkey("com.jhss.youguu", mACDevice, factors);
	}
	
	public void setDevice(IDevice device) {
		// TODO Auto-generated method stub
		this.mDevice = device;
	}

	
	public IDevice getDevice() {
		// TODO Auto-generated method stub
		return mDevice;
	}
	
	
	public void launchApp() {
		if (mPackage == null || mActivity == null)
			return;
		String cmd = "am start " + mPackage + "/" + mActivity;
		
		String result = ShellUtils.getShellOut(ShellUtils.shell(cmd));
		if (result.contains("does not exist") || result.contains("Error")) {
			throw new IllegalArgumentException(String.format("%s", result));
		}
		// 启动Monkey log抓取器
		startLogcat();

		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	void startLogcat(){
		
	}
	
	public void startMonkey(int counts){
		Random random = null;
		
	}
	
	
	
	public static void main(String[] args) throws InterruptedException {
		MonkeyTest m = new MonkeyTest("com.jhss.youguu", ".SplashActivity");
		System.out.println("ok");
		m.launchApp();
		Thread.sleep(3000);
		m.startMonkey(10);
	}
	
}
