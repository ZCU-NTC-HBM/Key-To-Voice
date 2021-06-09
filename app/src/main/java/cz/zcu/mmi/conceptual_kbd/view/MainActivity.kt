package cz.zcu.mmi.conceptual_kbd.view

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.speech.tts.TextToSpeech
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.util.TypedValue
import android.widget.Toast
import cz.zcu.mmi.conceptual_kbd.AppState
import cz.zcu.mmi.conceptual_kbd.R
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), TextToSpeech.OnInitListener {
    private val TAG = "MainActivity"
    private var ttsEngine: TextToSpeech? = null
    private var ttsInitialized = false

    private val userEventRouter = UserEventRouter(this)
    lateinit var state: AppState
    private val menu: PopupMenu by lazy {
        val menu = PopupMenu(this)
        menu.setOnDismissListener { onMenuDismiss() }
        menu
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        state = AppState(this)

        bindButtonsClickHandler()

        val checkIntent = Intent()
        checkIntent.action = TextToSpeech.Engine.ACTION_CHECK_TTS_DATA
        startActivityForResult(checkIntent, 1234)
    }

    fun updateActivity() {
        //TODO add reason for update, sometimes is not needed to delete/create the items
        itemsLayout.removeAllViews()
        TreeItemConverter.convert(state, itemsLayout, userEventRouter).forEach { itemsLayout.addView(it) }
        topPanelText.text = state.title
        sentenceTextView.text = state.sentence
    }

    private fun bindButtonsClickHandler() {
        homeButton.setOnClickListener(userEventRouter)
        helpButton.setOnClickListener(userEventRouter)
        settingsButton.setOnClickListener(userEventRouter)

        levelUpButton.setOnClickListener(userEventRouter)
        scrollUpButton.setOnClickListener(userEventRouter)
        scrollDownButton.setOnClickListener(userEventRouter)
        topPanelText.setOnClickListener(userEventRouter)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent) {
        if (requestCode == 1234) {
            if (resultCode == TextToSpeech.Engine.CHECK_VOICE_DATA_PASS) {
                Log.i(TAG, "TTS engine voice data passed.")
            } else {
                // missing data, install it
                val installIntent = Intent()
                installIntent.action = TextToSpeech.Engine.ACTION_INSTALL_TTS_DATA
                startActivity(installIntent)
            }
        }
    }

    override fun onResume() {
        super.onResume()
        ttsEngine = TextToSpeech(this, this)
        Log.i(TAG, "TTS engine created")

        updateActivity()
    }

    override fun onPause() {
        super.onPause()
        state.saveData()
        ttsEngine?.shutdown()
    }

    override fun onInit(status: Int) {
        if (status == TextToSpeech.SUCCESS) {
            Log.i(TAG, "TTS Initialized")
            ttsInitialized = true
            Toast.makeText(this, "TTS initialized", Toast.LENGTH_SHORT).show()
        } else {
            Log.e(TAG, "There was an error in TTS init")
            Toast.makeText(this, "TTS initialization failed", Toast.LENGTH_SHORT).show()
        }
    }

    @SuppressWarnings("deprecation") fun playTTS() {
        val text = sentenceTextView.text as String
        if (text.isEmpty()) {
            return
        }
        playTTS(text)
    }

    fun playTTS(text: String) {
        Log.i(TAG, "Speaking $text")
        var result: Int? = TextToSpeech.SUCCESS
        if (text.isNotEmpty()) {
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
                result = ttsEngine?.speak(text, TextToSpeech.QUEUE_FLUSH, null, text)
            } else {
                result = ttsEngine?.speak(text, TextToSpeech.QUEUE_FLUSH, null)
            }
        }
        if (result == TextToSpeech.ERROR) {
            Toast.makeText(this, "Speak failed.", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
    }

    fun scroll(direction: Int) {
        val dir = if (direction < 0) -1 else 1
        val scrollAmount = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 96.toFloat(), applicationContext.resources.displayMetrics).toInt()
        scrollView.smoothScrollBy(0, dir * scrollAmount)
    }

    fun showMenu() {
        menu.showAsDropDown(settingsButton)
        settingsButton.setBackgroundColor(ContextCompat.getColor(this, R.color.colorPrimaryDark))
    }

    private fun onMenuDismiss() {
        settingsButton.setBackgroundColor(Color.TRANSPARENT)
    }
}
