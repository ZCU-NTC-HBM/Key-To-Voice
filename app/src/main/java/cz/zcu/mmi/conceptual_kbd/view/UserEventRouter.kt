package cz.zcu.mmi.conceptual_kbd.view

import android.view.View
import cz.zcu.mmi.conceptual_kbd.R

class UserEventRouter(val source: MainActivity) : View.OnClickListener {
    private val rightColumnEventHandler = RightColumnEventHandler(source)
    private val topPanelEventHandler = TopPanelEventHandler(source)
    private val sentenceSelectionEventHandler = SentenceSelectionEventHandler(source)
    private val sentenceChildViewEventHandler = SentenceChildViewEventHandler(source)
    private val popupHelp by lazy { PopupHelp(source) }

    override fun onClick(view: View) {
        if (view.parent !is View) {
            return
        }

        if (source.state.isHelpModeActive && view.id != R.id.helpButton) {
            popupHelp.showHelp(view)
            return
        }

        val parent = view.parent as View

        when (parent.id) {
            R.id.rightColumnLayout -> rightColumnEventHandler.onClick(view)
            R.id.topPanelLayout -> topPanelEventHandler.onClick(view)
            R.id.itemsLayout -> sentenceSelectionEventHandler.onClick(view)
            R.id.sentenceView -> sentenceChildViewEventHandler.onClick(view)
            else -> throw UnsupportedOperationException("not implemented")
        }
    }
}
