package com.ibrahim.a2zshop.adapters

import android.annotation.SuppressLint
import android.view.View
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import com.ibrahim.a2zshop.R
import com.ibrahim.a2zshop.lang
import com.ibrahim.a2zshop.nf
import com.squareup.picasso.Picasso

@BindingAdapter("bindImage")
fun bindImageView(imageView: ImageView, url: String) {
    Picasso.get().load(url).into(imageView)
}

@BindingAdapter("bindMinusButton")
fun bindMinusButton(imageButton: ImageButton, number: Int) {
    if (number > 1) {
        imageButton.isEnabled = true
        imageButton.alpha = 1f
    } else {
        imageButton.isEnabled = false
        imageButton.alpha = 0.25f
    }
}

@BindingAdapter("bindPlusButton")
fun bindPlusButton(imageButton: ImageButton, number: Int) {
    if (number == 9) {
        imageButton.isEnabled = false
        imageButton.alpha = 0.25f
    } else {
        imageButton.isEnabled = true
        imageButton.alpha = 1f
    }
}

@BindingAdapter("bindEditQty")
fun bindEditQty(imageButton: ImageButton, difference: Boolean) {
    if (difference) {
        imageButton.isEnabled = true
        imageButton.alpha = 1f
    } else {
        imageButton.isEnabled = false
        imageButton.alpha = 0.25f
    }
}

@BindingAdapter("bindBtn")
fun bindButton(button: Button, difference: Boolean) {
    if (difference) {
        button.isEnabled = true
        button.alpha = 1f
    } else {
        button.isEnabled = false
        button.alpha = 0.25f
    }
}

@SuppressLint("SetTextI18n")
@BindingAdapter(value = ["bind:oldPrice", "bind:discount"], requireAll = true)
fun bindTextOldPrice(textView: TextView, oldPrice: Double, discount: Double) {
    if (discount > 0) {
        if (lang == "en") {
            textView.text = "${oldPrice.toInt()} EGP"
        } else {
            val oldPrice: String = nf.format(oldPrice.toInt())
            textView.text = "$oldPrice جنيه "
        }
    } else
        textView.visibility = View.INVISIBLE
}

@SuppressLint("SetTextI18n")
@BindingAdapter("bindOff")
fun bindTextOff(textView: TextView, discount: Double) {
    if (discount > 0) {
        if (lang == "en")
            textView.text = "${discount.toInt()}% OFF"
        else {
            val discount: String = nf.format(discount)
            textView.text = "${discount.toInt()}% خصم"
        }
    } else
        textView.visibility = View.INVISIBLE
}

@SuppressLint("SetTextI18n")
@BindingAdapter("bindTextPrice")
fun bindTextPrice(textView: TextView, price: Double) {
    if (lang == "en")
        textView.text = "${price.toInt()} EGP"
    else {
        val price: String = nf.format(price.toInt())
        textView.text = "$price جنيه "
    }
}

@BindingAdapter("bindButtonFavorite")
fun bindButtonFavorite(imageButton: ImageButton, isFavorite: Boolean) {
    if (isFavorite)
        imageButton.setImageDrawable(
            ContextCompat.getDrawable(
                imageButton.context,
                R.drawable.ic_favorite
            )
        )
    else
        imageButton.setImageDrawable(
            ContextCompat.getDrawable(
                imageButton.context,
                R.drawable.ic_favorite_border
            )
        )

}

@BindingAdapter("bindButtonAddCart")
fun bindButtonAddCart(button: Button, inCart: Boolean) {
    if (inCart)
        button.text = button.context.getString(R.string.remove_from_cart)
    else
        button.text = button.context.getString(R.string.add_to_cart)
}



