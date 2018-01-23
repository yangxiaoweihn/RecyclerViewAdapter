package ws.dyt.recyclerviewadapter.utils;

import android.content.res.Resources;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

import ws.dyt.recyclerviewadapter.R;
import ws.dyt.recyclerviewadapter.pinned.CourseResult;
import ws.dyt.recyclerviewadapter.swipe.News;

/**
 * Created by yangxiaowei on 16/6/21.
 */
public class Data {

    public static List<News> generateNews(Resources res) {
        String json = FileUtils.readRawFile(res, R.raw.news);
        List<News> data = new Gson().fromJson(json, new TypeToken<ArrayList<News>>(){}.getType());
        return data;
    }

    public static List<CourseResult> generateCourses(Resources res) {
        String json = FileUtils.readRawFile(res, R.raw.classes_pinned);

        List<CourseResult> results = new Gson().fromJson(json, new TypeToken<ArrayList<CourseResult>>() {
        }.getType());

        return results;
    }
}
