package org.samtech.naatexamen.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import org.samtech.naatexamen.R;
import org.samtech.naatexamen.activities.BuyAirTimeActivity;
import org.samtech.naatexamen.model.database.Sales;
import org.samtech.naatexamen.utils.ImageUtils;

import java.util.ArrayList;
import java.util.List;

import static org.samtech.naatexamen.utils.ImageUtils.getDrawable;
import static org.samtech.naatexamen.utils.Keys.SALESID;

public class SalesAdapter extends RecyclerView.Adapter<SalesAdapter.ViewHolder> implements Filterable{
    private Context context;
    private List<Sales> salesList;
    private List<Sales> salesListFiltered;
    private SalesAdapterListener listener;


    public SalesAdapter(Context context, List<Sales> salesList, SalesAdapterListener listener) {
        this.context = context;
        this.listener = listener;
        this.salesList = salesList;
        this.salesListFiltered = salesList;
    }

    @NonNull
    @Override
    @SuppressLint("InflateParams")
    public SalesAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View layoutView =
                LayoutInflater
                        .from(viewGroup.getContext())
                        .inflate(R.layout.item_sales, null);

        return new ViewHolder(layoutView);
    }

    @Override
    public void onBindViewHolder(@NonNull SalesAdapter.ViewHolder viewHolder, int position) {
        final Sales sales = salesListFiltered.get(position);
        int drawablesrc = getDrawable(sales.getBcname());
        ImageUtils.setImage(context, viewHolder.salesImage, drawablesrc);
        viewHolder.conceptTextView.setText(sales.getConcept());
        viewHolder.salesImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, BuyAirTimeActivity.class);
                intent.putExtra(SALESID, sales.getId());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return salesListFiltered.size();
    }

    @Override
    public Filter getFilter() {
        return new Filter(){

            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charString = charSequence.toString();
                if(charString.isEmpty()){
                    salesListFiltered = salesList;
                }else{
                    List<Sales> filteredList = new ArrayList<>();
                    for(Sales row : salesList){
                        if(row.getBcname().toLowerCase().contains(charString.toLowerCase())){
                            filteredList.add(row);
                        }
                    }

                    salesListFiltered = filteredList;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = salesListFiltered;

                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                salesListFiltered = (ArrayList<Sales>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }

    public interface SalesAdapterListener {
        void onSalesSelected(Sales sales);
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private TextView conceptTextView;
        private ImageView salesImage;

        ViewHolder(@NonNull View itemView) {
            super(itemView);

            conceptTextView = itemView.findViewById(R.id.item_sales_bc_concept);
            salesImage = itemView.findViewById(R.id.item_sales_bc_image);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.onSalesSelected(salesListFiltered.get(getAdapterPosition()));
                }
            });
        }
    }
}
