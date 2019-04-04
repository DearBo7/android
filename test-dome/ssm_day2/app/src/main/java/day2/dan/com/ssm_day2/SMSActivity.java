package day2.dan.com.ssm_day2;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.telephony.SmsManager;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

/**
 * 发送短信
 */
public class SMSActivity extends AppCompatActivity {

    private Boolean sendFlag = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sms_maim);

        send();
        Button button = (Button) findViewById(R.id.btn_send);
        button.setOnClickListener(new MyClickListener());
    }

    /**
     * 发送短信
     */
    private void send() {
        //读取内存权限
        String[] permissions = new String[]{Manifest.permission.SEND_SMS};
        //发送短信
        if (!checkPermission(Manifest.permission.SEND_SMS)) {
            getPermission(Manifest.permission.SEND_SMS, permissions);
        }
        //开启一个子线程
        new Thread(new Runnable() {
            @Override
            public void run() {
                //循环发送短信
                while (sendFlag) {
                    //睡眠一秒钟
                    SystemClock.sleep(1000);
                    //短信管理器
                    SmsManager smsManager = SmsManager.getDefault();
                    //发送人地址,服务器地址(为null，使用默认SMSC),发送的正文,如果发送成功，回调此广播,当对方接受成功，回调此广播
                    smsManager.sendTextMessage("10086", null, "101", null, null);
                    sendFlag = false;
                }
            }
        }).start();
    }

    class MyClickListener implements OnClickListener {

        @Override
        public void onClick(View v) {
            sendFlag = !sendFlag;
            send();
        }
    }

    /**
     * 检查某个权限是否已经获得
     */
    private boolean checkPermission(String permission) {
        //检查权限（NEED_PERMISSION）是否被授权 PackageManager.PERMISSION_GRANTED表示同意授权
        ActivityCompat.checkSelfPermission(SMSActivity.this, permission);

        return ActivityCompat.checkSelfPermission(SMSActivity.this, permission) == PackageManager.PERMISSION_GRANTED;
    }

    /**
     * 获取权限
     */
    private void getPermission(String permission, String[] permissions) {
        //申请权限
        ActivityCompat.requestPermissions(SMSActivity.this, permissions, 1);

        //用户已经拒绝过一次，再次弹出权限申请对话框需要给用户一个解释
        if (ActivityCompat.shouldShowRequestPermissionRationale(SMSActivity.this, permission)) {
            Toast.makeText(SMSActivity.this, "请开通相关权限，否则无法正常使用本应用！", Toast.LENGTH_SHORT).show();
        }
    }

}
