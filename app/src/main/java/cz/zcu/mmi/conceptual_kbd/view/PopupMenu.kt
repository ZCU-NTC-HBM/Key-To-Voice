package cz.zcu.mmi.conceptual_kbd.view

import android.content.Context
import android.content.DialogInterface
import android.graphics.Rect
import android.graphics.drawable.ColorDrawable
import android.support.v7.app.AlertDialog
import android.text.Html
import android.text.method.LinkMovementMethod
import android.text.util.Linkify
import android.view.LayoutInflater
import android.view.View
import android.widget.CheckedTextView
import android.widget.PopupWindow
import android.widget.RelativeLayout
import android.widget.TextView
import cz.zcu.mmi.conceptual_kbd.R

class PopupMenu(val context: MainActivity) : PopupWindow(PopupMenu.createMenuView(context),
        RelativeLayout.LayoutParams.WRAP_CONTENT,
        RelativeLayout.LayoutParams.WRAP_CONTENT) {
    private companion object Factory {
        fun createMenuView(context: Context): View {
            val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            return inflater.inflate(R.layout.popup_menu_layout, null)
        }
    }

    lateinit var anchorRect: Rect

    init {
        setBackgroundDrawable(ColorDrawable())
        isFocusable = true
        isOutsideTouchable = true

        contentView.findViewById(R.id.aboutOption).setOnClickListener { dismiss();showAbout() }
        val ctv = contentView.findViewById(R.id.toggleCategoryTitle) as CheckedTextView
        ctv.setOnClickListener {
            if (ctv.isChecked) {
                ctv.isChecked = false
            } else {
                ctv.isChecked = true
            }
            context.state.showCategoryTitles = ctv.isChecked
        }

        setTouchInterceptor(fun(view, motionEvent): Boolean {
            val rect = Rect()
            view.getHitRect(rect)
            if (rect.contains(motionEvent.x.toInt(), motionEvent.y.toInt()) ||
                    anchorRect.contains(motionEvent.rawX.toInt(), motionEvent.rawY.toInt())) {
                return false
            }
            return true
        })
    }

    override fun showAsDropDown(anchor: View) {
        super.showAsDropDown(anchor)
        val rect = Rect()
        anchor.getGlobalVisibleRect(rect)
        anchorRect = rect
    }

    private fun showAbout() {
        val builder = AlertDialog.Builder(context)
        builder.setMessage("")
        builder.setPositiveButton(R.string.closeDialog, null)
        val dialog = builder.create()
        dialog.show()

        val textSize = context.resources.getDimension(R.dimen.aboutTextSize)
        dialog.getButton(DialogInterface.BUTTON_POSITIVE)?.textSize = textSize
        dialog.getButton(DialogInterface.BUTTON_NEGATIVE)?.textSize = textSize
        val textView = dialog.findViewById(android.R.id.message) as TextView
        val txt = context.resources.getString(R.string.about)
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            textView.text = Html.fromHtml(txt, Html.FROM_HTML_MODE_LEGACY)
        } else {
            textView.text = Html.fromHtml(txt)
        }
        textView.textSize = textSize
        textView.autoLinkMask = Linkify.WEB_URLS
        textView.movementMethod = LinkMovementMethod.getInstance()
        textView.isClickable = true
        textView.linksClickable = true
    }
}

