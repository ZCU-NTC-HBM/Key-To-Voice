package cz.zcu.mmi.conceptual_kbd.view

import android.graphics.Color
import android.view.View
import cz.zcu.mmi.conceptual_kbd.R

interface IUserEventHandler {
    fun onClick(view: View)
}

class SentenceSelectionEventHandler(val source: MainActivity) : IUserEventHandler {
    override fun onClick(view: View) {
        val state = source.state
        when (view) {
            is CategoryView -> state.levelGoDown(view.category)
            is SentenceView -> state.updateSentence(view.sentence)
            else -> throw UnsupportedOperationException("not implemented")
        }
    }
}

const val DOWN = 1
const val UP = -1

class RightColumnEventHandler(val source: MainActivity) : IUserEventHandler {
    override fun onClick(view: View) {
        val state = source.state
        when (view.id) {
            R.id.levelUpButton -> state.levelGoUp()
            R.id.scrollUpButton -> source.scroll(UP)
            R.id.scrollDownButton -> source.scroll(DOWN)
        //R.id.speakButton -> source.playTTS()
            else -> throw UnsupportedOperationException("not implemented")
        }
    }
}

class TopPanelEventHandler(val source: MainActivity) : IUserEventHandler {
    override fun onClick(view: View) {
        val state = source.state
        when (view.id) {
            R.id.homeButton -> state.levelGoTop()
            R.id.settingsButton -> source.showMenu()
            R.id.helpButton -> {
                if (source.state.isHelpModeActive) {
                    source.state.isHelpModeActive = false
                    view.setBackgroundColor(Color.TRANSPARENT)
                } else {
                    source.state.isHelpModeActive = true
                    view.setBackgroundColor(Color.parseColor("#40b540"))
                }
            }
            R.id.topPanelText -> {
            }
            else -> throw UnsupportedOperationException("not implemented")
        }
    }
}


class SentenceChildViewEventHandler(val source: MainActivity) : IUserEventHandler {
    override fun onClick(view: View) {
        when (view.id) {
            R.id.sentenceCheckbox -> (view.parent as? SentenceView)?.toggleFavoriteStatus()
            R.id.playButton -> {
                val sentence = (view.parent as? SentenceView)?.sentence?.label ?: ""
                source.playTTS(sentence)
            }
            else -> throw UnsupportedOperationException("not implemented")
        }
    }
}
