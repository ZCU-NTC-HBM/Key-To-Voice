package cz.zcu.mmi.conceptual_kbd.model

import android.content.Context
import android.util.Log
import cz.zcu.mmi.conceptual_kbd.R
import org.yaml.snakeyaml.Yaml
import java.io.FileNotFoundException
import java.nio.ByteBuffer
import java.nio.ByteOrder

class WordTree(applicationContext: Context) {
    private val root: TreeRoot
    private var cursor: TreeItem
    private val filename: String = "favorites.bin"

    private val mainCategoryName = applicationContext.resources.getString(R.string.homeScreenCategoryName)

    init {
        val yaml = Yaml()
        val ins = applicationContext.resources.openRawResource(applicationContext.resources.getIdentifier("tree", "raw", applicationContext.packageName))
        val obj = yaml.load(ins) as Map<*, *>
        val favs = loadFavorites(applicationContext)
        val favoritesLabel = applicationContext.resources.getString(R.string.favsCategoryName)
        root = TreeRoot(obj["rootChildren"] as List<*>, favs, favoritesLabel)
        cursor = root
    }

    fun levelGoUp() {
        if (cursor != root) {
            cursor = (cursor as ChildItem).parent
        }
    }

    fun levelDown(category: ParentItem) {
        cursor = category
    }

    fun levelGoTop() {
        cursor = root
    }

    fun getItems(): List<TreeItem> {
        return (cursor as ParentItem).children
    }

    fun getCurrentCategoryName(): String {
        if (cursor is TreeRoot) {
            return mainCategoryName
        }

        return cursor.label
    }

    private fun loadFavorites(applicationContext: Context): List<Int> {
        try {
            applicationContext.openFileInput(filename).use { fin ->
                val byteArray = ByteArray(fin.available())
                // Read data into the array
                fin.read(byteArray)
                val intBuf = ByteBuffer.wrap(byteArray).order(ByteOrder.BIG_ENDIAN).asIntBuffer()
                val intArray = IntArray(intBuf.remaining())
                intBuf.get(intArray)
                return intArray.toList()
            }
        } catch (fnf: FileNotFoundException) {
            Log.e("WT", "Failed to load favorites: $fnf")
        }

        return emptyList()
    }

    fun storeFavorites(applicationContext: Context) {
        applicationContext.openFileOutput(filename, Context.MODE_PRIVATE).use { fos ->
            root.favorites.children.forEach { it -> fos.write(ByteBuffer.allocate(4).order(ByteOrder.BIG_ENDIAN).putInt(it.label.hashCode()).array()) }
        }
    }
}