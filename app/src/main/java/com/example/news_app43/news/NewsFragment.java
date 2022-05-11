package com.example.news_app43.news;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.news_app43.R;
import com.example.news_app43.appProgramm.App;
import com.example.news_app43.databinding.FragmentNewsBinding;
import com.example.news_app43.models.Article;


public class NewsFragment extends Fragment {
    private FragmentNewsBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentNewsBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.BtnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                save();
            }
        });
    }

    private void save() {
      String text =   binding.editText.getText().toString();
      Bundle bundle = new Bundle();
      Article article = new Article(text,System.currentTimeMillis());
      bundle.putSerializable("article", article);
      App.getDataBase().articleDao().Insert(article);
      //aa
      getParentFragmentManager().setFragmentResult("rk_news",bundle);
        close();
    }//dsad
    
    private void close() {
        NavController navController = Navigation.
                findNavController(requireActivity(),
                        R.id.nav_host_fragment_activity_main);
        navController.navigateUp();
    }
}