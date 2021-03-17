package com.example.noteapp.onboard;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.airbnb.lottie.LottieAnimationView;
import com.example.noteapp.R;

import de.hdodenhof.circleimageview.CircleImageView;

public class BoardAdapter extends RecyclerView.Adapter<BoardAdapter.ViewHolder> {

    private String[] titels = new String[]{"Fast", "Free", "Powerful"};
    private String[] desc = new String[]{"🙁", "👩", "💃"};
    private int[] images = new int[]{R.raw.loader,R.raw.ositiva, R.raw.silhouette};
    private OnStartClickListener listener;

    public interface OnStartClickListener {
        void onClick();

    }

    public BoardAdapter() {

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.pager_board, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(position);

    }

    @Override
    public int getItemCount() {
        return 3;
    }

    public void setOnStartClickListener(OnStartClickListener onStartClickListener) {
        this.listener = onStartClickListener;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView textTitle, txtDesc;
        private Button btnStart;
        private LottieAnimationView circleImageView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textTitle = itemView.findViewById(R.id.textTitle);
            btnStart = itemView.findViewById(R.id.btnStart);
            txtDesc = itemView.findViewById(R.id.textDesc);
            circleImageView = itemView.findViewById(R.id.imageView);
            btnStart.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onClick();
                }
            });
        }

        public void bind(int position) {
            txtDesc.setText(desc[position]);
            circleImageView.setAnimation(images[position]);
            textTitle.setText(titels[position]);
            if (position==2){
                btnStart.setVisibility(View.VISIBLE);
            }else {
                btnStart.setVisibility(View.INVISIBLE);
            }
        }
    }
}
