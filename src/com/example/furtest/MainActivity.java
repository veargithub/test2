package com.example.furtest;


import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.GZIPInputStream;

import com.example.ant_test.service2.MyService;

import android.net.Uri;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MainActivity extends Activity implements OnClickListener {
	private Button btn1;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		btn1 = (Button)findViewById(R.id.btn1);
		btn1.setOnClickListener(this);
		
		InputStream is3g = this.getResources().openRawResource(R.raw.news_3g);
		InputStream isWifi = this.getResources().openRawResource(R.raw.news_wifi);
		try {
			String re3g = getResponseBodyAsString(is3g, "utf-8");
			Log.d(">>>>3g_" + re3g.length(), re3g);
			String reWifi = getResponseBodyAsString(isWifi, "utf-8");
			Log.d(">>>>wifi_" + reWifi.length(), reWifi);
			
			File file1 = new File("/data/data/" + this.getPackageName() + "/news_3g.txt");
            if (file1.exists()) {
            	Log.d(">>>>>", "file1 exists");
            	InputStream is3g_2 = new FileInputStream(file1);
            	String re3g_2 = getResponseBodyAsString(is3g_2, "utf-8");
            	Log.d(">>>>3g2_" + re3g_2.length(), re3g_2);
            }
            
            File file2 = new File("/data/data/" + this.getPackageName() + "/news_wifi.txt");
            if (file2.exists()) {
            	Log.d(">>>>>", "file2 exists");
            	InputStream isWifi_2 = new FileInputStream(file2);
            	String reWifi_2 = getResponseBodyAsString(isWifi_2, "utf-8");
            	Log.d(">>>>wifi2_" + reWifi_2.length(), reWifi_2);
            }
            
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void onClick(View v) {
//		Intent intent = new Intent(this, MyService.class);
//		startService(intent);
		Uri uri = Uri.parse("scheme://123456");
		Intent intent = new Intent(Intent.ACTION_VIEW, uri);
		startActivity(intent);
	}
	
	public static String getResponseBodyAsString(InputStream is, String charSet) throws IOException {
		GZIPInputStream gzin = new GZIPInputStream(is);
		StringBuffer sb = new StringBuffer();
		List<byte[]> arr = new ArrayList<byte[]>();
		boolean isSuccess = true;
		try {
			byte[] by = new byte[1024];
			int actlength = 0;
			while ((actlength = gzin.read(by)) != -1) {
				byte[] b = new byte[actlength];
				System.arraycopy(by, 0, b, 0, actlength);
				arr.add(b);
			}
		} catch (java.io.EOFException e) {
			e.printStackTrace();
			isSuccess = false;
			try {
				byte[] by = new byte[1024];
				int actlength = 0;
				while ((actlength = gzin.read(by)) != -1) {
					byte[] b = new byte[actlength];
					System.arraycopy(by, 0, b, 0, actlength);
					arr.add(b);
				}
				isSuccess = true;
			} catch (java.io.EOFException e2) {
				e2.printStackTrace();
				isSuccess = false;
				int actlength = 0;
				actlength = 0;
				for (byte[] b : arr) {
					actlength += b.length;
				}
				byte[] by1 = new byte[actlength];
				actlength = 0;
				for (byte[] b : arr) {
					System.arraycopy(b, 0, by1, actlength, b.length);
					actlength += b.length;
				}
				String s = new String(by1, "utf-8");
				sb.append(s);
			}
		} finally {
			if(isSuccess && sb.length()==0) {//表示有错误情况发生
				int actlength = 0;
				actlength = 0;
				for (byte[] b : arr) {
					actlength += b.length;
				}
				byte[] by1 = new byte[actlength];
				actlength = 0;
				for (byte[] b : arr) {
					System.arraycopy(b, 0, by1, actlength, b.length);
					actlength += b.length;
				}
				String s = new String(by1, charSet);
				sb.append(s);
			}
		}
		return sb.toString();
	}  

	
}
