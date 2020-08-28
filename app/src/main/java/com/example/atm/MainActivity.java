package com.example.atm;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.View;

import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import static com.example.atm.R.id.item_icon;

public class MainActivity extends AppCompatActivity {

    private static final int REQUSEST_LOGON = 100;
    private static final String TAG = MainActivity.class.getSimpleName();
    boolean logon = false;
    private List<Function> functions;
    //String [] functions = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if(!logon){
            Intent intent = new Intent(this, LoginActivity.class);
            startActivityForResult(intent, REQUSEST_LOGON);
        }

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        //Recycler for mainactivity
        setupFunctions();

        //Recycler design Adapter
        RecyclerView recyclerView = findViewById(R.id.recycler);
        recyclerView.setHasFixedSize(true);                       //固定大小
        //recyclerView.setLayoutManager(new LinearLayoutManager(this));  //如何展示每一列資料
        recyclerView.setLayoutManager(new GridLayoutManager(this, 3));  //格狀表示資料
        //Adapter
        //functions = getResources().getStringArray(R.array.functions);                      //get array
        //FunctionAdapter adapter = new FunctionAdapter(this);
        IconAdapter adapter = new IconAdapter();
        recyclerView.setAdapter(adapter);
    }

    private void setupFunctions() {
        functions = new ArrayList<>();
        String[] funcs = getResources().getStringArray(R.array.functions);
        functions.add(new Function(funcs[0], R.drawable.func_transaction));
        functions.add(new Function(funcs[1], R.drawable.func_balance));
        functions.add(new Function(funcs[2], R.drawable.func_finance));
        functions.add(new Function(funcs[3], R.drawable.func_contacts));
        functions.add(new Function(funcs[4], R.drawable.func_exit));
    }

    public class IconAdapter extends RecyclerView.Adapter<IconAdapter.IconHolder> {

        @NonNull
        @Override
        public IconHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {  //回傳ViewHolder資料
            View view = getLayoutInflater().inflate(R.layout.item_icon, parent, false);
            return new IconHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull IconHolder holder, int position) {   //回傳特定資料資料，如位置名稱圖片
            final Function function = functions.get(position);
            holder.nameText.setText(function.getName());                           //Function class功能
            holder.iconImage.setImageResource(function.getIcon());
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //回報MainActivity誰按了某一個功能項目
                    itemClick(function);
                }
            });
        }

        @Override
        public int getItemCount() {   //自動呼叫，共有幾筆資料
            return functions.size();
        }

        public class IconHolder extends RecyclerView.ViewHolder{
            ImageView iconImage;
            TextView nameText;
            public IconHolder(@NonNull View itemView) {
                super(itemView);
                iconImage = itemView.findViewById(item_icon);
                nameText = itemView.findViewById(R.id.item_name);
            }
        }
    }

    private void itemClick(Function function) {             //功能實作區
        Log.d(TAG, "itemClick: " + function.getName());
        switch (function.getIcon()){
            case R.drawable.func_transaction:
                break;
            case R.drawable.func_balance:
                break;
            case R.drawable.func_finance:
                break;
            case R.drawable.func_contacts:
                Intent contacts = new Intent(this, ContactActivity.class);
                startActivity(contacts);
                break;
            case R.drawable.func_exit:
                finish();
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == REQUSEST_LOGON){
            if(resultCode != RESULT_OK){
                finish();
            }
        }
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