package com.example.teammark;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myproject.Booking;
import com.example.myproject.ProfileUser;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.List;

public class Myadapter extends RecyclerView.Adapter<Myadapter.MyViewHolder> {
    private ShowActivity activity;
    private List<Model> mList;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();

    public Myadapter(ShowActivity activity, List<Model> mList) {
        this.activity = activity;
        this.mList = mList;
    }

    //pass the data to Admin_PostAD
    public void updateData(int position) {
        Model item = mList.get(position);
        Bundle bundle = new Bundle();
        bundle.putString("uId", item.getId());
        bundle.putString("uTitle", item.getTitle());
        bundle.putString("uDistrict", item.getDistrict());
        bundle.putString("uPerson", item.getPerson());
        bundle.putString("uFuel", item.getFuel());
        bundle.putString("uEngine", item.getEngin());
        bundle.putString("uAmount", item.getAmount());
        bundle.putString("uMobile", item.getMobile());
        bundle.putString("uLand", item.getLand());
        bundle.putString("uDesc", item.getDesc());
        Intent intent = new Intent(activity, Admin_PostAD.class);
        intent.putExtras(bundle);
        activity.startActivity(intent);
    }

    public void deleteData(int position) {
        Model item = mList.get(position);
        db.collection("Advertisements").document(item.getId()).delete()
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            notifyRemoved(position);
                            Toast.makeText(activity, "Data Removed Successfully", Toast.LENGTH_SHORT).show();

                        } else {
                            Toast.makeText(activity, "Error :" + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private void notifyRemoved(int position) {
        mList.remove(position);
        notifyItemRemoved(position);
        activity.showData();
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(activity).inflate(R.layout.ads, parent, false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull Myadapter.MyViewHolder holder, int position) {
        holder.title.setText(mList.get(position).getTitle());
        holder.district.setText(mList.get(position).getDistrict());
        holder.person.setText(mList.get(position).getPerson());
        holder.amount.setText(mList.get(position).getAmount());
        holder.mobile.setText(mList.get(position).getMobile());
        holder.engine.setText(mList.get(position).getEngin());
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView title, district, amount, person, mobile, engine;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            title = itemView.findViewById(R.id.title_text);
            district = itemView.findViewById(R.id.district_text);
            amount = itemView.findViewById(R.id.amount_text);
            person = itemView.findViewById(R.id.person_text);
            mobile = itemView.findViewById(R.id.mobile_text);
            engine = itemView.findViewById(R.id.engine_text);
        }
    }


}
