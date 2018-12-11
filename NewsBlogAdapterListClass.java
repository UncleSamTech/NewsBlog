package com.newsapp.samuel.newsblog.Model;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.newsapp.samuel.newsblog.R;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class NewsBlogAdapterListClass extends ArrayAdapter<NewsBlogObjectClass>  {

/**constructor is declared with two arguements, first arguement is the Context i.e. the activity it is to handle
and secondly the arraylist of NewsObjectClass meant to be passed to the listview
*/

    public NewsBlogAdapterListClass(Context context, ArrayList<NewsBlogObjectClass> newsBlogObjectClasses) {
        super(context, 0, newsBlogObjectClasses);
    }

    NewsViewHolder holder;

/** The getView method is implemented for inflating the listview with data
*/

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        NewsBlogObjectClass newsBlogObjectClass = getItem(position);
        View view = convertView;
        if(view==null){
view = LayoutInflater.from(getContext()).inflate(R.layout.list_item, parent, false);
holder = createView(view);
view.setTag(holder);
        }
        holder = (NewsViewHolder)view.getTag();
         holder.newsTitle.setText(newsBlogObjectClass.getNewsHeader());
        holder.newsCount.setText(newsBlogObjectClass.getNewsCount());
        holder.newsDate.setText(newsBlogObjectClass.getNewsDate());
        if(newsBlogObjectClass.getNewsImgUrl().equals("")){
             holder.newsPic.setImageResource(R.drawable.news_loggo);
        }
        else{
            Picasso.get().load(newsBlogObjectClass.getNewsImgUrl()).into(holder.newsPic);
        }
        return view;
    }

/**this class is created for creating a viewholder for holding info
*/
    public class NewsViewHolder{
    TextView newsTitle;
    ImageView newsPic;
    TextView newsDate;
    TextView newsCount;

    public NewsViewHolder(TextView newsTitle, ImageView newsPic, TextView newsDate, TextView newsCount) {
        this.newsTitle = newsTitle;
        this.newsCount = newsCount;
        this.newsDate = newsDate;
        this.newsPic = newsPic;

    }

}

public NewsViewHolder createView(View v){
    TextView newsTitle = v.findViewById(R.id.tv_news_title);
    ImageView newsPic = v.findViewById(R.id.img_news_pic);
    TextView newsDate = v.findViewById(R.id.tv_news_date);
    TextView newsCount = v.findViewById(R.id.tv_news_count);

return new NewsViewHolder(newsTitle,newsPic,newsDate,newsCount);
}
}
