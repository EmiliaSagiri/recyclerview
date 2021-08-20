package com.example.repeatlayoutman;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.repeatlayoutman.R;
import com.example.repeatlayoutman.Product;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.io.File;
import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {
    private final List<Product> mProductList;

    static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView name;
        private final ImageView image;
        private final TextView number;
        View ljj;

        public ViewHolder(View view) {
            super(view);
            ljj=view;
            number = (TextView) view.findViewById(R.id.number_l);
            name = (TextView) view.findViewById(R.id.name_l);
            image = (ImageView) view.findViewById(R.id.image_l);
        }
    }
    public int getItemViewType(int position) {
        return position;
    }
    public MyAdapter(List<Product> productList) {
        mProductList = productList;
    }

    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cell, parent
                , false);
        final ViewHolder holder = new ViewHolder(view);
        holder.ljj.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position =holder.getAdapterPosition();
                Product product = mProductList.get(position);
                Toast.makeText(view.getContext(),"嘿嘿a"+product.getName(),Toast.LENGTH_SHORT).show();
            }
        });
        holder.image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int position =holder.getAdapterPosition();
                Product product = mProductList.get(position);
                Toast.makeText(view.getContext(),"嘿嘿"+product.getName(),Toast.LENGTH_SHORT).show();
                MainActivity.mtext.setText(product.getNumber());
            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Product product = mProductList.get(position);
        holder.name.setText(product.getName());
        holder.number.setText(product.getNumber());
        File file =new File (product.getImageId());
        Bitmap bitmap = BitmapFactory.decodeFile(file.getPath());
        holder.image.setImageBitmap(bitmap);


    }

    @Override
    public int getItemCount() {
        // TODO: Add for test
        return mProductList.size();
    }
}


