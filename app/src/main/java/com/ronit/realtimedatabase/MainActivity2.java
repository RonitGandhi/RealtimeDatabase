package com.ronit.realtimedatabase;

import androidx.appcompat.app.AppCompatActivity;
import android.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.widget.SearchView.OnQueryTextListener;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity2 extends AppCompatActivity {
    RecyclerView recview;

    myadapter myadapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        recview = findViewById(R.id.recview);
        recview.setLayoutManager(new LinearLayoutManager(this));

        FirebaseRecyclerOptions<Dataholder> options = new FirebaseRecyclerOptions.Builder<Dataholder>()
                .setQuery(FirebaseDatabase.getInstance().getReference().child("students"),Dataholder.class)
                .build();

        myadapter = new myadapter(options);
        recview.setAdapter(myadapter);
    }

    @Override
    protected void onStart() {
        super.onStart();
        myadapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        myadapter.stopListening();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.search_menu, menu);
        MenuItem item = menu.findItem(R.id.search);
        SearchView searchView = (SearchView) item.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                processSearch(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                processSearch(newText);
                return false;
            }
        });

        return super.onCreateOptionsMenu(menu);

    }

    private void processSearch(String query) {


        FirebaseRecyclerOptions<Dataholder> options = new FirebaseRecyclerOptions.Builder<Dataholder>()
                .setQuery(FirebaseDatabase.getInstance().getReference().child("students").orderByChild("name").startAt(query).endAt(query+"\uf8ff"),Dataholder.class)
                .build();

        myadapter = new myadapter(options);
        myadapter.startListening();
        recview.setAdapter(myadapter);


    }
}