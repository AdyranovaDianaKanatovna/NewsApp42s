package com.example.news_app43.board;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.news_app43.onItemClickListeners.OnItemClickListener;
import com.example.news_app43.R;
import com.example.news_app43.databinding.ItemPagerBoardBinding;

import java.util.ArrayList;

public class BoardAdaptor extends RecyclerView.Adapter<BoardAdaptor.ViewHolder> {
    private ArrayList<Board> list;
    private OnItemClickListener onClickListener;

    public BoardAdaptor(OnItemClickListener onClickListener) {
        this.onClickListener = onClickListener;
        list = new ArrayList<>();
        list.add(new Board("Welcome to my app", "This unussual"));
        list.add(new Board("Welcome to my app", "This unussual"));
        list.add(new Board("Welcome to my app", "This unussual"));
    }



    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(ItemPagerBoardBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(position);
    }

    @Override
    public int getItemCount() {
        return 3;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ItemPagerBoardBinding binding;

        public ViewHolder(@NonNull ItemPagerBoardBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(int position) {
            Board board = list.get(position);
            binding.Texttitle.setText(board.getTitle());
            binding.text2.setText(board.getDesc());
            if (position == list.size() - 1) {
                binding.BtnStart.setVisibility(View.VISIBLE);
            } else {
                binding.BtnStart.setVisibility(View.INVISIBLE);
            }

            binding.BtnStart.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onClickListener.itemClick();
                }
            });
        }
    }
}

