package com.example.myproject;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;

import org.jetbrains.annotations.NotNull;

import java.util.List;


public class MyAdapter  extends RecyclerView.Adapter<MyAdapter.myViewHolder> {


    private showAllPast activity;
    private List<Model> modelList;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();


    public MyAdapter (showAllPast activity ,List<Model> modelList){

        this.activity = activity;
        this.modelList = modelList;


    }




    public void deleteData(int position){
        Model item = modelList.get(position);
        db.collection("Vehicle_Booking").document(item.getId()).delete()
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()){
                            notifyRemoved(position);
                            Toast.makeText(activity, "Data Deleted" , Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(activity, "Error" + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private void notifyRemoved(int position){
        modelList.remove(position);
        notifyItemRemoved(position);
        activity.showData();
    }






    @NonNull
    @NotNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(activity).inflate(R.layout.item,parent,false);


        return new myViewHolder(v);
    }


    @Override
    public void onBindViewHolder(@NonNull @NotNull MyAdapter.myViewHolder holder, int position) {

        holder.date.setText(modelList.get(position).getDate());
        holder.id.setText(modelList.get(position).getId());
        holder.total.setText(modelList.get(position).getTotal());
        holder.phone.setText(modelList.get(position).getPhone());
        holder.model.setText(modelList.get(position).getModel());
        holder.location.setText(modelList.get(position).getLocation());
    }

    @Override
    public int getItemCount() {

        return modelList.size();
    }

    public static class myViewHolder extends RecyclerView.ViewHolder{

        TextView  date,total;
        EditText id,phone,model,location;
        public myViewHolder(View interviews){
            super(interviews);

            date = interviews.findViewById(R.id.PastOrder_Date2);
            id = interviews.findViewById(R.id.PastOrder_bookingId2);
            total = interviews.findViewById(R.id.PastOrder_Total2);
            phone = interviews.findViewById(R.id.PastOrder_phone2);
            model = interviews.findViewById(R.id.PastOrder_Model2);
            location = interviews.findViewById(R.id.PastOrder_Location2);




        }
    }

}

