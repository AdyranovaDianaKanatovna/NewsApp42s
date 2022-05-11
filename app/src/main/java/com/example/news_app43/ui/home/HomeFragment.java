package com.example.news_app43.ui.home;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentResultListener;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.example.news_app43.appProgramm.App;
import com.example.news_app43.news.NewsAdaptor;
import com.example.news_app43.onItemClickListeners.OnItemClickListener;
import com.example.news_app43.R;
import com.example.news_app43.databinding.FragmentHomeBinding;
import com.example.news_app43.models.Article;

import java.util.List;

public class HomeFragment extends Fragment {
    private List<Article> list;
    private NewsAdaptor adaptor;
    private FragmentHomeBinding binding;
    private boolean isediting = false;
    private int index;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        adaptor = new NewsAdaptor();
        setHasOptionsMenu(true);

        list = App.getDataBase().articleDao().getAll();
        adaptor.addItems(list);
    }


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentHomeBinding.inflate(inflater,
                container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.FAcBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isediting = false;
                openFragment();
            }
        });
        binding.editSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
            list = App.getDataBase().articleDao().getSearch(editable.toString());
            adaptor.addItems(list);
            }
        });
        binding.reciclerView.setAdapter(adaptor);
        list = App.getDataBase().articleDao().getAll();
        adaptor.addItems(list);
        getParentFragmentManager().setFragmentResultListener("rk_news", getViewLifecycleOwner(), new FragmentResultListener() {
            @Override
            public void onFragmentResult(@NonNull String requestKey, @NonNull Bundle result) {
                Article article = (Article) result.getSerializable("article");
                if (isediting){
                    adaptor.insertItem(article,index);
                }else {
                    adaptor.addItem(article);
                    Log.e("Home", "result = " + article.getText());
                }
            }
        });
        binding.reciclerView.setAdapter(adaptor);
        adaptor.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Article article = adaptor.getItem(position);
                Toast.makeText(requireContext(),article.getText(), Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onItemLongClick(int position) {

            }

            @Override
            public void itemClick() {

            }
        });

    }

    private void openFragment() {
        NavController navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment_activity_main);
        navController.navigate(R.id.newsFragment);
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        menu.removeItem(R.id.sort);
        MenuItem menuItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) menuItem.getActionView();
        searchView.setQueryHint("Type here to search");
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                adaptor.setList(App.getDataBase().articleDao().getSearch(s));
                return false;
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
            if (item.getItemId() == R.id.sort){
            adaptor.setList(App.getDataBase().articleDao().sort());
            binding.reciclerView.setAdapter(adaptor);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}