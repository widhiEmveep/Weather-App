package com.example.weatherapp.presentation.fragment.customview

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.isVisible
import com.example.weatherapp.R

class Toolbar(context: Context, attrs: AttributeSet? = null) :
    ConstraintLayout(context, attrs) {

    private val leftIcon: ImageView
    private val titleTextView: TextView
    private val searchIcon: ImageView
    val searchEditText: EditText
    private var icon = 0

    init {
        LayoutInflater.from(context).inflate(R.layout.layout_toolbar, this, true)

        leftIcon = findViewById(R.id.ivLeft)
        titleTextView = findViewById(R.id.tvTitle)
        searchIcon = findViewById(R.id.ivSearch)
        searchEditText = findViewById(R.id.etSearch)

        context.theme.obtainStyledAttributes(
            attrs,
            R.styleable.Toolbar,
            0, 0
        ).apply {

            try {
                icon = getInt(R.styleable.Toolbar_left_action_icon, 0)
                setLeftIconImage(icon)

                setTitle(getString(R.styleable.Toolbar_title_text).orEmpty())
                setSearchBar(getBoolean(R.styleable.Toolbar_search, false))

                var status = true
                searchIcon.setOnClickListener {
                    status = if (status) {
                        setSearchBar(true)
                        false
                    } else {
                        setSearchBar(false)
                        true
                    }

                }

            } finally {
                recycle()
            }
        }
    }

    fun setLeftIconImage(
        requestedIconImage: Int
    ) {
        icon = requestedIconImage
        if (icon == 0) {
            //Favorite
            leftIcon.setImageResource(R.drawable.ic_baseline_favorite_24)
        } else {
            //Back
            leftIcon.setImageResource(R.drawable.ic_baseline_arrow_back_16)
        }

        invalidate()
        requestLayout()
    }

    fun setTitle(title: String) {
        titleTextView.text = title
        invalidate()
        requestLayout()
    }

    fun setSearchBar(status: Boolean) {
        if (status) {
            titleTextView.isVisible = false
            searchEditText.isVisible = true
        } else {
            titleTextView.isVisible = true
            searchEditText.isVisible = false
        }
    }
}