package myfab.wildcardenter.com.first_app.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.List;

import myfab.wildcardenter.com.first_app.R;
import myfab.wildcardenter.com.first_app.models.SellModel;

public class MySellItemAdpter extends RecyclerView.Adapter<MySellItemAdpter.MySellItemViewHolder> {

    private Context mContext;
    private List<SellModel> mListMySell;

    public MySellItemAdpter(Context context, List<SellModel> mListMySell) {
        this.mContext = context;
        this.mListMySell = mListMySell;
    }

    @NonNull
    @Override
    public MySellItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MySellItemViewHolder(LayoutInflater.from(mContext).inflate(R.layout.my_sell_item_blueprint,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull final MySellItemViewHolder holder, int position) {
        SellModel currentItem=mListMySell.get(position);
        Picasso.with(mContext).load(currentItem.getPostImage()).resize(440,440).onlyScaleDown().into(holder.myItemImage);
        holder.myItemName.setText(currentItem.getBookName());
        holder.myItemDeptnSem.setText(String.format("%s,%s", currentItem.getForDepartment(), currentItem.getFor_Semester()));
        holder.myItemPriceTag.setText(currentItem.getBookPrice());
        holder.myItemDesc.setText(currentItem.getBookDescription());
        holder.myItemDeleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mContext, "Item "+holder.getAdapterPosition()+"will be deleted after development", Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public int getItemCount() {
        return mListMySell.size();
    }

    class MySellItemViewHolder extends RecyclerView.ViewHolder{
        TextView myItemName,myItemDeptnSem,myItemPriceTag,myItemDesc;
        ImageView myItemImage;
        AppCompatButton myItemDeleteBtn;

        public MySellItemViewHolder(View itemView) {
            super(itemView);
            myItemName=itemView.findViewById(R.id.MySellItemName);
            myItemDeptnSem=itemView.findViewById(R.id.MySellItemDeptnSem);
            myItemPriceTag=itemView.findViewById(R.id.MySellItemPriceTag);
            myItemDesc=itemView.findViewById(R.id.MySellItemDesc);
            myItemDeleteBtn=itemView.findViewById(R.id.MySellItemDeleteButton);
            myItemImage=itemView.findViewById(R.id.MySellItemImage);
        }
    }
}
