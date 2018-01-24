## 控件简介
    RecyclerView的数据适配器，主要拥有以下功能：
    1. 可以添加、删除任意数量的头部、尾部（就像ListView那样）
    2. 可以针对不同的item添加滑动菜单（目前只支持左滑、右滑），且菜单定制性高
    3. 支持粘性视图，同时支持滑动菜单
    
## 系列文章
进阶式的功能添加实现，都在[系列源码实现说明](http://www.jianshu.com/p/b9bbed94d02e)，请多多提意见。
## 添加依赖
在需要依赖的module中添加以下代码
```
    android {
        repositories {
            maven {
                url "https://dl.bintray.com/yangxiaoweihn/maven/"
            }
        }
        ...
    }
    dependencies {
        compile 'ws.dyt.view:recyclerview-adapter-hf:2.6.1'
    }
```
## 屏幕截图
<style type="text/css">
    .alt{
        display: block;
        color: #999;
        align: center;
        text-align: center;
        font-size: 0.8em;
        font-style: italic;
        padding-top: 20px;
        padding-bottom: 10px;
    }
</style>
<table> 
    <tr>
        <td><img src="https://raw.githubusercontent.com/yangxiaoweihn/RecyclerViewAdapter/master/app/screenshots/hf_line.png" /><span class="alt">头部尾部->线性布局</span class="alt"></td>
        <td><img src="https://raw.githubusercontent.com/yangxiaoweihn/RecyclerViewAdapter/master/app/screenshots/hf_grid.png" /><span class="alt">头部尾部->网格布局</span class="alt"></td>
        <td><img src="https://raw.githubusercontent.com/yangxiaoweihn/RecyclerViewAdapter/master/app/screenshots/cross_api.png" /><span class="alt">横跨列API</span class="alt"></td>
    </tr>
    <tr>
        <td><img src="https://raw.githubusercontent.com/yangxiaoweihn/RecyclerViewAdapter/master/app/screenshots/decoration.png" /><span class="alt">头部尾部->Decoration</span class="alt"></td>
        <td><img src="https://raw.githubusercontent.com/yangxiaoweihn/RecyclerViewAdapter/master/app/screenshots/sticky_line.png" /><span class="alt">粘性头部->线性布局+菜单</span class="alt"></td>
        <td><img src="https://raw.githubusercontent.com/yangxiaoweihn/RecyclerViewAdapter/master/app/screenshots/sticky_grid.png" /><span class="alt">粘性头部->网格布局</span class="alt"></td>
    </tr>
    <tr>
        <td><img src="https://raw.githubusercontent.com/yangxiaoweihn/RecyclerViewAdapter/master/app/screenshots/swipe.png" /><span class="alt">菜单</span class="alt"></td>
    </tr>
</table>

## 使用
-   与系统提供的Adapter使用方式无异（具体api参看demo）
```java
    ws.dyt.view.adapter.SuperAdapter
    ws.dyt.view.adapter.SuperPinnedAdapter
```
    首先要说明一点：该api的使用与系统原生提供的稍有区别，简单说起来就是使用更简单，功能更强大。大概姿势如下：
    1. 构造（构造方法）
    2. 数据绑定
    3. 多类型支持（设置布局，如果需要的话）
    4. 事件
    5. 添加头部尾部控件
  该库主要实现了两个具有一定功能的``Adapter``，命名为``SuperAdapter``、``SuperPinnedAdapter``。从实现上来讲，``SuperPinnedAdapter``间接继承自``SuperAdapter``，所以功能上就不言而喻了。
``SuperAdapter``具有添加头部尾部、以及滑动菜单功能，而``SuperPinnedAdapter``在此基础上增加了粘性视图的支持。

###   通过构造方法进行初始化（具体的参数说明请看注释）

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
###   数据绑定

数据的绑定在原生里是``onBindViewHolder()``，而在这里，将该方法进行了屏蔽，而是用``convert()``方法（使用上与``onBindViewHolder()``一致，但更简单）。
```java
/** 
* 绑定数据 
* @param holder 
* @param position  数据域从0开始，已经除去头部       
*/
abstract
public void convert(BaseViewHolder holder, int position);
```
  其中``BaseViewHolder``对常用的操作方法进行了封装，同时提供了链式操作:
```java
//比如可以这样使用
CourseResult.Course e = getItem(position).data;
holder.
  setText(R.id.tv_name, e.name).
  setText(R.id.tv_length, e.length);
```
###   多item支持

通过以下方法进行多item设置：
```java
/**  
* 提供item对应的布局 
*/
public int getItemViewLayout(int position) {}
```
###   为item添加事件

  该库内部支持对item的单击事件和长按事件，通过注册以下方法进行监听：
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
###   添加头部、尾部控件

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
针对上述域，api提供在以下文件中：
header部分api:
```java
ws.dyt.view.adapter.core.base.IUserHeader
```
footer部分api:
```java
ws.dyt.view.adapter.core.base.IUserFooter
```
数据域部分api:
```java
ws.dyt.view.adapter.core.base.CRUD
```
###   横跨列支持API

在``RecyclerView``的布局管理器是``GridLayoutManager``或者``StaggeredGridLayoutManager``时，有些item需要横跨所有列，这种情况下我们也提供了以下api去设置，默认为false，表示保持管理器设置，为true时表示横跨。
通过重写以下方法进行指定哪些数据需要横跨列，让这些数据返回``true``即可:
```java
/**
 * 设置是否横跨
 * @param position
 * @return
 */
public boolean isFullSpanWithItemView(int position) {
    return false;
}
```
###   添加菜单及菜单响应

每种item都可以设置一个或多个隐藏菜单，通过手势左滑或者右滑进行展示和隐藏。

```java
//创建菜单
public List<MenuItem> onCreateMultiMenuItem(int viewType) {
    List<MenuItem> mm = new ArrayList<>();
    mm.add(new MenuItem(R.layout.menu_item_test_delete, MenuItem.EdgeTrack.RIGHT, 01));
    mm.add(new MenuItem(R.layout.menu_item_test_mark, MenuItem.EdgeTrack.RIGHT, 02));
    return mm;
}

@Override
public boolean isCloseOtherItemsWhenThisWillOpen() {
    return true;
}

//设置菜单监听
adapter.setOnItemMenuClickListener(new OnItemMenuClickListener() {
    @Override
    public void onMenuClick(SwipeLayout swipeItemView, View itemView, View menuView, int position, int menuId) {
        if (menuId == 01) {
            swipeItemView.closeMenuItem();
            Log.d("DEBUG", "--menu: 删除 -> position: " + position + " , menuId: " + menuId);
            Toast.makeText(getContext(), "删除", Toast.LENGTH_SHORT).show();
        } else if (menuId == 02) {
            Log.d("DEBUG", "--menu: 关注 -> position: " + position + " , menuId: " + menuId);
            Toast.makeText(getContext(), "加关注", Toast.LENGTH_SHORT).show();
        }
    }
});
```
###   添加粘性头部支持及头部数据设置

注意粘性头部的``Adapter``是``PinnedAdapter``或者``SuperStickyAdapter``.

使用步骤：1. 设置粘性头部布局    2. 绑定粘性头部数据 3. 绑定其他数据

特别注意点：
在绑定粘性头部数据时最好做数据类型判断，因为在数据有分组的情况下滑动回调的position对应的数据不一定是头部数据，具体可以参看demo。

```java
//设置粘性头部布局
@Override
public int getPinnedItemViewLayout() {
    return R.layout.item_pinned;
}

//绑定粘性头部数据
@Override
public void convertPinnedHolder(BaseViewHolder holder, int position, int type) {
    holder.setText(R.id.tv_text_pinned, getItem(position).data.title);
}
```

###   多ViewHolder支持

从2.5版本开始，增加自定义ViewHolder对象支持，之前版本都是库内部通过创建BaseViewHolder实现，现在默认实现也是BaseViewHolder，但是子类可以通过重写以下方法进行自定义ViewHolder（通过继承BaseViewHolder）。
```java
/**
 * 重写该方法自定义ViewHolder
 * @param itemLayoutOfViewType   对应{@link SwipeAdapter#getItemViewLayout(int)}返回值
 * @return
 */
VH onCreateViewHolderByItemType(int itemLayoutOfViewType, ViewGroup parent);
```
```java
private static class DefinedViewHolder extends BaseViewHolder{

    private View btnTo;
    public DefinedViewHolder(View itemView) {
        super(itemView);
    }

    public DefinedViewHolder(View itemView, View eventItemView) {
        super(itemView, eventItemView);
    }
}
```

###  DataBinding支持
    
当我们在使用DataBinding绑定item数据时需要在ViewHolder传递DataBinding对象，这里有两种方式：
> 1. 继承BaseViewHolder增加DataBinding属性，但是这样做有风险（尤其在设置了菜单和粘性头部时）
> 2. [推荐]使用BaseViewHolder中的holderTag属性作为DataBinding的载体（使用方式参看demo）


## License
```xml
http://www.apache.org/licenses/LICENSE-2.0
```
