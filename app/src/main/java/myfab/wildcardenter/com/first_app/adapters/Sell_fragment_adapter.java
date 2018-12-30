package myfab.wildcardenter.com.first_app.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cooltechworks.views.shimmer.ShimmerRecyclerView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.List;
import java.util.Objects;

import myfab.wildcardenter.com.first_app.R;
import myfab.wildcardenter.com.first_app.activities.DetailedSellActivity;
import myfab.wildcardenter.com.first_app.models.SellModel;
import myfab.wildcardenter.com.first_app.models.UserModel;

public class Sell_fragment_adapter extends ShimmerRecyclerView.Adapter<Sell_fragment_adapter.Sell_ViewHolder> {


    private Context mContext;
    private List<SellModel> fragmentItemModels;

    public Sell_fragment_adapter(Context mContext, List<SellModel> fragmentItemModels) {
        this.mContext = mContext;
        this.fragmentItemModels = fragmentItemModels;
    }

    @NonNull
    @Override
    public Sell_ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

            return new Sell_ViewHolder(LayoutInflater.from(mContext).inflate(R.layout.sell_item_view, parent, false));

    }

    @Override
    public void onBindViewHolder(@NonNull final Sell_ViewHolder holder, int position) {
        SellModel currentItem=fragmentItemModels.get(position);
        Picasso.with(mContext).load(currentItem.getPostImage()).fit().into(holder.sell_image);
        setPublisherName(holder.sell_item_publisher,holder.db,currentItem.getPublisher());
        holder.sell_item_name.setText(currentItem.getBookName());
        holder.sell_item_price.setText(currentItem.getBookPrice());
        holder.sell_item_dept.setText(currentItem.getForDepartment());
        holder.sell_item_container.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(mContext,DetailedSellActivity.class);
                intent.putExtra("putPostId",fragmentItemModels.get(holder.getAdapterPosition()).getPostId());
                mContext.startActivity(intent);
            }
        });

    }

    private void setPublisherName(final TextView sell_item_publisher, DatabaseReference reference, final String publisherId) {
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot snapshot:dataSnapshot.getChildren()){
                    UserModel user=snapshot.getValue(UserModel.class);
                    if (Objects.requireNonNull(user).getUserId().equals(publisherId)){
                        sell_item_publisher.setText(user.getUsername());
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    @Override
    public int getItemCount() {
        return fragmentItemModels.size();
    }

    public class Sell_ViewHolder extends ShimmerRecyclerView.ViewHolder{
        ImageView sell_image;
        TextView sell_item_name,sell_item_price,sell_item_publisher,sell_item_dept;
        DatabaseReference db;
        LinearLayout sell_item_container;
        public Sell_ViewHolder(View itemView) {
            super(itemView);
            sell_image=itemView.findViewById(R.id.sell_item_image);
            sell_item_name=itemView.findViewById(R.id.sell_item_name);
            sell_item_publisher=itemView.findViewById(R.id.MySellItemDeptnSem);
            sell_item_price=itemView.findViewById(R.id.MySellItemPriceTag);
            sell_item_dept=itemView.findViewById(R.id.sell_item_dept);
            sell_item_container=itemView.findViewById(R.id.sell_item_container);

            db=FirebaseDatabase.getInstance().getReference().child("Users");

        }
    }
}
