package ui.fragments;


import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import R;
import adapters.DatabaseAdapter;
import adapters.IngredientAdapter;
import models.Ingredient;
import models.Recipe;

/**
 * A simple {@link Fragment} subclass.
 */
public class RecipeIngredientsFragment extends NavigableFragment {

    private IngredientListener mListener;
    private List<Ingredient> ingredientList;
    private IngredientAdapter ingredientAdapter;
    private DatabaseAdapter databaseAdapter;

    private RecyclerView ingredientRecyclerView;
    private TextView emptyView;
    private Button addButton;
    private EditText ingredientField;

    public RecipeIngredientsFragment() {
        // Required empty public constructor
    }

    public static RecipeIngredientsFragment newInstance(Recipe recipe) {
        RecipeIngredientsFragment fragment = new RecipeIngredientsFragment();

        if (recipe.getIngredients() != null) {
            Bundle args = new Bundle();
            args.putParcelableArrayList("ingredients", (ArrayList<Ingredient>) recipe.getIngredients());
            fragment.setArguments(args);
        }

        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_recipe_ingredients, container, false);
        databaseAdapter = DatabaseAdapter.getInstance(getActivity());

        Bundle args = getArguments();
        if (args != null)
            ingredientList = args.getParcelableArrayList("ingredients");
        if (ingredientList == null)
            ingredientList = new ArrayList<>();

        ingredientRecyclerView = view.findViewById(R.id.recyclerView);
        emptyView = view.findViewById(R.id.empty_view);
        addButton = view.findViewById(R.id.add_button);
        ingredientField = view.findViewById(R.id.ingredientField);
        ingredientAdapter = new IngredientAdapter(getActivity(), ingredientList);
        ingredientAdapter.setIngredientListener(position -> {
            ingredientList.remove(position);
            toggleEmptyView();
            ingredientAdapter.notifyDataSetChanged();
        });

        toggleEmptyView();

        ingredientRecyclerView.setHasFixedSize(true);
        ingredientRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        ingredientRecyclerView.setAdapter(ingredientAdapter);

        addButton.setOnClickListener(v -> {
            String newIngredient = ingredientField.getText().toString();
            if (!newIngredient.isEmpty()) {
                ingredientField.setText("");
                ingredientList.add(new Ingredient(newIngredient));
                toggleEmptyView();
                ingredientAdapter.notifyDataSetChanged();
            }
        });

        return view;
    }

    private void toggleEmptyView() {
        if (ingredientList.size() == 0) {
            emptyView.setVisibility(View.VISIBLE);
            ingredientRecyclerView.setVisibility(View.GONE);
        } else {
            emptyView.setVisibility(View.GONE);
            ingredientRecyclerView.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (IngredientListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement IngredientListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onNext() {
        if (mListener != null)
            mListener.navigateToDirectionsFragment(ingredientList);
    }

    public interface IngredientListener {
        void navigateToDirectionsFragment(List<Ingredient> ingredients);
    }
}
