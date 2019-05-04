package ConfigurationManagement.impl.ConfigFile;

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.TypeAdapterFactory;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

public class JsonObjectAdapter implements TypeAdapterFactory {

    private static TypeAdapter jsonObjectAdapter;
    private static final Map<Class, Class> conversionMap = getWrapperTypes();

    private static Map<Class, Class> getWrapperTypes() {
        Map<Class, Class> map = new HashMap<>();
        map.put(Boolean.class, Boolean.class);

        map.put(Character.class, String.class);
        map.put(String.class, String.class);

        map.put(Byte.class, Integer.class);
        map.put(Short.class, Integer.class);
        map.put(Integer.class, Integer.class);

        map.put(Long.class, Long.class);

        map.put(Float.class, Double.class);
        map.put(Double.class, Double.class);

        return map;
    }

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
                    if (conversionMap.get(classToWrite) != null) {
                        Class c = conversionMap.get(classToWrite);
                        if (c.equals(Boolean.class)) {
                            out.value((Boolean) objectToWrite.getObject());
                        } else if (c.equals(String.class)) {
                            out.value((String) objectToWrite.getObject());
                        } else if (c.equals(Integer.class)) {
                            out.value((Integer) objectToWrite.getObject());
                        } else if (c.equals(Long.class)) {
                            out.value((Long) objectToWrite.getObject());
                        } else if (c.equals(Double.class)) {
                            out.value((Double) objectToWrite.getObject());
                        }
                    } else {
                        String json = gsonAdapter.apply(classToWrite).toJson(objectToWrite.getObject());
                        out.value(json);
                    }
//
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

                        if (conversionMap.get(classType) != null) {
                            Class c = conversionMap.get(classType);
                            if (c.equals(Boolean.class)) {
                                jsonObject.setObject(in.nextBoolean());
                            } else if (c.equals(String.class)) {
                                jsonObject.setObject(in.nextString());
                            } else if (c.equals(Integer.class)) {
                                jsonObject.setObject(in.nextInt());
                            } else if (c.equals(Long.class)) {
                                jsonObject.setObject(in.nextLong());
                            } else if (c.equals(Double.class)) {
                                jsonObject.setObject(in.nextDouble());
                            }
                        } else {
                            String jsonObj = in.nextString();
                            jsonObject.setObject(gsonAdapter.apply(classType).fromJson(jsonObj));
                        }

                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                        return null;
                    }

                    in.endObject();
                    return (T) jsonObject;
                }
            };
        }
        return jsonObjectAdapter;
    }
}
