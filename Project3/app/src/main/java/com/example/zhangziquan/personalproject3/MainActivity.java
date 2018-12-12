package com.example.zhangziquan.personalproject3;

import android.Manifest;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

public class MainActivity extends AppCompatActivity {

    private myDB userdb;
    private Cursor mCursor;
    Integer id;
    boolean LRtag = true;
    boolean isAvatar = false;
    private EditText fpassword;
    private EditText spassword;
    private EditText username;
    private ImageView useravatar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
    }

    public void btnOkClick(View view) {
        if(LRtag){
            if(username.getText().toString().equals("")){
                Toast.makeText(getApplicationContext(), "Username cannot be empty.", Toast.LENGTH_SHORT).show();
            }
            else if(spassword.getText().toString().equals("")){
                Toast.makeText(getApplicationContext(), "Password cannot be empty.", Toast.LENGTH_SHORT).show();
            }
            else {
                loginUser(username.getText().toString(),spassword.getText().toString());
            }
        }
        else {
            if(username.getText().toString().equals("")){
                Toast.makeText(getApplicationContext(), "Username cannot be empty.", Toast.LENGTH_SHORT).show();
            }
            else if(spassword.getText().toString().equals("")){
                Toast.makeText(getApplicationContext(), "Password cannot be empty.", Toast.LENGTH_SHORT).show();
            }
            else if(!fpassword.getText().toString().equals(spassword.getText().toString())){
                Toast.makeText(getApplicationContext(), "Password Mismatch.", Toast.LENGTH_SHORT).show();
            }
            else{
                registerUser(username.getText().toString(),spassword.getText().toString());
            }
        }
    }

    public void btnClrClick(View view){
        fpassword.setText("");
        spassword.setText("");
        username.setText("");
        useravatar.setImageResource(R.drawable.add);
        isAvatar = false;
    }

    public void init(){

        ActivityCompat.requestPermissions(
                this,
                new String[] {
                        Manifest.permission.READ_CONTACTS
                },
                10000
        );

        fpassword = (EditText)findViewById(R.id.new_password);
        spassword = (EditText) findViewById(R.id.password);
        username = (EditText)findViewById(R.id.username);
        useravatar = (ImageView) findViewById(R.id.user_avatars);
        useravatar.setImageResource(R.drawable.add);

        RadioGroup radioGroup = (RadioGroup) findViewById(R.id.loginregister);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton radioButton = (RadioButton) findViewById(group.getCheckedRadioButtonId());
                String checkText = radioButton.getText().toString();
                if(checkText.equals("Login")){
                    LRtag = true;
                    fpassword.setText("");
                    spassword.setText("");
                    fpassword.setVisibility(View.GONE);
                    useravatar.setVisibility(View.GONE);
                    spassword.setHint("Password");
                }else if(checkText.equals("Register")){
                    LRtag = false;
                    fpassword.setText("");
                    spassword.setText("");
                    fpassword.setVisibility(View.VISIBLE);
                    useravatar.setVisibility(View.VISIBLE);
                    spassword.setHint("Confirm Password");
                }
            }
        });

        userdb = new myDB(this);
        mCursor = null;
    }

    public void loadavatar(View view) {
        // 激活系统图库，选择一张图片
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, 0);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data != null) {
            // 得到图片的全路径
            Uri uri = data.getData();
            // 通过路径加载图片
            //这里省去了图片缩放操作，如果图片过大，可能会导致内存泄漏
            //图片缩放的实现，请看：https://blog.csdn.net/reality_jie_blog/article/details/16891095
            this.useravatar.setImageURI(uri);
            Bitmap bm = null;
            try {
                bm = getBitmapFormUri(this,uri);
            } catch (IOException e) {
                e.printStackTrace();
            }
            this.useravatar.setImageBitmap(bm);
            isAvatar = true;
        }
    }

    public void loginUser(String name, String password){
        User user = userdb.selectUser(name);
        if(user==null) {
            Toast.makeText(getApplicationContext(), "Username not existed.", Toast.LENGTH_SHORT).show();
        }else{
            if(!user.getPassword().equals(password)){
                Toast.makeText(getApplicationContext(), "Invalid Password.", Toast.LENGTH_SHORT).show();
            }else {
                Intent intent = new Intent(MainActivity.this,comment_activity.class);
                id = user.get_id();
                String username = user.getUsername();
                intent.putExtra("userid",id);
                intent.putExtra("username",username);
                setResult(2,intent);
                startActivityForResult(intent,1);
            }
        }
    }

    public void registerUser(String name, String password){
        User user = userdb.selectUser(name);
        if(user != null) {
            Toast.makeText(getApplicationContext(), "Username already existed.", Toast.LENGTH_SHORT).show();
        }else {
            userdb.insertUser(name,password,img2byte());
            Toast.makeText(getApplicationContext(), "Register successfully.", Toast.LENGTH_SHORT).show();
        }
    }

    public byte[] img2byte()
    {
        ContentResolver cr = this.getContentResolver();
        Drawable medrawable = getResources().getDrawable(R.drawable.me);
        Drawable avatar = useravatar.getDrawable();
        if(!isAvatar)
        {
            avatar = medrawable;
        }
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        Bitmap bitmap = ((BitmapDrawable)avatar).getBitmap();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
        return baos.toByteArray();
    }


    public Bitmap getBitmapFormUri(Activity ac, Uri uri) throws FileNotFoundException, IOException {
        InputStream input = ac.getContentResolver().openInputStream(uri);
        BitmapFactory.Options onlyBoundsOptions = new BitmapFactory.Options();
        onlyBoundsOptions.inJustDecodeBounds = true;
        onlyBoundsOptions.inDither = true;//optional
        onlyBoundsOptions.inPreferredConfig = Bitmap.Config.ARGB_8888;//optional
        BitmapFactory.decodeStream(input, null, onlyBoundsOptions);
        input.close();
        int originalWidth = onlyBoundsOptions.outWidth;
        int originalHeight = onlyBoundsOptions.outHeight;
        if ((originalWidth == -1) || (originalHeight == -1))
            return null;
        //图片分辨率以480x800为标准
        float hh = 800f;//这里设置高度为800f
        float ww = 480f;//这里设置宽度为480f
        //缩放比。由于是固定比例缩放，只用高或者宽其中一个数据进行计算即可
        int be = 1;//be=1表示不缩放
        if (originalWidth > originalHeight && originalWidth > ww) {//如果宽度大的话根据宽度固定大小缩放
            be = (int) (originalWidth / ww);
        } else if (originalWidth < originalHeight && originalHeight > hh) {//如果高度高的话根据宽度固定大小缩放
            be = (int) (originalHeight / hh);
        }
        if (be <= 0)
            be = 1;
        //比例压缩
        BitmapFactory.Options bitmapOptions = new BitmapFactory.Options();
        bitmapOptions.inSampleSize = be;//设置缩放比例
        bitmapOptions.inDither = true;//optional
        bitmapOptions.inPreferredConfig = Bitmap.Config.ARGB_8888;//optional
        input = ac.getContentResolver().openInputStream(uri);
        Bitmap bitmap = BitmapFactory.decodeStream(input, null, bitmapOptions);
        input.close();

        return compressImage(bitmap);//再进行质量压缩
    }

    public static Bitmap compressImage(Bitmap image) {

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.JPEG, 100, baos);//质量压缩方法，这里100表示不压缩，把压缩后的数据存放到baos中
        int options = 100;
        while (baos.toByteArray().length / 1024 > 100) {  //循环判断如果压缩后图片是否大于100kb,大于继续压缩
            baos.reset();//重置baos即清空baos
            //第一个参数 ：图片格式 ，第二个参数： 图片质量，100为最高，0为最差  ，第三个参数：保存压缩后的数据的流
            image.compress(Bitmap.CompressFormat.JPEG, options, baos);//这里压缩options%，把压缩后的数据存放到baos中
            options -= 10;//每次都减少10
        }
        ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());//把压缩后的数据baos存放到ByteArrayInputStream中
        Bitmap bitmap = BitmapFactory.decodeStream(isBm, null, null);//把ByteArrayInputStream数据生成图片
        return bitmap;
    }

}
