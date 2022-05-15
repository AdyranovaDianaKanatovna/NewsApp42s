package com.example.news_app43.news;

import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.os.FileUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.news_app43.R;
import com.example.news_app43.appProgramm.App;
import com.example.news_app43.databinding.FragmentNewsBinding;
import com.example.news_app43.models.Article;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;


public class NewsFragment extends Fragment {
    private FragmentNewsBinding binding;
    private ProgressDialog ph;


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
        ph = new ProgressDialog(requireContext());
        ph.setTitle("Wait please");
        ph.setCanceledOnTouchOutside(false);
        binding.BtnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                save();
            }
        });
    }

    private int save() {
        String text = binding.editText.getText().toString();
        Bundle bundle = new Bundle();
        Article article = new Article(text, System.currentTimeMillis());
        saveToFirestore(article);
        bundle.putSerializable("article", article);
        App.getDataBase().articleDao().Insert(article);
        //aa
        getParentFragmentManager().setFragmentResult("rk_news", bundle);

        return bundle.size();
    }//dsad

    private void saveToFirestore(Article article) {
        ph.setTitle("Waiting for data information");
        ph.show();
        FirebaseFirestore
                .getInstance()
                .collection("news")
                .add(article)
                .addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentReference> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(requireContext(), "Success", Toast.LENGTH_SHORT).show();
                            close();
                        } else {
                            ph.dismiss();
                            Toast.makeText(requireContext(), "Error:" + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }


    private void close() {
        NavController navController = Navigation.
                findNavController(requireActivity(),
                        R.id.nav_host_fragment_activity_main);
        navController.navigateUp();
    }
}