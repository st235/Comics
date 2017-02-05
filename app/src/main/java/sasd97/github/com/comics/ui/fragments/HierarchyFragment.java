package sasd97.github.com.comics.ui.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import sasd97.github.com.comics.R;
import sasd97.github.com.comics.ui.ReaderActivity;
import sasd97.github.com.comics.ui.adapters.HierarchyAdapter;
import sasd97.github.com.comics.utils.FileUtils;

public class HierarchyFragment extends Fragment
        implements HierarchyAdapter.HierarchyInteractionListener {

    private static final String CURRENT_DIR = "CURRENT_DIR";

    @BindView(R.id.hierarchy_recycler)
    RecyclerView hierarchyRecycler;

    private LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
    private HierarchyAdapter hierarchyAdapter = new HierarchyAdapter();

    private File rootDir = new File("/");
    private File currentDir;

    private TextView directoryTextView;

    public HierarchyFragment() {
    }

    public static HierarchyFragment newInstance() {
        HierarchyFragment fragment = new HierarchyFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_hierarchy, container, false);
        ButterKnife.bind(this, v);

        ViewGroup toolbar = (ViewGroup) getActivity().findViewById(R.id.toolbar);
        ViewGroup breadcrumbLayout = (ViewGroup) inflater.inflate(R.layout.toolbar_breadcrumbs, toolbar, false);
        toolbar.addView(breadcrumbLayout);

        directoryTextView = (TextView) breadcrumbLayout.findViewById(R.id.breadcrumbs_directory);

        hierarchyAdapter.setHierarchyInteractionListener(this);

        hierarchyRecycler.setLayoutManager(linearLayoutManager);
        hierarchyRecycler.setAdapter(hierarchyAdapter);

        return v;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (savedInstanceState != null) currentDir = (File) savedInstanceState.getSerializable(CURRENT_DIR);
        else currentDir = Environment.getExternalStorageDirectory();

        setCurrentDir(currentDir);
    }

    private void setCurrentDir(File directory) {
         currentDir = directory;
        List<File> subdirs = new ArrayList<>();

        if (!currentDir.getAbsolutePath().equals(rootDir.getAbsolutePath())) {
            subdirs.add(currentDir.getParentFile());
        }

        File[] files = currentDir.listFiles();
        if (files != null) {
            for (File f : files) {
                if (f.isDirectory() || FileUtils.isArchive(f.getName())) {
                    subdirs.add(f);
                }
            }
        }

        Collections.sort(subdirs);
        hierarchyAdapter.refresh(currentDir.getParentFile(), subdirs.toArray(new File[subdirs.size()]));

        directoryTextView.setText(directory.getAbsolutePath());
    }

    @Override
    public void onDirectoryClick(int position, File directory) {
        setCurrentDir(directory);
    }

    @Override
    public void onFileClick(int position, File file) {
        Intent intent = new Intent(getActivity(), ReaderActivity.class);
        intent.putExtra(ReaderFragment.PARAM_HANDLER, file);
        intent.putExtra(ReaderFragment.PARAM_MODE, ReaderFragment.Mode.MODE_BROWSER);
        startActivity(intent);
    }

    @Override
    public void onDestroyView() {
        ViewGroup toolbar = (ViewGroup) getActivity().findViewById(R.id.toolbar);
        ViewGroup breadcrumb = (ViewGroup) toolbar.findViewById(R.id.breadcrumb_layout);
        toolbar.removeView(breadcrumb);
        super.onDestroyView();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putSerializable(CURRENT_DIR, currentDir);
        super.onSaveInstanceState(outState);
    }
}
