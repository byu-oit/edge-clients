package edu.byu.mpn.client.helpers;

/**
 * Created by cwoodfie on 5/24/16.
 */
public class AndroidNotification {
	private Notification data;

	public AndroidNotification() {
	}

	public AndroidNotification(String title, String message) {
		this.data = new Notification(title, message);
	}

	public Notification getData() {
		return data;
	}

	public void setData(Notification data) {
		this.data = data;
	}

	@Override
	public String toString() {
		return "AndroidNotification{" +
		       "data=" + data +
		       '}';
	}


	private class Notification {
		private String title;
		private String message;

		public Notification() {
		}

		public Notification(String title, String message) {
			this.title = title;
			this.message = message;
		}

		public String getTitle() {
			return title;
		}

		public void setTitle(String title) {
			this.title = title;
		}

		public String getMessage() {
			return message;
		}

		public void setMessage(String message) {
			this.message = message;
		}

		@Override
		public String toString() {
			return "Notification{" +
			       "title='" + title + '\'' +
			       ", message='" + message + '\'' +
			       '}';
		}
	}
}
