# ==============================================================================
# FARSHBAZAR PROGUARD / R8 RELEASE RULES (CAFE BAZAAR COMPATIBILITY)
# ==============================================================================

# 1. Keep domain models & DTOs for serialization/deserialization
-keep class com.example.model.** { *; }
-keep class com.example.service.** { *; }

# 2. Moshi & JSON Codegen
-keepclassmembers class * {
    @com.squareup.moshi.* <fields>;
    @com.squareup.moshi.* <methods>;
}
-keep class com.squareup.moshi.** { *; }

# 3. Retrofit & OkHttp
-dontwarn okhttp3.**
-dontwarn okio.**
-dontwarn retrofit2.**
-keepattributes Signature, InnerClasses, EnclosingMethod
-keepattributes RuntimeVisibleAnnotations, RuntimeVisibleParameterAnnotations

# 4. Kotlin Coroutines
-keepnames class kotlinx.coroutines.internal.MainDispatcherFactory {}
-keepnames class kotlinx.coroutines.CoroutineExceptionHandler {}
-keepclassmembernames class kotlinx.coroutines.** {
    volatile <fields>;
}

# 5. Room Database
-keep class * extends androidx.room.RoomDatabase
-dontwarn androidx.room.paging.**

# 6. Jetpack Compose
-keepclassmembers class * extends androidx.compose.runtime.State { *; }

# 7. Preserve Line Numbers for Crash Reporting / Bazaar Developer Console
-keepattributes SourceFile, LineNumberTable
-renamesourcefileattribute SourceFile
