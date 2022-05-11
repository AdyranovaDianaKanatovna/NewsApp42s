package com.example.news_app43.board;

import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.viewpager2.widget.ViewPager2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.news_app43.onItemClickListeners.OnItemClickListener;
import com.example.news_app43.prefs.Prefs;
import com.example.news_app43.R;
import com.example.news_app43.databinding.FragmentBoardBinding;

public class BoardFragment extends Fragment implements OnItemClickListener {
    private FragmentBoardBinding binding;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentBoardBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }



    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        BoardAdaptor adaptor = new BoardAdaptor(this);
        ViewPager2 viewPager = view.findViewById(R.id.viewPager);
        viewPager.setAdapter(adaptor);
        binding.dotsIndicator.setViewPager2(viewPager);
        requireActivity().getOnBackPressedDispatcher().addCallback(getViewLifecycleOwner(),
                new OnBackPressedCallback(true) {
                    @Override
                    public void handleOnBackPressed() {
                        requireActivity().finish();
                    }
                });
        viewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                if (position == 2){
                    binding.skipBtn.setVisibility(View.INVISIBLE);
                }else{
                    binding.skipBtn.setVisibility(View.VISIBLE);
                }
            }
        });
        binding.skipBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                itemClick();
            }
        });

    }


    @Override
    public void onItemClick(int position) {
        NavController navController = Navigation.
                findNavController(requireActivity(),
                        R.id.nav_host_fragment_activity_main);
        navController.navigateUp();
    }

    @Override
    public void onItemLongClick(int position) {

    }

    public void itemClick() {
        Prefs prefs = new Prefs(requireContext());
        prefs.saveState();
        NavController navController = Navigation.findNavController(requireActivity(),R.id.nav_host_fragment_activity_main);
        navController.navigateUp();
    }
}