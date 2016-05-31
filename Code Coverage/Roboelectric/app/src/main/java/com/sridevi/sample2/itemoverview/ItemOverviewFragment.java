package com.sridevi.sample2.itemoverview;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.sridevi.sample2.R;

/**
 * Created by sridevi on 3/27/15.
 */
public class ItemOverviewFragment extends Fragment {

    public ItemOverviewFragment(){
    }

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        //Inflate the layout for this fragment
        View view = inflater.inflate(
                R.layout.fragment_itemoverview, container, false);
        Bundle args = getArguments();
        String title = args.getString("title");
        String detail = args.getString("detail");
        TextView textView1 = (TextView) view.findViewById(R.id.text1);
        textView1.setText(Html.fromHtml("<h2>"+title+"</h2>"));
        TextView textView2 = (TextView) view.findViewById(R.id.text2);
        textView2.setText(Html.fromHtml(detail));
        return view;
    }

    public static ItemOverviewFragment newInstance() {
        ItemOverviewFragment fragment = new ItemOverviewFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

}
