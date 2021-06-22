package com.example.mysekolah.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.mysekolah.R;
import com.example.mysekolah.User;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
//to show the child card
//implement child recycleradapter
public class ChildRecyclerAdapter extends RecyclerView.Adapter<ChildRecyclerAdapter.InnerHolder> {

    List<User> children;
    Context context;
    LayoutInflater mInflater;

    public ChildRecyclerAdapter(List<User> children, Context context) {
        this.children = children;
        this.context = context;
        mInflater = LayoutInflater.from(context);
    }
    //list all the childern
    public List<User> getChildren() {
        return children;
    }

    public void setChildren(List<User> children) {
        this.children = children;
    }

    @NonNull
    @NotNull
    @Override
    public InnerHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.item_child, parent, false);
        return new InnerHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull ChildRecyclerAdapter.InnerHolder holder, int position) {
        holder.initData(position);
    }

    //get item count and set innerholder
    @Override
    public int getItemCount() {
        return children.size();
    }

    class InnerHolder extends RecyclerView.ViewHolder {
        private TextView mChild1name;
        public InnerHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            mChild1name = itemView.findViewById(R.id.child1name);
        }
        //init data
        public void initData(int position) {
            mChild1name.setText(children.get(position).getName());
        }
    }
}
