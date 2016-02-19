package me.tikitoo.demo.demo;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import org.simple.net.base.Request;
import org.simple.net.core.RequestQueue;
import org.simple.net.core.SimpleNet;
import org.simple.net.entity.MultipartEntity;
import org.simple.net.requests.MultipartRequest;

import java.io.ByteArrayOutputStream;
import java.io.File;

public class MainActivity extends AppCompatActivity {

    private RequestQueue mQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        setFab();
//        test();
    }

    private void setFab() {
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                test();
            }
        });
    }

    private void test() {
        // 1、构建请求队列
        mQueue = SimpleNet.newRequestQueue();

        // 2、创建请求
        MultipartRequest multipartRequest = new MultipartRequest("你的url", new   Request.RequestListener<String>() {
            @Override
            public void onComplete(int stCode, String response, String errMsg) {
                // 该方法执行在UI线程
            }
        });

        // 3、添加各种参数
        // 添加header
        multipartRequest.addHeader("header-name", "value");

        // 通过MultipartEntity来设置参数
        MultipartEntity multi = multipartRequest.getMultiPartEntity();
        // 文本参数
        multi.addStringPart("location", "模拟的地理位置");
        multi.addStringPart("type", "0");

        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), android.R.drawable.ic_delete);
        // 直接从上传Bitmap
        multi.addBinaryPart("images", bitmapToBytes(bitmap));
        // 上传文件
        multi.addFilePart("imgfile", new File("storage/emulated/0/test.jpg"));


        // 4、将请求添加到队列中
        mQueue.addRequest(multipartRequest);


        // 返回JSONObject的请求
        //        JsonRequest jsonRequest  = new JsonRequest(HttpMethod.GET, "服务器地址", new RequestListener<JSONObject>() {
        //
        //            @Override
        //            public void onComplete(int stCode, JSONObject response, String errMsg) {
        //
        //            }
        //
        //        }) ;
    }

    private byte[] bitmapToBytes(Bitmap bitmap) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        return stream.toByteArray();
    }

    @Override
    protected void onDestroy() {
        mQueue.stop();
        super.onDestroy();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
