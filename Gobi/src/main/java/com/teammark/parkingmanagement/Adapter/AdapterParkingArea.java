package com.teammark.parkingmanagement.Adapter;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.squareup.picasso.Picasso;
import com.teammark.parkingmanagement.CreateParkingAreaActivity;
import com.teammark.parkingmanagement.R;
import com.teammark.parkingmanagement.ViewParkingAreaActivity;
import com.teammark.parkingmanagement.model.ParkingArea;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class AdapterParkingArea extends RecyclerView.Adapter<AdapterParkingArea.MyViewHolder> {

    private ViewParkingAreaActivity viewAreaActivity;
    private List<ParkingArea> parkingAreaList;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();

    public  void updateParkingArea(int position){
        ParkingArea area = parkingAreaList.get(position);

        Bundle bundle = new Bundle();

        bundle.putString("uID", area.getParkingareaID());
        bundle.putString("uImg", area.getParkingAreaImg());
        bundle.putString("uTitle", area.getParkingareaTitle());
        bundle.putString("uAddress", area.getParkingareaAddress());

        bundle.putString("uTypeBike", area.getTypeBike());
        bundle.putString("uTypeCar", area.getTypeCar());

        bundle.putString("uCountBikeSlots", area.getCountBikeSlot());
        bundle.putString("uCountCarSlots", area.getCountCarSlot());

        bundle.putString("uFeeBike", area.getFeeBike());
        bundle.putString("uFeeCar", area.getFeeCar());

        bundle.putString("uEVFacility", area.getEvFacility());

        bundle.putString("uWattMin", area.getWattMin());
        bundle.putString("uWattMed", area.getWattMed());
        bundle.putString("uWattMax", area.getWattMax());

        bundle.putString("uConTypeTwo", area.getConTwo());
        bundle.putString("uConCombo", area.getConCombo());
        bundle.putString("uConCHA", area.getConCha());

        Intent intent = new Intent(viewAreaActivity, CreateParkingAreaActivity.class);
        intent.putExtras(bundle);
        viewAreaActivity.startActivity(intent);
    }

    public void deleteParkingArea(int postion){
        ParkingArea parkingArea = parkingAreaList.get(postion);

        db.collection("ParkingAreas").document(parkingArea.getParkingareaID()).delete()
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull @NotNull Task<Void> task) {
                        if(task.isSuccessful()){
                            notifyDeleted(postion);
                            Toast.makeText(viewAreaActivity, "Deleted Successfully", Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(viewAreaActivity, "Deletion Failed", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private void notifyDeleted(int position){
        parkingAreaList.remove(position);
        notifyItemRemoved(position);
        viewAreaActivity.fetchData();
    }

    public AdapterParkingArea(ViewParkingAreaActivity viewAreaActivity, List<ParkingArea> parkingAreaList){
        this.viewAreaActivity = viewAreaActivity;
        this.parkingAreaList = parkingAreaList;
    }

    @NonNull
    @NotNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(viewAreaActivity).inflate(R.layout.parking_area, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull AdapterParkingArea.MyViewHolder holder, int position) {
        holder.titleParkingArea.setText(parkingAreaList.get(position).getParkingareaTitle());

        holder.numBikeSlots.setText(parkingAreaList.get(position).getCountBikeSlot());
        holder.numCarSlots.setText(parkingAreaList.get(position).getCountCarSlot());

        holder.feeBike.setText("Rs " + parkingAreaList.get(position).getFeeBike() + "/hr");
        holder.feeCar.setText("Rs " + parkingAreaList.get(position).getFeeCar() + "/hr");

        if(parkingAreaList.get(position).getEvFacility().equals("No")){
            holder.iconEVFacility.setVisibility(View.INVISIBLE);
        }

        // txtEVFacility, txtConnector,

        Picasso.get()
                .load(parkingAreaList.get(position).getParkingAreaImg())
                .placeholder(R.drawable.parkingplaceholder)
                .fit()
                .centerCrop()
                .into(holder.imgParkingArea);
    }

    @Override
    public int getItemCount() {
        return parkingAreaList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        TextView titleParkingArea, numBikeSlots, numCarSlots, feeBike, feeCar;

        ImageView imgParkingArea, iconEVFacility;

        public MyViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);

            titleParkingArea = itemView.findViewById(R.id.titleParkingArea);
            imgParkingArea = itemView.findViewById(R.id.imgArea);

            numBikeSlots = itemView.findViewById(R.id.numBikeSlots);
            numCarSlots = itemView.findViewById(R.id.numCarSlots);

            iconEVFacility = itemView.findViewById(R.id.iconEVFacility);

            feeBike = itemView.findViewById(R.id.feeBike);
            feeCar = itemView.findViewById(R.id.feeCar);
        }
    }
}
