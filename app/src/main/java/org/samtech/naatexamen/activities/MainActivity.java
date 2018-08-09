package org.samtech.naatexamen.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import org.samtech.naatexamen.R;
import org.samtech.naatexamen.adapters.SalesAdapter;
import org.samtech.naatexamen.model.database.DatabaseManager;
import org.samtech.naatexamen.model.database.Sales;
import org.samtech.naatexamen.utils.PreferencesUtils;

import java.util.ArrayList;
import java.util.List;

import static org.samtech.naatexamen.utils.Keys.ISFIRSTTIME;
import static org.samtech.naatexamen.utils.Keys.SPANCOUNT;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private List<Sales> salesList = new ArrayList<>();
    DatabaseManager dbManager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.act_main);

        recyclerView = findViewById(R.id.act_main_recycler_view);
        dbManager = new DatabaseManager(this);
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (PreferencesUtils.with(this).getBoolean(ISFIRSTTIME)) {
            PreferencesUtils.with(this).putBoolean(ISFIRSTTIME, false);
            inserDummyValues();
        }

        populateRecyclerView();
    }

    private void inserDummyValues() {
        Sales sales = new Sales();
        sales.setBcname("Claro");
        sales.setCellnumber("0");
        sales.setAmount("0");
        sales.setConcept("Tiempo aire");

        dbManager.insertSale(sales);

        sales = new Sales();
        sales.setBcname("Claro");
        sales.setCellnumber("0");
        sales.setAmount("0");
        sales.setConcept("Megas");
        dbManager.insertSale(sales);

        sales = new Sales();
        sales.setBcname("Claro");
        sales.setCellnumber("0");
        sales.setAmount("0");
        sales.setConcept("Megas");
        dbManager.insertSale(sales);

        sales = new Sales();
        sales.setBcname("Tuenti");
        sales.setCellnumber("0");
        sales.setAmount("0");
        sales.setConcept("Tiempo aire");
        dbManager.insertSale(sales);

        sales = new Sales();
        sales.setBcname("Tuenti");
        sales.setCellnumber("0");
        sales.setAmount("0");
        sales.setConcept("Tiempo aire");
        dbManager.insertSale(sales);

        sales = new Sales();
        sales.setBcname("Tuenti");
        sales.setCellnumber("0");
        sales.setAmount("200");
        sales.setConcept("Tiempo aire");
        dbManager.insertSale(sales);

        sales = new Sales();
        sales.setBcname("Entel");
        sales.setCellnumber("0");
        sales.setAmount("0");
        sales.setConcept("Tiempo aire");
        dbManager.insertSale(sales);

        sales = new Sales();
        sales.setBcname("Entel");
        sales.setCellnumber("0");
        sales.setAmount("0");
        sales.setConcept("Tiempo aire");
        dbManager.insertSale(sales);

        sales = new Sales();
        sales.setBcname("Entel");
        sales.setCellnumber("0");
        sales.setAmount("0");
        sales.setConcept("Tiempo aire");
        dbManager.insertSale(sales);
    }

    private void populateRecyclerView() {
        salesList.clear();
        salesList.addAll(dbManager.getAllSales());

        if (!salesList.isEmpty()) {
            recyclerView.setHasFixedSize(true);
            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(MainActivity.this);
            recyclerView.setLayoutManager(layoutManager);
            recyclerView.setLayoutManager(new GridLayoutManager(this, SPANCOUNT));
            recyclerView.setItemAnimator(new DefaultItemAnimator());

            SalesAdapter salesAdapter = new SalesAdapter(salesList, MainActivity.this);
            recyclerView.setAdapter(salesAdapter);
        }
    }

}
