package com.balsikandar.crashreporter.ui;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.balsikandar.crashreporter.CrashReporter;
import com.balsikandar.crashreporter.R;
import com.balsikandar.crashreporter.adapter.CrashLogAdapter;
import com.balsikandar.crashreporter.utils.Constants;
import com.balsikandar.crashreporter.utils.CrashUtil;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;

/**
 * Created by bali on 11/08/17.
 */

public class ExceptionLogFragment extends Fragment {

    private CrashLogAdapter logAdapter;

    private RecyclerView exceptionRecyclerView;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.exception_log, container, false);
        exceptionRecyclerView = (RecyclerView) view.findViewById(R.id.exceptionRecyclerView);

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        loadAdapter(getActivity(), exceptionRecyclerView);
    }

    private void loadAdapter(Context context, RecyclerView exceptionRecyclerView) {

        logAdapter = new CrashLogAdapter(context, getAllExceptions());
        exceptionRecyclerView.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));
        exceptionRecyclerView.setAdapter(logAdapter);
    }

    public void clearLog() {
        if (logAdapter != null) {
            logAdapter.updateList(getAllExceptions());
        }
    }

    public ArrayList<File> getAllExceptions() {
        String crashDirPath;
        if (TextUtils.isEmpty(CrashReporter.getCrashReportPath())){
            crashDirPath = CrashUtil.getDefaultPath();
        } else{
            crashDirPath = CrashReporter.getCrashReportPath();
        }
        ArrayList<File> listOfFiles = new ArrayList<>(Arrays.asList(new File(crashDirPath).listFiles()));
        for (Iterator<File> iterator = listOfFiles.iterator(); iterator.hasNext(); ) {
            if (iterator.next().getName().contains(Constants.CRASH_SUFFIX)) {
                iterator.remove();
            }
        }
        Collections.sort(listOfFiles, Collections.reverseOrder());
        return listOfFiles;
    }

}