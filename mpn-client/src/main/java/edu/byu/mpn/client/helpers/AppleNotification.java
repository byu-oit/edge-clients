package edu.byu.mpn.client.helpers;

/**
 * Created by cwoodfie on 7/6/16.
 */
public class AppleNotification {
	private Notification aps;

	public AppleNotification() {
	}

	public AppleNotification(String title, String message) {
		this.aps = new Notification(title, message);
	}

	public Notification getAps() {
		return aps;
	}

	public void setAps(Notification aps) {
		this.aps = aps;
	}

	private class Notification {
		private Alert alert;

		public Notification() {
		}

		public Notification(String title, String message) {
			this.alert = new Alert(title, message);
		}

		public Alert getAlert() {
			return alert;
		}

		public void setAlert(Alert alert) {
			this.alert = alert;
		}

		private class Alert {

			public Alert() {
			}

			public Alert(String title, String body) {
				this.title = title;
				this.body = body;
			}

			public String getTitle() {
				return title;
			}

			public void setTitle(String title) {
				this.title = title;
			}

			public String getBody() {
				return body;
			}

			public void setBody(String body) {
				this.body = body;
			}

			private String title;
			private String body;
		}
	}
}
