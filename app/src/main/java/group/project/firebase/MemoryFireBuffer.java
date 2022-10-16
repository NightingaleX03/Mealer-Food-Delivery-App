package group.project.firebase;

import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class MemoryFireBuffer implements FireBuffer {

    private Map<String, Object> delegate;

    private MemoryFireBuffer(Map<String, Object> delegate) {
        this.delegate = delegate;
    }

    public static MemoryFireBuffer empty() {
        return new MemoryFireBuffer(new LinkedHashMap<>());
    }

    public static MemoryFireBuffer backing(Map<String, Object> delegate) {
        return new MemoryFireBuffer(new HashMap<>(delegate));
    }

    @Override
    public void write(String key, Object value) {
        this.delegate.put(key, value);
    }

    @Override
    public <T> T read(String key) {
        return (T)this.delegate.get(key);
    }

    public Map<String, Object> toMap() {
        return Collections.unmodifiableMap(this.delegate);
    }

}
