package edu.unidhaka.cse.cse2216.keephealthy;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class exercise extends Activity {

        List<type_of_exercise> lstBook ;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_exercise);

            lstBook = new ArrayList<>();
            lstBook.add(new type_of_exercise("The Vegitarian","Categorie Book","Description book",R.drawable.ss22));
            lstBook.add(new type_of_exercise("The Wild Robot","Categorie Book","Description book",R.drawable.ss22));
            lstBook.add(new type_of_exercise("Maria Semples","Categorie Book","Description book",R.drawable.ss22));
            lstBook.add(new type_of_exercise("The Martian","Categorie Book","Description book",R.drawable.ss22));
            lstBook.add(new type_of_exercise("He Died with...","Categorie Book","Description book",R.drawable.ss22));
            lstBook.add(new type_of_exercise("The Vegitarian","Categorie Book","Description book",R.drawable.ss22));
            lstBook.add(new type_of_exercise("The Wild Robot","Categorie Book","Description book",R.drawable.ss22));


            RecyclerView myrv = (RecyclerView) findViewById(R.id.recyclerview_id);
            RecyclerView_Adapter myAdaptor = new RecyclerView_Adapter(this,lstBook);
            myrv.setLayoutManager(new GridLayoutManager(this,3));
            myrv.setAdapter(myAdaptor);


        }
    }
