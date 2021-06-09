package cz.zcu.mmi.conceptual_kbd.model

/*

class FavoriteStorage() {
    private val filename: String = "favorites.bin"

    private val favorites = mutableListOf<Int>()

    fun favoriteWord(word: TreeItem.Sentence) {
        favorites.add(word.label.hashCode())
    }

    fun unfavoriteWord(word: TreeItem.Sentence) {
        favorites.remove(word.label.hashCode())
    }

    fun loadFavorites(applicationContext: Context) {
        favorites.clear()
        try {
            applicationContext.openFileInput(filename).use { fin ->
                val byteArray = ByteArray(fin.available())
                // Read data into the array
                fin.read(byteArray)
                val intBuf = ByteBuffer.wrap(byteArray).order(ByteOrder.BIG_ENDIAN).asIntBuffer()
                val intArray = IntArray(intBuf.remaining())
                intBuf.get(intArray)
                intArray.forEach { it -> favorites.add(it) }
            }
        } catch (fnf: FileNotFoundException) {
            Log.e("WT", "Failed to load favorites: $fnf")
        }
    }

    fun storeFavorites(applicationContext: Context) {
        applicationContext.openFileOutput(filename, Context.MODE_PRIVATE).use { fos ->
            favorites.forEach { it -> fos.write(ByteBuffer.allocate(4).order(ByteOrder.BIG_ENDIAN).putInt(it).array()) }
        }
    }

    fun isFavorite(word: TreeItem.Sentence): Boolean {
        return favorites.contains(word.label.hashCode())
    }
}
*/
