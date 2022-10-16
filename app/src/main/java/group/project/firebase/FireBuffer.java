package group.project.firebase;

import java.util.List;
import java.util.Map;

import group.project.util.Function;
import group.project.util.Supplier;

public interface FireBuffer {

    void write(String key, Object value);

    <T> T read(String key);

    default void writeBoolean(String key, boolean value) {
        this.write(key, value);
    }

    default boolean readBoolean(String key) {
        return this.read(key);
    }

    default void writeInt(String key, int value) {
        this.write(key, value);
    }

    default int readInt(String key) {
        return (int)this.readLong(key);
    }

    default void writeFloat(String key, float value) {
        this.write(key, value);
    }

    default float readFloat(String key) {
        return (float)this.readDouble(key);
    }

    default void writeLong(String key, long value) {
        this.write(key, value);
    }

    default long readLong(String key) {
        return this.read(key);
    }

    default void writeDouble(String key, double value) {
        this.write(key, value);
    }

    default double readDouble(String key) {
        return this.read(key);
    }

    default void writeChar(String key, char value) {
        this.writeString(key, value + "");
    }

    default char readChar(String key) {
        return this.readString(key).charAt(0);
    }

    default void writeEnum(String key, Enum<?> value) {
        this.write(key, value);
    }

    default <E extends Enum<E>> E readEnum(String key, Class<E> type) {
        return Enum.valueOf(type, this.readString(key));
    }

    default void writeString(String key, String value) {
        this.write(key, value);
    }

    default String readString(String key) {
        return this.read(key);
    }

    default void writeList(String key, List<?> value) {
        this.write(key, value);
    }

    default <E> List<E> readList(String key, Supplier<List<E>> supplier) {
        List<E> list = supplier.get();
        list.addAll(this.read(key));
        return list;
    }

    default void writeMap(String key, Map<?, ?> value) {
        this.write(key, value);
    }

    default <K, V> Map<K, V> readMap(String key) {
        return this.read(key);
    }

    default <K, V> Map<K, V> readMap(String key, Supplier<Map<K, V>> supplier) {
        Map<K, V> map = supplier.get();
        map.putAll(this.read(key));
        return map;
    }

    default <T extends IFireSerializable> void writeObject(String key, T value) {
        if(value == null) return;
        MemoryFireBuffer buffer = MemoryFireBuffer.empty();
        value.write(buffer);
        this.writeMap(key, buffer.toMap());
    }

    default <T extends IFireSerializable> T readObject(String key, Function<FireBuffer, T> value) {
        Map<String, Object> map = this.readMap(key);
        return map == null ? null : value.apply(MemoryFireBuffer.backing(map));
    }

}
