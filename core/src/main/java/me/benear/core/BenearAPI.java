package me.benear.core;

import java.util.Iterator;
import java.util.List;
import java.util.Set;

import me.benear.core.services.ResourceService;
import me.benear.model.BeResource;
import me.benear.model.BeTag;
import me.benear.model.BenearProvider;
import me.benear.model.OnResponseCallback;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by mreverter on 14/5/16.
 */
public class BenearAPI implements BenearProvider {

    private Retrofit mRetrofit;
    private BenearAPI(BenearBuilder benearBuilder) {
        mRetrofit = new Retrofit.Builder()
                .baseUrl("http://private-ff60b5-benearme.apiary-mock.com")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    @Override
    public void requestResource(BeTag tag, Set<String> types, final OnResponseCallback<List<BeResource>> callback) {
        ResourceService service = mRetrofit.create(ResourceService.class);

        String typesQueryList = "";
        Iterator<String> iterator = types.iterator();
        while (iterator.hasNext()) {
            typesQueryList += iterator.next();
            if (iterator.hasNext()) {
                typesQueryList += ",";
            }
        }

        Callback<List<BeResource>> retrofitCallback = new Callback<List<BeResource>>() {
            @Override
            public void onResponse(Call<List<BeResource>> call, Response<List<BeResource>> response) {
                callback.onSuccess(response.body());
            }

            @Override
            public void onFailure(Call<List<BeResource>> call, Throwable t) {
                //handle
            }
        };

        Call<List<BeResource>> call = service.getResourcesCall(tag.getId(), typesQueryList);
        call.enqueue(retrofitCallback);
    }

    public static class BenearBuilder {

        public BenearBuilder() {}

        public BenearAPI build() {
            return new BenearAPI(this);
        }
    }
}
