package adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import R;
import models.Direction;
import models.Ingredient;

public class DirectionAdapter extends RecyclerView.Adapter<DirectionAdapter.DirectionViewHolder> {

    private List<Direction> directionList;
    private boolean isEditable = true;
    private Context mContext;
    private DirectionListener directionListener;

    public DirectionAdapter(Context context, List<Direction> directionList) {
        mContext = context;
        this.directionList = directionList;
    }

    public DirectionAdapter(Context context, List<Direction> directionList, boolean isEditable) {
        mContext = context;
        this.directionList = directionList;
        this.isEditable = isEditable;
    }

    @Override
    public int getItemViewType(int position) {
        return isEditable ? 0 : 1;
    }

    @Override
    public DirectionViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(viewType == 0 ? R.layout.direction_item_row : R.layout.direction_item_row_non_editable,
                        parent, false);
        return new DirectionViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final DirectionViewHolder holder, int position) {
        Direction direction = directionList.get(position);
        holder.bind(direction);
    }

    @Override
    public int getItemCount() {
        return directionList.size();
    }

    public class DirectionViewHolder extends RecyclerView.ViewHolder {

        TextView directionText;
        ImageView wasteBin;

        public DirectionViewHolder(View itemView) {
            super(itemView);

            directionText = itemView.findViewById(R.id.directionText);
            if (isEditable) {
                wasteBin = itemView.findViewById(R.id.wasteBin);
                wasteBin.setOnClickListener(v -> {
                    if (directionListener != null)
                        directionListener.onDeleteDirection(getAdapterPosition());
                });
            }
        }

        public void bind(Direction direction) {
            directionText.setText(direction.getBody());
        }
    }

    public void setDirectionListener(DirectionListener directionListener) {
        this.directionListener = directionListener;
    }

    public interface DirectionListener {
        void onDeleteDirection(int position);
    }
}
