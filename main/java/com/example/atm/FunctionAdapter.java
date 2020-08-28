package com.example.atm;

import android.annotation.SuppressLint;
import android.content.Context;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.w3c.dom.Text;

public class FunctionAdapter extends RecyclerView.Adapter<FunctionAdapter.FunctionViewHolder>{  //FunctionAdapter繼承Function.Adapter
    private final String[] functions;
    Context context;
    public FunctionAdapter(Context context){
        this.context = context;
        functions = context.getResources().getStringArray(R.array.functions);  //res-values-arrays
    }
    @NonNull
    @Override
    public FunctionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {  //2.產生ViewHolder
        View view = LayoutInflater.from(context).inflate(
                android.R.layout.simple_list_item_1, parent, false);
        return new FunctionViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FunctionViewHolder holder, int position) {    //3.顯示第幾筆資料，回傳viewholder & position
        holder.nameText.setText(functions[position]);
    }

    @Override
    public int getItemCount() {   //1.有多少筆數的資料
        return functions.length;
    }

    public class FunctionViewHolder extends RecyclerView.ViewHolder{  //FunctionViewHolder繼承RecyclerView.ViewHolder
        TextView nameText;
        @SuppressLint("ResourceType")
        public FunctionViewHolder(@NonNull View itemView) {
            super(itemView);
            nameText = itemView.findViewById(android.R.id.text1);  //use android layout
        }
    }
}
