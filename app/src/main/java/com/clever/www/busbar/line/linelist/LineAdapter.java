package com.clever.www.busbar.line.linelist;

import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.clever.www.busbar.R;

import java.util.List;

/**
 * Created by Lzy on 17-8-4.
 */

public class LineAdapter extends RecyclerView.Adapter<LineAdapter.ViewHolder>{
    private List<LineItem> mLineItems = null;

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView  nameTv, volTv,curTv, powTv,pfTv, eleTv,plTv, volThdTv,curThdTv,temTv;

        public ViewHolder(View itemView) {
            super(itemView);

            nameTv = itemView.findViewById(R.id.name_tv);
            volTv = itemView.findViewById(R.id.vol_tv);
            curTv = itemView.findViewById(R.id.cur_tv);
            plTv = itemView.findViewById(R.id.pl_tv);
            volThdTv = itemView.findViewById(R.id.vol_thd_tv);
            curThdTv = itemView.findViewById(R.id.cur_thd_tv);

            powTv = itemView.findViewById(R.id.pow_tv);
            pfTv = itemView.findViewById(R.id.pf_tv);
            eleTv = itemView.findViewById(R.id.ele_tv);
            temTv = itemView.findViewById(R.id.tem_tv);
        }
    }

    public LineAdapter(List<LineItem> list) {
        mLineItems = list;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.line_item, parent, false);
        ViewHolder holder = new ViewHolder(view);
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
        LineItem item = mLineItems.get(position);

        String str = item.getName();
         holder.nameTv.setText(str);

        double value = item.getVol();
        if(value>=0) str = (int)value +"V";
        else  str = "---";
        holder.volTv.setText(str);
        setTextColor(holder.volTv, item.getVolALarm(), item.getVolCrALarm());

        value = item.getCur();
        if(value>=0) str = value +"A";
        else  str = "---";
        holder.curTv.setText(str);
        setTextColor(holder.curTv, item.getCurALarm(), item.getCurCrALarm());

        value = item.getPl();
        if(value>=0) str = value +"%";
        else  str = "---";
        holder.plTv.setText(str);

        value = item.getCurThd();
        if(value>=0) str = value +"%";
        else  str = "---";
        holder.curThdTv.setText(str);

        value = item.getVolThd();
        if(value>=0) str = value +"%";
        else  str = "---";
        holder.volThdTv.setText(str);

        value = item.getPow();
        if(value>=0) str = value +"KW";
        else  str = "---";
        holder.powTv.setText(str);

        value = item.getPf();
        if(value>=0) str = value +"";
        else  str = "---";
        holder.pfTv.setText(str);

        value = item.getEle();
        if(value>=0) str = value +"KWh";
        else  str = "---";
        holder.eleTv.setText(str);

        value = item.getTem();
        if(value>=0) str = value +"℃";
        else  str = "---";
        holder.temTv.setText(str);
        setTextColor(holder.temTv, item.getTemALarm(), item.getTemCrALarm());

    }

    @Override
    public int getItemCount() {
        return mLineItems.size();
    }


}
