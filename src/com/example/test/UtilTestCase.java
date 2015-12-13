package com.example.test;



import com.example.utils.MyTimeUtils;

import android.test.AndroidTestCase;
import android.util.Log;

public class UtilTestCase extends AndroidTestCase{
	private final String TAG="TEST";
	public void testms2s(){
		Log.i(TAG, MyTimeUtils.ms2s(1000*60));
	}
}
