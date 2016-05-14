package me.benear.model;

import java.util.List;
import java.util.Set;

/**
 * Created by mreverter on 14/5/16.
 */
public interface BenearProvider {
    void requestResource(BeTag tag, Set<String> types, RequestCallback<List<BeResource>> callback);
}
