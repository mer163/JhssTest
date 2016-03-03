package autotest;

import autotest.element.Element;

public interface Solo {
	
	void sendKeyEvent(int keycode);
	void longPressKey(int keycode);
	void clickOn(int x, int y);
	void clickOn(double x, double y);
	void ClickOnElement(Element e);
	void swipe(int startX, int startY, int endX, int endY, long ms) ;
	void swipe(double startX, double startY, double endX, double endY,
			long ms) ;
	void swipeElementTo(Element e1, Element e2, long ms);
	void swipeToLeft();
	void swipeToRight();
	void swipeToUp();
	void swipeToDown();
	void longPress(int x, int y) ;
	void longPress(double x, double y);
	void longPress(Element e);
	void sendText(String text);
	void clearText(String text);
	double[] ratio(double x, double y);
	double[] ratio(double startX, double startY, double endX,
			double endY);
	void sleep(long millis);
	
}
