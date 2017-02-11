package com.example.lakshminarayanabr.ilovezappos;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.media.Image;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;


import java.util.ArrayList;
import java.util.List;



/**
 * Created by lakshminarayanabr on 2/6/17.
 */

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.BindingHolder> {
    RecyclerView rcView;

    private List<Product> products;
    private int lastPosition = -1;
    private Context context;
    boolean isCart=false;

    public ProductAdapter(ArrayList<Product> productArrayList, Context context,RecyclerView rcView) {
        products=productArrayList;
        this.context = context;
        this.rcView=rcView;
    }

    public static class BindingHolder extends RecyclerView.ViewHolder {
        private ViewDataBinding binding;
        public ImageView imageView;
        public ImageView imgShare;
        public Button button;

        public BindingHolder(View v) {
            super(v);
            binding = DataBindingUtil.bind(v);
            imageView=(ImageView)v.findViewById(R.id.imgCart);
            button=(Button)v.findViewById(R.id.btnMore);
            imgShare=(ImageView)v.findViewById(R.id.imgShare);
        }

        public ViewDataBinding getBinding() {
            return binding;
        }
    }

    @Override
    public ProductAdapter.BindingHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.product_item, parent, false);
        BindingHolder holder = new BindingHolder(v);

        return holder;
    }

    @Override
    public void onBindViewHolder(final ProductAdapter.BindingHolder holder, int position) {
        final Product aProduct = products.get(position);
        holder.getBinding().setVariable(BR.product, aProduct);
        holder.getBinding().executePendingBindings();
        holder.imageView.setId(position);
        holder.button.setId(position);
        holder.imgShare.setId(position);
        holder.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int pos=v.getId();
                Product p=products.get(pos);
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(p.getProductURL()));
                context.startActivity(intent);
            }
        });

        final SearchResultsActivity searchResultsActivity=(SearchResultsActivity)context;
        final List<Product> cartItems= searchResultsActivity.getCartItems(context);
        if(cartItems.contains(aProduct))
        {
            holder.imageView.setImageResource(R.mipmap.shopcartexclude);
        }
        else
        {
            holder.imageView.setImageResource(R.mipmap.shopcartadd);
        }


        holder.imgShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int pos=v.getId();
                Product p=products.get(pos);

                Intent i = new Intent(Intent.ACTION_SEND);
                i.setType("text/plain");
                i.putExtra(Intent.EXTRA_SUBJECT, "Sharing URL");
                i.putExtra(Intent.EXTRA_TEXT, p.productUrl);
                context.startActivity(Intent.createChooser(i, "Share URL"));

            }
        });
        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ImageView img=(ImageView)v;
               int pos= img.getId();
               Product product= products.get(pos);
                if(cartItems.contains(product))
                {
                    img.setImageResource(R.mipmap.shopcartadd);
                    cartItems.remove(product);
                    searchResultsActivity.saveCartItems(cartItems,context);

                    {
                        products.remove(pos);
                        if(products.size()==0)
                        {
                            searchResultsActivity.textView.setVisibility(View.VISIBLE);
                            rcView.setVisibility(View.INVISIBLE);
                        }
                        else
                        {
                            searchResultsActivity.textView.setVisibility(View.INVISIBLE);
                            rcView.setVisibility(View.VISIBLE);
                        }
                        Log.d("Demos","Removed from list");
                        rcView.getAdapter().notifyDataSetChanged();



                    }

                }
                else
                {
                    img.setImageResource(R.mipmap.shopcartexclude);
                    searchResultsActivity.addCartItems(product,context);


                }




            }
        });


    }

    @Override
    public int getItemCount() {
        return products.size();
    }

    public void remove(int position) {
        products.remove(position);
        notifyItemRemoved(position);
    }

    public void add(Product text, int position) {
        products.add(position, text);
        notifyItemInserted(position);
    }


}
