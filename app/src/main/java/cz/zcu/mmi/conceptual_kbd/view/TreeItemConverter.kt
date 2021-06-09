package cz.zcu.mmi.conceptual_kbd.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import cz.zcu.mmi.conceptual_kbd.AppState
import cz.zcu.mmi.conceptual_kbd.R
import cz.zcu.mmi.conceptual_kbd.model.ParentItem
import cz.zcu.mmi.conceptual_kbd.model.Sentence

class TreeItemConverter {
    companion object {
        fun convert(state: AppState, parent: ViewGroup, userEventRouter: UserEventRouter): List<View> {
            val li = LayoutInflater.from(parent.context)

            return state.getItems().map {
                when (it) {
                    is ParentItem -> {
                        val widget = li.inflate(R.layout.category_layout, parent, false) as CategoryView
                        widget.setCategoryItem(it)
                        widget.showSubtitle(state.showCategoryTitles)
                        widget.setOnClickListener(userEventRouter)
                        widget.tag = parent.context.resources.getString(R.string.categoryButton_help) + " \"${it.label}\""
                        widget
                    }
                    is Sentence -> {
                        val widget = li.inflate(R.layout.sentence_layout, parent, false) as SentenceView
                        widget.setSentenceItem(it)
                        widget.setOnClickListener(userEventRouter)
                        widget.tag = parent.context.resources.getString(R.string.sentenceButton_help)
                        widget
                    }
                    else -> throw UnsupportedOperationException()
                }
            }
        }
    }
}