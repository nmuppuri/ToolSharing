package com.example.toolsharing.Student;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.toolsharing.PojoClasses.MessageDetails_Pojo;
import com.example.toolsharing.R;

import java.util.ArrayList;


public class MessageDetailsRecylerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

        private static final int TYPE_ONE = 1;
        private static final int TYPE_TWO = 2;

        private ArrayList<MessageDetails_Pojo> itemList;
        String tsid;

        // Constructor of the class
        public MessageDetailsRecylerAdapter(ArrayList<MessageDetails_Pojo> itemList, Context applicationContext, String tsid) {
            this.itemList = itemList;
            this.tsid = tsid;
        }

        // get the size of the list
        @Override
        public int getItemCount() {
            return itemList == null ? 0 : itemList.size();
        }

        // determine which layout to use for the row
        @Override
        public int getItemViewType(int position) {
            MessageDetails_Pojo item = itemList.get(position);
            String tsid1 = itemList.get(position).getMDToStudentId().toString();
            if (tsid.equalsIgnoreCase(tsid1)/*position % 2 == 0*/) {     // Even position
                return TYPE_ONE;
            } else {                   // Odd position
                return TYPE_TWO;
            }
        }


        // specify the row layout file and click for each row
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            if (viewType == TYPE_ONE) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_their_message, parent, false);
                return new ViewHolderOne(view);
            } else if (viewType == TYPE_TWO) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_my_message, parent, false);
                return new ViewHolderTwo(view);
            } else {
                throw new RuntimeException("The type has to be ONE or TWO");
            }
        }

        // load data in each row element
        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int listPosition) {
            switch (holder.getItemViewType()) {
                case TYPE_ONE:
                    holder.setIsRecyclable(false);
                    initLayoutOne((ViewHolderOne) holder, listPosition);
                    break;
                case TYPE_TWO:
                    holder.setIsRecyclable(false);
                    initLayoutTwo((ViewHolderTwo) holder, listPosition);
                    break;
                default:
                    break;
            }
        }

        private void initLayoutOne(ViewHolderOne holder, int pos) {
            holder.recvd_sid.setText(String.valueOf(itemList.get(pos).getMDFromStudentId()));
            holder.recvd_msg.setText(itemList.get(pos).getMDMessage());
        }

        private void initLayoutTwo(ViewHolderTwo holder, int pos) {
            holder.sent_msg.setText(itemList.get(pos).getMDMessage());
        }


        // Static inner class to initialize the views of rows
        static class ViewHolderOne extends RecyclerView.ViewHolder {
            public TextView recvd_sid, recvd_msg;

            public ViewHolderOne(View itemView) {
                super(itemView);
                recvd_sid = (TextView) itemView.findViewById(R.id.recvd_sid);
                recvd_msg = (TextView) itemView.findViewById(R.id.recvd_msg);
            }
        }

        static class ViewHolderTwo extends RecyclerView.ViewHolder {
            public TextView sent_msg;

            public ViewHolderTwo(View itemView) {
                super(itemView);
                sent_msg = (TextView) itemView.findViewById(R.id.sent_msg);
            }
        }
    }
