package cz.zcu.mmi.conceptual_kbd.view

import android.content.Context
import android.content.DialogInterface
import android.support.v7.app.AlertDialog
import android.view.View
import android.widget.TextView
import cz.zcu.mmi.conceptual_kbd.R

class PopupHelp(val context: Context) {

    fun showHelp(view: View) {
        val builder = AlertDialog.Builder(context)
        builder.setMessage("")
        builder.setPositiveButton(R.string.closeDialog, null)
        val dialog = builder.create()
        dialog.show()

        val textSize = context.resources.getDimension(R.dimen.aboutTextSize)
        dialog.getButton(DialogInterface.BUTTON_POSITIVE)?.textSize = textSize
        val textView = dialog.findViewById(android.R.id.message) as TextView
        textView.text = view.tag?.toString() ?: ""
        textView.textSize = textSize
    }
}

