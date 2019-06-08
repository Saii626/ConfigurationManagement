package app.saikat.ConfigurationManagement.Gson;

import java.io.IOException;

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.TypeAdapterFactory;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

public class PostProcessingAdapterFactory implements TypeAdapterFactory {

    @Override
    public <T> TypeAdapter<T> create(Gson gson, TypeToken<T> type) {
        TypeAdapter<T> defaultAdapter = gson.getDelegateAdapter(this, type);

        return new TypeAdapter<T>() {

            @Override
            public void write(JsonWriter out, T value) throws IOException {
                defaultAdapter.write(out, value);
            }

            @Override
            public T read(JsonReader in) throws IOException {
                T obj = defaultAdapter.read(in);
                if (obj instanceof PostProcessable) {
                    ((PostProcessable) obj).postProcess();
                }

                return obj;
            }
        };
    }

}