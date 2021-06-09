package cz.zcu.mmi.conceptual_kbd.model

import kotlin.properties.Delegates

interface TreeItem {
    val label: String
}

interface ChildItem : TreeItem {
    val parent: ParentItem
}

abstract class ParentItem : TreeItem {
    abstract val children: List<ChildItem>
    abstract val image: String

    protected fun parseList(parent: ParentItem, lst: List<*>): List<ChildItem> {
        val items: MutableList<ChildItem> = mutableListOf()
        for (itm in lst) {
            itm as Map<*, *>
            when (itm["type"]) {
                "cat" -> {
                    val cat = Category(parent, itm["image"] as String, itm["label"] as String, itm["children"] as List<*>)
                    items.add(cat)
                }

                "wrd" -> {
                    val wrd = Sentence(parent, itm["label"] as String)
                    items.add(wrd)
                }
            }
        }

        return items
    }
}

class Sentence(override val parent: ParentItem, override val label: String) : ChildItem {
    private val favorites: Favorites by lazy {
        var tmp = this.parent
        while (tmp !is TreeRoot) {
            tmp = (tmp as ChildItem).parent
        }
        tmp.favorites
    }

    var favorite: Boolean by Delegates.observable(false) { prop, old, new ->
        if (old == false && new == true) {
            favorites.addFavorite(this)
        }

        if (old == true && new == false) {
            favorites.removeFavorite(this)
        }
    }
}

class Category(override val parent: ParentItem, override val image: String, override val label: String, list: List<*>) : ParentItem(), ChildItem {
    override val children: List<ChildItem>

    init {
        this.children = parseList(this, list)
    }
}

class TreeRoot(list: List<*>, favs: List<Int>, favoritesLabel: String) : ParentItem() {
    override val image: String
        get() = ""
    override val label: String
        get() = "main"
    override val children: List<ChildItem>

    internal val favorites = Favorites(this, "star", favoritesLabel)

    init {
        val extraCategories = listOf(favorites)
        children = extraCategories + parseList(this, list)
        markFavorites(this, favs)
    }

    private fun markFavorites(item: TreeItem, favs: List<Int>) {
        when (item) {
            is Category -> item.children.forEach { markFavorites(it, favs) }
            is Sentence -> item.favorite = favs.contains(item.label.hashCode())
        }
    }
}

class Favorites(override val parent: TreeRoot, override val image: String, override val label: String) : ChildItem, ParentItem() {
    override var children: MutableList<ChildItem> = mutableListOf()

    fun addFavorite(sentence: Sentence) = children.add(sentence)

    fun removeFavorite(sentence: Sentence) = children.remove(sentence)
}
