/*
 *	Copyright 2016 Brigham Young University
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package edu.byu.mpn.client.helpers;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.SerializedName;

/**
 * Created by cwoodfie on 4/27/16.
 */
public class GenericNotification {
	@SerializedName("default")
	private String message;
//	This is serialized by itself because amazon requires the GCM object to be in a string, and this gets gson to do that for us.
	@SerializedName("GCM")
	private String androidNotificationAsJson;
//	Same deal as androidNotificationAsJson
	@SerializedName("APNS")
	private String appleNotificationAsJson;

	public GenericNotification() {
	}

	public GenericNotification(String title, String message) {
		this.message = message;
		Gson gson = new GsonBuilder().setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES).create();
		this.androidNotificationAsJson = gson.toJson(new AndroidNotification(title, message));
		this.appleNotificationAsJson = gson.toJson(new AppleNotification(title, message));
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getAndroidNotificationAsJson() {
		return androidNotificationAsJson;
	}

	public void setAndroidNotificationAsJson(String androidNotificationAsJson) {
		this.androidNotificationAsJson = androidNotificationAsJson;
	}

	public String getAppleNotificationAsJson() {
		return appleNotificationAsJson;
	}

	public void setAppleNotificationAsJson(String appleNotificationAsJson) {
		this.appleNotificationAsJson = appleNotificationAsJson;
	}

	@Override
	public String toString() {
		return "GenericNotification{" +
		       "message='" + message + '\'' +
		       ", androidNotificationAsJson='" + androidNotificationAsJson + '\'' +
		       ", appleNotificationAsJson='" + appleNotificationAsJson + '\'' +
		       '}';
	}
}
