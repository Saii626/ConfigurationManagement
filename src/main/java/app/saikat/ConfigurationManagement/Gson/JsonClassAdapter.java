package app.saikat.ConfigurationManagement.Gson;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;

public class JsonClassAdapter extends TypeAdapter<Class> {
    @Override
    public void write(JsonWriter out, Class value) throws IOException {
        out.value(value.getName());
    }

    @Override
    public Class read(JsonReader in) throws IOException {
        String className = in.nextString();
        try {
            return Class.forName(className);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }
}
