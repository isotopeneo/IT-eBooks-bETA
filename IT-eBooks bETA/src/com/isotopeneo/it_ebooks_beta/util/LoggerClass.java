package com.isotopeneo.it_ebooks_beta.util;

import android.util.Log;

public class LoggerClass {
	
	private static final String TAG = "com.isotopeneo.it_ebooks_beta";
	
	public static void log(String msg) {
		if (null != msg) {
			Log.d(TAG, msg);
		}
	}
}
