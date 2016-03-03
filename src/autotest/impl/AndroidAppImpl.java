package autotest.impl;

import java.io.File;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import autotest.AndroidApp;
import autotest.model.AppInfo;

public class AndroidAppImpl implements AndroidApp {

	private String apkFile;
	private String packageName;
	private String activityName;
	public String getBasePackage() {
		// TODO Auto-generated method stub
		return packageName;
	}

	private String version;
	
	
	/**
	 * 
	 * @param appInfo(tld.company.app:version/ActivityClass)
	 */
	public AndroidAppImpl(String appInfo ){
		Pattern infoPattern = Pattern.compile("(.+):(.+)/(.+)");
		Matcher patternMatcher = infoPattern.matcher(appInfo);
		if (patternMatcher.matches()) {
			packageName = patternMatcher.group(1);
			version = patternMatcher.group(2);
			activityName = patternMatcher.group(3);
			
		} else {
			throw new RuntimeException(
					"Format for installed app is:  tld.company.app:version/ActivityClass");
		}
	}
	
		
	public String getMainActivity() {
		// TODO Auto-generated method stub
		return (activityName.contains(".")) ? activityName : packageName + "."
		+ activityName;
	}

	public void setMainActivity(String mainActivity) {
		this.activityName = mainActivity;

	}

	public String getVersionName() {
		// TODO Auto-generated method stub
		return version;
	}

	public void deleteFileFromWithinApk(String file) {
		// TODO Auto-generated method stub

	}

	public String getAppId() {
		// TODO Auto-generated method stub
		return packageName + ":" + version;
	}

	public String getAbsolutePath() {
		// TODO Auto-generated method stub
		return null;
	}



	public AppInfo toAppInfo() {
		AppInfo appInfo = new AppInfo();
		appInfo.setMainActivity(this.getMainActivity());
		appInfo.setPackageURI(this.getAbsolutePath());
		appInfo.setBasePackage(this.getBasePackage());
		appInfo.setVersion(this.getVersionName());
		return appInfo;
	}

}
