## 控件简介
    RecyclerView的数据适配器，主要进行了以下几方面的封装：
    1. 数据处理（定义在ws.dyt.view.adapter.base.CRUD）
    2. 可以支持增加和删除头部尾部（类似于ListView）
    3. 对外接口映射(onCreateViewHolder()->getItemViewLayout(), onBindViewHolder()->convert())
    4. 布局管理器的管理 isDataItemView()
## 添加依赖
```
    compile 'ws.dyt.view:recyclerview-adapter:1.0'
```
## 屏幕截图
![RecyclerViewAdapter](https://raw.githubusercontent.com/yangxiaoweihn/RecyclerViewAdapter/master/app/screenshots/1.png)
![RecyclerViewAdapter](https://raw.githubusercontent.com/yangxiaoweihn/RecyclerViewAdapter/master/app/screenshots/2.png)


![RecyclerViewAdapter](https://raw.githubusercontent.com/yangxiaoweihn/RecyclerViewAdapter/master/app/screenshots/3.png)
![RecyclerViewAdapter](https://raw.githubusercontent.com/yangxiaoweihn/RecyclerViewAdapter/master/app/screenshots/4.png)


![RecyclerViewAdapter](https://raw.githubusercontent.com/yangxiaoweihn/RecyclerViewAdapter/master/app/screenshots/5.png)
    
## 使用
-   与系统提供的Adapter使用方式无异（具体api参看demo）
```java
    ws.dyt.view.adapter.MultiAdapter
```
### 类介绍
-   MultiAdapter 继承自 BaseAdapter
    该类是一个抽象类， 必须实现的方法是convert(BaseViewHolder holder, int position)，该方法由系统提供的onBindViewHolder()方法映射而来。
    支持单一的item和多种item类型，具体请参看构造方法的说明。
```java
    /**
     * 调用该构造方法时需要调用 {@link #getItemViewLayout(int)} 设置item布局
     * @param context
     * @param datas
     */
    public MultiAdapter(Context context, List<T> datas) {
        super(context, datas);
    }

    /**
     * 调用该构造方法时默认数据项都采用 itemLayoutResId 布局，同样可以调用 {@link #getItemViewLayout(int)} 重新设置item布局
     * @param context
     * @param datas
     * @param itemLayoutResId
     */
    public MultiAdapter(Context context, List<T> datas, @LayoutRes int itemLayoutResId) {
        this(context, datas);
        this.itemLayoutResId = itemLayoutResId;
    }
    
    /**
     * 提供item对应的布局
     */
    public int getItemViewLayout(int position) {
        return this.itemLayoutResId;
    }
```
-   BaseAdapter 继承自 HeaderFooterAdapter
    该类主要封装了数据的操作（实现自 ws.dyt.view.adapter.base.CRUD）。
    
-   HeaderFooterAdapter
    该类是本库的主角，主要封装了头尾控件管理、item监听器、布局管理器处理
    该类的处理是将适配器的数据项分为5部分进行，分别为：系统头部、头部、数据部分、尾部、系统尾部，以下简称{item_sys_header - item_header - item_data - item_footer - item_sys_footer}
    除了数据部分（item_data）其他的4部分将会独占一行
    部分方法说明：
    1. item数量获取
    ```java
        //系统头数量
        public final int getSysHeaderViewCount()
        //头部数量
        public final int getHeaderViewCount()
        //数据部分
        public int getDataSectionItemCount()
        //尾部部分
        public final int getFooterViewCount()
        //系统尾部
        public final int getSysFooterViewCount()
    ```
    2. 真实数据项处理
    ```java
        /**
         * 用来判断item是否为真实数据项，除了头部、尾部、系统尾部等非真实数据项，结构为:
         * item_header - item_data - item_footer - item_sys_footer
         * @param position
         * @return true:将保留LayoutManager的设置  false:该item将会横跨整行(对GridLayoutManager,StaggeredLayoutManager将很有用)
         */
        protected boolean isDataItemView(int position) {
        }
    ```
    3. 添加监听器
    ```java
        MultiAdapter adapter = new MultiAdapter(XXX);
        adapter.setOnItemClickListener(new HeaderFooterAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View itemView, int position) {
            }
        });
        
        adapter.setOnItemLongClickListener(new HeaderFooterAdapter.OnItemLongClickListener() {
            @Override
            public void onItemLongClick(View itemView, int position) {
            }
        });
```
## License
```xml
        http://www.apache.org/licenses/LICENSE-2.0
```