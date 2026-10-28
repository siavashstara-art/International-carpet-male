package com.example.service

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import androidx.core.content.FileProvider
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File
import java.io.FileOutputStream

object StoryShareHelper {

    suspend fun saveBitmapToCache(context: Context, bitmap: Bitmap): File = withContext(Dispatchers.IO) {
        val cacheFolder = File(context.cacheDir, "stories")
        if (!cacheFolder.exists()) {
            cacheFolder.mkdirs()
        }
        val file = File(cacheFolder, "story_${System.currentTimeMillis()}.png")
        FileOutputStream(file).use { out ->
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, out)
        }
        file
    }

    fun shareStoryImage(context: Context, imageFile: File, title: String = "استوری فرش دستباف") {
        val authority = "${context.packageName}.fileprovider"
        val contentUri = FileProvider.getUriForFile(context, authority, imageFile)

        val shareIntent = Intent(Intent.ACTION_SEND).apply {
            type = "image/png"
            putExtra(Intent.EXTRA_STREAM, contentUri)
            putExtra(Intent.EXTRA_SUBJECT, title)
            putExtra(Intent.EXTRA_TEXT, "معرفی شاهکار قالی دستباف در بازار فرش ایران و جهان (دیجیتال مارکت)")
            addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
        }

        val chooserIntent = Intent.createChooser(shareIntent, "اشتراک‌گذاری استوری در اینستاگرام یا تلگرام").apply {
            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        }
        context.startActivity(chooserIntent)
    }
}
