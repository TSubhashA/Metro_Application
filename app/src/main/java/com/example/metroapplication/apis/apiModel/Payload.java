package com.example.metroapplication.apis.apiModel;

import java.util.ArrayList;

public class Payload {
    private ArrayList<ListRouteDetail> listRouteDetail;
    private ArrayList<ListFare> listFare;

    public ArrayList<ListRouteDetail> getListRouteDetail() {
        return this.listRouteDetail;
    }

    public void setListRouteDetail(ArrayList<ListRouteDetail> listRouteDetail) {
        this.listRouteDetail = listRouteDetail;
    }

    public ArrayList<ListFare> getListFare() {
        return this.listFare;
    }

    public void setListFare(ArrayList<ListFare> listFare) {
        this.listFare = listFare;
    }

}