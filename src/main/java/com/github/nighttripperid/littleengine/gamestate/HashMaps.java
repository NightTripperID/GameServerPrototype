/*
 * Copyright (c) 2021, BitBurger, Evan Doering
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 *     Redistributions of source code must retain the above copyright notice,
 *     this list of conditions and the following disclaimer.
 *
 *     Redistributions in binary form must reproduce the above copyright notice,
 *     this list of conditions and the following disclaimer in the documentation
 *     and/or other materials provided with the distribution.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 */

package com.github.nighttripperid.littleengine.gamestate;

import java.io.Serializable;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.DoubleStream;
import java.util.stream.IntStream;
import java.util.stream.LongStream;

/**
 * Object representing a bundle of primitive and complex objexts. Values are stored in lazily instantiated HashMaps.
 */

public abstract class HashMaps implements Serializable {

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


    public final void putExtra(String key, byte value) {
        if(byteExtras == null)
            byteExtras = new HashMap<>();
        byteExtras.put(key, value);
    }

    public final void putExtra(String key, char value) {
        if(characterExtras == null)
            characterExtras = new HashMap<>();
        characterExtras.put(key, value);
    }

    public final void putExtra(String key, double value) {
        if(doubleExtras == null)
            doubleExtras = new HashMap<>();
        doubleExtras.put(key, value);
    }

    public final void putExtra(String key, float value) {
        if(floatExtras == null)
            floatExtras = new HashMap<>();
        floatExtras.put(key, value);
    }

    public final void putExtra(String key, int value) {
        if(integerExtras == null)
            integerExtras = new HashMap<>();
        integerExtras.put(key, value);
    }

    public final void putExtra(String key, long value) {
        if(longExtras == null)
            longExtras = new HashMap<>();
        longExtras.put(key, value);
    }

    public final void putExtra(String key, Serializable value) {
        if(serializableExtras == null)
            serializableExtras = new HashMap<>();
        serializableExtras.put(key, value);
    }

    public final void putExtra(String key, String value) {
        if(stringExtras == null)
            stringExtras = new HashMap<>();
        stringExtras.put(key, value);
    }

    public final void putExtra(String key, byte[] value) {
        if(byteArrayExtras == null)
            byteArrayExtras = new HashMap<>();
        Byte[] result = new Byte[value.length];
        int index = 0;
        for(byte b: value)
            result[index++] = b;
        byteArrayExtras.put(key, result);
    }

    public final void putExtra(String key, char[] value) {
        if(characterArrayExtras == null)
            characterArrayExtras = new HashMap<>();
        Character[] result = new Character[value.length];
        int index = 0;
        for(Character c: value)
            result[index++] = c;
        characterArrayExtras.put(key, result);
    }

    public final void putExtra(String key, double[] value) {
        if(doubleArrayExtras == null)
            doubleArrayExtras = new HashMap<>();
        Double[] result = DoubleStream.of(value).boxed().toArray(Double[]::new);
        doubleArrayExtras.put(key, result);
    }

    public final void putExtra(String key, float[] value) {
        if(floatArrayExtras == null)
            floatArrayExtras = new HashMap<>();
        Float[] result = new Float[value.length];
        int index = 0;
        for(Float f: value)
            result[index++] = f;
        floatArrayExtras.put(key, result);
    }

    public final void putExtra(String key, int[] value) {
        if(integerArrayExtras == null)
            integerArrayExtras = new HashMap<>();
        Integer[] result = IntStream.of(value).boxed().toArray(Integer[]::new);
        integerArrayExtras.put(key, result);
    }

    public final void putExtra(String key, long[] value) {
        if(longArrayExtras == null)
            longArrayExtras = new HashMap<>();
        Long[] result = LongStream.of(value).boxed().toArray(Long[]::new);
        longArrayExtras.put(key, result);
    }

    public final void putExtra(String key, Serializable[] value) {
        if(serializableArrayExtras == null)
            serializableArrayExtras = new HashMap<>();
        serializableArrayExtras.put(key, value);
    }

    public final void putExtra(String key, String[] value) {
        if(stringArrayExtras == null)
            stringArrayExtras = new HashMap<>();
        stringArrayExtras.put(key, value);
    }

    public final byte getByteExtra(String key) {
        return byteExtras.get(key);
    }

    public final char getCharacterExtra(String key) {
        return characterExtras.get(key);
    }

    public final double getDoubleExtra(String key) {
        return doubleExtras.get(key);
    }

    public final float getFloatExtra(String key) {
        return floatExtras.get(key);
    }

    public final int getIntegerExtra(String key) {
        return integerExtras.get(key);
    }

    public final long getLongExtra(String key) {
        return longExtras.get(key);
    }

    public final Serializable getSerializableExtra(String key) {
        if(serializableExtras == null)
            serializableExtras = new HashMap<>();
        return serializableExtras.get(key);
    }

    public final String getStringExtra(String key) {
        return stringExtras.get(key);
    }

    public final byte[] getByteArrayExtra(String key) {
        Byte[] boxed = byteArrayExtras.get(key);
        byte[] unboxed = new byte[boxed.length];
        int index = 0;
        for(Byte b: boxed)
            unboxed[index++] = b;
        return unboxed;
    }

    public final char[] getCharacterArrayExtra(String key) {
        Character[] boxed = characterArrayExtras.get(key);
        char[] unboxed = new char[boxed.length];
        int index = 0;
        for(Character c: boxed)
            unboxed[index++] = c;
        return unboxed;
    }

    public final double[] getDoubleArrayExtra(String key) {
        return Arrays.stream(doubleArrayExtras.get(key)).mapToDouble(Double::doubleValue).toArray();
    }

    public final float[] getFloatArrayExtra(String key) {
        Float[] boxed = floatArrayExtras.get(key);
        float[] unboxed = new float[boxed.length];
        int index = 0;
        for(Float f: boxed)
            unboxed[index++] = f;
        return unboxed;
    }

    public final int[] getIntegerArrayExtra(String key) {
        return Arrays.stream(integerArrayExtras.get(key)).mapToInt(Integer::intValue).toArray();
    }

    public final long[] getLongArrayExtra(String key) {
        return Arrays.stream(longArrayExtras.get(key)).mapToLong(Long::longValue).toArray();
    }


    public final Serializable[] getSerializableArrayExtra(String key) {
        return serializableArrayExtras.get(key);
    }


    public final String[] getStringArrayExtra(String key) {
        return stringArrayExtras.get(key);
    }
}
