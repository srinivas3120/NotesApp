package com.srinivas.notesapp.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.srinivas.notesapp.model.BasicModel;

import java.util.Observer;


public abstract class AbstractFragment extends Fragment implements Observer {
    private BasicModel model;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = null;
        try{
            model = getModel();
            model.addObserver(this);
            view = onCreateViewPost(inflater, container, savedInstanceState);
        }catch (Exception e){
            e.printStackTrace();
        }
        return view;
    }

    protected abstract View onCreateViewPost(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState);
    protected abstract BasicModel getModel();

}
