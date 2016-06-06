package app.yongjiasoftware.com.videoplayer.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import app.yongjiasoftware.com.videoplayer.R;
import app.yongjiasoftware.com.videoplayer.bean.MedieModel;

/**
 * @Title VideoRecyclerViewAdapter
 * @Description:
 * @Author: alvin
 * @Date: 2016/5/31.14:57
 * @E-mail: 49467306@qq.com
 */
public class VideoRecyclerViewAdapter extends RecyclerView.Adapter<VideoRecyclerViewAdapter.VideoViewHolder> {
    private Context mContext;
    private List<MedieModel> mList;
    private LayoutInflater mInflater;

    public VideoRecyclerViewAdapter(Context context, List<MedieModel> list) {
        mContext = context;
        mList = list;
        mInflater = LayoutInflater.from(mContext);
    }

    public interface OnItemClickLitener {
        void onItemClick(View view, int position);

        void onItemLongClick(View view, int position);
    }

    private OnItemClickLitener mOnItemClickLitener;

    public void setOnItemClickLitener(OnItemClickLitener mOnItemClickLitener) {
        this.mOnItemClickLitener = mOnItemClickLitener;
    }

    @Override
    public VideoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        VideoViewHolder viewHolder = new VideoViewHolder(mInflater.inflate(R.layout.item_recylerview, parent, false));
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final VideoViewHolder holder, int position) {
        if (mOnItemClickLitener != null) {
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mOnItemClickLitener.onItemClick(holder.itemView, holder.getLayoutPosition());
                }
            });

            holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    mOnItemClickLitener.onItemLongClick(holder.itemView, holder.getLayoutPosition());
                    return false;
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        if (mList == null)
            return 0;
        return mList.size();
    }

    class VideoViewHolder extends RecyclerView.ViewHolder {
        TextView nameTextView;
        ImageView mImageView;

        public VideoViewHolder(View itemView) {
            super(itemView);

            nameTextView = (TextView) itemView.findViewById(R.id.id_item_name);
            mImageView = (ImageView) itemView.findViewById(R.id.id_item_image);
        }
    }
}
