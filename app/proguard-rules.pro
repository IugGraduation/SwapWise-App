# Add project specific ProGuard rules here.
# You can control the set of applied configuration files using the
# proguardFiles setting in build.gradle.

# Dagger/Hilt Rules
-keep,allowobfuscation @dagger.hilt.android.AndroidEntryPoint class *
-keep,allowobfuscation @dagger.hilt.android.HiltAndroidApp class *
-keep @dagger.Module class * { *; }
-keep @dagger.BindsInstance class * { *; }
-keepclassmembers,allowobfuscation @javax.inject.Inject class * { *; }
-keepclassmembers @dagger.Provides class * { *; }
-keep @dagger.hilt.InstallIn class *
-keepclassmembers class * extends androidx.lifecycle.ViewModel { <init>(...); }

# Kotlinx Serialization Rules
-keep,includedescriptorclasses class **.* { @kotlinx.serialization.Serializable *; }
-keepclassmembers class **.* { @kotlinx.serialization.Serializable *; }
-keepclassmembers class **.*$Companion { @kotlinx.serialization.Serializable *; }
-keep,includedescriptorclasses class **.*$$serializer { *; }

# Ktor & Supabase Rules
# Supabase uses Ktor for networking. Ktor uses reflection, so its classes must be kept.
-keep class io.ktor.** { *; }
-dontwarn io.ktor.**
-keep class io.github.jan.supabase.** { *; }
-dontwarn io.github.jan.supabase.**

# Common missing classes for Ktor/Supabase transitive dependencies
-dontwarn org.slf4j.**
-dontwarn org.apache.http.**
-dontwarn sun.misc.Unsafe
-dontwarn sun.reflect.**
-dontwarn com.google.errorprone.annotations.**
-dontwarn com.google.j2objc.annotations.RetainedWith
-dontwarn javax.annotation.**

# Coil Rules (for image loading)
-keep class coil.** { *; }
-keep class coil.compose.** { *; }
-dontwarn coil.compose.**
-dontwarn coil3.PlatformContext

# Kotlinx Coroutines Rules
-keepclassmembers class kotlinx.coroutines.internal.** { *; }
-keepclassmembers class kotlinx.coroutines.flow.internal.** { *; }
-dontwarn kotlinx.coroutines.debug.**

# Jetpack Compose Rules
-keepclassmembers class **.R$* { public static <fields>; }
-keep class **.R$* { <init>(); }
-keepclassmembers class * { @androidx.compose.runtime.Composable <methods>; }
