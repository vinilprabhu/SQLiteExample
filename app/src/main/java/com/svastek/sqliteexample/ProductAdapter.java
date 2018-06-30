package com.svastek.sqliteexample;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.List;

/**
 * Created by vinil on 30/6/18.
 */

class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ViewHolder>{

    List<Product> productList;
    Context context;

    DatabaseHelper db;

    public ProductAdapter(List<Product> productList, Context context) {
        this.productList = productList;
        this.context = context;
        db=new DatabaseHelper(context);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.product_list_item_layout, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final Product product = productList.get(position);

        holder.serialNo.setText(product.getId()+"");
        holder.name.setText(product.getName());
        holder.price.setText(product.getPrice()+"");



        holder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);

                builder.setTitle("Delete " +product.getName() +" ?");
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        db.deleteProduct(product.getId());
                        ((MainActivity)context).getProducts();
                    }
                });
                builder.setNeutralButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });

                final AlertDialog alert1 = builder.create();

                alert1.show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        TextView serialNo,name,price;
        LinearLayout linearLayout;

        ViewHolder(View itemView) {
            super(itemView);

            serialNo= (TextView) itemView.findViewById(R.id.serialNo);
            name= (TextView) itemView.findViewById(R.id.name);
            price= (TextView) itemView.findViewById(R.id.price);

            linearLayout= (LinearLayout) itemView.findViewById(R.id.linearLayout);

        }


    }
}
