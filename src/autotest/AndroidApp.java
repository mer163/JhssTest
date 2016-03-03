package autotest;

import autotest.model.AppInfo;

public interface AndroidApp {

	String getBasePackage();

	String getMainActivity();

	void setMainActivity(String mainActivity);

	String getVersionName();

	void deleteFileFromWithinApk(String file);

	String getAppId();

	/**
	 * For testing only
	 */
	String getAbsolutePath();

	AppInfo toAppInfo();
}
