package com.ronit.realtimedatabase;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {
    EditText e1,e2,e3,e4;
    Button add,view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        e1 = findViewById(R.id.roll);
        e2 = findViewById(R.id.name);
        e3 = findViewById(R.id.course);
        e4 = findViewById(R.id.duration);
        add = findViewById(R.id.add);
        view = findViewById(R.id.view);

        view.setOnClickListener(view -> openAct2());

        add.setOnClickListener(view -> {

            if (e1.getText().toString().isEmpty() && e2.getText().toString().isEmpty() && e3.getText().toString().isEmpty() && e4.getText().toString().isEmpty()){

                Toast.makeText(this, "Please enter all fields", Toast.LENGTH_SHORT).show();
            }
            else {
                String roll = e1.getText().toString();
                String name = e2.getText().toString();
                String course = e3.getText().toString();
                String duration = e4.getText().toString();

                Dataholder obj = new Dataholder(name, course, duration);
                FirebaseDatabase db = FirebaseDatabase.getInstance();
                DatabaseReference node = db.getReference("students");

                node.child(roll).setValue(obj);
                e1.setText("");
                e2.setText("");
                e3.setText("");
                e4.setText("");
                Toast.makeText(this, "Data inserted", Toast.LENGTH_SHORT).show();
            }

        });
    }

    private void openAct2() {
        Intent intent = new Intent(this, MainActivity2.class);
        startActivity(intent);
    }
}