package ws.dyt.recyclerviewadapter.databinding.viewmodels;

import android.databinding.BaseObservable;
import android.databinding.Bindable;

import ws.dyt.recyclerviewadapter.BR;
import ws.dyt.recyclerviewadapter.pinned.CourseResult;

/**
 * Created by yangxiaowei on 2018/1/22.
 */

public class CourseViewModel extends BaseObservable{

    @Bindable
    public String chapterName;
    @Bindable
    public String className;
    @Bindable
    public String duration;

    public CourseViewModel(CourseResult.Course course) {
        this.by(course);
    }

    public void by(CourseResult.Course course) {
        this.chapterName = course.title;
        this.className = course.name;
        this.duration = course.length;

//        notifyPropertyChanged(BR._all);
        notifyPropertyChanged(BR.chapterName);
        notifyPropertyChanged(BR.className);
        notifyPropertyChanged(BR.duration);
    }
}
