# SUtils

![releasesvg] ![apisvg] [![license][licensesvg]][license]

## 关于
SUtils是一款轻量级Android工具类库，集成了众多平时开发中常用到的工具类，将一直持续更新！

## 添加依赖

- **Gradle**
```Java
   dependencies {
       compile 'com.liyi:sutils:2.0.5'
   }
```

- **Maven**
```Java
   <dependency>
     <groupId>com.liyi</groupId>
     <artifactId>sutils</artifactId>
     <version>2.0.5</version>
     <type>pom</type>
   </dependency>
```

## 目录（源码中附有详细注释，如不知使用方法，请留言）
| 文件名 | 描述 |
| ---- | ---- |
| **加密相关** |
| [AesUtil][AesUtil] | aes 加密 |
| [Base64Util][Base64Util] | base64 加密  |
| [Md5Util][Md5Util] | md5 加密 |
| [RsaUtil][RsaUtil] | rsa 加密 |
| [XorUtil][XorUtil] | 异或加密 |
| **图形处理相关** |
| [FastBlur][FastBlur] | fastblur 高斯模糊算法 |
| [RSBlur][RSBlur] | RenderScript 模高斯糊 |
| [ImageUtil][ImageUtil] | 图片相关工具类 |
| [ShapeUtil][ShapeUtil] | shape 相关工具类 |
| **io 相关** |
| [ACache][ACache] | 轻量级缓存工具类 |
| [AssetUtil][AssetUtil] | assets 相关工具类 |
| [FileUtil][FileUtil] | file 相关工具类 |
| [GsonUtil][GsonUtil] | gson 相关工具类 |
| [SPUtil][SPUtil] | sharedpreferences 相关类 |
| **log 相关** |
| [CrashUtil][CrashUtil] | 崩溃处理相关工具类 |
| [LogUtil][LogUtil] | log 相关工具类 |
| **权限相关** |
| [PermissionUtil][PermissionUtil] | 权限相关工具类 |
| **时间相关** |
| [CountdownUtil][CountdownUtil] | 倒计时相关工具类 |
| [TimeUtil][TimeUtil] | 时间相关工具类 |
| **其他** |
| [AlertDialogUtil][AlertDialogUtil] | 系统弹框相关工具类 |
| [AppUtil][AppUtil] | app 相关工具类 |
| [AtyTransitionUtil][AtyTransitionUtil] | activity 转场动画 |
| [CleanUtil][CleanUtil] | 清除相关工具类 |
| [ClipboardUtil][ClipboardUtil] | 剪切板相关工具类 |
| [DensityUtil][DensityUtil] | 单位转换工具类 |
| [DeviceUtil][DeviceUtil] | 设备相关工具类 |
| [EmptyUtil][EmptyUtil] | 判空相关工具类 |
| [EventBusUtil][EventBusUtil] | EventBus 3.0 工具类 |
| [HandlerUtil][HandlerUtil] | handler 相关工具类 |
| [KeyboardUtil][KeyboardUtil] | 键盘相关工具类 |
| [LocationUtil][LocationUtil] | 位置相关工具类 |
| [NetUtil][NetUtil] | 网络相关工具类 |
| [NfcUtil][NfcUtil] | NFC 相关的工具类 |
| [OrientationUtil][OrientationUtil] | 屏幕方向监听工具类 |
| [PhoneUtil][PhoneUtil] | 手机相关工具类 |
| [PinyinUtil][PinyinUtil] | 拼音相关工具类 |
| [QRCodeUtil][QRCodeUtil] | 二维码、条形码相关工具类 |
| [ReflectUtil][ReflectUtil] | 反射相关工具类 |
| [RegexUtil][RegexUtil] | 正则相关工具类 |
| [ScreenUtil][ScreenUtil] | 屏幕相关工具类 |
| [ServiceUtil][ServiceUtil] | service 相关工具类 |
| [ShellUtil][ShellUtil] | shell 相关工具类 |
| [SpanUtil][SpanUtil] | SpannableString 相关工具类 |
| [StringUtil][StringUtil] |  String 相关工具类 |
| [SystemBarUtil][SystemBarUtil] | 系统状态栏与底部导航栏相关工具类 |
| [SystemSettingUtil][SystemSettingUtil] | 进入指定系统功能界面的相关工具类 |
| [ToastUtil][ToastUtil] | Toast 相关工具类 |
| [ZipUtil][ZipUtil] | 压缩相关工具类 |
| [SUtils][SUtils] | SUtils 初始化工具类 |

### 权限使用相关
- [PermissionUtil][PermissionUtil]
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

## 赞赏
如果你感觉 `SUtils` 帮助到了你，可以点右上角 "Star" 支持一下 谢谢！:blush:

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

[releasesvg]: https://img.shields.io/badge/Release-v2.0.5-brightgreen.svg
[apisvg]: https://img.shields.io/badge/API-9+-brightgreen.svg
[licensesvg]: https://img.shields.io/badge/License-Apache--2.0-brightgreen.svg
[license]:http://www.apache.org/licenses/LICENSE-2.0
[statussvg]:https://img.shields.io/librariesio/github/phoenixframework/phoenix.svg

[AesUtil]:https://github.com/albert-lii/SUtils/blob/master/sutils/src/main/java/com/liyi/sutils/utils/encrypt/AesUtil.java
[Base64Util]:https://github.com/albert-lii/SUtils/blob/master/sutils/src/main/java/com/liyi/sutils/utils/encrypt/Base64Util.java
[Md5Util]:https://github.com/albert-lii/SUtils/blob/master/sutils/src/main/java/com/liyi/sutils/utils/encrypt/Md5Util.java
[RsaUtil]:https://github.com/albert-lii/SUtils/blob/master/sutils/src/main/java/com/liyi/sutils/utils/encrypt/RsaUtil.java
[XorUtil]:https://github.com/albert-lii/SUtils/blob/master/sutils/src/main/java/com/liyi/sutils/utils/encrypt/XorUtil.java

[FastBlur]:https://github.com/albert-lii/SUtils/blob/master/sutils/src/main/java/com/liyi/sutils/utils/graphic/blur/FastBlur.java
[RSBlur]:https://github.com/albert-lii/SUtils/blob/master/sutils/src/main/java/com/liyi/sutils/utils/graphic/blur/RSBlur.java
[ImageUtil]:https://github.com/albert-lii/SUtils/blob/master/sutils/src/main/java/com/liyi/sutils/utils/graphic/ImageUtil.java
[ShapeUtil]:https://github.com/albert-lii/SUtils/blob/master/sutils/src/main/java/com/liyi/sutils/utils/graphic/ShapeUtil.java

[ACache]:https://github.com/albert-lii/SUtils/blob/master/sutils/src/main/java/com/liyi/sutils/utils/io/ACache.java
[AssetUtil]:https://github.com/albert-lii/SUtils/blob/master/sutils/src/main/java/com/liyi/sutils/utils/io/AssetUtil.java
[FileUtil]:https://github.com/albert-lii/SUtils/blob/master/sutils/src/main/java/com/liyi/sutils/utils/io/FileUtil.java
[GsonUtil]:https://github.com/albert-lii/SUtils/blob/master/sutils/src/main/java/com/liyi/sutils/utils/io/GsonUtil.java
[SPUtil]:https://github.com/albert-lii/SUtils/blob/master/sutils/src/main/java/com/liyi/sutils/utils/io/SPUtil.java  

[CrashUtil]:https://github.com/albert-lii/SUtils/blob/master/sutils/src/main/java/com/liyi/sutils/utils/log/CrashUtil.java 
[LogUtil]:https://github.com/albert-lii/SUtils/blob/master/sutils/src/main/java/com/liyi/sutils/utils/log/LogUtil.java  

[PermissionUtil]:https://github.com/albertlii/SUtils/blob/master/sutils/src/main/java/com/liyi/sutils/utils/permission/PermissionUtil.java

[CountdownUtil]:https://github.com/albert-lii/SUtils/blob/master/sutils/src/main/java/com/liyi/sutils/utils/time/CountdownUtil.java 
[TimeUtil]:https://github.com/albert-lii/SUtils/blob/master/sutils/src/main/java/com/liyi/sutils/utils/time/TimeUtil.java

[AlertDialogUtil]:https://github.com/albert-lii/SUtils/blob/master/sutils/src/main/java/com/liyi/sutils/utils/AlertDialogUtil.java  
[AppUtil]:https://github.com/albert-lii/SUtils/blob/master/sutils/src/main/java/com/liyi/sutils/utils/AppUtil.java  
[AtyTransitionUtil]:https://github.com/albert-lii/SUtils/blob/master/sutils/src/main/java/com/liyi/sutils/utils/AtyTransitionUtil.java 
[CleanUtil]:https://github.com/albert-lii/SUtils/blob/master/sutils/src/main/java/com/liyi/sutils/utils/CleanUtil.java  
[ClipboardUtil]:https://github.com/albert-lii/SUtils/blob/master/sutils/src/main/java/com/liyi/sutils/utils/ClipboardUtil.java  
[DensityUtil]:https://github.com/albert-lii/SUtils/blob/master/sutils/src/main/java/com/liyi/sutils/utils/DensityUtil.java
[DeviceUtil]:https://github.com/albert-lii/SUtils/blob/master/sutils/src/main/java/com/liyi/sutils/utils/DeviceUtil.java
[EmptyUtil]:https://github.com/albert-lii/SUtils/blob/master/sutils/src/main/java/com/liyi/sutils/utils/EmptyUtil.java  
[EventBusUtil]:https://github.com/albert-lii/SUtils/blob/master/sutils/src/main/java/com/liyi/sutils/utils/EventBusUtil.java 
[HandlerUtil]:https://github.com/albert-lii/SUtils/blob/master/sutils/src/main/java/com/liyi/sutils/utils/HandlerUtil.java  
[KeyboardUtil]:https://github.com/albert-lii/SUtils/blob/master/sutils/src/main/java/com/liyi/sutils/utils/KeyboardUtil.java 
[LocationUtil]:https://github.com/albert-lii/SUtils/blob/master/sutils/src/main/java/com/liyi/sutils/utils/LocationUtil.java 
[NetUtil]:https://github.com/albert-lii/SUtils/blob/master/sutils/src/main/java/com/liyi/sutils/utils/NetUtil.java  
[NfcUtil]:https://github.com/albert-lii/SUtils/blob/master/sutils/src/main/java/com/liyi/sutils/utils/NfcUtil.java  
[OrientationUtil]:https://github.com/albert-lii/SUtils/blob/master/sutils/src/main/java/com/liyi/sutils/utils/OrientationUtil.java  
[PhoneUtil]:https://github.com/albert-lii/SUtils/blob/master/sutils/src/main/java/com/liyi/sutils/utils/PhoneUtil.java  
[PinyinUtil]:https://github.com/albert-lii/SUtils/blob/master/sutils/src/main/java/com/liyi/sutils/utils/PinyinUtil.java  
[QRCodeUtil]:https://github.com/albert-lii/SUtils/blob/master/sutils/src/main/java/com/liyi/sutils/utils/QRCodeUtil.java  
[ReflectUtil]:https://github.com/albert-lii/SUtils/blob/master/sutils/src/main/java/com/liyi/sutils/utils/ReflectUtil.java 
[RegexUtil]:https://github.com/albert-lii/SUtils/blob/master/sutils/src/main/java/com/liyi/sutils/utils/RegexUtil.java  
[ScreenUtil]:https://github.com/albert-lii/SUtils/blob/master/sutils/src/main/java/com/liyi/sutils/utils/ScreenUtil.java  
[ServiceUtil]:https://github.com/albert-lii/SUtils/blob/master/sutils/src/main/java/com/liyi/sutils/utils/ServiceUtil.java  
[ShellUtil]:https://github.com/albert-lii/SUtils/blob/master/sutils/src/main/java/com/liyi/sutils/utils/ShellUtil.java 
[ScreenUtil]:https://github.com/albert-lii/SUtils/blob/master/sutils/src/main/java/com/liyi/sutils/utils/ScreenUtil.java 
[SpanUtil]:https://github.com/albert-lii/SUtils/blob/master/sutils/src/main/java/com/liyi/sutils/utils/SpanUtil.java  
[StringUtil]:https://github.com/albert-lii/SUtils/blob/master/sutils/src/main/java/com/liyi/sutils/utils/StringUtil.java  
[SystemBarUtil]:https://github.com/albert-lii/SUtils/blob/master/sutils/src/main/java/com/liyi/sutils/utils/SystemBarUtil.java
[SystemSettingUtil]:https://github.com/albert-lii/SUtils/blob/master/sutils/src/main/java/com/liyi/sutils/utils/SystemSettingUtil.java 
[StringUtil]:https://github.com/albert-lii/SUtils/blob/master/sutils/src/main/java/com/liyi/sutils/utils/StringUtil.java 
[ToastUtil]:https://github.com/albert-lii/SUtils/blob/master/sutils/src/main/java/com/liyi/sutils/utils/ToastUtil.java
[ZipUtil]:https://github.com/albert-lii/SUtils/blob/master/sutils/src/main/java/com/liyi/sutils/utils/ZipUtil.java  
[SUtils]:https://github.com/albert-lii/SUtils/blob/master/sutils/src/main/java/com/liyi/sutils/utils/SUtils.java
