package cz.zcu.mmi.conceptual_kbd.model

/*
class CustomStorage(val customCategory: TreeItem.Category) {
    private val filename: String = "custom.bin"
    private val customSenteces = mutableListOf<String>()

    fun loadCustom(applicationContext: Context) {
        customSenteces.clear()
        customCategory.children.clear()
        try {
            applicationContext.openFileInput(filename).use {
                InputStreamReader(it).useLines {
                    it.forEach {
                        customSenteces.add(it)
                        customCategory.children.add(TreeItem.Sentence(customCategory, it))
                    }
                }
            }
        } catch (fnf: FileNotFoundException) {
            Log.e("WT", "Failed to load favorites: $fnf")
        }
    }

    fun addCustom(sentence: String) {
        customSenteces.add(sentence)
        customCategory.children.add(TreeItem.Sentence(customCategory, sentence))
    }

    fun removeCustom(word: TreeItem.Sentence) {
        customSenteces.remove(word.label)
        customCategory.children.remove(word)
    }

    fun storeCustoms(applicationContext: Context) {
        applicationContext.openFileOutput(filename, Context.MODE_PRIVATE).bufferedWriter().use { fos ->
            customSenteces.forEach { fos.write(it); fos.newLine() }
        }
    }
}*/
