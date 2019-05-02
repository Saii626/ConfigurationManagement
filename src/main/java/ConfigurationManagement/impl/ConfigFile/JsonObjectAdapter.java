package ConfigurationManagement.impl.ConfigFile;

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.TypeAdapterFactory;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;
import java.util.function.Function;

public class JsonObjectAdapter implements TypeAdapterFactory {

    private static TypeAdapter jsonObjectAdapter;

    @Override
    public <T> TypeAdapter<T> create(Gson gson, TypeToken<T> type) {

        if (!type.equals(TypeToken.get(JsonObject.class))) {
            return null;
        }

        if (jsonObjectAdapter == null) {

            Function<Class, TypeAdapter> gsonAdapter = gson::getAdapter;

            jsonObjectAdapter = new TypeAdapter<T>() {

                @SuppressWarnings("unchecked")
                @Override
                public void write(JsonWriter out, T value) throws IOException {
                    if (value == null) {
                        out.nullValue();
                        return;
                    }

                    JsonObject objectToWrite = (JsonObject) value;
                    Class classToWrite = objectToWrite.getObject().getClass();

                    out.beginObject();
                    out.name(objectToWrite.getTypeName()).value(classToWrite.getName());

                    out.name(objectToWrite.getDataName());
                    String json = gsonAdapter.apply(classToWrite).toJson(objectToWrite.getObject());
                    out.value(json);
                    out.endObject();
                }

                @SuppressWarnings("unchecked")
                @Override
                public T read(JsonReader in) throws IOException {
                    if (in.peek() == JsonToken.NULL) {
                        in.nextNull();
                        return null;
                    }

                    JsonObject jsonObject = new JsonObject();

                    in.beginObject();

                    if (!in.nextName().equals(jsonObject.getTypeName())) {
                        throw new IOException("required parameter \""+jsonObject.getTypeName()+"\" not found");
                    }

                    String className = in.nextString();
                    try {
                        Class classType = Class.forName(className);
                        if (!in.nextName().equals(jsonObject.getDataName())) {
                            throw new IOException("required parameter \"" + jsonObject.getDataName() + "\" not found");
                        }

                        jsonObject.setObject(gsonAdapter.apply(classType).read(in));

                        return (T) jsonObject;
                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                        return null;
                    }

                }
            };
        }
        return jsonObjectAdapter;
    }
}
