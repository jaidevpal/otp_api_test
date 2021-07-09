package com.example.otpandlayouts.View_Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.otpandlayouts.data_models.ProfileData_Model;
import com.example.otpandlayouts.databinding.NotesLikesRvLayoutBinding;
import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class NotesScreen_Likes_Adapter extends RecyclerView.Adapter<NotesScreen_Likes_Adapter.VH> {

    private Context context;
    private List<ProfileData_Model.Likes.Profile__1> likesList;

    public NotesScreen_Likes_Adapter(Context context, List<ProfileData_Model.Likes.Profile__1> likesList) {
        this.context = context;
        this.likesList = likesList;
    }

    @NonNull
    @NotNull
    @Override
    public NotesScreen_Likes_Adapter.VH onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        NotesLikesRvLayoutBinding bindView = NotesLikesRvLayoutBinding.inflate(LayoutInflater.from(context),parent,false);

        return new NotesScreen_Likes_Adapter.VH(bindView);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull NotesScreen_Likes_Adapter.VH holder, int position) {

        String name = likesList.get(position).getFirstName();
        String imageURL = likesList.get(position).getAvatar();

        holder.BindViews(name, imageURL);

    }

    @Override
    public int getItemCount() {
        return likesList.size();
    }

    public class VH extends RecyclerView.ViewHolder{
        NotesLikesRvLayoutBinding binding;
        public VH(@NonNull @NotNull NotesLikesRvLayoutBinding binding) {
            super(binding.getRoot());
            this.binding=binding;
        }

        void BindViews(String name, String avatar_URL){

            if (name==null||name==""||name.isEmpty()){
                binding.tvNameBottom.setText("no name");
            }else {

                binding.tvNameBottom.setText(name);
            }

            Picasso.get()
                    .load(avatar_URL)
                    .centerCrop()
                    .fit()
                    .into(binding.imageAvatarBottom);
        }
    }
}
