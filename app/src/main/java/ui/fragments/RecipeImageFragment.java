package ui.fragments;


import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;

import R;
import models.Recipe;

/**
 * A simple {@link Fragment} subclass.
 */
public class RecipeImageFragment extends NavigableFragment {

    private static final int MAX_DESCRIPTION_LENGTH = 200;
    private String currentRecipeImage;
    private ImageListener mListener;

    private ImageView recipeImage;
    private Button selectImageBtn;
    private EditText recipeName;
    private EditText recipeDescription;

    public static RecipeImageFragment newInstance(Recipe recipe) {
        RecipeImageFragment fragment = new RecipeImageFragment();

        Bundle args = new Bundle();
        if (recipe.getDescription() != null) {
            args.putString("imagePath", recipe.getImagePath());
            args.putString("description", recipe.getDescription());
            args.putString("name", recipe.getName());
            fragment.setArguments(args);
        }

        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_recipe_image, container, false);
        recipeImage = view.findViewById(R.id.recipe_image);
        selectImageBtn = view.findViewById(R.id.choose_image);
        recipeDescription = view.findViewById(R.id.recipe_description);
        recipeName = view.findViewById(R.id.recipe_name);

        Bundle args = getArguments();
        if (args != null) {
            String imagePath = args.getString("imagePath");
            String description = args.getString("description");
            String name = args.getString("name");
            onImageSelected(imagePath);
            recipeDescription.setText(description);
            recipeName.setText(name);
        }

        selectImageBtn.setOnClickListener(v -> {
            if (mListener != null)
                mListener.onSelectImage();
        });

        return view;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (ImageListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement ImageListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onNext() {
        if (currentRecipeImage == null) {
            Toast.makeText(getActivity(), "Please choose an image for this recipe.", Toast.LENGTH_LONG).show();
            return;
        }

        String name = recipeName.getText().toString();
        String description = recipeDescription.getText().toString();

        if (name.isEmpty()) {
            Toast.makeText(getActivity(), "Please specify a name for this recipe.", Toast.LENGTH_LONG).show();
            return;
        }

        if (description.isEmpty()) {
            Toast.makeText(getActivity(), "Please type in a description for this recipe.", Toast.LENGTH_LONG).show();
            return;
        } else {
            if (description.length() > 200) {
                Toast.makeText(getActivity(), "Your description shouldn't exceed " + MAX_DESCRIPTION_LENGTH + " characters.", Toast.LENGTH_LONG).show();
                return;
            }
        }

        if (mListener != null)
            mListener.navigateToIngredientsFragment(name, description);
    }

    public void onImageSelected(String imagePath) {
        currentRecipeImage = imagePath;
        if (!currentRecipeImage.isEmpty()) {
            recipeImage.setImageURI(Uri.fromFile(new File(currentRecipeImage)));
            selectImageBtn.setText("Update recipe image");
        }
    }

    public interface ImageListener {
        void onSelectImage();

        void navigateToIngredientsFragment(String name, String description);
    }
}
