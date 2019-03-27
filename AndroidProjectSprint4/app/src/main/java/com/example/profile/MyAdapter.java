package com.example.profile;

import android.content.Context;
import android.media.Image;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {


    List<sosRequest> requestList;
    private Context mCtx;
    private OnItemClickListener mListener;

    public void setOnItemClickListener(OnItemClickListener listener){
        mListener = listener;
    }

    public MyAdapter(List<sosRequest> requestList, Context mCtx) {
        this.requestList = requestList;
        this.mCtx = mCtx;


    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(R.layout.list_item, null);
        return new ViewHolder(view, mListener);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        sosRequest listitem = requestList.get(position);
        holder.textViewHead.setText(listitem.getHeader());
        holder.textViewDesc.setText(listitem.getDesc());
    }


    @Override
    public int getItemCount() {

        return requestList.size();
    }

    public interface OnItemClickListener {
        void onAcceptClick(int position);

    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView textViewHead;
        TextView textViewDesc;
        public ImageView AcceptSos;

//

        public ViewHolder(View itemView, final OnItemClickListener listener) {
            super(itemView);
            textViewHead = (TextView) itemView.findViewById(R.id.textViewHead);
            textViewDesc = (TextView) itemView.findViewById(R.id.textViewDesc);
            AcceptSos = (ImageView) itemView.findViewById(R.id.buttonAccept);
            AcceptSos.bringToFront();
            AcceptSos.setClickable(true);

            AcceptSos.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(listener != null){
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION){
                            listener.onAcceptClick(position);
                        }
                    }
                }
            });
        }

    }

}
