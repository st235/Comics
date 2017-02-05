package sasd97.github.com.comics.ui.adapters;

import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;

import butterknife.BindView;
import butterknife.ButterKnife;
import sasd97.github.com.comics.R;
import sasd97.github.com.comics.utils.FileUtils;

/**
 * Created by Alexadner Dadukin on 2/5/2017.
 */

public class HierarchyAdapter extends RecyclerView.Adapter<HierarchyAdapter.HierarchyViewHolder> {

    private static final String DOTS = "...";

    public interface HierarchyInteractionListener {
        void onDirectoryClick(int position, File directory);
        void onFileClick(int position, File file);
    }

    private Context context;
    private File root;
    private File[] directory;
    private HierarchyInteractionListener hierarchyInteractionListener;

    public HierarchyAdapter() {}

    public void setHierarchyInteractionListener(HierarchyInteractionListener hierarchyInteractionListener) {
        this.hierarchyInteractionListener = hierarchyInteractionListener;
    }

    @Override
    public HierarchyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        View v = LayoutInflater.from(context).inflate(R.layout.row_hierarchy, parent, false);
        return new HierarchyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(HierarchyViewHolder holder, int position) {
        File file = directory[position];
        if (root != null && file.getAbsolutePath().equals(root.getAbsolutePath())) holder.hierarchyText.setText(DOTS);
        else holder.hierarchyText.setText(file.getName());
        setIcon(holder.hierarchyIcon, file);
    }

    @Override
    public int getItemCount() {
        if (directory == null) return 0;
        return directory.length;
    }

    public void refresh(@NonNull File root, @NonNull File[] files) {
        this.root = root;
        this.directory = files;
        notifyDataSetChanged();
    }

    private void setIcon(ImageView imageView, File file) {
        int colorRes = R.color.colorFolder;

        if (file.isDirectory()) {
            imageView.setImageResource(R.drawable.ic_folder_white_24dp);
        }
        else {
            imageView.setImageResource(R.drawable.ic_insert_drive_file_white_24dp);

            String name = file.getName();
            if (FileUtils.isZip(name)) {
                colorRes = R.color.colorZip;
            }
            else if (FileUtils.isRar(name)) {
                colorRes = R.color.colorRar;
            }
        }

        GradientDrawable shape = (GradientDrawable) imageView.getBackground();
        shape.setColor(ContextCompat.getColor(context, colorRes));
    }

    class HierarchyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @BindView(R.id.hierarchy_icon)
        ImageView hierarchyIcon;

        @BindView(R.id.hierarchy_path)
        TextView hierarchyText;

        public HierarchyViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (hierarchyInteractionListener == null) return;

            File file = directory[getAdapterPosition()];
            if (file.isDirectory()) hierarchyInteractionListener.onDirectoryClick(getAdapterPosition(), file);
            else hierarchyInteractionListener.onFileClick(getAdapterPosition(), file);
        }
    }


}
