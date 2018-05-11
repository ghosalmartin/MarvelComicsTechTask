package com.example.mgh01.techtask.extensions

import android.widget.ImageView
import com.bumptech.glide.Glide

fun ImageView.load(path: String?) {
    path?.let {
        Glide.with(this.context).load(path).into(this)
    }
}