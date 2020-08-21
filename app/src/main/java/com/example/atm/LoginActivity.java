package com.example.atm;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LoginActivity extends AppCompatActivity {

    private EditText edPassword;
    private EditText edUserid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        edUserid = findViewById(R.id.userid);
        edPassword = findViewById(R.id.password);
    }
    public void login(View view){
        String userid = edUserid.getText().toString();
        final String password = edPassword.getText().toString();
        FirebaseDatabase.getInstance().getReference("users").child(userid).child("passwword")
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        String pw = (String) snapshot.getValue();
                            if(pw.equals(password)){
                            setResult(RESULT_OK);
                            finish();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
        /*if(userid.equals("jack") && password.equals("1234")){
            setResult(RESULT_OK);
            finish();*/
        //}
    }
    public void quit(View view){

    }
}