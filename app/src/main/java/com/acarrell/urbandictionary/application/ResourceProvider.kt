package com.acarrell.urbandictionary.application

import android.content.Context
import android.graphics.drawable.Drawable
import androidx.annotation.*
import androidx.core.content.ContextCompat

class ResourceProvider private constructor(private val context: Context) {
    fun getString(@StringRes resId: Int, vararg args: Any): String {
        return if (args.isNotEmpty()) {
            context.resources.getString(resId, *args)
        } else {
            context.resources.getString(resId)
        }
    }

    fun getStringArray(@ArrayRes resId: Int): Array<String> =
        context.resources.getStringArray(resId)

    fun getInteger(@IntegerRes resId: Int): Int =
        context.resources.getInteger(resId)

    fun getIntegerArray(@ArrayRes resId: Int): Array<Int> =
        context.resources.getIntArray(resId).toTypedArray()

    fun getBoolean(@BoolRes resId: Int): Boolean =
        context.resources.getBoolean(resId)

    fun getColor(@ColorRes resId: Int): Int =
        ContextCompat.getColor(context, resId)

    fun getDrawable(@DrawableRes resId: Int): Drawable? =
        ContextCompat.getDrawable(context, resId)

    companion object {
        @JvmStatic
        fun from(context: Context): ResourceProvider =
            ResourceProvider(context)
    }
}