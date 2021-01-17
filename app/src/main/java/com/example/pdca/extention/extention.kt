package com.example.pdca.extention

import android.content.Context
import android.widget.Toast
import android.view.View

fun showToast(context: Context, textId: Int) {
    Toast.makeText(context, textId, Toast.LENGTH_SHORT).show()
}

fun View.toggle(isVisible: Boolean) {
    visibility = if (isVisible) View.VISIBLE else View.GONE
}

fun View.visible(isVisible: Boolean) {
    visibility = if (isVisible) View.VISIBLE else View.INVISIBLE
}