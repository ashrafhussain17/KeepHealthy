package edu.unidhaka.cse.cse2216.keephealthy;

import android.app.Activity;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.database.*;

import java.util.ArrayList;
import java.util.List;

public class meal extends AppCompatActivity {
    EditText editTextName;
    Spinner spinnerGenre;
    Button buttonAddArtist;
    ListView listViewArtists;

    List<Food> artists;

    DatabaseReference databaseArtists;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.meal_layout);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setDisplayHomeAsUpEnabled(true);

        int id = getIntent().getIntExtra("viewid",-1);
        switch (id)
        {
            case R.id.breakfasT:  this.setTitle("Breakfast"); break;
            case R.id.luncH:  this.setTitle("Lunch"); break;
            case R.id.dinneR:  this.setTitle("Dinner"); break;
            case R.id.morningsnackS:  this.setTitle("Morning Snacks"); break;
            case R.id.eveningsnackS:  this.setTitle("Evening Snacks");break;
        }

        databaseArtists = FirebaseDatabase.getInstance().getReference("Foods");

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_NOTHING);

        editTextName = (EditText) findViewById(R.id.editTextName);
        spinnerGenre = (Spinner) findViewById(R.id.spinnerGenres);
        listViewArtists = (ListView) findViewById(R.id.listViewArtists);

        buttonAddArtist = (Button) findViewById(R.id.buttonAddArtist);


        artists = new ArrayList<>();


        buttonAddArtist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addArtist();
            }
        });

        listViewArtists.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                Food artist = artists.get(i);
                showUpdateDeleteDialog(artist.getArtistId(), artist.getArtistName());
                return true;
            }
        });
    }

    private void addArtist() {
        String name = editTextName.getText().toString().trim();
        String genre = spinnerGenre.getSelectedItem().toString();

        CalorieCalculation.mealAmount += 500;

        if (!TextUtils.isEmpty(name)) {
            String id = databaseArtists.push().getKey();

            Food artist = new Food(id, name, genre);

            databaseArtists.child(id).setValue(artist);

            editTextName.setText("");

            Toast.makeText(this, "Food added", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(this, "Please enter a food name", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        databaseArtists.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                artists.clear();

                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    Food artist = postSnapshot.getValue(Food.class);
                    artists.add(artist);
                }
                FoodList artistAdapter = new FoodList(meal.this, artists);
                listViewArtists.setAdapter(artistAdapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private boolean updateArtist(String id, String name, String genre) {
        DatabaseReference dR = FirebaseDatabase.getInstance().getReference("Foods").child(id);

        Food artist = new Food(id, name, genre);
        dR.setValue(artist);
        Toast.makeText(getApplicationContext(), "Foods Updated", Toast.LENGTH_LONG).show();
        return true;
    }

    private void showUpdateDeleteDialog(final String artistId, String artistName) {

        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.update_dialogue, null);
        dialogBuilder.setView(dialogView);

        final EditText editTextName = (EditText) dialogView.findViewById(R.id.editTextName);
        final Spinner spinnerGenre = (Spinner) dialogView.findViewById(R.id.spinnerGenres);
        final Button buttonUpdate = (Button) dialogView.findViewById(R.id.buttonUpdateArtist);
        final Button buttonDelete = (Button) dialogView.findViewById(R.id.buttonDeleteArtist);

        dialogBuilder.setTitle(artistName);
        final AlertDialog b = dialogBuilder.create();
        b.show();

        buttonUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = editTextName.getText().toString().trim();
                String genre = spinnerGenre.getSelectedItem().toString();
                if (!TextUtils.isEmpty(name)) {
                    updateArtist(artistId, name, genre);
                    b.dismiss();
                }
            }
        });

        buttonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                buttonDelete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        deleteArtist(artistId);
                        b.dismiss();
                    }
                });
            }
        });
    }

    private boolean deleteArtist(String id) {
        DatabaseReference dR = FirebaseDatabase.getInstance().getReference("Foods").child(id);

        dR.removeValue();

        Toast.makeText(getApplicationContext(), "Foods Deleted", Toast.LENGTH_LONG).show();

        return true;
    }
}