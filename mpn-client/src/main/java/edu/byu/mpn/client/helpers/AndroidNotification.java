package edu.byu.mpn.client.helpers;

/**
 * Created by cwoodfie on 5/24/16.
 */
public class AndroidNotification {
	private Notification notification;
	private Data data;
	private final String priority = "high";
	
	public AndroidNotification() {
	}
	
	public AndroidNotification(String title, String message) {
		this.notification = new Notification(title);
		this.data = new Data(title, message);
	}
	
	public Notification getNotification() {
		return notification;
	}
	
	public void setNotification(Notification notification) {
		this.notification = notification;
	}
	
	public Data getData() {
		return data;
	}
	
	public void setData(Data data) {
		this.data = data;
	}
	
	@Override
	public String toString() {
		return "AndroidNotification{" +
			   "notification=" + notification +
			   '}';
	}
	
	
	private class Notification {
		private String title;
		private final String sound = "default";
		private final String clickAction = "edu.byu.suite.NOTIFICATION";
		
		public Notification() {
		}
		
		public Notification(String title) {
			this.title = title;
		}
		
		public String getTitle() {
			return title;
		}
		
		public void setTitle(String title) {
			this.title = title;
		}
		
		@Override
		public String toString() {
			return "Notification{" +
				   "title='" + title + '\'' +
				   ", sound='" + sound + '\'' +
				   '}';
		}
	}
	
	private class Data {
		private String title;
		private String message;
		
		public Data(String title, String message) {
			this.title = title;
			this.message = message;
		}
	}
}
