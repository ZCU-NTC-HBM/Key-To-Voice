package cz.zcu.mmi.conceptual_kbd.view

import android.content.Context
import android.support.graphics.drawable.VectorDrawableCompat
import android.util.AttributeSet
import android.util.Log
import android.view.View
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import cz.zcu.mmi.conceptual_kbd.R
import cz.zcu.mmi.conceptual_kbd.model.ParentItem
import cz.zcu.mmi.conceptual_kbd.model.Sentence

class CategoryView : LinearLayout {
    constructor(context: Context) : super(context) {
        initView()
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        initView()
    }

    constructor(context: Context, attrs: AttributeSet, styleId: Int) : super(context, attrs, styleId) {
        initView()
    }

    fun initView() {
        inflate(context, R.layout.category_view, this)
    }

    lateinit var category: ParentItem

    fun setCategoryItem(category: ParentItem) {
        this.category = category
        val label = findViewById(R.id.categoryLabel) as TextView
        val icon = findViewById(R.id.categoryIcon) as ImageView
        label.text = category.label
        val resId = context.resources.getIdentifier(category.image, "drawable", context.packageName)
        if (resId != 0) {
            val con = VectorDrawableCompat.create(context.resources, resId, context.theme)
            icon.setImageDrawable(con)
        } else {
            Log.e("CBW", "Could not load drawable ${category.image}")
        }
    }

    fun showSubtitle(show: Boolean) {
        val label = findViewById(R.id.categoryLabel) as TextView
        label.visibility = if (show) View.VISIBLE else View.GONE
    }
}

class SentenceView : LinearLayout {
    constructor(context: Context) : super(context) {
        initView()
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        initView()
    }

    constructor(context: Context, attrs: AttributeSet, styleId: Int) : super(context, attrs, styleId) {
        initView()
    }

    lateinit var favoriteButton: ImageButton
    lateinit var playButton: ImageButton
    fun initView() {
        inflate(context, R.layout.sentence_view, this)
        favoriteButton = findViewById(R.id.sentenceCheckbox) as ImageButton
        playButton = findViewById(R.id.playButton) as ImageButton
    }

    lateinit var sentence: Sentence

    override fun setOnClickListener(l: OnClickListener) {
        super.setOnClickListener(l)
        favoriteButton.setOnClickListener(l)
        playButton.setOnClickListener(l)
    }

    fun setSentenceItem(sentence: Sentence) {
        this.sentence = sentence
        val label = findViewById(R.id.sentenceLabel) as TextView
        label.text = sentence.label
        updateButtonImage()
    }

    fun toggleFavoriteStatus() {
        sentence.favorite = !sentence.favorite
        updateButtonImage()
    }

    private fun updateButtonImage() {
        if (sentence.favorite) {
            favoriteButton.setImageResource(R.drawable.star_filled)
        } else {
            favoriteButton.setImageResource(R.drawable.star)
        }
    }
}