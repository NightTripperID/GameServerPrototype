package gamestate;

import com.sun.istack.internal.NotNull;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class Intent {

    private Class<? extends GameState> mGsc;

    private Map<String, Byte> byteExtras;
    private Map<String, Character> characterExtras;
    private Map<String, Double> doubleExtras;
    private Map<String, Float> floatExtras;
    private Map<String, Integer> integerExtras;
    private Map<String, Long> longExtras;
    private Map<String, Serializable> serializableExtras;
    private Map<String, String> stringExtras;

    public Intent(@NotNull Class<? extends GameState> gsc) {
        mGsc = gsc;
    }

    public final Class<? extends GameState> getGsc() {
        return mGsc;
    }

    public final void putExtra(@NotNull String key, @NotNull Byte value) {
        if(byteExtras == null)
            byteExtras = new HashMap<>();

        byteExtras.put(key, value);
    }

    public final void putExtra(@NotNull String key, @NotNull Character value) {
        if(characterExtras == null)
            characterExtras = new HashMap<>();

        characterExtras.put(key, value);
    }

    public final void putExtra(@NotNull String key, @NotNull Double value) {
        if(doubleExtras == null)
            doubleExtras = new HashMap<>();

        doubleExtras.put(key, value);
    }

    public final void putExtra(@NotNull String key, @NotNull Float value) {
        if(floatExtras == null)
            floatExtras = new HashMap<>();

        floatExtras.put(key, value);
    }

    public final void putExtra(@NotNull String key, @NotNull Integer value) {
        if(integerExtras == null)
            integerExtras = new HashMap<>();

        integerExtras.put(key, value);
    }

    public final void putExtra(@NotNull String key, @NotNull Long value) {
        if(longExtras == null)
            longExtras = new HashMap<>();

        longExtras.put(key, value);
    }

    public final void putExtra(@NotNull String key, @NotNull Serializable value) {
        if(serializableExtras == null)
            serializableExtras = new HashMap<>();

        serializableExtras.put(key, value);
    }

    public final void putExtra(@NotNull String key, @NotNull String value) {
        if(stringExtras == null)
            stringExtras = new HashMap<>();

        stringExtras.put(key, value);
    }

    public final byte getByteExtra(@NotNull String key) {
        return byteExtras.get(key);
    }

    public final char getCharacterExtra(@NotNull String key) {
        return characterExtras.get(key);
    }

    public final float getFloatExtra(@NotNull String key) {
        return floatExtras.get(key);
    }

    public final double getDoubleExtra(@NotNull String key) {
        return doubleExtras.get(key);
    }

    public final int getIntegerExtra(@NotNull String key) {
        return integerExtras.get(key);
    }

    public final long getLongExtra(@NotNull String key) {
        return longExtras.get(key);
    }

    public final Serializable getSerializableExtra(@NotNull String key) {
        return serializableExtras.get(key);
    }

    public final String getStringExtra(@NotNull String key) {
        return stringExtras.get(key);
    }
}
