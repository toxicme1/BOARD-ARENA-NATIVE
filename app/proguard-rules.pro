# ProGuard rules for Board Arena Native

# Keep Hilt generated classes
-keep class dagger.hilt.** { *; }
-keep class com.boardarena.native.Hilt_* { *; }

# Keep Room entities and DAOs
-keep class com.boardarena.native.game.model.** { *; }
-keep class com.boardarena.native.data.database.** { *; }

# Keep Kotlin serialization
-keep class kotlinx.serialization.** { *; }
-keepclassmembers class * {
    @kotlinx.serialization.Serializable *;
}

# Keep Socket.IO
-keep class io.socket.** { *; }

# Keep OkHttp and Retrofit
-keep class okhttp3.** { *; }
-keep class retrofit2.** { *; }
-keep class com.squareup.okhttp3.** { *; }
-keep class com.squareup.retrofit2.** { *; }

# Keep Coil
-keep class coil.** { *; }

# Keep Accompanist
-keep class com.google.accompanist.** { *; }

# Keep navigation
-keep class androidx.navigation.** { *; }

# Keep Hilt navigation
-keep class androidx.hilt.navigation.** { *; }

# Keep Compose
-keep class androidx.compose.** { *; }