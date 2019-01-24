package edu.unidhaka.cse.cse2216.keephealthy;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import java.util.ArrayList;
import java.util.List;

public class exercise extends Activity {

        List<type_of_exercise> lstBook ;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_exercise);


            lstBook = new ArrayList<>();
            lstBook.add(new type_of_exercise("Hiking","Good Exercise",R.drawable.hiking));
            lstBook.add(new type_of_exercise("Walking","Good Exercise",R.drawable.walk));
            lstBook.add(new type_of_exercise("Running","Good Exercise",R.drawable.running));
            lstBook.add(new type_of_exercise("Badminton","Good Exercise",R.drawable.badminton));
            lstBook.add(new type_of_exercise("Football","Good Exercise",R.drawable.football));
            lstBook.add(new type_of_exercise("Cycling","Good Exercise",R.drawable.cycle));
            lstBook.add(new type_of_exercise("Boxing","Good Exercise",R.drawable.boxing));
            lstBook.add(new type_of_exercise("Archery","Good Exercise",R.drawable.archery));
            lstBook.add(new type_of_exercise("Cricket","Good Exercise",R.drawable.cricket));
            lstBook.add(new type_of_exercise("Golf","Good Exercise",R.drawable.golf));
            lstBook.add(new type_of_exercise("Basketball","Good Exercise",R.drawable.basketball));
            lstBook.add(new type_of_exercise("Baseball","Good Exercise",R.drawable.baseball));
            lstBook.add(new type_of_exercise("Beach Volleyball","Good Exercise",R.drawable.beach_volleyball));



            RecyclerView myrv = (RecyclerView) findViewById(R.id.recyclerview_id);
            RecyclerView_Adapter myAdaptor = new RecyclerView_Adapter(this,lstBook);
            myrv.setLayoutManager(new GridLayoutManager(this,3));
            myrv.setAdapter(myAdaptor);


        }
    }
