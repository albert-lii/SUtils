# SUtils
> 轻量级Android工具类库，集成了众多平时开发用到的工具类，方便快速开发项目！

## 联系方式
> **QQ：1009365545**

> **电子邮箱：albertlii@163.com**

## 项目内容
### 目录
#### APP相关
- [PermissionUtil](#PermissionUtil)  Android6.0后权限管理类
- [AppUtil](#AppUtil) 与APP相关
- [AtyTransitionUtil](https://github.com/albert-lii/SUtils/blob/master/sutils/src/main/java/com/liyi/sutils/utils/app/AtyTransitionUtil.java) Activity过渡动画类
- [DensityUtil](https://github.com/albert-lii/SUtils/blob/master/sutils/src/main/java/com/liyi/sutils/utils/app/DensityUtil.java) dp、sp、px转换类
- [KeyboardUtil](https://github.com/albert-lii/SUtils/blob/master/sutils/src/main/java/com/liyi/sutils/utils/app/KeyboardUtil.java) 虚拟键盘工具类
- [ScreenUtil](https://github.com/albert-lii/SUtils/blob/master/sutils/src/main/java/com/liyi/sutils/utils/app/ScreenUtil.java) 与屏幕相关
- [SystemBarUtil](https://github.com/albert-lii/SUtils/blob/master/sutils/src/main/java/com/liyi/sutils/utils/app/SystemBarUtil.java) 系统状态栏和底部虚拟导航栏的工具类
- [SystemPageUtil](https://github.com/albert-lii/SUtils/blob/master/sutils/src/main/java/com/liyi/sutils/utils/app/SystemPageUtil.java) 系统功能界面工具类
#### 设备相关
- []() 设备信息相关工具类


### APP相关
##### [PermissionUtil](https://github.com/albertlii/SUtils/blob/master/sutils/src/main/java/com/liyi/sutils/utils/app/permission/PermissionUtil.java) 
```Java
// 判断是否需要进行权限获取
boolean isNeedRequest()

// 判断用户是否已经拥有指定权限
boolean hasPermissions(@NonNull Context context, @NonNull String... permissions)

// 获取缺少的权限
String[] getDeniedPermissions(@NonNull Context context, @NonNull String... permissions)

// 判断是否在自动弹出的权限弹框中勾选了总是拒绝授权
boolean hasAlwaysDeniedPermission(@NonNull Activity activity, @NonNull String... deniedPermissions)

// 显示提示框
void showTipDialog(@NonNull final Context context, String message)

// 处理请求授权后返回的结果
// 此方法需要放在onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults)方法中执行
void handleRequestPermissionsResult(@NonNull Activity activity, int requestCode, @NonNull String[] permissions, int[] grantResults) 


PermissionUtil.with(@NonNull Activity activity)（with(@NonNull Fragment fragment)）
// 请求码
.requestCode(int requestCode)
// 需要获取的权限
.permissions(@NonNull String... permissions)
// 请求权限结果的回调
.callback(OnPermissionListener callback)
// 是否自动显示拒绝授权时的提示
.autoShowTip(boolean isAutoShowTip)
// 执行权限请求
.execute()
```

- [<span id="AppUtil">AppUtil</span>](https://github.com/albert-lii/SUtils/blob/master/sutils/src/main/java/com/liyi/sutils/utils/app/AppUtil.java) 与APP相关
~~~
// 获取应用程序名称
String getAppName(@NonNull Context context)  

// 获取应用版本名称
String getVersionName(@NonNull Context context)

// 获取应用版本code
int getVersionCode(@NonNull Context context)  

// 判断app是否存活
boolean isAppAlive(@NonNull Context context, @NonNull String packageName) 

// 获取app的状态（运行在前台、运行在后台、app已被杀死）
int getAppSatus(@NonNull Context context, @NonNull String packageName)

// 判断服务是否存活
boolean isServiceAlive(@NonNull Context context, @NonNull String serviceName) 

// 获取SHA1的值
String getSHA1(@NonNull Context context) 

// 检测某应用程序是否安装
boolean isInstalledApp(Context context, String packageName) 
~~~

- [AtyTransitionUtil](https://github.com/albert-lii/SUtils/blob/master/sutils/src/main/java/com/liyi/sutils/utils/app/AtyTransitionUtil.java) Activity过渡动画类
~~~
// Activity 从左边进入
void enterFromLeft(Activity activity) 

// Activity 从左边退出
void exitToLeft(Activity activity)

// Activity 从右边进入
void enterFromRight(Activity activity)

// Activity 从右边退出
void exitToRight(Activity activity)

// Activity 从上边进入
void enterFromTop(Activity activity)

// Activity 从上边退出
void exitToTop(Activity activity)

// Activity 从下边进入
enterFromBottom(Activity activity)

//  Activity 从下边退出
void exitToBottom(Activity activity)

// 启动过渡动画
void startTransition(Activity activity, int enterStyle, int outStyle)
~~~

- [DensityUtil](https://github.com/albert-lii/SUtils/blob/master/sutils/src/main/java/com/liyi/sutils/utils/app/DensityUtil.java) dp、sp、px转换类
~~~
// dp 转 px
int dp2px(@NonNull Context context, float dpVal)

// sp 转 px
int sp2px(@NonNull Context context, float spVal)

// px 转 dp
int px2dp(@NonNull Context context, float pxVal)

// px 转 sp
int px2sp(@NonNull Context context, float pxVal)
~~~

- [KeyboardUtil](https://github.com/albert-lii/SUtils/blob/master/sutils/src/main/java/com/liyi/sutils/utils/app/KeyboardUtil.java) 虚拟键盘工具类
~~~
// 弹出虚拟软键盘
void openKeyboard(Context context, EditText et) 

// 弹出虚拟软键盘并把布局顶上去
openKeyboardByTop(Context context, EditText et)

// 关闭软键盘
void closeKeyboard(Context context, EditText et)

// 判断软件盘是否弹出
boolean isSoftInputShow(Activity activity)
~~~

- [ScreenUtil](https://github.com/albert-lii/SUtils/blob/master/sutils/src/main/java/com/liyi/sutils/utils/app/ScreenUtil.java) 与屏幕相关
~~~
// 获取屏幕密度dpi（120 / 160 / 240 /...）
int getDensityDpi(@NonNull Context context)

// 获取屏幕的宽和高
Point getScreenSize(@NonNull Context context)

// 获取屏幕高和宽的比
float getScreenRate(@NonNull Context context)

// 获取状态栏的高度
int getStatusBarHeight(@NonNull Context context)

// 获取底部导航栏的高度
int getNavBarHeight(@NonNull Context context)

// 判断是否有底部导航栏
hasNavigationBar(@NonNull Context context)

// 截屏 （返回的bitmap包括状态栏）
Bitmap screenShot(@NonNull Activity activity)

// 截屏 （返回的bitmap不包括状态栏）
Bitmap screenShotNoStatusBar(@NonNull Activity activity)
~~~

- [SystemBarUtil](https://github.com/albert-lii/SUtils/blob/master/sutils/src/main/java/com/liyi/sutils/utils/app/SystemBarUtil.java) 系统状态栏和底部虚拟导航栏的工具类
~~~
// 设置状态栏和底部导航栏的显示方式
void setDisplayOption(Activity activity, boolean isFitSystemWindow, boolean clipToPadding)

// 设置顶部状态栏
void setupStatusBar(Activity activity, int color)

// 设置状态栏的透明度
void setStatusBarAlpha(Activity activity, float alpha) 

// 设置状态栏的显示和隐藏
void showStatusBar(Activity activity, boolean isShow)

// 设置底部导航栏
void setupNavBar(Activity activity, int color)

// 设置底部导航栏的透明度
void setNavBarAlpha(Activity activity, float alpha)

// 设置底部导航栏的显示和隐藏
void showNavBar(Activity activity, boolean isShow)
~~~

- [SystemPageUtil](https://github.com/albert-lii/SUtils/blob/master/sutils/src/main/java/com/liyi/sutils/utils/app/SystemPageUtil.java) 系统功能界面工具类
~~~
// 跳转至系统设置界面
void openSettings(Context context)

// 跳转到Wifi列表设置界面
void openWifiSettings(Context context)

// 跳转到移动网络设置界面
void openMobileNetSettings(Context context)

//  跳转到飞行模式设置界面
void openAirPlaneSettings(Context context)

// 跳转到蓝牙设置界面
void openBluetoothSettings(Context context)

// 跳转到NFC设置界面
void openNFCSettings(Context context)

// 跳转到NFC共享设置界面
openNFCShareSettings(Context context) 

// 跳转位置服务界面
void openGpsSettings(Context context)

// 根据包名跳转到系统自带的应用程序信息界面
void openAppDetail(Context context)
~~~
