package me.benear.benearqr;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import me.benear.model.BeResource;
import me.benear.model.BeTag;
import me.benear.model.BenearClient;
import me.benear.model.BenearProvider;
import me.benear.model.OnDetectionCallback;
import me.benear.model.RequestCallback;

/**
 * Created by mreverter on 14/5/16.
 */
public class BenearQRClient implements BenearClient {

    private Map<String, OnDetectionCallback> mCallbacksForTypes;
    private BenearProvider mBenearProvider;
    private Boolean mConnected;

    public BenearQRClient(BenearQRBuilder benearQRBuilder) {
        mCallbacksForTypes = benearQRBuilder.mCallbacksForTypes;
    }

    @Override
    public void setBenearProvider(BenearProvider provider) {
        mBenearProvider = provider;
    }

    @Override
    public void disconnect() {
        mConnected = false;
    }

    public void onResourcesDetected(List<BeResource> resources) {
        if(mConnected) {
            Map<String, List<BeResource>> foundResources = new HashMap<>();

            for (BeResource resource : resources) {

                if (foundResources.get(resource.getType()) == null) {
                    foundResources.put(resource.getType(), new ArrayList<BeResource>());
                }
                foundResources.get(resource.getType()).add(resource);
            }

            for (String type : foundResources.keySet()) {
                if (mCallbacksForTypes.containsKey(type)) {
                    mCallbacksForTypes.get(type).onDetected(foundResources.get(type));
                }
            }
        }
    }

    public void scanQR() {
        //TODO Scan data
        BeTag tag = new BeTag();
        tag.setId("1");

        this.mConnected = true;
        RequestCallback<List<BeResource>> callback = new RequestCallback<List<BeResource>>() {
            @Override
            public void onSuccess(List<BeResource> response) {
                onResourcesDetected(response);
            }

            @Override
            public void onFailed() {

            }
        };

        mBenearProvider.requestResource(tag, mCallbacksForTypes.keySet(), callback);
    }

    public static class BenearQRBuilder {

        private Map<String, OnDetectionCallback> mCallbacksForTypes;

        public BenearQRBuilder() {
            mCallbacksForTypes = new HashMap<>();
        }

        public BenearQRBuilder addOnDetectionCallbackForType(String type, OnDetectionCallback onDetectionCallback) {
            mCallbacksForTypes.put(type, onDetectionCallback);
            return this;
        }

        public BenearQRClient build() {
            return new BenearQRClient(this);
        }
    }
}
