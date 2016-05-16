package me.benear.model;

import java.util.ArrayList;
import java.util.List;

import me.benear.model.utils.JsonUtil;

/**
 * Created by mreverter on 15/5/16.
 */
public class ResourceBuilder {
    public static <T> T getCustomizedResource(Class<T> tClass, BeResource resource) {
        return JsonUtil.getInstance().fromJson(resource.getMetadata().toString(), tClass);
    }

    public static <T> List<T> getCustomizedResources(Class<T> tClass, List<BeResource> resourcesOfType) {
        List<T> list = new ArrayList<T>();
        for(BeResource resource : resourcesOfType) {
            list.add(ResourceBuilder.getCustomizedResource(tClass, resource));
        }
        return list;
    }
}
