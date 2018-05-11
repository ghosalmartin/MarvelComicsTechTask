package com.example.mgh01.techtask.utils

import android.annotation.TargetApi
import android.content.Context
import android.graphics.Rect
import android.os.Build
import android.util.AttributeSet
import android.view.View
import android.widget.AutoCompleteTextView

class InstantAutoCompleteTextView : AutoCompleteTextView {

    private var showAlways: Boolean = false

    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)
    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int, defStyleRes: Int) : super(context, attrs, defStyleAttr, defStyleRes)

    fun setShowAlways(showAlways: Boolean) {
        this.showAlways = showAlways
    }

    override fun enoughToFilter(): Boolean {
        return showAlways || super.enoughToFilter()
    }

    override fun onFocusChanged(focused: Boolean, direction: Int, previouslyFocusedRect: Rect?) {
        super.onFocusChanged(focused, direction, previouslyFocusedRect)
        showDropDownIfFocused()
    }

    private fun showDropDownIfFocused() {
        if (enoughToFilter() && isFocused && windowVisibility == View.VISIBLE) {
            showDropDown()
        }
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        showDropDownIfFocused()
    }

}