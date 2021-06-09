package cz.zcu.mmi.conceptual_kbd

import cz.zcu.mmi.conceptual_kbd.model.ParentItem
import cz.zcu.mmi.conceptual_kbd.model.Sentence
import cz.zcu.mmi.conceptual_kbd.model.TreeItem
import cz.zcu.mmi.conceptual_kbd.model.WordTree
import cz.zcu.mmi.conceptual_kbd.view.MainActivity
import kotlin.properties.Delegates

class AppState(val mainActivity: MainActivity) {
    private val dataStorage = WordTree(mainActivity)

    fun getItems(): List<TreeItem> {
        return dataStorage.getItems()
    }

    var title: String = ""
        get() = dataStorage.getCurrentCategoryName()
        private set

    fun levelGoDown(category: ParentItem) {
        dataStorage.levelDown(category)
        mainActivity.updateActivity()
    }

    var sentence: String = ""

    fun updateSentence(sentence: Sentence) {
        this.sentence = sentence.label
        mainActivity.updateActivity()
    }

    fun levelGoUp() {
        dataStorage.levelGoUp()
        mainActivity.updateActivity()
    }

    fun levelGoTop() {
        dataStorage.levelGoTop()
        mainActivity.updateActivity()
    }

    fun clearSentence() {
        this.sentence = ""
        mainActivity.updateActivity()
    }

    fun saveData() {
        dataStorage.storeFavorites(mainActivity)
    }

    var isHelpModeActive: Boolean = false
    var showCategoryTitles: Boolean by Delegates.observable(false) { prop, old, new -> mainActivity.updateActivity() }
}
