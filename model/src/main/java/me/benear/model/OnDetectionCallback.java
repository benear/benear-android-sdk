package me.benear.model;

import java.util.List;

/**
 * Created by mreverter on 14/5/16.
 */
public interface OnDetectionCallback<T> {
    void onDetected(List<T> resources);
}
