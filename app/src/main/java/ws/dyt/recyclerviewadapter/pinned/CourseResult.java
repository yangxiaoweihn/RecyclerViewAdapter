package ws.dyt.recyclerviewadapter.pinned;

import java.util.ArrayList;

/**
 * Created by yangxiaowei on 16/8/12.
 */
public class CourseResult {
    public String title;
    public ArrayList<Course> course_list;

    public static class Course{
        public String title;
        public String name;
        public String length;

        public Course(String title) {
            this.title = title;
        }
    }
}
