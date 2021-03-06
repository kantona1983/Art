package com.Karim.Art.activity.arts;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.Karim.Art.R;
import com.Karim.Art.model.Artwork;
import com.Karim.Art.utils.ImageLoadingUtils;
import com.Karim.Art.utils.LocalStoreUtil;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by HP-HP on 29-03-2016.
 */
public class FavoriteArtsAdapter extends RecyclerView.Adapter<FavoriteArtsAdapter.ArtViewHolder> {

    public interface Callbacks {
        public void onArtClick(Artwork artwork);
        public void onFavoriteClick(Artwork artwork);
    }

    private Callbacks mCallbacks;
    private Context context;
    private List<Artwork> mFeedList;

    public FavoriteArtsAdapter(List<Artwork> feedList) {
        this.mFeedList = feedList;
    }

    @Override
    public FavoriteArtsAdapter.ArtViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();

        View view = View.inflate(parent.getContext(), R.layout.item_art, null);
        return new ArtViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ArtViewHolder artViewHolder, int position) {

        final Artwork artwork = (Artwork) mFeedList.get(position);

        artViewHolder.mArtName.setText(artwork.getTitle());
        artViewHolder.mArtBy.setText("by "+artwork.getPrincipalOrFirstMaker());
        ImageLoadingUtils.load(artViewHolder.mArtImage, artwork.getThumbnailImage());
        artViewHolder.mArtImage.setAspectRatio(artwork.getAspectRatio());

        if(LocalStoreUtil.hasInFavorites(context, artwork.getLongId())) {
            artViewHolder.mFavoriteButton.setSelected(true);
            artwork.setFavorite(true);
        } else {
            artViewHolder.mFavoriteButton.setSelected(false);
            artwork.setFavorite(false);
        }

        artViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mCallbacks!=null){
                    mCallbacks.onArtClick(artwork);
                }
            }
        });

        artViewHolder.mFavoriteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mCallbacks!=null) {
                    artViewHolder.mFavoriteButton.setSelected(!artwork.isFavorite());
                    mCallbacks.onFavoriteClick(artwork);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return (mFeedList!=null? mFeedList.size():0);
    }

    public void setCallbacks(Callbacks callbacks) {
        this.mCallbacks = callbacks;
    }

    public class ArtViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.artTextView) TextView mArtName;
        @BindView(R.id.artBy) TextView mArtBy;
        @BindView(R.id.artImage) SimpleDraweeView mArtImage;
        @BindView(R.id.art_item_btn_favorite) ImageButton mFavoriteButton;

        public ArtViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

}
