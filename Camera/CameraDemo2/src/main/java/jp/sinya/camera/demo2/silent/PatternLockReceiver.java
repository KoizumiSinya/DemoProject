package jp.sinya.camera.demo2.silent;

import android.app.admin.DeviceAdminReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class PatternLockReceiver extends DeviceAdminReceiver {
    private static final String TAG = "PatternLockReceiver";

    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        if (ACTION_PASSWORD_SUCCEEDED.equals(action)) {
            onPasswordSuccess();
        } else if (ACTION_PASSWORD_FAILED.equals(action)) {
            onPasswordFailed(context);
        }
    }

    @Override
    public void onEnabled(Context context, Intent intent) {
        super.onEnabled(context, intent);
        Log.d(TAG, "onEnabled: ");
    }

    @Override
    public void onDisabled(Context context, Intent intent) {
        super.onDisabled(context, intent);
        Log.d(TAG, "onDisabled: ");
    }

    private void onPasswordSuccess() {
        Log.d(TAG, "onPasswordSuccess");
    }

    private void onPasswordFailed(Context context) {
        Log.d(TAG, "onPasswordFailed");
        Intent i = new Intent();
        i.setComponent(new ComponentName("jp.sinya.camera.demo2", "jp.sinya.camera.demo2.silent.CaptureService"));
        i.setAction(CaptureService.ACTION_SINGLE_PIC);
        context.startService(i);
    }
}
