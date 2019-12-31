package com.example.yeonwookang0702.RecyclerViewAdapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.yeonwookang0702.DetailActivity;
import com.example.yeonwookang0702.R;
import com.example.yeonwookang0702.RecyclerViewItems.TownItem;

import java.util.List;

/**
 * Created by Sadruddin on 12/24/2017.
 */

public class RecyclerViewListAdapter extends RecyclerView.Adapter<RecyclerViewListAdapter.TownViewHolder>{
    private List<TownItem> horizontalTownList;
    Context context;

    public RecyclerViewListAdapter(List<TownItem> horizontalTownList, Context context){
        this.horizontalTownList= horizontalTownList;
        this.context = context;
    }

    @Override
    public TownViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //inflate the layout file
        View townView = LayoutInflater.from(parent.getContext()).inflate(R.layout.horizontal_recycler_item, parent, false);
        TownViewHolder tvh = new TownViewHolder(townView);
        return tvh;
    }

    @Override
    public void onBindViewHolder(TownViewHolder holder, final int position) {
        holder.imageView.setImageResource(horizontalTownList.get(position).getTownImage());
        holder.txtview.setText(horizontalTownList.get(position).getTownName());
        holder.txtview2.setText(horizontalTownList.get(position).getTownAddress());

        // 아이템이 클릭 되었을 때 액션 처리할 리스너
        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // 현재는 토스트 박스로 확인 하게 해둠 => 추후 디테일 액티비티로 이동하도록 수정해야함
                String productName = horizontalTownList.get(position).getTownName().toString();
                Toast.makeText(context, productName + " is selected", Toast.LENGTH_SHORT).show();

                // 디테일 액티비티로 이동시 키값을 넘겨주도록 처리
                // 예) callDetailView(getTownKey().toString())의 형태
                Intent intent = new Intent(context, DetailActivity.class);
                context.startActivity(intent);

            }
        });
    }

    @Override
    public int getItemCount() {
        return horizontalTownList.size();
    }

    public class TownViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView txtview;
        TextView txtview2;

        public TownViewHolder(View view) {
            super(view);
            imageView=view.findViewById(R.id.townImage);
            txtview=view.findViewById(R.id.townName);
            txtview2=view.findViewById(R.id.townAddress);
        }
    }
}