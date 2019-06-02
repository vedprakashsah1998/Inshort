package com.client.vpman.inshort;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MainFragment extends Fragment {
    View view;
    String TAG;

    VerticalViewPageAdapter verticalViewPageAdapter;
    private int pos = 0;
    Adapter adapter;
    int check = 0;
    String data;
    public static List<Article> articles = new ArrayList<>();


    List<String> finalwall = new ArrayList<>();
    List<String> slides = new ArrayList<>();

    private String JSonUrl = "https://newsapi.org/v2/top-headlines?country=in&apiKey=5a75a7095cce4aa9bad4699b53a0eabf&pageSize=100";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.maincontent, container, false);
        //  Adapter adapter=new Adapter(getActivity(),articles);

        verticalViewPageAdapter = view.findViewById(R.id.viewPager);
        adapter = new Adapter(getActivity(), articles);
        verticalViewPageAdapter.setAdapter(adapter);
        verticalViewPageAdapter.setCurrentItem(pos);
        LoadNews();
        verticalViewPageAdapter.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                pos = position;
            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        // verticalViewPageAdapter.setCurrentItem(pos);
        return view;
    }

    public static MainFragment newInstance(String text) {
        MainFragment f = new MainFragment();
        Bundle b = new Bundle();
        b.putString("msg", text);
        f.setArguments(b);
        return f;
    }


    public void LoadNews() {
        verticalViewPageAdapter = view.findViewById(R.id.viewPager);
        articles = new ArrayList<>();
        verticalViewPageAdapter.setAdapter(adapter);
        adapter = new Adapter(getActivity(), articles);
        // final Article model = articles.get(pos);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, JSonUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {

                    JSONObject obj = new JSONObject(response);
                    JSONArray newsArray = obj.getJSONArray("articles");


                    for (int i = 0; i < newsArray.length(); i++) {
                        JSONObject wallObj = newsArray.getJSONObject(i);


                        Article article = new Article();
                        article.setTitle(wallObj.getString("title"));
                        article.setUrlToImage(wallObj.getString("urlToImage"));
                        article.setPublishedAt(wallObj.getString("publishedAt"));
                        article.setContent(wallObj.getString("content"));
                        article.setDesc(wallObj.getString("description"));
                        article.setUrl(wallObj.getString("url"));
                        JSONObject source=wallObj.getJSONObject("source");
                        Source source1=new Source();
                        source1.setName(source.getString("name"));
                        article.setSource(source1);

                        articles.add(article);
                        adapter = new Adapter(getActivity(), articles);
                        Collections.shuffle(articles);
                        verticalViewPageAdapter.setAdapter(adapter);
                        // adapter.notifyDataSetChanged();
                        Log.d("Hello bhai", article.getUrlToImage());


                    }


                } catch (JSONException e) {
                    e.getStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                NetworkResponse response = error.networkResponse;
                if (error instanceof ServerError && response != null) {
                    try {
                        String res = new String(response.data,
                                HttpHeaderParser.parseCharset(response.headers, "utf-8"));
                        // Now you can use any deserializer to make sense of data
                        JSONObject obj = new JSONObject(res);
                        Log.d(TAG, "Error: " + error
                                + "\nStatus Code " + error.networkResponse.statusCode
                                + "\nResponse Data " + error.networkResponse.data
                                + "\nCause " + error.getCause()
                                + "\nmessage" + error.getMessage());
                    } catch (UnsupportedEncodingException e1) {
                        // Couldn't properly decode data to string
                        e1.printStackTrace();
                    } catch (JSONException e2) {
                        // returned data is not JSONObject?
                        e2.printStackTrace();
                    }
                }

                Log.e("Nhi mila ", String.valueOf(error));
            }
        });


        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        requestQueue.add(stringRequest);

    }

}
