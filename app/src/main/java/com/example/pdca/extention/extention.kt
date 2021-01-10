package com.example.pdca.extention

import android.content.Context
import android.widget.Toast

fun showToast(context: Context, textId: Int) {
    Toast.makeText(context, textId, Toast.LENGTH_SHORT).show()
}