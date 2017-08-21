package ws.dyt.recyclerviewadapter.pinned;

import java.util.ArrayList;

import ws.dyt.view.adapter.ItemWrapper;

/**
 * Created by yangxiaowei on 16/8/12.
 */
public class CourseResult {
    public String title;
    public ArrayList<Course> course_list;

    public class Course{
        public String title;
        public String name;
        public String length;
    }
}
