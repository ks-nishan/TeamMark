package com.teammark.parkingmanagement.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;
import com.teammark.parkingmanagement.R;
import com.teammark.parkingmanagement.ViewParkingAreaActivity;
import com.teammark.parkingmanagement.model.ParkingArea;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class AdapterParkingArea extends RecyclerView.Adapter<AdapterParkingArea.MyViewHolder> {

    private ViewParkingAreaActivity viewAreaActivity;
    private List<ParkingArea> parkingAreaList;

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

        holder.numBikeSlots.setText(parkingAreaList.get(position).getCountBikeSlot());
        holder.numCarSlots.setText(parkingAreaList.get(position).getCountCarSlot());

        holder.feeBike.setText("Rs " + parkingAreaList.get(position).getFeeBike() + "/hr");
        holder.feeCar.setText("Rs " + parkingAreaList.get(position).getFeeCar() + "/hr");

        if(parkingAreaList.get(position).getEvFacility() != 0){
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

        TextView titleParkingArea, numBikeSlots, numCarSlots, txtEVFacility, txtConnector, feeBike, feeCar, iconEVFacility;

        ImageView imgParkingArea;

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
