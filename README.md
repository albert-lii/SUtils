# SUtils
> 轻量级Android工具类库，集成了众多平时开发用到的工具类，方便快速开发项目！

## 联系方式
> **QQ：1009365545**

> **电子邮箱：albertlii@163.com**

## 项目内容
### 目录
#### APP相关
- [PermissionUtil](#PermissionUtil) Android6.0后权限管理类
- [AppUtil](#AppUtil) 与APP相关
- [AtyTransitionUtil](#AtyTransitionUtil) Activity过渡动画类
- [DensityUtil](#DensityUtil) dp、sp、px转换类
- [KeyboardUtil](#KeyboardUtil) 虚拟键盘工具类
- [ScreenUtil](#ScreenUtil) 与屏幕相关
- [SystemBarUtil](#SystemBarUtil) 系统状态栏和底部虚拟导航栏的工具类
- [SystemPageUtil](#SystemPageUtil) 系统功能界面工具类
#### 设备相关
- [DeviceUtil](#DeviceUtil) 设备信息相关工具类
- [GpsUtil](#GpsUtil) GPS工具类
- [NfcUtil](#NfcUtil) NFC工具类

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
- [GpsUtil](https://github.com/albert-lii/SUtils/blob/master/sutils/src/main/java/com/liyi/sutils/utils/device/GpsUtil.java)
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

- [NfcUtil](https://github.com/albert-lii/SUtils/blob/master/sutils/src/main/java/com/liyi/sutils/utils/device/NfcUtil.java)
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

-[RSBlur](https://github.com/albert-lii/SUtils/blob/master/sutils/src/main/java/com/liyi/sutils/utils/graphic/blur/RSBlur.java)
```Java
Bitmap blur(Context context, Bitmap blurredBitmap, int radius)
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

- [<div id="ACache">ACache</div>](https://github.com/albert-lii/SUtils/blob/master/sutils/src/main/java/com/liyi/sutils/utils/io/ACache.java)

-[<div id="AssetUtil">AssetUtil</div>](https://github.com/albert-lii/SUtils/blob/master/sutils/src/main/java/com/liyi/sutils/utils/io/AssetUtil.java)
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
 
 - [<div id="SpUtil">SpUtil</div>](https://github.com/albert-lii/SUtils/blob/master/sutils/src/main/java/com/liyi/sutils/utils/io/SpUtil.java)
 
