package com.harsharjeria.cuijan.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.harsharjeria.cuijan.Models.FoodType;
import com.harsharjeria.cuijan.R;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ClassesHomeList extends RecyclerView.Adapter<ClassesHomeList.WebsiteListsViewHolder> {

    private FoodType[] listdata;
    private Context context;

    public ClassesHomeList(Context context, FoodType[] listdata) {
        this.listdata = listdata;
        this.context = context;
    }

    @NonNull
    @Override
    public WebsiteListsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem= layoutInflater.inflate(R.layout.item_recycler_classes, parent, false);
        return new WebsiteListsViewHolder(listItem);
    }

    @Override
    public void onBindViewHolder(@NonNull WebsiteListsViewHolder holder, int position) {
        holder.imageViewLogo.setText(listdata[position].nameclass);
  }

    @Override
    public int getItemCount() {
        return listdata.length;
    }

    public static class WebsiteListsViewHolder extends RecyclerView.ViewHolder {
        public TextView imageViewLogo;

        public WebsiteListsViewHolder(@NonNull View itemView) {
            super(itemView);
            this.imageViewLogo = itemView.findViewById(R.id.textClass);
        }
    }
}
