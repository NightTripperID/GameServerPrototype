package gamestate;

import com.sun.istack.internal.NotNull;

import java.io.Serializable;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.DoubleStream;
import java.util.stream.IntStream;
import java.util.stream.LongStream;

public class Bundle implements Serializable {

    public static final long serialVersionUID = 201710161356L;

    private Map<String, Byte> byteExtras;
    private Map<String, Character> characterExtras;
    private Map<String, Double> doubleExtras;
    private Map<String, Float> floatExtras;
    private Map<String, Integer> integerExtras;
    private Map<String, Long> longExtras;
    private Map<String, Serializable> serializableExtras;
    private Map<String, String> stringExtras;

    private Map<String, Byte[]> byteArrayExtras;
    private Map<String, Character[]> characterArrayExtras;
    private Map<String, Double[]> doubleArrayExtras;
    private Map<String, Float[]> floatArrayExtras;
    private Map<String, Integer[]> integerArrayExtras;
    private Map<String, Long[]> longArrayExtras;
    private Map<String, Serializable[]> serializableArrayExtras;
    private Map<String, String[]> stringArrayExtras;


    public final void putExtra(@NotNull String key, byte value) {
        if(byteExtras == null)
            byteExtras = new HashMap<>();
        byteExtras.put(key, value);
    }

    public final void putExtra(@NotNull String key, char value) {
        if(characterExtras == null)
            characterExtras = new HashMap<>();
        characterExtras.put(key, value);
    }

    public final void putExtra(@NotNull String key, double value) {
        if(doubleExtras == null)
            doubleExtras = new HashMap<>();
        doubleExtras.put(key, value);
    }

    public final void putExtra(@NotNull String key, float value) {
        if(floatExtras == null)
            floatExtras = new HashMap<>();
        floatExtras.put(key, value);
    }

    public final void putExtra(@NotNull String key, int value) {
        if(integerExtras == null)
            integerExtras = new HashMap<>();
        integerExtras.put(key, value);
    }

    public final void putExtra(@NotNull String key, long value) {
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

    public final void putExtra(@NotNull String key, byte[] value) {
        if(byteArrayExtras == null)
            byteArrayExtras = new HashMap<>();
        Byte[] result = new Byte[value.length];
        int index = 0;
        for(byte b: value)
            result[index++] = b;
        byteArrayExtras.put(key, result);
    }

    public final void putExtra(@NotNull String key, char[] value) {
        if(characterArrayExtras == null)
            characterArrayExtras = new HashMap<>();
        Character[] result = new Character[value.length];
        int index = 0;
        for(Character c: value)
            result[index++] = c;
        characterArrayExtras.put(key, result);
    }

    public final void putExtra(@NotNull String key, double[] value) {
        if(doubleArrayExtras == null)
            doubleArrayExtras = new HashMap<>();
        Double[] result = DoubleStream.of(value).boxed().toArray(Double[]::new);
        doubleArrayExtras.put(key, result);
    }

    public final void putExtra(@NotNull String key, float[] value) {
        if(floatArrayExtras == null)
            floatArrayExtras = new HashMap<>();
        Float[] result = new Float[value.length];
        int index = 0;
        for(Float f: value)
            result[index++] = f;
        floatArrayExtras.put(key, result);
    }

    public final void putExtra(@NotNull String key, int[] value) {
        if(integerArrayExtras == null)
            integerArrayExtras = new HashMap<>();
        Integer[] result = IntStream.of(value).boxed().toArray(Integer[]::new);
        integerArrayExtras.put(key, result);
    }

    public final void putExtra(@NotNull String key, long[] value) {
        if(longArrayExtras == null)
            longArrayExtras = new HashMap<>();
        Long[] result = LongStream.of(value).boxed().toArray(Long[]::new);
        longArrayExtras.put(key, result);
    }

    public final void putExtra(@NotNull String key, @NotNull Serializable[] value) {
        if(serializableArrayExtras == null)
            serializableArrayExtras = new HashMap<>();
        serializableArrayExtras.put(key, value);
    }

    public final void putExtra(@NotNull String key, @NotNull String[] value) {
        if(stringArrayExtras == null)
            stringArrayExtras = new HashMap<>();
        stringArrayExtras.put(key, value);
    }

    public final byte getByteExtra(@NotNull String key) {
        return byteExtras.get(key);
    }

    public final char getCharacterExtra(@NotNull String key) {
        return characterExtras.get(key);
    }

    public final double getDoubleExtra(@NotNull String key) {
        return doubleExtras.get(key);
    }

    public final float getFloatExtra(@NotNull String key) {
        return floatExtras.get(key);
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

    public final byte[] getByteArrayExtra(@NotNull String key) {
        Byte[] boxed = byteArrayExtras.get(key);
        byte[] unboxed = new byte[boxed.length];
        int index = 0;
        for(Byte b: boxed)
            unboxed[index++] = b;
        return unboxed;
    }

    public final char[] getCharacterArrayExtra(@NotNull String key) {
        Character[] boxed = characterArrayExtras.get(key);
        char[] unboxed = new char[boxed.length];
        int index = 0;
        for(Character c: boxed)
            unboxed[index++] = c;
        return unboxed;
    }

    public final double[] getDoubleArrayExtra(@NotNull String key) {
        return Arrays.stream(doubleArrayExtras.get(key)).mapToDouble(Double::doubleValue).toArray();
    }

    public final float[] getFloatArrayExtra(@NotNull String key) {
        Float[] boxed = floatArrayExtras.get(key);
        float[] unboxed = new float[boxed.length];
        int index = 0;
        for(Float f: boxed)
            unboxed[index++] = f;
        return unboxed;
    }

    public final int[] getIntegerArrayExtra(@NotNull String key) {
        return Arrays.stream(integerArrayExtras.get(key)).mapToInt(Integer::intValue).toArray();
    }

    public final long[] getLongArrayExtra(@NotNull String key) {
        return Arrays.stream(longArrayExtras.get(key)).mapToLong(Long::longValue).toArray();
    }

    @NotNull
    public final Serializable[] getSerializableArrayExtra(@NotNull String key) {
        return serializableArrayExtras.get(key);
    }

    @NotNull
    public final String[] getStringArrayExtra(@NotNull String key) {
        return stringArrayExtras.get(key);
    }
}
