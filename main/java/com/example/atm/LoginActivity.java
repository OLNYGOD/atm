package com.example.atm;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LoginActivity extends AppCompatActivity {

    private static final String TAG = LoginActivity.class.getSimpleName();
    private static final int REQUEST_CODE_CAMERA = 5;
    private EditText edPassword;
    private EditText edUserid;
    private CheckBox cbRemember;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        //user allow to get the 危險權限
        int CAMERA_Permission = ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA);  //取得使用者同意
        //如果使用者同意
        if(CAMERA_Permission == PackageManager.PERMISSION_GRANTED){
//            takePhoto();
        }//跳出詢問user允許對話框
        else{
            ActivityCompat.requestPermissions(this,
                   new  String[]{Manifest.permission.CAMERA}, REQUEST_CODE_CAMERA);
        }

        getSharedPreferences("atm", MODE_PRIVATE)
                .edit()
                .putInt("LEVEL", 3)
                .putString("NAME", "jack")
                .commit();
        int level =  getSharedPreferences("atm", MODE_PRIVATE)
                .getInt("LEVEL", 0);
        Log.d(TAG, "onCreate: " + level);  //logcat執行狀況
        edUserid = findViewById(R.id.userid);
        edPassword = findViewById(R.id.password);
        cbRemember = findViewById(R.id.cb_rem_userid);
        cbRemember.setChecked(
                getSharedPreferences("atm", MODE_PRIVATE)
                    .getBoolean("REMEMBER_USERIS", false));
        cbRemember.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {  //判斷使用者是否勾選
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                getSharedPreferences("atm", MODE_PRIVATE)
                        .edit()
                        .putBoolean("REMEMBER_USERIS", b)
                        .apply();
            }
        });
        String userid = getSharedPreferences("atm", 0)
                .getString("USERID", "");
        edUserid.setText(userid);
    }

    @Override
    //user對話框後輸出到此
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_CODE_CAMERA){
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED){
                takePhoto();
            }
        }
    }

    private void takePhoto() {                                              //使用者允許後開啟相機
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivity(intent);
    }

    public void login(View view){
        final String userid = edUserid.getText().toString();
        final String password = edPassword.getText().toString();
        FirebaseDatabase.getInstance().getReference("users").child(userid).child("password")  //Firebase 確認帳號後輸出密碼
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        String pw = (String) snapshot.getValue();                                //save userid
                            if(pw.equals(password)){
                                Boolean remember = getSharedPreferences("atm", MODE_PRIVATE)
                                        .getBoolean("REMEMBER_USERIS", false);
                                if(remember) {
                                    getSharedPreferences("atm", MODE_PRIVATE)                   //save userid
                                            .edit()
                                            .putString("USERID", userid)
                                            .apply();
                                    }
                            setResult(RESULT_OK);
                            finish();
                        }else {
                            new AlertDialog.Builder(LoginActivity.this)
                                    .setTitle("登入結果")
                                    .setMessage("登入失敗")
                                    .setPositiveButton("ok", null)
                                    .show();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
       /* if(userid.equals("jack") && password.equals("1234")){
            Boolean remember = getSharedPreferences("atm", MODE_PRIVATE)
                    .getBoolean("REMEMBER_USERIS", false);
            //save userid
            if(remember) {
                getSharedPreferences("atm", MODE_PRIVATE)
                        .edit()
                        .putString("USERID", userid)
                        .apply();
            }
            setResult(RESULT_OK);
            finish();*/
        //}
    }
    public void quit(View view){
        finish();
    }
}