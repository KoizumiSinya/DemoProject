本module主要学习Android5.0 之Material Design设计 新特性
【一】主要包括
AppBarLayout，NavigationView，CoordinatorLayout，CollapsingToolbarLayout，
FloatingActionButton，TextInputLayout，Snackbar，TabLayout的使用

【二】需要导包
compile 'com.android.support:design:22.2.0'

【三】AppBarLayout
1、AppBarLayout 是继承LinerLayout实现的一个ViewGroup容器组件，它是为了Material Design设计的App Bar，支持手势滑动操作。
2、默认的AppBarLayout是垂直方向的，它的作用是把AppBarLayout包裹的内容都作为AppBar。
3、AppBarLayout要实现手势滑动效果的时候，要和CoordinatorLayout配合使用

【四】CoordinatorLayout
1、CoordinatorLayout是一个增强型的FrameLayout。它的作用有两个
  ①作为一个布局的根布局
  ②为子视图之间相互协调手势效果
2、其中可以跟随滑动隐藏\出现的控件，必须存放在AppBarLayout中，且AppBarLayout要作为其第一个ChildView。
  ①这个跟随手势滑动 隐藏\出现的控件，内部要加上属性标签：app:layout_scrollFlags=”scroll|enterAlways” 属性来确定
  ②附加：
    设置的layout_scrollFlags有如下几种选项：
    (1)scroll: 所有想滚动出屏幕的view都需要设置这个flag- 没有设置这个flag的view将被固定在屏幕顶部。
    (2)enterAlways: 这个flag让任意向下的滚动都会导致该view变为可见，启用快速“返回模式”。
    (3)enterAlwaysCollapsed: 当你的视图已经设置minHeight属性又使用此标志时，你的视图只能已最小高度进入，只有当滚动视图到达顶部时才扩大到完整高度。
    (4)exitUntilCollapsed: 滚动退出屏幕，最后折叠在顶端。
3、其次，还要包含一个可以滑动的View。
  ①比如 RecyclerView，NestedScrollView(ListView，ScrollView不支持)具有滑动效果的组件
  ②且内部必须包含属性标签：app:layout_behavior="@string/appbar_scrolling_view_behavior"

【五】CollapsingToolbarLayout
1、CollapsingToolbarLayout包裹 Toolbar 的时候提供一个可折叠的 Toolbar，一般作为AppbarLayout的子视图使用。
2、属性标签介绍
  ① Collapsing title：ToolBar的标题，当CollapsingToolbarLayout全屏没有折叠时，title显示的是大字体，在折叠的过程中，title不断变小到一定大小的效果。你可以调用setTitle(CharSequence)方法设置title。
  ②Content scrim：ToolBar被折叠到顶部固定时候的背景，你可以调用setContentScrim(Drawable)方法改变背景或者 在属性中使用 app:contentScrim=”?attr/colorPrimary”来改变背景。
  ③Status bar scrim：状态栏的背景，调用方法setStatusBarScrim(Drawable)。还没研究明白，不过这个只能在Android5.0以上系统有效果。
  ④Parallax scrolling children：CollapsingToolbarLayout滑动时，子视图的视觉差，可以通过属性app:layout_collapseParallaxMultiplier=”0.6”改变。
  ⑤CollapseMode ：子视图的折叠模式，有两种
    (1)"pin"：固定模式，在折叠的时候最后固定在顶端；
    (2)"parallax"：视差模式，在折叠的时候会有个视差折叠的效果。我们可以在布局中使用属性app:layout_collapseMode=”parallax”来改变。
3、总结：
  ①CollapsingToolbarLayout主要是提供一个可折叠的Toolbar容器，对容器中的不同视图设置layout_collapseMode折叠模式，来达到不同的折叠效果。
  ②Toolbar 的高度layout_height必须固定，不能 “wrap_content”，否则Toolbar不会滑动，也没有折叠效果。
  ③为了能让FloatingActionButton也能折叠且消失出现，我们必须给FAB设置锚点属性app:layout_anchor="@id/appbar"

【六】NavigationView
1、可以和谷歌推荐的DrawerLayout合用，制作一个侧滑菜单
2、其中NavigationView 中的 android:layout_gravity=”start” 属性来控制抽屉菜单从哪边滑出，一般“start ”从左边滑出，“end”从右边滑出。
3、设置引入布局的属性标签：
  ①app:headerLayout: 给NavigationView添加头部布局
  ②app：menu：给NavigationView添加menu菜单布局
