package me.benear.model;

/**
 * Created by mreverter on 14/5/16.
 */
public interface OnResponseCallback<T> {
    void onSuccess(T response);
    void onFailed();
}
