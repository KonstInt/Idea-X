package com.ovs_corp.goal.ui.shop;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.ovs_corp.goal.R;
import com.ovs_corp.goal.ui.dashboard.DashboardViewModel;

import java.util.ArrayList;

public class Shop extends Fragment {

    private DashboardViewModel dashboardViewModel;

    GridView gvMain;
    ArrayList<String> items = new ArrayList<>();
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        dashboardViewModel =
                ViewModelProviders.of(this).get(DashboardViewModel.class);
        View root = inflater.inflate(R.layout.fragment_shop, container, false);

        gvMain = root.findViewById(R.id.gvMain);


        items.add("термокружка 1");
        items.add("термокружка 2");
        items.add("термокружка 3");

        GirdAdapterShop adapterShop = new GirdAdapterShop(getContext(), items);
        gvMain.setAdapter(adapterShop);
        adjustGridView();
        return root;
    }

    private void adjustGridView() {
        gvMain.setNumColumns(2);
        gvMain.setColumnWidth(210);
        gvMain.setVerticalSpacing(35);
        gvMain.setHorizontalSpacing(35);
        gvMain.setStretchMode(GridView.STRETCH_COLUMN_WIDTH);

    }
}
