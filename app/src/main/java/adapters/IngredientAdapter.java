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
import models.Ingredient;

public class IngredientAdapter extends RecyclerView.Adapter<IngredientAdapter.IngredientViewHolder> {

    private List<Ingredient> ingredientList;
    private boolean isEditable = true;
    private Context mContext;
    private IngredientListener ingredientListener;

    public IngredientAdapter(Context context, List<Ingredient> ingredientList) {
        mContext = context;
        this.ingredientList = ingredientList;
    }

    public IngredientAdapter(Context context, List<Ingredient> ingredientList, boolean isEditable) {
        mContext = context;
        this.ingredientList = ingredientList;
        this.isEditable = isEditable;
    }

    @Override
    public int getItemViewType(int position) {
        return isEditable ? 0 : 1;
    }

    @Override
    public IngredientViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(viewType == 0 ? R.layout.ingredient_item_row : R.layout.ingredient_item_row_non_editable,
                        parent, false);
        return new IngredientViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final IngredientViewHolder holder, int position) {
        Ingredient ingredient = ingredientList.get(position);
        holder.bind(ingredient);
    }

    @Override
    public int getItemCount() {
        return ingredientList.size();
    }

    public class IngredientViewHolder extends RecyclerView.ViewHolder {

        TextView ingredientText;
        ImageView wasteBin;

        public IngredientViewHolder(View itemView) {
            super(itemView);

            ingredientText = itemView.findViewById(R.id.ingredientText);
            if (isEditable) {
                wasteBin = itemView.findViewById(R.id.wasteBin);
                wasteBin.setOnClickListener(v -> {
                    if (ingredientListener != null)
                        ingredientListener.onDeleteIngredient(getAdapterPosition());
                });
            }
        }

        public void bind(Ingredient ingredient) {
            ingredientText.setText(ingredient.getName());
        }
    }

    public void setIngredientListener(IngredientListener ingredientListener) {
        this.ingredientListener = ingredientListener;
    }

    public interface IngredientListener {
        void onDeleteIngredient(int position);
    }
}
