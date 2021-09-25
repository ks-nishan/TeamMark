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
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;
import com.teammark.parkingmanagement.CreateParkingAreaActivity;
import com.teammark.parkingmanagement.DetailsParkingAreaActivity;
import com.teammark.parkingmanagement.FindParkingSlotActivity;
import com.teammark.parkingmanagement.R;
import com.teammark.parkingmanagement.model.ParkingArea;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class AdapterParkingSlot extends RecyclerView.Adapter<AdapterParkingSlot.MyViewHolder>{
    private FindParkingSlotActivity findSlotActivity;
    private List<ParkingArea> parkingSlotList;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private FirebaseStorage storage = FirebaseStorage.getInstance();

    public AdapterParkingSlot(FindParkingSlotActivity findSlotActivity, List<ParkingArea> parkingSlotList){
        this.findSlotActivity = findSlotActivity;
        this.parkingSlotList = parkingSlotList;
    }

    public Bundle detailsParkingArea(int position){
        ParkingArea slot = parkingSlotList.get(position);

        Bundle bundle = new Bundle();

        bundle.putString("dID", slot.getParkingareaID());
        bundle.putString("dImg", slot.getParkingAreaImg());
        bundle.putString("dTitle", slot.getParkingareaTitle());
        bundle.putString("dAddress", slot.getParkingareaAddress());

        bundle.putString("dTypeBike", slot.getTypeBike());
        bundle.putString("dTypeCar", slot.getTypeCar());

        bundle.putString("dCountBikeSlots", slot.getCountBikeSlot());
        bundle.putString("dCountCarSlots", slot.getCountCarSlot());

        bundle.putString("dFeeBike", slot.getFeeBike());
        bundle.putString("dFeeCar", slot.getFeeCar());

        bundle.putString("dEVFacility", slot.getEvFacility());

        bundle.putString("dWattMin", slot.getWattMin());
        bundle.putString("dWattMed", slot.getWattMed());
        bundle.putString("dWattMax", slot.getWattMax());

        bundle.putString("dConTypeTwo", slot.getConTwo());
        bundle.putString("dConCombo", slot.getConCombo());
        bundle.putString("dConCHA", slot.getConCha());

        return bundle;
    }

    @NonNull
    @NotNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(findSlotActivity).inflate(R.layout.parking_slot, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull AdapterParkingSlot.MyViewHolder holder, int position) {
        holder.titleParkingArea.setText(parkingSlotList.get(position).getParkingareaTitle());

        holder.feeBike.setText("Rs " + parkingSlotList.get(position).getFeeBike() + "/hr");
        holder.feeCar.setText("Rs " + parkingSlotList.get(position).getFeeCar() + "/hr");

        holder.locationParkingArea.setText(parkingSlotList.get(position).getParkingareaAddress());

        if(parkingSlotList.get(position).getEvFacility().equals("No")){
            holder.iconEVFacility.setVisibility(View.INVISIBLE);
        }

        Picasso.get()
                .load(parkingSlotList.get(position).getParkingAreaImg())
                .placeholder(R.drawable.parkingplaceholder)
                .fit()
                .centerCrop()
                .into(holder.imgParkingArea);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(findSlotActivity, DetailsParkingAreaActivity.class);
                intent.putExtras(detailsParkingArea(position));
                findSlotActivity.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return parkingSlotList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        TextView titleParkingArea, locationParkingArea, feeBike, feeCar;

        ImageView imgParkingArea, iconEVFacility;

        public MyViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);

            titleParkingArea = itemView.findViewById(R.id.titleParkingArea);
            imgParkingArea = itemView.findViewById(R.id.imgArea);

            locationParkingArea = itemView.findViewById(R.id.locationParkingArea);

            iconEVFacility = itemView.findViewById(R.id.iconEVFacility);

            feeBike = itemView.findViewById(R.id.feeBike);
            feeCar = itemView.findViewById(R.id.feeCar);
        }
    }
}
