package com.example.se_project;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.alibaba.fastjson.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.util.List;

import pub.devrel.easypermissions.EasyPermissions;


/**
 * The type Upload activity.
 */
@SuppressLint("NewApi")

public class UploadActivity extends AppCompatActivity implements View.OnClickListener, EasyPermissions.PermissionCallbacks {

    private EditText editTextName;
    private ProgressDialog prgDialog;

    private static final int RESULT_LOAD_IMG = 1;
    //private RequestParams params = new RequestParams();
    private String encodedString;
    private Bitmap bitmap;
    private String imgPath;


    private boolean getPermission;
    private boolean permissionGranted;
    private boolean permissionDenied;
    //要申请的权限
    private final String[] permissions = {Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            getPermission();
        }

        prgDialog= new ProgressDialog(this);
        prgDialog.setCancelable(false);



        findViewById(R.id.choose_image).setOnClickListener(this);
        findViewById(R.id.upload_image).setOnClickListener(this);

    }

    //获取权限
    private void getPermission() {
        if (EasyPermissions.hasPermissions(this, permissions)) {
            //已经打开权限
            Toast.makeText(this, "已经申请相关权限", Toast.LENGTH_SHORT).show();
        } else {
            //没有打开相关权限、申请权限
            EasyPermissions.requestPermissions(this, "需要获取您的相册、照相使用权限", 1, permissions);
        }

    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        //框架要求必须这么写
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
        getPermission=true;
    }


    //成功打开权限
    @Override
    public void onPermissionsGranted(int requestCode, @NonNull List<String> perms) {

        Toast.makeText(this, "相关权限获取成功", Toast.LENGTH_SHORT).show();
        permissionGranted=true;
    }
    //用户未同意权限
    @Override
    public void onPermissionsDenied(int requestCode, @NonNull List<String> perms) {
        Toast.makeText(this, "请同意相关权限，否则功能无法使用", Toast.LENGTH_SHORT).show();
        permissionDenied=true;
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.choose_image:
                loadImage();
                break;
            case R.id.upload_image:
                uploadImage();
                break;
            default:
                break;
        }
    }

    private void loadImage() {
        //这里就写了从相册中选择图片，相机拍照的就略过了
        Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(galleryIntent, RESULT_LOAD_IMG);
    }

    //当图片被选中的返回结果
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try {
            if (requestCode == RESULT_LOAD_IMG && resultCode == RESULT_OK && null != data) {

                Uri selectedImage = data.getData();
                String[] filePathColumn = { MediaStore.Images.Media.DATA };

                // 获取游标
                Cursor cursor = getContentResolver().query(selectedImage, filePathColumn, null, null, null);
                cursor.moveToFirst();

                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                imgPath = cursor.getString(columnIndex);
                cursor.close();
                ImageView imgView = (ImageView) findViewById(R.id.imageView);
                imgView.setImageBitmap(BitmapFactory.decodeFile(imgPath));
            } else {
                Toast.makeText(this, "You haven't picked Image",
                        Toast.LENGTH_LONG).show();
            }
        } catch (Exception e) {
            Toast.makeText(this, "Something went wrong", Toast.LENGTH_LONG).show();
        }
    }

    //开始上传图片
    private void uploadImage() {
        if (imgPath == null || imgPath.isEmpty()) {
            Toast.makeText(getApplicationContext(), "You must select image from gallery before you try to upload",
                    Toast.LENGTH_LONG).show();
        } else {
            prgDialog.setMessage("Converting Image to Binary Data");
            prgDialog.show();
            String userId = AppData.getInstance().getMe().getId();
            String recvId = AppData.getInstance().getChattingFriend().getId();
            String base64 = path2Base64(imgPath);
            if(recvId != null)
                upload(userId, recvId, base64);
        }
    }

    /**
     * Handle the upload in message in {@link JSONObject} type.
     */
    @SuppressLint("HandlerLeak")
    final Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            JSONObject result = (JSONObject)msg.obj;

            switch (result.getIntValue("status")) {
                case 200:
                    //发送成功
                    String imagMsg = result.getString("data");
                    AppData.getInstance().sendImageMsg(imagMsg);
                    Log.d("result: ", result.getString("data"));
                    break;
                case 500:
                    //发送失败
                    Log.d("result", result.getString("msg"));
                    break;
                default:
                    Log.d("result", result.getString("msg"));
                    break;
            }
            UploadActivity.this.finish();
        }
    };

    /**
     * Get information of upload and send to server.
     */
    private void upload(final String userId, final String recvId, final String imageData ) {
        final String request_url = this.getString(R.string.IM_Server_Url) + "/sendBase64";
        new Thread(new Runnable() {
            @Override
            public void run() {
                Message message = new Message();
                try {
                    JSONObject json_data = new JSONObject();
                    json_data.put("userId", userId);
                    json_data.put("recvId", recvId);
                    json_data.put("imageData", imageData);

                    message.obj = HttpRequest.jsonRequest(request_url, json_data);
                    JSONObject result = (JSONObject)message.obj;
                    Log.d("upload",result.toString());
                    handler.sendMessage(message);

                } catch (Exception e) {
                    JSONObject result_json = new JSONObject();
                    result_json.put("status",500);
                    result_json.put("msg","发送图片失败...");
                    message.obj = result_json;
                    handler.sendMessage(message);
                    e.printStackTrace();
                }
            }
        }).start();

    }


    /**
     * 通过Base32将图片转换成Base64字符串
     * @param path
     * @return
     */
    private String path2Base64(String path){
        if(TextUtils.isEmpty(path)){
            return null;
        }
        ByteArrayOutputStream bos=new ByteArrayOutputStream();
        try{
            Bitmap bit = BitmapFactory.decodeStream(new FileInputStream(path));
            bit.compress(Bitmap.CompressFormat.JPEG, 40, bos);//参数100表示不压缩
        }catch (Exception e){
            e.printStackTrace();
        }
        byte[] bytes=bos.toByteArray();
        return Base64.encodeToString(bytes, Base64.DEFAULT);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (prgDialog != null) {
            prgDialog .dismiss();
        }
    }

    /**
     * Is get permission boolean.
     *
     * @return the boolean
     */
    public boolean isGetPermission() {
        return getPermission;
    }

    /**
     * Is permission granted boolean.
     *
     * @return the boolean
     */
    public boolean isPermissionGranted() {
        return permissionGranted;
    }

    /**
     * Is permission denied boolean.
     *
     * @return the boolean
     */
    public boolean isPermissionDenied() {
        return permissionDenied;
    }

}