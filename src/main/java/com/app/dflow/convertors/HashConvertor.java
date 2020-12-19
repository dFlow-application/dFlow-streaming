package com.app.dflow.convertors;

import com.google.common.hash.HashFunction;
import com.google.common.hash.Hashing;

import java.nio.charset.StandardCharsets;

public class HashConvertor {
    static HashFunction hashFunction = Hashing.sha256();

    private HashConvertor(){}

    public static String convertToHash(String targetValue) {
        return hashFunction.hashString(targetValue, StandardCharsets.UTF_8).toString();
    }
}
