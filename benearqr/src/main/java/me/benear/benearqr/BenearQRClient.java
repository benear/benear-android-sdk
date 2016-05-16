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
import me.benear.model.OnResponseCallback;
import me.benear.model.ResourceBuilder;

/**
 * Created by mreverter on 14/5/16.
 */
public class BenearQRClient implements BenearClient {

    private Map<String, OnDetectionCallback> mTypeOnDetectionCallbacksMap;
    private Map<String, Class> mTypeClassMap;
    private BenearProvider mBenearProvider;
    private Boolean mConnected;

    public BenearQRClient(BenearQRBuilder benearQRBuilder) {
        mTypeOnDetectionCallbacksMap = benearQRBuilder.mTypeOnDetectionCallbackMap;
        mTypeClassMap = benearQRBuilder.mTypeClassMap;
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

            Map<String, List<BeResource>> typeResourcesMap = getTypeResourcesMap(resources);

            for (String type : typeResourcesMap.keySet()) {

                List<BeResource> resourcesOfType = typeResourcesMap.get(type);

                Class typeClass = mTypeClassMap.get(type);

                List list = ResourceBuilder.getCustomizedResources(typeClass, resourcesOfType);

                mTypeOnDetectionCallbacksMap.get(type).onDetected(list);
            }
        }
    }

    private Map<String, List<BeResource>> getTypeResourcesMap(List<BeResource> resources) {

        HashMap<String, List<BeResource>> resourcesForTypeMap = new HashMap<>();

        for (BeResource resource : resources) {
            if (resourcesForTypeMap.get(resource.getType()) == null) {
                resourcesForTypeMap.put(resource.getType(), new ArrayList<BeResource>());
            }
            resourcesForTypeMap.get(resource.getType()).add(resource);
        }
        return resourcesForTypeMap;
    }

    public void scanQR() {
        //TODO Scan data
        BeTag tag = new BeTag();
        tag.setId("1");

        this.mConnected = true;
        OnResponseCallback<List<BeResource>> onResponseCallback = new OnResponseCallback<List<BeResource>>() {
            @Override
            public void onSuccess(List<BeResource> response) {
                onResourcesDetected(response);
            }

            @Override
            public void onFailed() {

            }
        };

        mBenearProvider.requestResource(tag, this.mTypeClassMap.keySet(), onResponseCallback);
    }

    public static class BenearQRBuilder {

        private Map<String, OnDetectionCallback> mTypeOnDetectionCallbackMap;
        private Map<String, Class> mTypeClassMap;

        public BenearQRBuilder() {
            mTypeClassMap = new HashMap<>();
            mTypeOnDetectionCallbackMap = new HashMap<>();
        }

        public <T> BenearQRBuilder addOnDetectionCallbackForType(Class tClass, OnDetectionCallback<T> onDetectionCallback) {
            mTypeOnDetectionCallbackMap.put(tClass.getSimpleName(), onDetectionCallback);
            mTypeClassMap.put(tClass.getSimpleName(), tClass);
            return this;
        }

        public BenearQRClient build() {
            return new BenearQRClient(this);
        }
    }
}
