package com.udacity.asteroidradar.main

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.squareup.picasso.Picasso
import com.udacity.asteroidradar.PictureOfDay

@BindingAdapter("image")
fun loadImage(view: ImageView, data: PictureOfDay?) {
    Picasso.get().load(data?.url).into(view)
}