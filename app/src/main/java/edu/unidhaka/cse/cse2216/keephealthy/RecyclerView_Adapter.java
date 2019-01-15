package edu.unidhaka.cse.cse2216.keephealthy;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;


public class RecyclerView_Adapter extends RecyclerView.Adapter<RecyclerView_Adapter.MyViewHolder> {

        private Context mContext ;
        private List<type_of_exercise> mData ;


        public RecyclerView_Adapter(Context mContext, List<type_of_exercise> mData) {
            this.mContext = mContext;
            this.mData = mData;
        }

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

            View view ;
            LayoutInflater mInflater = LayoutInflater.from(mContext);
            view = mInflater.inflate(R.layout.cardview_item_exercise,parent,false);
            return new MyViewHolder(view);
        }

        @Override
        public void onBindViewHolder(MyViewHolder holder, final int position) {

            holder.tv_book_title.setText(mData.get(position).getTitle());
            holder.img_book_thumbnail.setImageResource(mData.get(position).getThumbnail());
            holder.cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent intent = new Intent(mContext,list_of_exercise.class);

                    // passing data to the book activity
                    intent.putExtra("Title",mData.get(position).getTitle());
                    intent.putExtra("Description",mData.get(position).getDescription());
                    intent.putExtra("Thumbnail",mData.get(position).getThumbnail());
                    // start the activity
                    mContext.startActivity(intent);

                }
            });



        }

        @Override
        public int getItemCount() {
            return mData.size();
        }

        public static class MyViewHolder extends RecyclerView.ViewHolder {

            TextView tv_book_title;
            ImageView img_book_thumbnail;
            CardView cardView ;

            public MyViewHolder(View itemView) {
                super(itemView);

                tv_book_title = (TextView) itemView.findViewById(R.id.book_title_id) ;
                img_book_thumbnail = (ImageView) itemView.findViewById(R.id.book_img_id);
                cardView = (CardView) itemView.findViewById(R.id.cardview_id);


            }
        }


    }
