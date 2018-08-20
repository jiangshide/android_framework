# Add project specific ProGuard rules here.
# You can control the set of applied configuration files using the
# proguardFiles setting in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}

# Uncomment this to preserve the line number information for
# debugging stack traces.
#-keepattributes SourceFile,LineNumberTable

# If you keep the line number information, uncomment this to
# hide the original source file name.
#-renamesourcefileattribute SourceFile


# 指定代码的压缩级别0~7
-optimizationpasses 5
## 是否使用大小写混合
#-dontusemixedcaseclassnames
## 是否混淆第三方jar
#-dontskipnonpubliclibraryclasses
## 混淆时是否做预校验
#-dontpreverify
##重命名
#-renamesourcefileattribute SourceFile
##保护给定的可选属性，例如LineNumberTable, LocalVariableTable, SourceFile, Deprecated, Synthetic, Signature, and InnerClasses.
#-keepattributes SourceFile,LineNumberTable
## 混淆时是否记录日志
#-verbose
#-optimizations !code/simplification/arithmetic,!field/*,!class/merging/*
###压缩
# #不压缩输入的类文件
#-dontshrink
###优化
##不优化输入的类文件
#-dontoptimize
##优化时允许访问并修改有修饰符的类和类的成员
#-allowaccessmodification
#
## Disabling obfuscation is useful if you collect stack traces from production crashes
## (unless you are using a system that supports de-obfuscate the stack traces).
#-dontobfuscate
#
## TextLayoutBuilder uses a non-public Android constructor within StaticLayout.
## See libs/proxy/src/main/java/com/facebook/fbui/textlayoutbuilder/proxy for details.
#-dontwarn android.text.StaticLayout
#
## okhttp
#
#-keepattributes Signature
#-keepattributes *Annotation*
#-keep class okhttp3.** { *; }
#-keep interface okhttp3.** { *; }
#-dontwarn okhttp3.**
#
## okio
#
#-keep class sun.misc.Unsafe { *; }
#-dontwarn java.nio.file.*
#-dontwarn org.codehaus.mojo.animal_sniffer.IgnoreJRERequirement
#-dontwarn okio.**
#
##http
#-keep class org.apache.http.**
#-keep interface org.apache.http.**
#-dontwarn org.apache.**

# Retain generic type information for use by reflection by converters and adapters.
#-keepattributes Signature
## Retain service method parameters.
#-keepclassmembernames,allowobfuscation interface * {
#    @retrofit2.http.* <methods>;
#}
## Ignore annotation used for build tooling.
#-dontwarn org.codehaus.mojo.animal_sniffer.IgnoreJRERequirement

-dontwarn okhttp3.**
-dontwarn okio.**
-dontwarn javax.annotation.**
-dontwarn org.conscrypt.**
# A resource is loaded with a relative path so the package of this class must be preserved.
-keepnames class okhttp3.internal.publicsuffix.PublicSuffixDatabase

###########-->gio start<---#########
-keep class com.growingio.android.sdk.** {
    *;
}
-dontwarn com.growingio.android.sdk.**
-keepnames class * extends android.view.View
-keep class * extends android.app.Fragment {
    public void setUserVisibleHint(boolean);
    public void onHiddenChanged(boolean);
    public void onResume();
    public void onPause();
}
-keep class android.support.v4.app.Fragment {
    public void setUserVisibleHint(boolean);
    public void onHiddenChanged(boolean);
    public void onResume();
    public void onPause();
}
-keep class * extends android.support.v4.app.Fragment {
    public void setUserVisibleHint(boolean);
    public void onHiddenChanged(boolean);
    public void onResume();
    public void onPause();
}
-keep class com.growingio.android.sdk.collection.GrowingIOInstrumentation {
    public *;
    static <fields>;
}
############--->gio end<----###########

###########-->jpush start<---#########
-dontoptimize
-dontpreverify

-dontwarn cn.jpush.**
-keep class cn.jpush.** { *; }
-keep class * extends cn.jpush.android.helpers.JPushMessageReceiver { *; }

-dontwarn cn.jiguang.**
-keep class cn.jiguang.** { *; }
############--->jpush end<----###########

############--->js start<----###########
-keepclassmembers class cn.xx.xx.Activity$AppAndroid {
  public *;
}

-keepattributes *Annotation*
-keepattributes *JavascriptInterface*
###########-->js end<---#########

############--->bugly start<----###########
-dontwarn com.tencent.bugly.**
-keep public class com.tencent.bugly.**{*;}
###########-->bugly end<---#########

############--->webview start<----###########
-keepattributes *Annotation*
-keepattributes *JavascriptInterface*
-keepclassmembers class fqcn.of.javascript.interface.for.webview {
   public *;
 }
-keepclassmembers class com.stateunion.p2p.etongdai.WebActivity$* {
  public *;
}
############--->webview end<----###########