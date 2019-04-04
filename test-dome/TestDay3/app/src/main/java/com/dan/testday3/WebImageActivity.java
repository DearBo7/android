package com.dan.testday3;

import android.Manifest;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.dan.util.PermissionsUtil;

import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by Dan on 2018/10/15 11:41
 */
public class WebImageActivity extends AppCompatActivity {

    private final static int IMG_ERROR = 0;
    private final static int IMG_SUCCESS = 1;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.web_image);

        final ImageView img_id = (ImageView) findViewById(R.id.img_id);
        final EditText et_url = (EditText) findViewById(R.id.et_url);
        Button btn_submit = (Button) findViewById(R.id.btn_submit);
        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String url = et_url.getText().toString();
                if (StringUtils.isBlank(url)) {
                    Toast.makeText(WebImageActivity.this, "请输入图片地址!", Toast.LENGTH_SHORT).show();
                    return;
                }
                //读取内存权限
                String[] permissions = new String[]{Manifest.permission.INTERNET};
                PermissionsUtil permissionsUtil = new PermissionsUtil(WebImageActivity.this);
                if (permissionsUtil.checkPermission(Manifest.permission.INTERNET)) {
                    permissionsUtil.getPermission(Manifest.permission.INTERNET, permissions);
                }
                //1.使用异步调用-从SDK3.0开始，google不再允许网络请求（HTTP、Socket）等相关操作直接在主线程中,在SDK3.0以下的版本，还可以继续在主线程里这样做
                DownImage downImage = new DownImage(img_id);
                downImage.execute(url);

                /*final Handler handler = new Handler() {
                    @Override
                    public void handleMessage(Message msg) {
                        super.handleMessage(msg);
                        if (msg != null) {
                            if (msg.what == IMG_SUCCESS) {
                                //接收发送的消息
                                img_id.setImageBitmap((Bitmap) msg.obj);
                            } else if (msg.what == IMG_ERROR) {
                                Toast.makeText(WebImageActivity.this, "获取图片失败!", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                };
                //2.使用多线程方式调用,开启一个子线程-使用Handler消息通信,View只能在主线程修改
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        Bitmap bitmap = getImageFromNetHttp(url);
                        Message message = new Message();
                        if (bitmap != null) {
                            message.what = IMG_SUCCESS;
                            message.obj = bitmap;
                        } else {
                            message.what = IMG_ERROR;
                        }
                        //发送消息
                        handler.sendMessage(message);
                    }
                }).start();*/
            }
        });
    }

    /**
     * AsyncTask是个抽象类，使用时需要继承这个类，然后调用execute()方法。注意继承时需要设定三个泛型Params，Progress和Result的类型，如AsyncTask<Void,Inetger,Void>：
     * Params是指调用execute()方法时传入的参数类型和doInBackgound()的参数类型
     * Progress是指更新进度时传递的参数类型，即publishProgress()和onProgressUpdate()的参数类型
     * Result是指doInBackground()的返回值类型
     * 上面的说明涉及到几个方法：
     * doInBackground() 这个方法是继承AsyncTask必须要实现的，运行于后台，耗时的操作可以在这里做
     * publishProgress() 更新进度，给onProgressUpdate()传递进度参数
     * onProgressUpdate() 在publishProgress()调用完被调用，更新进度
     */
    class DownImage extends AsyncTask<String, Integer, Bitmap> {
        private ImageView imageView;

        public DownImage(ImageView imageView) {
            this.imageView = imageView;
        }

        @Override
        protected Bitmap doInBackground(String... strings) {
            String string = strings[0];
            return getImageFromNetHttp(string);
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            if (bitmap != null) {
                imageView.setImageBitmap(bitmap);
            } else {
                Toast.makeText(WebImageActivity.this, "获取图片失败!", Toast.LENGTH_SHORT).show();
            }
        }

        private Bitmap getImageFromNet(String url) {
            Bitmap bitmap = null;
            //加载一个网络图片
            InputStream is = null;
            try {
                is = new URL(url).openStream();
            } catch (IOException e) {
                System.out.println("获取连接失败!url:" + url + ",msg:" + e.getMessage());
                e.printStackTrace();
            }
            if (is != null) {
                bitmap = BitmapFactory.decodeStream(is);
            }
            return bitmap;
        }
    }

    /**
     * 根据图片url连接取网络抓取图片-http
     *
     * @param url
     */
    private Bitmap getImageFromNetHttp(String url) {
        HttpURLConnection connection = null;
        try {
            URL imgUrl = new URL(url);
            try {
                //得到http连接
                connection = (HttpURLConnection) imgUrl.openConnection();
                //设置请求为GET
                connection.setRequestMethod("GET");
                //设置超时时间-10秒
                connection.setConnectTimeout(1000 * 10);
                //设置读取超时时间-5秒
                connection.setReadTimeout(1000 * 5);
                //开始连接
                connection.connect();

                //得到响应码
                int code = connection.getResponseCode();

                if (code == 200) {
                    //访问成功,获取服务器返回数据
                    InputStream inputStream = connection.getInputStream();

                    return BitmapFactory.decodeStream(inputStream);
                }else{
                    System.err.println("访问失败!code:" + code);
                }

            } catch (IOException e) {
                System.err.println("连接超时!url:" + url + ",msg:" + e.getMessage());
                e.printStackTrace();
            }
        } catch (MalformedURLException e) {
            System.err.println("获取连接失败!url:" + url + ",msg:" + e.getMessage());
            e.printStackTrace();
        } finally {
            if (connection != null) {
                //关闭连接
                connection.disconnect();
            }
        }
        return null;
    }

}
