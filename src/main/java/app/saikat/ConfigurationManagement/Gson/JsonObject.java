package app.saikat.ConfigurationManagement.Gson;

import com.google.gson.annotations.JsonAdapter;

@JsonAdapter(JsonObjectAdapter.class)
public class JsonObject {
    private Object object;
    private String typeName, dataName;

    public JsonObject(Object object) {
        this.object = object;
        this.typeName = "type";
        this.dataName = "data";
    }

    public JsonObject() {
        this(null);
    }

    public Object getObject() {
        return object;
    }

    public String getTypeName() {
        return typeName;
    }

    public String getDataName() {
        return dataName;
    }

    public void setObject(Object object) {
        this.object = object;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public void setDataName(String dataName) {
        this.dataName = dataName;
    }
}
