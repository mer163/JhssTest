package autotest.impl;

import java.io.File;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import autotest.AndroidApp;
import autotest.TestException;
import autotest.model.AppInfo;
import autotest.utils.AndroidSdk;
import autotest.utils.ShellUtils;

public class DefaultAndroidAppImpl implements AndroidApp {
	private File apkFile;
	private String mainPackage = null;
	protected String mainActivity = null;
	private String versionName = null;
	private String packageURI = null;
	

	public DefaultAndroidAppImpl(File apkFile) {
		this.apkFile = apkFile;
		this.mainPackage= getBasePackage();
		this.mainActivity = getMainActivity();
		this.versionName = getVersionName();
		this.packageURI = getAbsolutePath();
	}

	private String extractApkDetails(String regex){
		String command = null;
		File aaptPath = AndroidSdk.aapt();
		
		command = aaptPath.getAbsolutePath()+" dump badging " +apkFile;
		
		String output="";
		try {
			output = ShellUtils.getShellOut(ShellUtils.cmd(command));
		} catch (Exception e) {
			output=e.getCause().getMessage();
		}

		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(output);
		if (matcher.find()) {
			return matcher.group(1);
		}

		return null;
	}

	public String getBasePackage(){
		if (mainPackage == null) {
			try {
				mainPackage = extractApkDetails("package: name='(.*?)'");
			} catch (TestException e) {
				
				
				throw new RuntimeException("The base package name of the apk "
						+ apkFile.getName() + " cannot be extracted.");
			}

		}
		return mainPackage;
	}

	public String getMainActivity() throws TestException {
		if (mainActivity == null) {
			try {
				mainActivity = extractApkDetails("launchable-activity: name='(.*?)'");
			} catch (TestException e) {
				throw new RuntimeException("The main activity of the apk "
						+ apkFile.getName() + " cannot be extracted.");
			}
		}
		return mainActivity;
	}

	public void setMainActivity(String mainActivity) {
		this.mainActivity = mainActivity;
	}

	public void deleteFileFromWithinApk(String file)
			throws TestException {
		String command = AndroidSdk.aapt()+"remove"+apkFile.getAbsolutePath()+file;
		ShellUtils.shell(command);
		
	}

	public String getAbsolutePath() {
		return apkFile.getAbsolutePath();
	}

	public String getVersionName() throws TestException {
		if (versionName == null) {
			try {
				versionName = extractApkDetails("versionName='(.*?)'");
			} catch (TestException e) {
				throw new RuntimeException("The versionName of the apk "
						+ apkFile.getName() + " cannot be extracted.");
			}
		}
		return versionName;
	}

	public String getAppId() throws TestException {
		return getBasePackage() + ":" + getVersionName();
	}

	public AppInfo toAppInfo() {
		
		AppInfo appInfo = new AppInfo();
		appInfo.setMainActivity(mainActivity);
		appInfo.setPackageURI(packageURI);
		appInfo.setBasePackage(mainPackage);
		appInfo.setVersion(versionName);
		
		return appInfo;
	}
}

