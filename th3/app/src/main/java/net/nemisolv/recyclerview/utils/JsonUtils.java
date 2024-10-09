package net.nemisolv.recyclerview.utils;

import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import net.nemisolv.recyclerview.data.model.Student;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.util.List;

public class JsonUtils {

    public static List<Student> readStudentsFromJson(Context context) {
        String json = null;
        try {
            InputStream inputStream = context.getAssets().open("students.json");
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            StringBuilder stringBuilder = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                stringBuilder.append(line);
            }
            json = stringBuilder.toString();
            reader.close();
        } catch (IOException e) {
            Log.e("JsonUtils", "Error reading JSON file", e);
        }

        // Parse JSON using Gson
        Gson gson = new Gson();
        Type studentListType = new TypeToken<List<Student>>() {}.getType();
        return gson.fromJson(json, studentListType);
    }
}
