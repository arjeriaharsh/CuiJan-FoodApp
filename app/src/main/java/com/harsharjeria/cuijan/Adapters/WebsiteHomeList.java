package com.harsharjeria.cuijan.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.harsharjeria.cuijan.Activity.SearchResultActivity;
import com.harsharjeria.cuijan.Models.Cuisines;
import com.harsharjeria.cuijan.R;
import com.squareup.picasso.Picasso;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import maes.tech.intentanim.CustomIntent;

public class WebsiteHomeList extends RecyclerView.Adapter<WebsiteHomeList.WebsiteListsViewHolder> {

    private Cuisines[] listdata;
    private Context context;
    private static String intValue = "websiteId";

    public WebsiteHomeList(Context context, Cuisines[] listdata) {
        this.listdata = listdata;
        this.context = context;
    }

    @NonNull
    @Override
    public WebsiteListsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem= layoutInflater.inflate(R.layout.item_recycler_websites, parent, false);
        return new WebsiteListsViewHolder(listItem);
    }

    @Override
    public void onBindViewHolder(@NonNull WebsiteListsViewHolder holder, final int position) {
        Picasso.get().load(listdata[position].websiteIconLink).resize(150, 80).centerInside()
                .into(holder.imageViewLogo);

        holder.websiteLinearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent searchList = new Intent(context, SearchResultActivity.class);
                searchList.putExtra(intValue, position);
                context.startActivity(searchList);
                CustomIntent.customType(context, "left-to-right");
            }
        });
  }

    @Override
    public int getItemCount() {
        return listdata.length;
    }

    public static class WebsiteListsViewHolder extends RecyclerView.ViewHolder {
        public CardView websiteLinearLayout;
        public ImageView imageViewLogo;

        public WebsiteListsViewHolder(@NonNull View itemView) {
            super(itemView);
            this.imageViewLogo = itemView.findViewById(R.id.imageView);
            this.websiteLinearLayout = itemView.findViewById(R.id.websiteLinearLayout);
        }
    }
}
