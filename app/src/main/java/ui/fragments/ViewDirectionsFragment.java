package ui.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import R;
import adapters.DirectionAdapter;
import models.Direction;

public class ViewDirectionsFragment extends Fragment {

    private List<Direction> directionList;
    private DirectionAdapter directionAdapter;

    private RecyclerView directionRecyclerView;
    private TextView emptyView;

    public ViewDirectionsFragment() {
        // Required empty public constructor
    }

    public static ViewDirectionsFragment newInstance(List<Direction> directions) {
        ViewDirectionsFragment fragment = new ViewDirectionsFragment();
        if (directions == null)
            directions = new ArrayList<>();
        Bundle args = new Bundle();
        args.putParcelableArrayList("directions", (ArrayList<Direction>) directions);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_view_directions, container, false);

        Bundle args = getArguments();
        if (args != null)
            directionList = args.getParcelableArrayList("directions");

        directionRecyclerView = view.findViewById(R.id.recyclerView);
        emptyView = view.findViewById(R.id.empty_view);

        directionAdapter = new DirectionAdapter(getActivity(), directionList, false);
        toggleEmptyView();

        directionRecyclerView.setHasFixedSize(true);
        directionRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        directionRecyclerView.setAdapter(directionAdapter);

        return view;
    }

    private void toggleEmptyView() {
        if (directionList.size() == 0) {
            emptyView.setVisibility(View.VISIBLE);
            directionRecyclerView.setVisibility(View.GONE);
        } else {
            emptyView.setVisibility(View.GONE);
            directionRecyclerView.setVisibility(View.VISIBLE);
        }
    }
}
