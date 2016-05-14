package me.benear.model;

/**
 * Created by mreverter on 14/5/16.
 */
public interface BenearClient {
    void setBenearProvider(BenearProvider observable);
    void disconnect();
}
