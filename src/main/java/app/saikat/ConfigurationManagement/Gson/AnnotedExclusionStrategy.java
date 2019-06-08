package app.saikat.ConfigurationManagement.Gson;

import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;

public class AnnotedExclusionStrategy implements ExclusionStrategy {

    @Override
    public boolean shouldSkipField(FieldAttributes f) {
        return f.getAnnotation(Exclude.class) != null;
    }

    @Override
    public boolean shouldSkipClass(Class<?> clazz) {
        return false;
    }

}