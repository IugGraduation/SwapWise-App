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

# Dagger/Hilt Rules
# These rules are essential for Hilt to work correctly with minification.
# They prevent the removal of generated code that Hilt uses for dependency injection.
-keep,allowobfuscation @dagger.hilt.android.AndroidEntryPoint class *
-keep,allowobfuscation @dagger.hilt.android.HiltAndroidApp class *
-keep @dagger.Module class * { *; }
-keep @dagger.BindsInstance class * { *; }
-keepclassmembers,allowobfuscation @javax.inject.Inject class * { *; }
-keepclassmembers @dagger.Provides class * { *; }
-keep @dagger.hilt.InstallIn class *
-keepclassmembers class * extends androidx.lifecycle.ViewModel { <init>(...); }

# Kotlinx Serialization Rules
# These rules prevent ProGuard from renaming classes and properties annotated
# with @Serializable, which would break JSON parsing.
-keep,includedescriptorclasses class **.* { @kotlinx.serialization.Serializable *; }
-keepclassmembers class **.* { @kotlinx.serialization.Serializable *; }
-keepclassmembers class **.*$Companion { @kotlinx.serialization.Serializable *; }
-keep,includedescriptorclasses class **.*$$serializer { *; }

# Ktor Rules (for Supabase Networking)
# Supabase uses Ktor for networking. Ktor uses reflection, so its classes must be kept.
-keep class io.ktor.** { *; }
-dontwarn io.ktor.**

# Coil Rules (for image loading)
# Keeps Coil's classes to ensure image loading works in release builds.
-keep class coil.** { *; }
-keep class coil.compose.** { *; }
-dontwarn coil.compose.**

# Kotlinx Coroutines Rules
# Prevents issues with coroutine machinery and exception handling.
-keepclassmembers class kotlinx.coroutines.internal.** { *; }
-keepclassmembers class kotlinx.coroutines.flow.internal.** { *; }

# Jetpack Compose Rules
# Keeps composable functions and their related compiler-generated classes.
-keepclassmembers class **.R$* { public static <fields>; }
-keep class **.R$* { <init>(); }
-keepclassmembers class * { @androidx.compose.runtime.Composable <methods>; }
