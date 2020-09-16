package com.danhdueexoictif.statemachine.utils

import android.content.res.AssetManager
import android.content.res.Resources
import android.graphics.drawable.Drawable
import android.util.DisplayMetrics
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.danhdueexoictif.statemachine.BuildConfig
import com.danhdueexoictif.statemachine.R
import com.squareup.moshi.Moshi
import java.io.IOException

fun Exception.safeLog() {
    if (BuildConfig.DEBUG) printStackTrace()
}

fun RecyclerView.setUpRecyclerView() {
    this.setUpRecyclerView(true)
}

fun RecyclerView.setUpRecyclerView(
    isDecoration: Boolean,
    orientation: Int = RecyclerView.VERTICAL
) {
    this.apply {
        layoutManager = LinearLayoutManager(this.context, orientation, false).apply {
            isSmoothScrollbarEnabled = true
            isAutoMeasureEnabled = true
        }
        setHasFixedSize(true)
        isNestedScrollingEnabled = false
        if (isDecoration) {
            val divider =
                DividerItemDecoration(
                    this.context,
                    (layoutManager as LinearLayoutManager).orientation
                ).apply {
                    context.getDrawable(R.drawable.divider)?.let { setDrawable(it) }
                }
            this.addItemDecoration(divider)
        }
    }
}

fun RecyclerView.setUpRecyclerView(decoration: Drawable?) {
    this.apply {
        layoutManager = LinearLayoutManager(this.context, RecyclerView.VERTICAL, false).apply {
            isSmoothScrollbarEnabled = true
            isAutoMeasureEnabled = true
        }
        setHasFixedSize(true)
        isNestedScrollingEnabled = false
        decoration?.also {
            addItemDecoration(
                DividerItemDecoration(
                    context,
                    (layoutManager as LinearLayoutManager).orientation
                ).apply {
                    setDrawable(decoration)
                })
        }
    }
}

/**
 * get json object from file.
 * @param assets: app asset manager.
 * @param fileName: file name in the asset folder.
 */
inline fun <reified T> getObjectFromJsonFile(assets: AssetManager, fileName: String): T? {
    var json: String? = null
    try {
        val inputStream = assets.open(fileName)
        val size = inputStream.available()
        val buffer = ByteArray(size)
        inputStream.read(buffer)
        inputStream.close()
        json = String(buffer, Charsets.UTF_8)
    } catch (e: IOException) {
        e.printStackTrace()
    }
    return json?.let { Moshi.Builder().build().adapter(T::class.java).fromJson(it) }
}

/**
 * get json string from file
 * @param assets: app asset manager
 * @param fileName: file name in the asset folder.
 */
fun getJsonStringFromFile(assets: AssetManager, fileName: String): String {
    var json = ""
    try {
        val inputStream = assets.open(fileName)
        val size = inputStream.available()
        val buffer = ByteArray(size)
        inputStream.read(buffer)
        inputStream.close()
        json = String(buffer, Charsets.UTF_8)
    } catch (e: IOException) {
        e.printStackTrace()
    }
    return json
}

/**
 * deep copy for any object.
 */
inline fun <reified T> T.deepCopy(): T? {
    return Moshi.Builder().build().adapter(T::class.java)
        .fromJson(Moshi.Builder().build().adapter(T::class.java).toJson(this))
}

fun dpToPx(dp: Int): Int {
    val metrics = Resources.getSystem().displayMetrics
    return dp * (metrics.densityDpi / DisplayMetrics.DENSITY_DEFAULT)
}

/**
 * remove a range items in the MutableList.
 */
inline fun <reified T> MutableList<T>.removeRange(range: IntRange) {
    val fromIndex = range.first
    val toIndex = range.last
    if (fromIndex == toIndex) {
        return
    }

    if (fromIndex >= size) {
        throw IndexOutOfBoundsException("fromIndex $fromIndex >= size $size")
    }
    if (toIndex > size) {
        throw IndexOutOfBoundsException("toIndex $toIndex > size $size")
    }
    if (fromIndex > toIndex) {
        throw IndexOutOfBoundsException("fromIndex $fromIndex > toIndex $toIndex")
    }

    val filtered = filterIndexed { i, _ -> i < fromIndex || i > toIndex }
    clear()
    addAll(filtered)
}

/**
 * convert Boolean to Int. This one supports for data binding.
 */
fun Boolean.toInt() = if (this) 1 else 0

/**
 * convert Long to Boolean. This one supports for data binding.
 */
fun Long.toBoolean(): Boolean = this > 0
