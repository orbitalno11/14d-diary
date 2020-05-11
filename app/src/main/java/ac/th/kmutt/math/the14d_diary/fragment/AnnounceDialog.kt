package ac.th.kmutt.math.the14d_diary.fragment

import ac.th.kmutt.math.the14d_diary.R
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import kotlinx.android.synthetic.main.dialog_announce_fragment.*

class AnnounceDialog: DialogFragment() {

    private lateinit var title: String
    private lateinit var message: String
    private lateinit var buttonText: String
    private var buttonBackground: Int = R.drawable.btn_positive
    private lateinit var onClick: OnDialogListener

    companion object{
        private const val KEY_MESSAGE = "key_message"
        private const val KEY_TITLE = "key_title"
        private const val KEY_BUTTON = "key_button"
        private const val KEY_BACKGROUND = "key_background"
        private const val KEY_ONCLICK = "key_onclick"

        fun newInstance(
            title: String,
            message: String,
            but: String,
            bg: Int
        ): AnnounceDialog{
            val dialog = AnnounceDialog()
            val bundle = Bundle()
            bundle.putString(KEY_TITLE, title)
            bundle.putString(KEY_MESSAGE, message)
            bundle.putString(KEY_BUTTON, but)
            bundle.putInt(KEY_BACKGROUND, bg)
            dialog.arguments = bundle
            return dialog
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (savedInstanceState == null){
            restoreArguments(arguments!!)
        }else{
            restoreInstanceState(savedInstanceState)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.dialog_announce_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpView()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString(KEY_TITLE, title)
        outState.putString(KEY_MESSAGE, message)
        outState.putString(KEY_BUTTON, buttonText)
        outState.putInt(KEY_BACKGROUND, buttonBackground)
    }

    private fun restoreInstanceState(bundle: Bundle){
        title = bundle.getString(KEY_TITLE)!!
        message = bundle.getString(KEY_MESSAGE)!!
        buttonText = bundle.getString(KEY_BUTTON)!!
        buttonBackground = bundle.getInt(KEY_BACKGROUND)
    }

    private fun restoreArguments(bundle: Bundle){
        title = bundle.getString(KEY_TITLE)!!
        message = bundle.getString(KEY_MESSAGE)!!
        buttonText = bundle.getString(KEY_BUTTON)!!
        buttonBackground = bundle.getInt(KEY_BACKGROUND)
    }

    private fun setUpView(){
        announce_dialog_title.text = title
        announce_dialog_message.text = message
        announce_dialog_button.text = buttonText

        announce_dialog_button.background = context!!.getDrawable(buttonBackground!!)

        announce_dialog_button.setOnClickListener {
            this.onClick.onButtonClick()
            dismiss()
        }
    }

    private fun setOnDialogClick(onClick: OnDialogListener){
        this.onClick = onClick
    }

    interface OnDialogListener{
        fun onButtonClick()
    }

    class Builder{
        private lateinit var title: String
        private lateinit var message: String
        private lateinit var button: String
        private var buttonBackground: Int = R.drawable.btn_positive
        private lateinit var onClickListener: OnDialogListener

        fun setTitle(title: String): Builder{
            this.title = title
            return this
        }

        fun setMessage(message: String): Builder{
            this.message = message
            return this
        }

        fun setButton(pos: String): Builder{
            this.button = pos
            return this
        }

        fun setBackground(bg: Int): Builder{
            this.buttonBackground = bg
            return this
        }

        fun setOnclickListener(onClick: OnDialogListener): Builder{
            this.onClickListener = onClick
            return this
        }

        fun build(): AnnounceDialog{
            val dialog = newInstance(title, message, button, buttonBackground)
            dialog.setOnDialogClick(onClickListener)
            return dialog
        }
    }
}