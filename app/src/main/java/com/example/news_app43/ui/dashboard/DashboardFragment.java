package com.example.news_app43.ui.dashboard;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.news_app43.databinding.FragmentDashboardBinding;
import com.example.news_app43.models.Article;
import com.example.news_app43.news.NewsAdaptor;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.List;

public class DashboardFragment extends Fragment {

    private FragmentDashboardBinding binding;
    private NewsAdaptor adaptor;
    private ProgressDialog pb;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        adaptor = new NewsAdaptor();
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentDashboardBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        pb = new ProgressDialog(requireContext());
        pb.setTitle("Wait please");
        pb.setCanceledOnTouchOutside(false);
        binding.reciclerView.setAdapter(adaptor);
        getData();
    }


    private void getData() {
        pb.setMessage("Waiting for information");
        pb.show();
        FirebaseFirestore
                .getInstance()
                .collection("news")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            List<Article> list = task.getResult().toObjects(Article.class);
                            pb.dismiss();
                            adaptor.addItems(list);
                        }
                    }
                });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}