package myfab.wildcardenter.com.first_app.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import myfab.wildcardenter.com.first_app.R;
import myfab.wildcardenter.com.first_app.models.SellFragmentItemModel;

public class Sell_fragment_adapter extends RecyclerView.Adapter<Sell_fragment_adapter.Sell_ViewHolder> {

    private Context mContext;
    private List<SellFragmentItemModel> fragmentItemModels;

    public Sell_fragment_adapter(Context mContext, List<SellFragmentItemModel> fragmentItemModels) {
        this.mContext = mContext;
        this.fragmentItemModels = fragmentItemModels;
    }

    @NonNull
    @Override
    public Sell_ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

       return new Sell_ViewHolder(LayoutInflater.from(mContext).inflate(R.layout.sell_item_view,parent,false));


    }

    @Override
    public void onBindViewHolder(@NonNull Sell_ViewHolder holder, int position) {
        SellFragmentItemModel currentItem=fragmentItemModels.get(position);
        Picasso.with(mContext).load(currentItem.getImageSource()).fit().into(holder.sell_image);
        holder.sell_item_name.setText(currentItem.getDetails());

    }

    @Override
    public int getItemCount() {
        return fragmentItemModels.size();
    }

    public class Sell_ViewHolder extends RecyclerView.ViewHolder{
        ImageView sell_image;
        TextView sell_item_name;

        public Sell_ViewHolder(View itemView) {
            super(itemView);
            sell_image=itemView.findViewById(R.id.sell_item_image);
            sell_item_name=itemView.findViewById(R.id.sell_item_name);

        }
    }
}
