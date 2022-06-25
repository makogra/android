package com.mako.heroslandidle.database.typeconverters;

import androidx.room.ProvidedTypeConverter;
import androidx.room.TypeConverter;

import java.util.Arrays;


public class intArrayConverter {

    private static final String SEPARATOR = ",";

    @TypeConverter
    public int[] StringToIntArray(String string){
        if (string == null)
            return null;
        String[] strings = string.split(SEPARATOR);
        int[] result = new int[strings.length];
        for (int i = 0; i < strings.length; i++) {
            result[i] = Integer.parseInt(strings[i]);
        }
        return result;
    }

    @TypeConverter
    public String IntArrayToString(int[] intArr){
        if (intArr == null)
            return null;
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(intArr[0]);
        for (int i = 1; i < intArr.length; i++) {
            stringBuilder.append(SEPARATOR)
                    .append(intArr[i]);
        }
        return stringBuilder.toString();
    }
}
