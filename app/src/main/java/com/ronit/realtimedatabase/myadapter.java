package com.ronit.realtimedatabase;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.FirebaseDatabase;
import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.ViewHolder;

import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

public class myadapter extends FirebaseRecyclerAdapter<Dataholder, myadapter.myviewholder> {

    public myadapter(@NonNull  FirebaseRecyclerOptions<Dataholder> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull  final myviewholder holder, final int position, @NonNull  final Dataholder model) {

        holder.mduration.setText(model.getDuration());
        holder.mcourse.setText(model.getCourse());
        holder.mname.setText(model.getName());
        holder.edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final DialogPlus dialogPlus = DialogPlus.newDialog(holder.mcourse.getContext())
                        .setContentHolder(new ViewHolder(R.layout.dialog_content))
                        .setExpanded(true, 1100)
                        .create();

                View myview = dialogPlus.getHolderView();
                EditText nametxt = myview.findViewById(R.id.nameTxt);
                EditText coursetxt = myview.findViewById(R.id.courseTxt);
                EditText durationtxt = myview.findViewById(R.id.durationTxt);
                Button button = myview.findViewById(R.id.updateBtn);

                nametxt.setText(model.getName());
                coursetxt.setText(model.getCourse());
                durationtxt.setText(model.getDuration());
                dialogPlus.show();
                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Map<String, Object> map = new HashMap<>();
                        map.put("name", nametxt.getText().toString());
                        map.put("course", coursetxt.getText().toString());
                        map.put("duration", durationtxt.getText().toString());

                        FirebaseDatabase.getInstance().getReference().child("students")
                                .child(getRef(position).getKey())
                                .updateChildren(map)
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void unused) {
                                        dialogPlus.dismiss();
                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull @NotNull Exception e) {
                                        dialogPlus.dismiss();
                            }
                        });

                    }
                });


            }
        });


        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(holder.mname.getContext());
                builder.setTitle("Delete Panel");
                builder.setMessage("Delete .... ?");
                        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                FirebaseDatabase.getInstance().getReference().child("students")
                                        .child(getRef(position).getKey()).removeValue();

                            }
                        });
                        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {

                                    }
                        });
                        builder.show();
            }
        });
    }

    @NotNull
    @Override
    public myviewholder onCreateViewHolder(@NonNull  ViewGroup parent, int viewType) {
       View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row, parent,false);
       return new myviewholder(view);
    }

    class myviewholder extends RecyclerView.ViewHolder{

        TextView mname;
        TextView mcourse;
        TextView mduration;
        ImageView edit, delete;

        public myviewholder(@NonNull  View itemView) {
            super(itemView);
            mname = itemView.findViewById(R.id.name2);
            mcourse = itemView.findViewById(R.id.course2);
            mduration =itemView.findViewById(R.id.duration2);
            edit = itemView.findViewById(R.id.editBtn);
            delete  = itemView.findViewById(R.id.deleteBtn);
        }
    }
}
