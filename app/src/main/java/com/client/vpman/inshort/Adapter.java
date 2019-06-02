package com.client.vpman.inshort;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.bumptech.glide.signature.ObjectKey;

import java.util.ArrayList;
import java.util.List;

public class Adapter extends PagerAdapter {

    Context context;
    List<Article> articles = new ArrayList<>();

    LayoutInflater inflater;
    ProgressBar progressBar;

    public Adapter(Context context, List<Article> articles) {
        this.context = context;
        this.articles = articles;
    }

    @Override
    public int getCount() {
        return articles.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return (view == object);
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, final int position) {
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);


        View view = inflater.inflate(R.layout.data_store, null);
        ImageView imageView = view.findViewById(R.id.imageView1);
        TextView title = view.findViewById(R.id.title1);
        TextView desc=view.findViewById(R.id.description);
        Log.d("Title Text", articles.get(position).getUrlToImage());
        System.out.println("qwertyuiop");
        final TextView content = view.findViewById(R.id.content);
        Log.d("Content1", articles.get(position).getContent());

        CardView cardView=view.findViewById(R.id.cardView);

        TextView readMore=view.findViewById(R.id.readMore);

        TextView source=view.findViewById(R.id.source);


        TextView time = view.findViewById(R.id.time);

        title.setText(articles.get(position).getTitle());




        Glide.with(context).load(articles.get(position).getUrlToImage()).into(imageView);


        time.setText(" \u2022 " + Utils.DateToTimeFormat(articles.get(position).getPublishedAt()));
        String newsDetail = articles.get(position).getContent();
        if (newsDetail.length() > 260) {
            newsDetail = articles.get(position).getContent().substring(0, 260);
        }
        content.setText(newsDetail);

          source.setText(articles.get(position).getSource().getName());

          desc.setText(articles.get(position).getDesc());

          readMore.setOnClickListener(new View.OnClickListener() {
              @Override
              public void onClick(View v) {
                  Intent intent=new Intent(context,WebActivity.class);
                  intent.putExtra("url",articles.get(position).getUrl());
                  context.startActivity(intent);
              }
          });

        ViewPager vp = (ViewPager) container;
        vp.addView(view, 0);
        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {

        ViewPager vp = (ViewPager) container;
        View view = (View) object;
        vp.removeView(view);

    }


}
