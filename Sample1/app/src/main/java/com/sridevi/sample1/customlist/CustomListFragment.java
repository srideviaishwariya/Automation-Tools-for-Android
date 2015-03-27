package com.sridevi.sample1.customlist;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.sridevi.sample1.R;
import com.sridevi.sample1.customlist.model.Item;
import com.sridevi.sample1.itemoverview.ItemOverviewFragment;
import com.sridevi.sample1.utils.XMLParser;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sridevi on 3/27/15.
 */
public class CustomListFragment extends Fragment {

    private List<Item> items = new ArrayList<Item>();
    private CustomListAdapter adapter;
    private ProgressDialog pDialog;
    private String[] titleList;
    private String[] descriptionList;
    private XMLParser xmlParser;

    public CustomListFragment(){
    }

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        //Inflate the layout for this fragment
        View view = inflater.inflate(
                R.layout.fragment_customlist, container, false);
        Bundle args = getArguments();
        String contentXML = args.getString("contentXML");
        ListView listView = (ListView) view.findViewById(R.id.list);
        adapter = new CustomListAdapter(getActivity(), items);
        listView.setAdapter(adapter);
        populateList(contentXML);
        // ListView Item Click Listener
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                openItemOverviewFragment(position);
            }
        });
        return view;
    }

    private void openItemOverviewFragment(int position) {
        Fragment fragment =  ItemOverviewFragment.newInstance();
        Bundle args = new Bundle();
        args.putString("title", titleList[position]);
        args.putString("description", descriptionList[position]);
        args.putString("detail", xmlParser.getDetail(titleList[position]));
        fragment.setArguments(args);
        if (fragment != null) {
            FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
            FragmentTransaction ft = fragmentManager.beginTransaction();
            ft.replace(R.id.frame_container, fragment);
            ft.addToBackStack("custom list view");
            ft.commit();
        } else {
            // error in creating fragment
            Log.e("MainActivity", "Error in creating fragment");
        }
    }

    public static CustomListFragment newInstance() {
        CustomListFragment fragment = new CustomListFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    public void populateList(String contentXML){
        pDialog = new ProgressDialog(getActivity());
        // Showing progress dialog before making http request
        pDialog.setMessage("Loading...");
        pDialog.show();
        xmlParser = new XMLParser(getActivity().getApplicationContext(), contentXML);
        titleList = xmlParser.getTitleList();
        descriptionList = xmlParser.getDescriptionList();
        adapter.removeAll();
        for(int i =0 ; i <titleList.length; i++){
            Item item = new Item();
            item.setTitle(titleList[i]);
            item.setDescription(descriptionList[i]);
            items.add(item);
        }
        adapter.notifyDataSetChanged();
        hidePDialog();
    }

    private void hidePDialog() {
        if (pDialog != null) {
            pDialog.dismiss();
            pDialog = null;
        }
    }

}