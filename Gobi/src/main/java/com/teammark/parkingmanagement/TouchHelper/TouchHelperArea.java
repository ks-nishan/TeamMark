package com.teammark.parkingmanagement.TouchHelper;

import android.graphics.Canvas;
import android.graphics.Color;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.teammark.parkingmanagement.Adapter.AdapterParkingArea;
import com.teammark.parkingmanagement.R;

import org.jetbrains.annotations.NotNull;

import it.xabaras.android.recyclerview.swipedecorator.RecyclerViewSwipeDecorator;

public class TouchHelperArea extends ItemTouchHelper.SimpleCallback {
    private AdapterParkingArea adapterParkingArea;

    public TouchHelperArea(AdapterParkingArea adapterParkingArea) {
        super(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT);
        this.adapterParkingArea = adapterParkingArea;
    }

    @Override
    public boolean onMove(@NonNull @NotNull RecyclerView recyclerView, @NonNull @NotNull RecyclerView.ViewHolder viewHolder, @NonNull @NotNull RecyclerView.ViewHolder target) {
        return false;
    }

    @Override
    public void onSwiped(@NonNull @NotNull RecyclerView.ViewHolder viewHolder, int direction) {
        final  int position = viewHolder.getAdapterPosition();

        if(direction == ItemTouchHelper.LEFT){
            adapterParkingArea.updateParkingArea(position);
            adapterParkingArea.notifyDataSetChanged();
        }else{
            adapterParkingArea.deleteParkingArea(position);
        }
    }

    @Override
    public void onChildDraw(@NonNull @NotNull Canvas c, @NonNull @NotNull RecyclerView recyclerView, @NonNull @NotNull RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {

        new RecyclerViewSwipeDecorator.Builder(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
                .addSwipeRightBackgroundColor(Color.RED)
                .addSwipeRightActionIcon(R.drawable.ic_baseline_delete_24)
                .addSwipeRightLabel("Delete")
                .setSwipeRightLabelColor(Color.WHITE)
                .addSwipeLeftBackgroundColor(Color.BLUE)
                .addSwipeLeftActionIcon(R.drawable.ic_baseline_edit_24)
                .addSwipeLeftLabel("Update")
                .setSwipeLeftLabelColor(Color.WHITE)
                .create()
                .decorate();

        super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
    }
}
