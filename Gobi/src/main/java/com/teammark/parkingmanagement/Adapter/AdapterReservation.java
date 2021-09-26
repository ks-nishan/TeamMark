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
import com.teammark.parkingmanagement.CreateParkingReservationActivity;
import com.teammark.parkingmanagement.R;
import com.teammark.parkingmanagement.UpdateParkingReservationActivity;
import com.teammark.parkingmanagement.ViewQRCodeActivity;
import com.teammark.parkingmanagement.ViewReservationActivity;
import com.teammark.parkingmanagement.model.ParkingReservation;
import com.teammark.parkingmanagement.util.QRGenerator;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class AdapterReservation extends RecyclerView.Adapter<AdapterReservation.MyViewHolder>{

    private ViewReservationActivity viewReservationActivity;
    private List<ParkingReservation> parkingReservationList;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();

    public  void updateReservation(int position){
        ParkingReservation reservation = parkingReservationList.get(position);

        Bundle bundle = new Bundle();

        bundle.putString("uReservationID", reservation.getReservationID());
        bundle.putString("uSlotID", reservation.getSlotID());
        bundle.putString("uSlotTitle", reservation.getSlotTitle());
        bundle.putString("uVehicleNumber", reservation.getVehicleNumber());
        bundle.putString("uCustomerName", reservation.getCustomerName());
        bundle.putString("uLicenceNumber", reservation.getLicenceNumber());
        bundle.putString("uContactNumber", reservation.getContactNumber());
        bundle.putString("uArrivalDate", reservation.getArrivalDate());
        bundle.putString("uArrivalTime", reservation.getArrivalTime());
        bundle.putString("uEvcRequire", reservation.getEvcRequire());

        Intent intent = new Intent(viewReservationActivity, UpdateParkingReservationActivity.class);
        intent.putExtras(bundle);
        viewReservationActivity.startActivity(intent);
    }

    public void deleteReservation(int postion){
        ParkingReservation reservation = parkingReservationList.get(postion);

        db.collection("ParkingReservations").document(reservation.getReservationID()).delete()
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull @NotNull Task<Void> task) {
                        if(task.isSuccessful()){
                            notifyDeleted(postion);
                            Toast.makeText(viewReservationActivity, "Deleted Successfully", Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(viewReservationActivity, "Deletion Failed", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private void notifyDeleted(int position){
        parkingReservationList.remove(position);
        notifyItemRemoved(position);
        viewReservationActivity.fetchData();
    }


    public AdapterReservation(ViewReservationActivity viewReservationActivity, List<ParkingReservation> parkingReservationList) {
        this.viewReservationActivity = viewReservationActivity;
        this.parkingReservationList = parkingReservationList;
    }

    @NonNull
    @NotNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(viewReservationActivity).inflate(R.layout.parking_reservation, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull AdapterReservation.MyViewHolder holder, int position) {
        holder.txtparkingSlot.setText(parkingReservationList.get(position).getSlotTitle());
        holder.txtarrivalDate.setText(parkingReservationList.get(position).getArrivalDate());
        holder.txtarrivalTime.setText(parkingReservationList.get(position).getArrivalTime());

        String reservationID = parkingReservationList.get(position).getReservationID();

        QRGenerator myQR = new QRGenerator();

        myQR.generateQR(reservationID, holder.imgQR);

        holder.imgQR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(viewReservationActivity, ViewQRCodeActivity.class);
                intent.putExtra("reservationID", reservationID);
                viewReservationActivity.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return parkingReservationList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        TextView txtparkingSlot, txtarrivalDate, txtarrivalTime;

        ImageView imgQR;

        public MyViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);

            txtparkingSlot = itemView.findViewById(R.id.txtparkingSlot);
            txtarrivalDate = itemView.findViewById(R.id.txtarrivalDate);
            txtarrivalTime = itemView.findViewById(R.id.txtarrivalTime);

            imgQR = itemView.findViewById(R.id.imgQR);

        }
    }
}
