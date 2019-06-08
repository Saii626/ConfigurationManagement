package app.saikat.ConfigurationManagement;

import com.google.gson.annotations.JsonAdapter;

import app.saikat.ConfigurationManagement.Gson.Exclude;
import app.saikat.ConfigurationManagement.Gson.JsonClassAdapter;
import app.saikat.ConfigurationManagement.Gson.PostProcessable;

public class GsonTestFile implements PostProcessable{

    @Exclude
    private String excludedString;
    
    @JsonAdapter(JsonClassAdapter.class)
    private Class jsonClass;

    private GsonTestObject gsonObject;
    private int gsonInteger;
    private char gsonChar;
    private long gsonLong;
    private double gsonDouble;

    public static boolean postProcessRan = false;

    public static GsonTestFile createDeafult() {

        GsonTestFile testFile = new GsonTestFile();
        testFile.excludedString = "excluded";

        testFile.jsonClass = GsonTestObject.class;

        testFile.gsonObject = new GsonTestObject();
        testFile.gsonInteger = 5;
        testFile.gsonChar = 'c';
        testFile.gsonDouble = 12.55454;
        testFile.gsonLong = 5413154644544L;

        return testFile;
    }

    private GsonTestFile() {}

    public GsonTestObject getGsonObject() {
        return gsonObject;
    }

    public int getGsonInteger() {
        return gsonInteger;
    }

    public char getGsonChar() {
        return gsonChar;
    }

    public long getGsonLong() {
        return gsonLong;
    }

    public double getGsonDouble() {
        return gsonDouble;
    }

    public Class getJsonClass() {
        return jsonClass;
    }

    public String getExcludedString() {
        return excludedString;
    }

    @Override
    public void postProcess() {
        postProcessRan = true;
    }
}