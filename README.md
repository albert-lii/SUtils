# SUtils
> 轻量级Android工具类库，集成了众多平时开发用到的工具类，以后将持续更新！

## 联系方式
> **QQ：1009365545**

> **电子邮箱：albertlii@163.com**

## 添加依赖
```Java
Step 1:

      allprojects {
          repositories {
              ...
              maven { url 'https://jitpack.io' }
          }
      }
  
Step 2:
      dependencies {
          compile 'com.github.albert-lii:SUtils:1.0.8'
      }
```

## 项目内容
### 目录
#### APP相关
- [PermissionUtil](#PermissionUtil) Android6.0后权限管理类
- [AppUtil](#AppUtil) 与APP相关
- [AtyTransitionUtil](#AtyTransitionUtil) Activity过渡动画类
- [DensityUtil](#DensityUtil) dp、sp、px转换类
- [KeyboardUtil](#KeyboardUtil) 虚拟键盘工具类
- [ScreenRotationUtil ](#ScreenRotationUtil ) 屏幕旋转角度监测类
- [ScreenUtil](#ScreenUtil) 与屏幕相关
- [SystemBarUtil](#SystemBarUtil) 系统状态栏和底部虚拟导航栏的工具类
- [SystemPageUtil](#SystemPageUtil) 系统功能界面工具类
#### 设备相关
- [DeviceUtil](#DeviceUtil) 设备信息相关工具类
- [GpsUtil](#GpsUtil) GPS工具类
- [NfcUtil](#NfcUtil) NFC工具类
#### 加密相关
- [AesUtil](#AesUtil) AES加密工具类
- [Base64Util](#Base64Util) Base64加密工具类
- [Md5Util](#Md5Util) MD5加密工具类
- [RsaUtil](#RsaUtil) RSA加密工具类
- [XorUtil](#XorUtil) 异或加密工具类
#### 图像相关
- [FastBlur](#FastBlur) 高斯模糊
- [RSBlur](#RSBlur) 高斯模糊
- [GlideManager](#GlideManager) glide4.xx管理工具类
- [ImageComprsUtil](#ImageComprsUtil) 图片压缩工具类
- [ImageUtil](#ImageUtil) 图片相关工具类
- [ShapeUtil](#ShapeUtil) Shape工具类
#### IO相关
- [ACache](#ACache) 缓存工具类
- [AssetUtil](#AssetUtil) asset工具类
- [FileUtil](#FileUtil) file工具类
- [GsonUtil](GsonUtil) gson工具类
- [SpUtil](#SpUtil) SharedPreferences工具类
#### Log相关
- [CrashHandler](#CrashHandler) 异常捕捉工具类
- [LogUtil](#LogUtil) log工具类
#### 网络相关
- [NetUtil](#NetUtil) 网络相关工具类
#### 其他
- [AlertDialogUtil](#AlertDialogUtil) AlertDialog工具类
- [EventBusUtil](#EventBusUtil) EventBus3.0工具类
- [QRCodeUtil](#QRCodeUtil) 二维码工具类
- [ReflectUtil](#ReflectUtil) 反射工具类
- [RegExUtil](#RegExUtil) 正则表达式工具类
- [SpanUtil](#SpanUtil) SpannableString工具类
- [TimeUtil](#TimeUtil) 时间工具类
- [ToastUtil](#ToastUtil) Toast工具类


### APP相关
- [<div id="PermissionUtil">PermissionUtil</div>](https://github.com/albertlii/SUtils/blob/master/sutils/src/main/java/com/liyi/sutils/utils/app/permission/PermissionUtil.java)
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


/**
 * 使用方法
 */
PermissionUtil.with(@NonNull Activity activity) // with(@NonNull Fragment fragment)
              // 请求码
              .requestCode(int requestCode)
              // 需要获取的权限
              .permissions(@NonNull String... permissions)
              // 请求权限结果的回调（使用此回调方法时，必须执行handleRequestPermissionsResult()方法）
              .callback(OnPermissionListener callback)
              // 是否自动显示拒绝授权时的提示
              .autoShowTip(boolean isAutoShowTip)
              // 执行权限请求
              .execute()
```

- [<div id="AppUtil">AppUtil</div>](https://github.com/albert-lii/SUtils/blob/master/sutils/src/main/java/com/liyi/sutils/utils/app/AppUtil.java)
```Java
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
boolean isInstalledApp(@NonNull Context context, String packageName) 
```

- [<div id="AtyTransitionUtil">AtyTransitionUtil</div>](https://github.com/albert-lii/SUtils/blob/master/sutils/src/main/java/com/liyi/sutils/utils/app/AtyTransitionUtil.java)
```Java
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
```

- [<div id="DensityUtil">DensityUtil</div>](https://github.com/albert-lii/SUtils/blob/master/sutils/src/main/java/com/liyi/sutils/utils/app/DensityUtil.java)
```Java
// dp 转 px
int dp2px(@NonNull Context context, float dpVal)

// sp 转 px
int sp2px(@NonNull Context context, float spVal)

// px 转 dp
int px2dp(@NonNull Context context, float pxVal)

// px 转 sp
int px2sp(@NonNull Context context, float pxVal)
```

- [<div id="KeyboardUtil">KeyboardUtil</div>](https://github.com/albert-lii/SUtils/blob/master/sutils/src/main/java/com/liyi/sutils/utils/app/KeyboardUtil.java)
```Java
// 弹出虚拟软键盘
void openKeyboard(Context context, EditText et) 

// 弹出虚拟软键盘并把布局顶上去
openKeyboardByTop(Context context, EditText et)

// 关闭软键盘
void closeKeyboard(Context context, EditText et)

// 判断软件盘是否弹出
boolean isSoftInputShow(Activity activity)
```

- [<div id="">ScreenRotationUtil</div>](https://github.com/albert-lii/SUtils/blob/master/sutils/src/main/java/com/liyi/sutils/utils/app/ScreenRotationUtil.java)
```Java
// 使用方法
ScreenRotationUtil.getInstance(@NonNull Context context)
                  .rate(int rate)
                  .callback(OnRotationListener listener)
                  .start()

ScreenRotationUtil.getInstance(@NonNull Context context).stop() 
```

- [<div id="ScreenUtil">ScreenUtil</div>](https://github.com/albert-lii/SUtils/blob/master/sutils/src/main/java/com/liyi/sutils/utils/app/ScreenUtil.java) 
```Java
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

// 获取屏幕方向
// @return Configuration.ORIENTATION_PORTRAIT、Configuration.ORIENTATION_LANDSCAPE
int getScreenSimpleOrientation(@NonNull Context context)

// 获取屏幕方向
// @return Surface.ROTATION_0、Surface.ROTATION_90、Surface.ROTATION_180、Surface.ROTATION_270
int getScreenOrientation(@NonNull Activity activity) 

// 设置屏幕竖屏
void setScreenPortrait(@NonNull Activity activity)

// 设置屏幕横屏
void setScreenLandscape(@NonNull Activity activity)
```

- [<div id="SystemBarUtil">SystemBarUtil</div>](https://github.com/albert-lii/SUtils/blob/master/sutils/src/main/java/com/liyi/sutils/utils/app/SystemBarUtil.java) 
```Java
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
```

- [<div id="SystemPageUtil">SystemPageUtil</div>](https://github.com/albert-lii/SUtils/blob/master/sutils/src/main/java/com/liyi/sutils/utils/app/SystemPageUtil.java) 
```Java
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
```

### 设备相关
- [<div id="DeviceUtil">DeviceUtil</div>](https://github.com/albert-lii/SUtils/blob/master/sutils/src/main/java/com/liyi/sutils/utils/device/DeviceUtil.java)
```Java
// 获取设备的唯一ID（即IMEI）
String getIMEI(@NonNull Context context)

// 获取IMSI
String getIMSI(@NonNull Context context)

// 获取设备当前的系统版本号
int getSDKVersion()

//  获取设备型号
String getDeviceModel()

// 获取设备厂商
String getDeviceBrand()

// 获取当前的系统语言
String getSystemLanguage() 

// 获取系统支持的语言列表
Locale[] getSystemLanguageList()

// 判断设备是否Root过
boolean isRooted()
 
// 在系统上执行一个命令
boolean canExecuteCommand(String command)

// 根据ip获取mac地址
String getMacAddress(@NonNull Context context) 

// 获取本地IP地址
String getLocalIpAddress()
```
- [<div id="GpsUtil">GpsUtil</div>](https://github.com/albert-lii/SUtils/blob/master/sutils/src/main/java/com/liyi/sutils/utils/device/GpsUtil.java)
```Java
// 根据经纬度获取地理位置
Address getAddress(@NonNull Context context, double latitude, double longitude)

// 根据经纬度获取所在国家
String getCountryName(@NonNull Context context, double latitude, double longitude)

// 根据经纬度获取所在地
String getLocality(@NonNull Context context, double latitude, double longitude)

// 根据经纬度获取所在街道
String getStreet(@NonNull Context context, double latitude, double longitude)

// 判断Gps是否可用
boolean isGpsEnabled(@NonNull Context context)

// 判断定位是否可用
boolean isLocationEnabled(@NonNull Context context) 

// 注册
boolean register(@NonNull Activity activity)

// 注销
void unregister()

// 自定义定位参数
GpsUtil criteria(Criteria criteria)

// 设置最小重新定位距离
GpsUtil minDistance(int minDistance)

// 设置最小重新定位时间
GpsUtil minTime(int minTime)

// 设置定位监听器
GpsUtil location(OnLocationListener listener)

// 设置gps状态监听器
GpsUtil gpsStatus(GpsStatus.Listener listener)
```

- [<div id="NfcUtil">NfcUtil</div>](https://github.com/albert-lii/SUtils/blob/master/sutils/src/main/java/com/liyi/sutils/utils/device/NfcUtil.java)
```Java
// 判断设备是否支持NFC功能
boolean isSupportNfc()

// 是否打开NFC功能
boolean isNfcEnabled()

// 判断是否有NFC相关的intent
boolean isHasNfcIntent(Intent intent)
```

### 加密相关
- [<div id="AesUtil">AesUtil</div>](https://github.com/albert-lii/SUtils/blob/master/sutils/src/main/java/com/liyi/sutils/utils/encrypt/AesUtil.java)
```Java
// 生成随机数，可以当做动态的密钥 加密和解密的密钥必须一致，不然将不能解密
String generateKey()

// 对密钥进行处理
byte[] getRawKey(byte[] seed)

// 加密
String encrypt(String key, String plaintext)

// 加密
byte[] encrypt(String key, byte[] plain)

// 解密
String decrypt(String key, String encrypted)

// 解密
byte[] decrypt(String key, byte[] encrypted)

// 二进制转字符
String toHex(byte[] buf)
```

- [<div id="Base64Util">Base64Util</div>](https://github.com/albert-lii/SUtils/blob/master/sutils/src/main/java/com/liyi/sutils/utils/encrypt/Base64Util.java)
```Java
// base64加密字符串
String encrypt(String plaintext)

// base64解码字符串
String decrypt(String ciphertext)

// base64加密文件
String encrypt(File file)

// base64解密文件
void decrypt(String ciphertext, File file)
```

- [Md5Util](https://github.com/albert-lii/SUtils/blob/master/sutils/src/main/java/com/liyi/sutils/utils/encrypt/Md5Util.java)
```Java
// 计算字符串的 MD5 值
String encrypt(String plaintext)

// 计算文件的 MD5 值
String encrypt(File file)

// 采用nio方式进行 MD5 加密
String encryptByNio(File file)

// 对字符串进行对此 MD5 加密，提高安全性
String encrypt(String string, int times)

// MD5加盐
String encrypt(String string, String slat)
```

- [<div id="RsaUtil">RsaUtil</idv>](https://github.com/albert-lii/SUtils/blob/master/sutils/src/main/java/com/liyi/sutils/utils/encrypt/RsaUtil.java)
```Java
// 随机生成RSA密钥对
KeyPair generateRSAKeyPair(int keyLength)

// 用公钥对字符串进行加密
byte[] encryptByPublicKey(byte[] data, byte[] publicKey)

// 私钥加密
byte[] encryptByPrivateKey(byte[] data, byte[] privateKey)

// 公钥解密
byte[] decryptByPublicKey(byte[] data, byte[] publicKey)

// 使用私钥进行解密
byte[] decryptByPrivateKey(byte[] encrypted, byte[] privateKey)

// 用公钥对字符串进行分段加密
byte[] encryptByPublicKeyForSpilt(byte[] data, byte[] publicKey)

// 私钥分段加密
byte[] encryptByPrivateKeyForSpilt(byte[] data, byte[] privateKey)

// 公钥分段解密
byte[] decryptByPublicKeyForSpilt(byte[] encrypted, byte[] publicKey)

// 使用私钥分段解密
byte[] decryptByPrivateKeyForSpilt(byte[] encrypted, byte[] privateKey)
```

- [<div id="XorUtil">XorUtil</div>](https://github.com/albert-lii/SUtils/blob/master/sutils/src/main/java/com/liyi/sutils/utils/encrypt/XorUtil.java)
```Java
// 固定key的方式加密
byte[] encryptAsFix(byte[] bytes)

// 非固定key的方式加密
byte[] encrypt(byte[] bytes)

// 解密
byte[] decrypt(byte[] bytes)
```

### 图像相关
- [<div id="FastBlur">FastBlur</div>](https://github.com/albert-lii/SUtils/blob/master/sutils/src/main/java/com/liyi/sutils/utils/graphic/blur/FastBlur.java)
```Java
Bitmap blur(Bitmap sentBitmap, int radius, boolean canReuseInBitmap)
```

- [<div id="RSBlur">RSBlur</div>](https://github.com/albert-lii/SUtils/blob/master/sutils/src/main/java/com/liyi/sutils/utils/graphic/blur/RSBlur.java)
```Java
Bitmap blur(Context context, Bitmap blurredBitmap, int radius)
```

- [<div id="GlideManager">GlideManager</div>](https://github.com/albert-lii/SUtils/blob/master/sutils/src/main/java/com/liyi/sutils/utils/graphic/glide/GlideManager.java)
```Java
// 注：extends CusGlideModulel类可以配置glide
// 工具类中已配有：BlurTransformation（高斯模糊）、CircleTransform（圆形）、RoundTransform（圆角）三种显示效果


GlideRequests getRequests(@NonNull Object obj)

// 恢复请求，一般在停止滚动的时候
void resumeRequests(Context context)

// 暂停请求 正在滚动的时候
void pauseRequests(Context context)

// 清除磁盘缓存
// 注：需要在子线程中进行
clearDiskCache(final Context context)

// 清除内存缓存
// 清理内存缓存可以在UI主线程中进行
void clearMemory(Context context)
```

- [<div id="ImageComprsUtil">ImageComprsUtil</div>](https://github.com/albert-lii/SUtils/blob/master/sutils/src/main/java/com/liyi/sutils/utils/graphic/ImageComprsUtil.java)
```Java
// 质量压缩
Bitmap comprsQlty(@NonNull Bitmap source, @NonNull Bitmap.CompressFormat format, int options)

// 质量压缩，并且指定压缩后图片的大小
Bitmap comprsQltyToSize(@NonNull Bitmap source, @NonNull Bitmap.CompressFormat format, int size)

// 按比例压缩
Bitmap comprsScale(@NonNull Bitmap source, float ws, float hs, boolean filter)

// 按比例压缩
Bitmap comprsScale(@NonNull Bitmap source, int dstw, int dsth, boolean filter)

// 计算图片缩放的比例
float getRatioRate(int width, int height, int reqWidth, int reqHeight)
```

- [<div id="ImageUtil">ImageUtil</div>](https://github.com/albert-lii/SUtils/blob/master/sutils/src/main/java/com/liyi/sutils/utils/graphic/ImageUtil.java)
```Java
// drawbale 转 bitmap
Bitmap drawable2Bitmap(@NonNull Context context, int resId)

// bitmap 转 drawbale
Drawable bitmap2Drawable(@NonNull Bitmap bm)

// bitmap 转 byte array
byte[] bitmap2Byte(@NonNull Bitmap bm, Bitmap.CompressFormat format)

// byte array 转 bitmap
Bitmap byte2Bitmap(byte[] b)

// 将图片旋转指定角度
Bitmap rotateBitmap(@NonNull Bitmap source, float degree)

// 获取图片需要旋转的角度
int getImageDegree(@NonNull String path)

// 获取位图的内存大小
int getBitmapSize(@NonNull Bitmap source)

// bitmap截图
Bitmap cutBitmap(Bitmap source, int x, int y, int width, int height)

// 图片去色,返回灰度图片
Bitmap toGrayscale(Bitmap source)
```

- [<div id="ShapeUtil">ShapeUtil</div>](https://github.com/albert-lii/SUtils/blob/master/sutils/src/main/java/com/liyi/sutils/utils/graphic/ShapeUtil.java)
```Java
// 获取圆角矩形
GradientDrawable getRectShape(int fillColor, int radius)
GradientDrawable getRectShape(int fillColor, int radius, int strokeColor, int strokeWidth)
GradientDrawable getRectShape(int fillColor, float[] radii, int strokeColor, int strokeWidth) 
```

### IO相关

- [<div id="ACache">ACache</div>](https://github.com/albert-lii/SUtils/blob/master/sutils/src/main/java/com/liyi/sutils/utils/io/ACache.java)

- [<div id="AssetUtil">AssetUtil</div>](https://github.com/albert-lii/SUtils/blob/master/sutils/src/main/java/com/liyi/sutils/utils/io/AssetUtil.java)
```Java
// 获取assets目录下的文件
String getFileFromAssets(@NonNull Context context, @NonNull String path)

// 获取assets目录下的图片
Bitmap getImageFromAssets(Context context, String path)
```

- [<div id="FileUtil">FileUtil</div>](https://github.com/albert-lii/SUtils/blob/master/sutils/src/main/java/com/liyi/sutils/utils/io/FileUtil.java)
```Java
// 判断是否有SD卡
boolean isHasSDCard()

// 获取SD卡路径
String getSDCardPath()

// 创建文件夹 （你必须先创建文件夹，才能创建文件，否则会报“找不到路径”）
boolean createDir(@NonNull String path)

// 创建文件
boolean createFile(@NonNull String path)

======================IO 操作======================

// 保存String数据
put(String key, String value) 

// 读取String数据
getAsString(String key)

// 保存byte[]
put(String key, byte[] value)

// 读取byte[]
getAsBinary(String key)

// 保存序列化对象
put(String key, Serializable value)

// 读取序列化对象
Object getAsObject(String key)

// 保存bitmap数据
put(String key, Bitmap value)

// 读取bitmap数据
Bitmap getAsBitmap(String key)

======================拷贝删除 操作======================

// 拷贝文件到指定路径
copyFile(@NonNull String oldPath, @NonNull String newPath)

// 拷贝文件夹内容到指定位置
copyDir(@NonNull String oldPath, @NonNull String newPath)

// 获取指定文件夹中文件的数量
int getFileCount(@NonNull String dir, boolean isAll)

// 删除文件或文件夹
boolean delete(@NonNull String path)

//  删除单个文件
boolean deleteFile(@NonNull String path)

// 删除文件夹
boolean deleteDir(@NonNull String dir)

=======================获取文件大小========================

// 获取指定文件或者文件夹的大小
long getFileSize(@NonNull String path)

// 获取单个文件的大小
long getSingleFileSize(@NonNull File file)

// 获取指定文件夹的大小
long getFileDirSize(@NonNull File dir)

=======================获取文件的真实路径========================

// 获取文件的真实路径
getRealPath(@NonNull final Context context, @NonNull final Uri uri)
```

 - [<div id="GsonUtil">GsonUtil</div>](https://github.com/albert-lii/SUtils/blob/master/sutils/src/main/java/com/liyi/sutils/utils/io/GsonUtil.java)
 ```Java
 // 将 json 数据转化为 bean
 <T> T json2Bean(String jsonStr, Class<T> cls)
 
 // 将 json 数据转换为 list
 <T> List<T> json2List(String jsonStr, Type type)
 
 // 将 json 数据转化为 map
 <T> Map<String, T> json2Map(String jsonStr)
 
 // 将 json 数据转化为 map 元素的 list
 <T> List<Map<String, T>> json2ListMap(String jsonStr)
 
 // 将对象转换成 string 数据
 String obj2String(Object obj)
 ````
 
 - [<div id="SpUtil">SpUtil</div>](https://github.com/albert-lii/SUtils/blob/master/sutils/src/main/java/com/liyi/sutils/utils/io/SpUtil.java)
```Java
SpUtil get(@NonNull Context context)

SpUtil get(@NonNull Context context, @NonNull String fileName, @NonNull int mode)

// 保存单个数据
put(String key, Object object)

// 同时保存多条数据
void add(Map<String, Object> map)

// 获取存储的数据
Object get(String key, Object object)

// 根据 key 删除数据
void remove(String key)

// 清除所有的数据
void clear()

SharedPreferences.Editor getEditor()
```

### Log相关
- [<div id="CrashHandler">CrashHandler</div>](https://github.com/albert-lii/SUtils/blob/master/sutils/src/main/java/com/liyi/sutils/utils/log/CrashHandler.java)
```Java
// 建议在application中初始化
initialize(@NonNull Context context)
initialize(@NonNull Context context, Config config)
```

- [<div id="LogUtil">LogUtil</div>](https://github.com/albert-lii/SUtils/blob/master/sutils/src/main/java/com/liyi/sutils/utils/log/LogUtil.java)
```Java
// 可以用于release时，统一关闭log
public static boolean isLogEnable = true;

void v(String tag, String msg)

void i(String tag, String msg)

void w(String tag, String msg)

void e(String tag, String msg)

// 输出log中包含的信息
String getLogInfo(StackTraceElement stackTraceElement)
```

### 网络相关
- [<div id="NetUtil">NetUtil</div>](https://github.com/albert-lii/SUtils/blob/master/sutils/src/main/java/com/liyi/sutils/utils/network/NetUtil.java)
```Java
// 判断网络是否连接
boolean isConnected(@NonNull Context context)

// 判断wifi是否连接
boolean isWifiConnected(@NonNull Context context)

// 获取当前网络的类型
// 返回 NETTYPE_WIFI，表示 wifi;
// 返回 NETTYPE_2G，表示 2g网;
// 返回 NETTYPE_3G，表示 3g网;
// 返回 NETTYPE_4G，表示 4g网;
// 返回 NETTYPE_NONE，表示当前未连接网络
int getNetWorkType(@NonNull Context context)
```

### 其他
- [<div id="AlertDialogUtil">AlertDialogUtil</div>](https://github.com/albert-lii/SUtils/blob/master/sutils/src/main/java/com/liyi/sutils/utils/other/AlertDialogUtil.java)

 - [<div id="EventBusUtil">EventBusUtil</div>](https://github.com/albert-lii/SUtils/blob/master/sutils/src/main/java/com/liyi/sutils/utils/other/EventBusUtil.java)
```Java
// 使用索引加速
// 推荐在application中使用
void installIndex(SubscriberInfoIndex index)

// 注册eventbus
void register(Object subscriber)

// 取消注册eventbus
void unregister(Object subscriber)

// 发布一个订阅事件
// 必须先注册，才能接收到发布的事件，有点类似于startActivityForResult()方法
void post(Object event)

// 发布粘性事件（可以先发布事件，在注册后在接收）
void postSticky(Object event)

// 移除指定的粘性订阅事件
void removeStickyEvent(Class<T> eventType)

// 移除所有的粘性订阅事件
void removeAllStickyEvents()

// 优先级高的订阅者可以终止事件往下传递
// 只有在事件通过时才能调用（即在事件接收方法中调用）
void cancelEventDelivery(Object event)

// 获取EventBus单例
EventBus getEventBus()
````

- [<div id="QRCodeUtil">QRCodeUtil</div>](https://github.com/albert-lii/SUtils/blob/master/sutils/src/main/java/com/liyi/sutils/utils/other/QRCodeUtil.java)
```Java
// 生成二维码的Bitmap
Bitmap generateQRImage(@NonNull String content, int width, int height) 

// 生成二维码的Bitmap
// @param content 二维码中的内容
// @param width   二维码的宽
// @param height  二维码的高
// @param height  二维码空白边距的宽度
Bitmap generateQRImage(@NonNull String content, int width, int height, int border)

// 在二维码中间添加Logo图案
Bitmap addLogo(Bitmap qrBitmap, Bitmap logoBitmap)

// 解析二维码（使用解析RGB编码数据的方式）
Result decodeQRcodeRGB(String path)

// 解析二维码 （使用解析RGB编码数据的方式）
Result decodeQRcodeRGB(Bitmap qrcode)

// 解析二维码（使用解析YUV编码数据的方式）
Result decodeQRcodeYUV(String path)

// 解析二维码（使用解析YUV编码数据的方式）
Result decodeQRcodeYUV(Bitmap qrcode)

// 生成条形码
// @param contents      需要生成的内容
// @param desiredWidth  生成条形码的宽带
// @param desiredHeight 生成条形码的高度
// @param displayCode   是否在条形码下方显示内容
Bitmap generateBarImage(@NonNull Context context, String contents, int desiredWidth, int desiredHeight, boolean displayCode)
```

- [<div id="ReflectUtil">ReflectUtil</div>](https://github.com/albert-lii/SUtils/blob/master/sutils/src/main/java/com/liyi/sutils/utils/other/ReflectUtil.java)

- [<div id="RegExUtil">RegExUtil</div>](https://github.com/albert-lii/SUtils/blob/master/sutils/src/main/java/com/liyi/sutils/utils/other/RegExUtil.java)
```Java
boolean compile(@NonNull String regEx, CharSequence value) 
```

- [<div id="SpanUtil">SpanUtil</div>](https://github.com/albert-lii/SUtils/blob/master/sutils/src/main/java/com/liyi/sutils/utils/other/SpanUtil.java)
```Java
SpanUtil.bind(TextView textView)
        // 设置SpannableString样式
        .setSpanFlag(int flag)
        .addContent(CharSequence text)
        // 设置文字颜色
        .addFontColorByKey(@ColorInt int color, String key)
        // 设置背景色
        .addBgColorByKey(@ColorInt int color, String key)
        // 对文字添加url链接
        .addURLByKey(String url, String key)
        // 设置文字字体样式，例如斜体
        .addTypefaceByKey(int style, String key) 
        // 添加删除线
        .addStrikethroughByKey(String key)
        // 用图片替代指定文字
        .addImageByKey(ImageSpan span, String key)
        // 为指定文字添加点击事件
        .addClickByKey(final OnTextClickListener listener, final boolean isNeedUnderLine, String key)
        // 执行
        .build()

// 查询字符串中的所有关键字
List<int[]> searchAllIndex(String key) 
```

- [<div id="TimeUtil">TimeUtil</div>](https://github.com/albert-lii/SUtils/blob/master/sutils/src/main/java/com/liyi/sutils/utils/other/TimeUtil.java)
```Java
// 将时间戳转换为指定格式的时间字符串
String getTimeStr(long timeStamp, @NonNull String dateType)

// 将时间字符串转换为时间戳
long getTimeStamp(@NonNull String timeStr, String dateType)

// 计算时间差
long caculateTimeDiff(@NonNull Object startTime, @NonNull Object endTime, String dateType)

// 计算时间差,返回天数、小时数、分钟数、秒数
int[] caculateTimeDiffArray(@NonNull Object startTime, @NonNull Object endTime, String dateType)
```

- [<div id="ToastUtil">ToastUtil</div>](https://github.com/albert-lii/SUtils/blob/master/sutils/src/main/java/com/liyi/sutils/utils/other/ToastUtil.java)
```Java
void show(@NonNull Context context, CharSequence msg) 

void show(@NonNull Context context, @StringRes int stringId)

void void show(@NonNull Context context, CharSequence msg, int duration)

void show(@NonNull Context context, View root)

void show(@NonNull Context context, View root, int duration)
```


## LICENSE
Copyright 2017 liyi

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

   http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
