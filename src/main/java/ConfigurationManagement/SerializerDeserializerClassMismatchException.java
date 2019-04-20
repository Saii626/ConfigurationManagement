package ConfigurationManagement;

public class SerializerDeserializerClassMismatchException extends Exception{

    public SerializerDeserializerClassMismatchException(Class serializer, Class deserializer) {
        super(String.format("Serializer: %s\t\tDeserializer: %s", serializer.getSimpleName(), deserializer.getSimpleName()));
    }
}
