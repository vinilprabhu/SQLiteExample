package com.svastek.sqliteexample;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    DatabaseHelper db;
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        db = new DatabaseHelper(this);
        recyclerView=findViewById(R.id.recyclerView);recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);

                LinearLayout linearLayout = new LinearLayout(MainActivity.this);
                linearLayout.setOrientation(LinearLayout.VERTICAL);

                final EditText name = new EditText(MainActivity.this);
                name.setHint("name");
                final EditText price = new EditText(MainActivity.this);
                price.setHint("price");
                price.setInputType(InputType.TYPE_CLASS_PHONE);
                linearLayout.addView(name);
                linearLayout.addView(price);

                builder.setView(linearLayout);


                builder.setTitle("Add item");
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        addToDB(name.getText().toString(),price.getText().toString());
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

        getProducts();

    }

    public void getProducts() {
        List<Product> products = db.getAllProducts();

        ProductAdapter productAdapter = new ProductAdapter(products,this);
        recyclerView.setAdapter(productAdapter);
    }

    private void addToDB(String name, String price) {

        Product product = new Product();

        product.setId(db.getLastProductId()+1);
        product.setName(name);
        product.setPrice(Integer.parseInt(price));

        db.addProduct(product);
        getProducts();
    }

}
