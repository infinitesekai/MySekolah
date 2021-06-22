package com.example.mysekolah.adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.view.View;

import androidx.appcompat.app.AlertDialog;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.example.mysekolah.R;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class ChildInfoAdapter extends BaseQuickAdapter<String, BaseViewHolder> {
    private Context context;
    private OnDeleteChildListener onDeleteChildListener;
    //set child information adapter
    public ChildInfoAdapter(Context context, @Nullable List<String> data) {
        super(R.layout.item_child_info, data);
        this.context = context;
    }

    @Override
    protected void convert(@NotNull BaseViewHolder baseViewHolder, String s) {
        baseViewHolder.setText(R.id.tv_item_child_name, s);
        baseViewHolder.getView(R.id.iv_item_child_edit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Delete");
                builder.setMessage("Are you sure to delete this child?");
                builder.setPositiveButton("ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                        if (onDeleteChildListener!=null){
                            onDeleteChildListener.onDelete(baseViewHolder.getAdapterPosition());
                        }
                    }
                });
                builder.setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });
                builder.show();
            }
        });
    }
    //delete child listener
    public  interface  OnDeleteChildListener{
        void onDelete(int position);
    }
    //set on delete child listener
    public void setOnDeleteChildListener(OnDeleteChildListener onDeleteChildListener){
        this.onDeleteChildListener = onDeleteChildListener;
    };
}

