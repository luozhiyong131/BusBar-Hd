package com.clever.www.busbar.setting.setbox;

import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.clever.www.busbar.R;
import com.clever.www.busbar.common.rate.RateEnum;
import com.clever.www.busbar.setting.setcom.SetComActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Lzy on 17-8-16.
 */

public class SetBoxAdapter extends RecyclerView.Adapter<SetBoxAdapter.ViewHolder>{
    private List<SetBoxItem> mItems;

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView idTv, nameTv;
        List<TextView> curTvs = new ArrayList<>();

        public ViewHolder(View itemView) {
            super(itemView);

            idTv = itemView.findViewById(R.id.id_tv);
            nameTv = itemView.findViewById(R.id.name_tv);

            TextView tv = itemView.findViewById(R.id.line_tv_1);
            curTvs.add(tv);

            tv = itemView.findViewById(R.id.line_tv_2);
            curTvs.add(tv);

            tv = itemView.findViewById(R.id.line_tv_3);
            curTvs.add(tv);
        }
    }

    public SetBoxAdapter(List<SetBoxItem> list) {
        mItems = list;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.set_box_item, parent,false);
        final ViewHolder holder = new ViewHolder(view);

        for(int i=0; i<holder.curTvs.size(); ++i) {
            holder.curTvs.get(i).setOnClickListener( new View.OnClickListener() {
                @Override
                public void onClick(View v){
                    int line = 0;
                    int position = holder.getAdapterPosition();

                    switch (v.getId()) {
                        case R.id.line_tv_1:  line = 0; break;
                        case R.id.line_tv_2:  line = 1; break;
                        case R.id.line_tv_3:  line = 2; break;
                    }
                    SetComActivity.actionStart(v.getContext(), position+1, line, 3);
                }
            });
        }

        return holder;
    }

    private void setTextColor(TextView tv, int alarm, int crAlarm) {
        int color = Color.BLACK;

        if(alarm > 0) {
            color = Color.RED;
        } else if(crAlarm > 0) {
            color = Color.YELLOW;
        }

        tv.setTextColor(color);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        SetBoxItem item = mItems.get(position);
        String str = item.getId() + "";
        holder.idTv.setText(str);

        str = item.getName();
        holder.nameTv.setText(str);

        for(int i=0; i<holder.curTvs.size(); ++i) {
            double value = item.getCur(i);
            if(value < 0)
                str = "---";
            else
                str =  value / RateEnum.CUR.getValue() + "A";
            holder.curTvs.get(i).setText(str);
            setTextColor(holder.curTvs.get(i), item.getAlarm(i), item.getCrAlarm(i));
        }
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }


}
