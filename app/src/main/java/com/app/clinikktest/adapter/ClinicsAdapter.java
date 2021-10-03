package com.app.clinikktest.adapter;
import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import com.app.clinikktest.R;
import com.app.clinikktest.model.Clinics;
import com.bumptech.glide.Glide;
import java.util.ArrayList;
public class ClinicsAdapter extends RecyclerView.Adapter<ClinicsAdapter.TextItemViewHolder> {
    Context context;
    ArrayList<Clinics> clinicsArrayList;

    public ClinicsAdapter(Activity activity, ArrayList<Clinics> clinicsArrayList) {
        context = activity;
        this.clinicsArrayList = clinicsArrayList;
    }
    @Override
    public TextItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_clinic, parent, false);
        return new TextItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final TextItemViewHolder holder, final int position) {
        if(clinicsArrayList!=null&&clinicsArrayList.size()>0)
        {
            if(position==0){
                holder.clinnikImage.setVisibility(View.VISIBLE);
            }
            holder.clinnikName.setText(clinicsArrayList.get(position).getName());
            holder.clinikkAddress.setText(clinicsArrayList.get(position).getAddress());
            if(clinicsArrayList.get(position).getImage()!="null")
            {
                Glide
                        .with(context)
                        .load(clinicsArrayList.get(position).getImage())
                        .placeholder(R.drawable.ic_loading)
                        .into(holder.clinnikImage);
            }

            holder.clinikdirectionsImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                   getDirections();
                }
            });

        }


    }

    @Override
    public long getItemId(int position) {

        return position;
    }

    @Override
    public int getItemCount() {

        return clinicsArrayList.size();
    }

    protected void getDirections() {
       //Hardcoded for now
        Intent intent = new Intent(Intent.ACTION_VIEW,
                Uri.parse("http://maps.google.com/maps?f=d&daddr="+12.9784+","+77.6408));
        intent.setComponent(new ComponentName("com.google.android.apps.maps",
                "com.google.android.maps.MapsActivity"));
        context.startActivity(intent);
    }

    class TextItemViewHolder extends RecyclerView.ViewHolder {
        ImageView clinnikImage,clinikdirectionsImage;
        TextView clinnikName,clinikkAddress;
        public TextItemViewHolder(View itemView) {
            super(itemView);
            clinnikImage =  itemView.findViewById(R.id.img_clinikk);
            clinnikName=itemView.findViewById(R.id.tv_clinikk);
            clinikkAddress=itemView.findViewById(R.id.tv_address);
            clinikdirectionsImage=itemView.findViewById(R.id.img_get_directions);

        }
    }

}