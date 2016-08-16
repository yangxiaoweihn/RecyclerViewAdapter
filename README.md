## 控件简介
    RecyclerView的数据适配器，主要拥有以下功能：
    1. 可以添加、删除任意数量的头部、尾部（就像ListView那样）
    2. 可以添加系统头部、尾部（数量和使用上做了限制，在使用上尽量不要去使用这部分），具体说明将会在之后的文章中进行阐述。
    3. 可以针对不同的item添加滑动菜单（目前只支持左滑、右滑），并且菜单定制性高
    4. 支持粘性视图，同时支持滑动菜单
## 添加依赖
```
    compile 'ws.dyt.view:recyclerview-adapter-hf:2.0'
```
## 屏幕截图
![RecyclerViewAdapter](https://raw.githubusercontent.com/yangxiaoweihn/RecyclerViewAdapter/master/app/screenshots/1.png)
![RecyclerViewAdapter](https://raw.githubusercontent.com/yangxiaoweihn/RecyclerViewAdapter/master/app/screenshots/2.png)


![RecyclerViewAdapter](https://raw.githubusercontent.com/yangxiaoweihn/RecyclerViewAdapter/master/app/screenshots/3.png)
![RecyclerViewAdapter](https://raw.githubusercontent.com/yangxiaoweihn/RecyclerViewAdapter/master/app/screenshots/4.png)


![RecyclerViewAdapter](https://raw.githubusercontent.com/yangxiaoweihn/RecyclerViewAdapter/master/app/screenshots/5.png)
![RecyclerViewAdapter](https://raw.githubusercontent.com/yangxiaoweihn/RecyclerViewAdapter/master/app/screenshots/6.png)

![RecyclerViewAdapter](https://raw.githubusercontent.com/yangxiaoweihn/RecyclerViewAdapter/master/app/screenshots/7.png)
    
## 使用
-   与系统提供的Adapter使用方式无异（具体api参看demo）
```java
    ws.dyt.view.adapter.SuperAdapter
    ws.dyt.view.adapter.SuperPinnedAdapter
```
    首先要说明一点：该api的使用与系统原生提供的有区别，不要思维定式。简单说起来就是使用更简单，大概姿势如下：
    1. 构造（构造方法）
    2. 数据绑定
    3. 多类型支持（设置布局，如果需要的话）
    4. 事件
    5. 添加头部尾部控件
  该库主要实现了两个具有一定功能的``Adapter``，命名为``SuperAdapter``、``SuperPinnedAdapter``。从实现上来讲，``SuperPinnedAdapter``间接继承自``SuperAdapter``，所以功能上就不言而喻了。
``SuperAdapter``具有添加头部尾部、以及滑动菜单功能，而``SuperPinnedAdapter``在此基础上增加了粘性视图的支持。

>   通过构造方法进行初始化（具体的参数说明请看注释）

```java
    /** 
    * 调用该构造方法时需要调用 {@link #getItemViewLayout(int)} 设置item布局 
    * @param context 
    * @param datas 
    */
  public SuperAdapter(Context context, List<T> datas) {
      super(context, datas);
  }
  /** 
    * 调用该构造方法时默认数据项都采用 itemLayoutResId 布局，同样可以调用 {@link #getItemViewLayout(int)} 重新设置item布局 
    * @param context 
    * @param datas 
    * @param itemLayoutResId 
    */
public SuperAdapter(Context context, List<T> datas, @LayoutRes int itemLayoutResId) {    
      this(context, datas);
}
```
以上两个构造方法最大的区别是：用第一种方法构造后需要调用``getItemViewLayout()``设置item的布局。用后一种的话已经设置了item布局，不过这种方式是所有的item布局都是一样的。
>   数据绑定

数据的绑定在原生里是``onBindViewHolder()``，而在这里，将该方法进行了屏蔽，而是用``convert()``方法（使用上与``onBindViewHolder()``一致，但是也更简单），可以简单的将他俩看成是对等的关系，但是事实上不是这样的，具体的我将会在后续的文章中进行具体描述。
```java
  /** 
    * 绑定数据 
    * @param holder 
    * @param position  数据域从0开始，已经除去头部       
    */
  abstract
  public void convert(BaseViewHolder holder, int position);
```
  该方法的第一个参数该库做了进一步封装，具体来说就是整个库里``BaseViewHolder``类无法被扩展，所有常用的操作方法已经在该类中进行了封装（后续版本在决定是否可以允许该类扩展），``BaseViewHolder``的功能将会在后面的文章进行描述。``BaseViewHolder``的链条操作:
```java
  //只是一个例子而已，旨在说明链条操作，不必注意实体类是什么
  CourseResult.Course e = getItem(position).data;
  holder.
      setText(R.id.tv_name, e.name).
      setText(R.id.tv_length, e.length);
```
>   多item支持

通过构造方法已经知道，用构造方法是没有办法设置``Adapter``的多item样式支持的。那么在该库中，通过以下方法进行设置：
```java
  /**  
    * 提供item对应的布局 
    */
public int getItemViewLayout(int position) {}
```
>   事件

  网上一大片文章都在讲``RecyclerView``怎么设置item的点击事件云云，事实上确实很简单，``RecyclerView``暴露的方法就那么几个，所以在这里事件是怎么加上去的也将在后续的文章中阐述，这里只需要知道该库一定也提供了设置事件监听的方法。
对item的点击事件支持以下两种：
```java
  /** 
    * 点击事件 
    */
  public interface OnItemClickListener {    
      void onItemClick(View itemView, int position);
  }
  /** 
    * 长按事件 
    */
  public interface OnItemLongClickListener {    
      void onItemLongClick(View itemView, int position);
  }
```
设置监听器：
```java
  //设置点击事件
  adapter.setOnItemClickListener(new SuperAdapter.OnItemClickListener() {    
      @Override    
      public void onItemClick(View itemView, int position) {     
      }
});
  //设置长按事件
  adapter.setOnItemLongClickListener(new SuperAdapter.OnItemLongClickListener() {    
      @Override    
      public void onItemLongClick(View itemView, int position) {     
      }
    });
```
>   添加头部、尾部控件

  原生``RecyclerView``并没有提供像``ListView``中那样可以随意添加header和footer的api，但是在实际的场景中我们确实需要这样的功能，那么该库也一定提供了这样的方法。  
  首先需要简单说一下``SuperAdapter``数据域的构成：
```
  {
      item_sys_header - item_header - 
      item_data - 
      item_footer - item_sys_footer
  }
```
    从上面的结构中可以看到，大体分为3中（header部分、数据部分、footer部分），细分的话，头部又分为系统header部分(item_sys_header)和用户header部分(item_header)，footer部分分为系统footer部分(item_footer)和用户footer部分(item_sys_footer)。
    我为什么要这么划分呢，在实现上是有一些考虑的，头部和尾部不要去污染数据部分（分离），这样在扩展上更好一些。一般在使用时系统header和系统footer可以忽略，加这两个的用途也是为了扩展用，比如在我的另外一个上拉加载库中就用了系统footer。
主要提供了一下几个api：
```java
    final
    public void addHeaderView(View view);
    final
    public void addHeaderView(View view, boolean changeAllVisibleItems);
    final
    public void removeHeaderView(View view);
    final
    public void removeHeaderView(View view, boolean changeAllVisibleItems);
    final
    public void addFooterView(View view);
    final
    public void addFooterView(View view, boolean changeAllVisibleItems);
    final
    public void removeFooterView(View view);
    final
    public void removeFooterView(View view, boolean changeAllVisibleItems);
```
其中changeAllVisibleItems表示是否只刷新可见区域（为true时），否则只刷新当前发生变化的item，默认为false
>   横跨列的api

在``RecyclerView``的布局管理器是``GridLayoutManager``或者``StaggeredGridLayoutManager``时，有些item需要横跨所有列，这种情况下我们也提供了以下api去设置，默认为false，表示保持管理器设置，为true时表示横跨。
```java
    /**
     * 设置是否横跨
     * @param position
     * @return
     */
    protected boolean isFullSpanWithItemView(int position) {
        return false;
    }
```
## License
```xml
        http://www.apache.org/licenses/LICENSE-2.0
```