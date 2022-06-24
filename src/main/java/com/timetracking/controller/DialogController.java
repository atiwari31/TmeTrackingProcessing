package com.dcardprocessing.controller;
import com.dcardprocessing.bean.ScanActivity;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

public class DialogController  {

    private ObservableList<ScanActivity> appMainObservableList;

    @FXML
    void fillTable(ActionEvent event) {

    	ScanActivity data = new ScanActivity();
        appMainObservableList.add(data);

    }

    public void setAppMainObservableList(ObservableList<ScanActivity> tvObservableList) {
        this.appMainObservableList = tvObservableList;

    }

}