package com.example.metroapplication.apis.apiModel;

public class ChangePassModule {

    private int channelId;

    private String tokenId;

    ChangePassData payload;

    public int getChannelId() {
        return channelId;
    }

    public void setChannelId(int channelId) {
        this.channelId = channelId;
    }

    public String getTokenId() {
        return tokenId;
    }

    public void setTokenId(String tokenId) {
        this.tokenId = tokenId;
    }

    public ChangePassData getPayload() {
        return payload;
    }

    public void setPayload(ChangePassData payload) {
        this.payload = payload;
    }

}
