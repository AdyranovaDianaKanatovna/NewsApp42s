package com.example.news_app43.news;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.news_app43.onItemClickListeners.OnItemClickListener;
import com.example.news_app43.R;
import com.example.news_app43.databinding.ItemNewsBinding;
import com.example.news_app43.models.Article;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class NewsAdaptor extends RecyclerView.Adapter<NewsAdaptor.ViewHolder> {
    private Context context;
    private List<Article> list;
    private OnItemClickListener onItemClickListener;

    public  NewsAdaptor(){
        list = new ArrayList<>();
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        return new ViewHolder(ItemNewsBinding.inflate(LayoutInflater.from(parent.getContext()),parent,false));

    }
    public void setList(List<Article> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
    holder.bind(list.get(position));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void addItem(Article article) {
        list.add(0,article);
        notifyItemInserted(list.indexOf(article));
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public Article getItem(int position) {
        return list.get(position);
    }
    public void insertItem(Article article,int index){
        list.set(index, article);
        notifyItemChanged(index);
    }

    public void addItems(List<Article> list) {
        this.list = list;
        notifyDataSetChanged();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        private ItemNewsBinding binding;

        public ViewHolder(@NonNull ItemNewsBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
            onItemClickListener.onItemClick(getAdapterPosition());
                }
            });
            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    onItemClickListener.onItemLongClick(getAdapterPosition());
                   new AlertDialog.Builder(view.getContext()).setTitle("Delete")
                           .setMessage("Are you sure?").setNegativeButton("No",null)
                           .setPositiveButton("YES", new DialogInterface.OnClickListener() {
                               @Override
                               public void onClick(DialogInterface dialogInterface, int i) {
                                   Toast.makeText(view.getContext(), "DELETE", Toast.LENGTH_SHORT).show();
                                   list.remove(getAdapterPosition());
                                   notifyDataSetChanged();
                               }
                           }).show();
                   return true;
                }
            });
        }

        public void bind(Article article) {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm:ss, dd MMM yyy", Locale.ROOT);
            String data = String.valueOf(simpleDateFormat.format(article.getDate()));
            binding.smallText.setText(data);
            binding.Texttitle.setText(article.getText());
            if (getAdapterPosition() % 2 == 0){
                binding.itemNews.setBackgroundColor(ContextCompat.getColor(context, R.color.purple_200));
            }else{
                binding.itemNews.setBackgroundColor(ContextCompat.getColor(context,R.color.white));
            }
        }
    }
}
