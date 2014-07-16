package com.example.broadcast;

import android.app.Activity;
import android.content.IntentFilter;

/**
 * @Title: BroadcastTestActivity.java
 * @Package com.example.broadcast
 * @Description: TODO(ÃÌº”√Ë ˆ)
 * @author Name
 * @date 2014-7-7 …œŒÁ11:05:54
 * @version V1.0
 * @company East Money Information Co., Ltd.
 */
public class BroadcastTestActivity extends Activity{
	TestReceiver receiver;
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		IntentFilter filter = new IntentFilter();
		filter.addAction("com.example.ant_test.boardcast.foo");
		receiver = new TestReceiver();
		this.registerReceiver(receiver, filter);
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		this.unregisterReceiver(receiver);
	}
}
