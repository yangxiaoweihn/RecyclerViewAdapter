package ws.dyt.recyclerviewadapter.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import ws.dyt.recyclerviewadapter.R;

/**
 * Created by yangxiaowei on 16/6/21.
 */
public class Data {
    public static List<SkinData> generate() {
        return new ArrayList(Arrays.asList(new SkinData[]{
                new SkinData("Hamburger", R.drawable.xy),
                new SkinData("SUPER MARIO", R.drawable.xy),
                new SkinData("默认皮肤", R.drawable.xy),
                new SkinData("ios风格", R.drawable.xy),
                new SkinData("保卫萝卜3", R.drawable.xy),
                new SkinData("欧洲杯", R.drawable.xy)
//                new SkinData("别字", R.drawable.xy),
//                new SkinData("素白", R.drawable.xy),
//                new SkinData("魔兽", R.drawable.xy),
//                new SkinData("X战警", R.drawable.xy),
//                new SkinData("彩漆怀旧", R.drawable.xy),
//                new SkinData("雪碧-击败炎热", R.drawable.xy),
//                new SkinData("呆萌树懒", R.drawable.xy),
//                new SkinData("江南水乡", R.drawable.xy),
//                new SkinData("四驱车", R.drawable.xy),
//                new SkinData("黄色机甲", R.drawable.xy),
//                new SkinData("阿狸·照亮路的月亮", R.drawable.xy),
//                new SkinData("一拳超人", R.drawable.xy),
//                new SkinData("粉色少女", R.drawable.xy),
//                new SkinData("草莓", R.drawable.xy),
//                new SkinData("少女心事", R.drawable.xy),
//                new SkinData("紫罗兰少女", R.drawable.xy),
//                new SkinData("Crazy Magic", R.drawable.xy),
//                new SkinData("蓝色简约", R.drawable.xy),
//                new SkinData("Brwon BEAR", R.drawable.xy),
//                new SkinData("Brwon BEAR", R.drawable.xy),
//                new SkinData("Brwon BEAR", R.drawable.xy),
//                new SkinData("Brwon BEAR", R.drawable.xy),
//                new SkinData("Brwon BEAR", R.drawable.xy),
//                new SkinData("Brwon BEAR", R.drawable.xy),
//                new SkinData("Brwon BEAR", R.drawable.xy),
//                new SkinData("Brwon BEAR", R.drawable.xy),
//                new SkinData("Brwon BEAR", R.drawable.xy),
//                new SkinData("Brwon BEAR", R.drawable.xy),
//                new SkinData("Brwon BEAR", R.drawable.xy),
//                new SkinData("Brwon BEAR", R.drawable.xy),
//                new SkinData("Brwon BEAR", R.drawable.xy),
//                new SkinData("Brwon BEAR", R.drawable.xy),
//                new SkinData("Brwon BEAR", R.drawable.xy),
//                new SkinData("Brwon BEAR", R.drawable.xy),
//                new SkinData("Brwon BEAR", R.drawable.xy),
//                new SkinData("Brwon BEAR", R.drawable.xy),
//                new SkinData("Brwon BEAR", R.drawable.xy),
//                new SkinData("Brwon BEAR", R.drawable.xy),
//                new SkinData("Brwon BEAR", R.drawable.xy),
//                new SkinData("Brwon BEAR", R.drawable.xy),
//                new SkinData("Brwon BEAR", R.drawable.xy),
//                new SkinData("Brwon BEAR", R.drawable.xy),
//                new SkinData("Brwon BEAR", R.drawable.xy),
//                new SkinData("Brwon BEAR", R.drawable.xy),
//                new SkinData("Brwon BEAR", R.drawable.xy),
//                new SkinData("Brwon BEAR", R.drawable.xy),
//                new SkinData("Brwon BEAR", R.drawable.xy),
//                new SkinData("Brwon BEAR", R.drawable.xy),
//                new SkinData("Brwon BEAR", R.drawable.xy),
//                new SkinData("Brwon BEAR", R.drawable.xy),
//                new SkinData("Brwon BEAR", R.drawable.xy),
//                new SkinData("Brwon BEAR", R.drawable.xy),
//                new SkinData("Brwon BEAR", R.drawable.xy),
//                new SkinData("Brwon BEAR", R.drawable.xy),
//                new SkinData("Brwon BEAR", R.drawable.xy),
//                new SkinData("Brwon BEAR", R.drawable.xy),
//                new SkinData("Brwon BEAR", R.drawable.xy),
//                new SkinData("Brwon BEAR", R.drawable.xy),
//                new SkinData("Brwon BEAR - 0", R.drawable.xy),
//                new SkinData("Brwon BEAR - 1", R.drawable.xy),
//                new SkinData("Brwon BEAR - 2", R.drawable.xy)
        }));
    }

    public static List<List<SkinData>> generateSection() {
        List<List<SkinData>> data = new ArrayList<>();
        data.add(new ArrayList(Arrays.asList(
                new SkinData[]{new SkinData("Hamburger", R.drawable.xy), new SkinData("SUPER MARIO", R.drawable.xy)})));
        data.add(
                new ArrayList(Arrays.asList(new SkinData[]{
                        new SkinData("默认皮肤", R.drawable.xy),
                        new SkinData("ios风格", R.drawable.xy),
                        new SkinData("保卫萝卜3", R.drawable.xy),
                        new SkinData("欧洲杯", R.drawable.xy),
                        new SkinData("别字", R.drawable.xy),
                        new SkinData("素白", R.drawable.xy),
                        new SkinData("魔兽", R.drawable.xy),
                        new SkinData("X战警", R.drawable.xy),
                        new SkinData("彩漆怀旧", R.drawable.xy),
                        new SkinData("雪碧-击败炎热", R.drawable.xy),
                        new SkinData("呆萌树懒", R.drawable.xy),
                        new SkinData("江南水乡", R.drawable.xy),
                        new SkinData("四驱车", R.drawable.xy),
                        new SkinData("黄色机甲", R.drawable.xy),
                        new SkinData("阿狸·照亮路的月亮", R.drawable.xy),
                        new SkinData("一拳超人", R.drawable.xy),
                        new SkinData("粉色少女", R.drawable.xy),
                        new SkinData("草莓", R.drawable.xy),
                        new SkinData("少女心事", R.drawable.xy),
                        new SkinData("紫罗兰少女", R.drawable.xy),
                        new SkinData("Crazy Magic", R.drawable.xy),
                        new SkinData("蓝色简约", R.drawable.xy),
                        new SkinData("Brwon BEAR", R.drawable.xy),
                        new SkinData("Brwon BEAR", R.drawable.xy),
                        new SkinData("Brwon BEAR", R.drawable.xy),
                        new SkinData("Brwon BEAR", R.drawable.xy),
                        new SkinData("Brwon BEAR", R.drawable.xy),
                        new SkinData("Brwon BEAR", R.drawable.xy),
                        new SkinData("Brwon BEAR", R.drawable.xy),
                        new SkinData("Brwon BEAR", R.drawable.xy),
                        new SkinData("Brwon BEAR", R.drawable.xy),
                        new SkinData("Brwon BEAR", R.drawable.xy),
                        new SkinData("Brwon BEAR", R.drawable.xy),
                        new SkinData("Brwon BEAR", R.drawable.xy),
                        new SkinData("Brwon BEAR", R.drawable.xy),
                        new SkinData("Brwon BEAR", R.drawable.xy),
                        new SkinData("Brwon BEAR", R.drawable.xy),
                        new SkinData("Brwon BEAR", R.drawable.xy),
                        new SkinData("Brwon BEAR", R.drawable.xy),
                        new SkinData("Brwon BEAR", R.drawable.xy),
                        new SkinData("Brwon BEAR", R.drawable.xy),
                        new SkinData("Brwon BEAR", R.drawable.xy),
                        new SkinData("Brwon BEAR", R.drawable.xy),
                        new SkinData("Brwon BEAR", R.drawable.xy),
                        new SkinData("Brwon BEAR", R.drawable.xy),
                        new SkinData("Brwon BEAR", R.drawable.xy),
                        new SkinData("Brwon BEAR", R.drawable.xy),
                        new SkinData("Brwon BEAR", R.drawable.xy),
                        new SkinData("Brwon BEAR", R.drawable.xy),
                        new SkinData("Brwon BEAR", R.drawable.xy),
                        new SkinData("Brwon BEAR", R.drawable.xy),
                        new SkinData("Brwon BEAR", R.drawable.xy),
                        new SkinData("Brwon BEAR", R.drawable.xy),
                        new SkinData("Brwon BEAR", R.drawable.xy),
                        new SkinData("Brwon BEAR", R.drawable.xy),
                        new SkinData("Brwon BEAR", R.drawable.xy),
                        new SkinData("Brwon BEAR", R.drawable.xy),
                        new SkinData("Brwon BEAR", R.drawable.xy),
                        new SkinData("Brwon BEAR", R.drawable.xy),
                        new SkinData("Brwon BEAR", R.drawable.xy),
                        new SkinData("Brwon BEAR", R.drawable.xy),
                        new SkinData("Brwon BEAR", R.drawable.xy),
                        new SkinData("Brwon BEAR", R.drawable.xy),
                        new SkinData("Brwon BEAR", R.drawable.xy),
                        new SkinData("Brwon BEAR", R.drawable.xy)
                })));
        return data;
    }

    public static class SkinData {
        public String title;
        public int img;

        public SkinData(String title, int img) {
            this.title = title;
            this.img = img;
        }
    }
}
