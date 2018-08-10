package org.samtech.naatexamen.activities;

import android.app.SearchManager;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import org.samtech.naatexamen.R;
import org.samtech.naatexamen.adapters.SalesAdapter;
import org.samtech.naatexamen.model.database.DatabaseManager;
import org.samtech.naatexamen.model.database.Sales;
import org.samtech.naatexamen.utils.MessagesUtils;
import org.samtech.naatexamen.utils.PreferencesUtils;

import java.util.ArrayList;
import java.util.List;

import static org.samtech.naatexamen.utils.Keys.ISFIRSTTIME;
import static org.samtech.naatexamen.utils.Keys.SPANCOUNT;

public class MainActivity extends AppCompatActivity implements SalesAdapter.SalesAdapterListener {

    private List<Sales> salesList;
    private SalesAdapter salesAdapter;
    private SearchView searchView;
    DatabaseManager dbManager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.act_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        toolbar.setBackgroundColor(
                ContextCompat.getColor(MainActivity.this, R.color.colorBlueDark)
        );

        dbManager = new DatabaseManager(this);
        RecyclerView recyclerView = findViewById(R.id.act_main_recycler_view);
        salesList = new ArrayList<>();
        salesAdapter =
                new SalesAdapter
                        (MainActivity.this, salesList, MainActivity.this);
        whiteNotificationBar(recyclerView);
        recyclerView.setAdapter(salesAdapter);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(MainActivity.this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setLayoutManager(new GridLayoutManager(this, SPANCOUNT));
        recyclerView.setItemAnimator(new DefaultItemAnimator());

    }

    @Override
    protected void onResume() {
        super.onResume();

        if (PreferencesUtils.with(this).getBoolean(ISFIRSTTIME)) {
            PreferencesUtils.with(this).putBoolean(ISFIRSTTIME, false);
            inserDummyValues();
        }

        fetchSales();
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

    private void fetchSales() {
        salesList.clear();
        salesList.addAll(dbManager.getAllSales());

        if (!salesList.isEmpty()) {
            salesAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);

        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        searchView = (SearchView) menu.findItem(R.id.action_search)
                .getActionView();
        assert searchManager != null;
        searchView.setSearchableInfo(searchManager
                .getSearchableInfo(getComponentName()));
        searchView.setMaxWidth(Integer.MAX_VALUE);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                salesAdapter.getFilter().filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String query) {
                salesAdapter.getFilter().filter(query);
                return false;
            }
        });

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        return id == R.id.action_search || super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        if (!searchView.isIconified()) {
            searchView.setIconified(true);
        }
        super.onBackPressed();
    }

    private void whiteNotificationBar(View view) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            int flags = view.getSystemUiVisibility();
            flags |= View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;
            view.setSystemUiVisibility(flags);
            getWindow().setStatusBarColor(Color.WHITE);
        }
    }

    @Override
    public void onSalesSelected(Sales sales) {
        MessagesUtils.showToast(MainActivity.this, "" + sales.getBcname());
    }
}
