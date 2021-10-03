package com.app.clinikktest.adapter;
import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.recyclerview.widget.RecyclerView;
import com.app.clinikktest.R;
import com.app.clinikktest.constant.Config;
import com.app.clinikktest.model.Languages;
import java.util.ArrayList;

public class LanguagesAdapter extends RecyclerView.Adapter<LanguagesAdapter.TextItemViewHolder> {
    Context context;
    ArrayList<Languages> languagesArrayList;
    RecyclerViewDataPass recyclerViewDataPass;
    public LanguagesAdapter(Activity activity, ArrayList<Languages> languagesArrayList, RecyclerViewDataPass recyclerViewDataPass) {
        context = activity;
        this.languagesArrayList = languagesArrayList;
        this.recyclerViewDataPass=recyclerViewDataPass;
    }

    @Override
    public TextItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_language, parent, false);
        return new TextItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final TextItemViewHolder holder, final int position) {
        if(languagesArrayList!=null&&languagesArrayList.size()>0)

        {
            if(Config.LANGUAGE_SELECTED_CODE==languagesArrayList.get(position).getLanguageCode())
            {
             holder.llc.setBackgroundResource(R.drawable.bg_blue);
             holder.imgSelected.setVisibility(View.VISIBLE);
            }
            else
            {
                holder.llc.setBackgroundResource(R.drawable.bg_grey);
                holder.imgSelected.setVisibility(View.INVISIBLE);
            }
            holder.tvLanguage.setText(languagesArrayList.get(position).getLanguage());

        }

        holder.llc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                recyclerViewDataPass.pass(languagesArrayList.get(position).getLanguageCode());
                Config.LANGUAGE_SELECTED_CODE=languagesArrayList.get(position).getLanguageCode();
                notifyDataSetChanged();
            }
        });
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return languagesArrayList.size();
    }

    class TextItemViewHolder extends RecyclerView.ViewHolder {
        TextView tvLanguage;
        AppCompatImageView imgSelected;
        LinearLayoutCompat llc;
        public TextItemViewHolder(View itemView) {
            super(itemView);
            tvLanguage =  itemView.findViewById(R.id.tv_language);
            imgSelected=itemView.findViewById(R.id.img_selected);
            llc=itemView.findViewById(R.id.ll_background);
        }
    }
}