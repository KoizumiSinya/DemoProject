package studio.sinya.jp.demo_httpframwork2;

import android.app.Application;
import android.content.Context;

public class MyApplication extends Application {

	public static Context mContext;

	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		mContext = this;
	}
	
}
