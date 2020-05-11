package ac.th.kmutt.math.the14d_diary.fragment

import ac.th.kmutt.math.the14d_diary.R
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import kotlinx.android.synthetic.main.dialog_loading_fragment.*

class LoadingDialog: DialogFragment() {

    private lateinit var title: String
    private lateinit var message: String

    companion object{
        private const val KEY_MESSAGE = "key_message"
        private const val KEY_TITLE = "key_title"

        fun newInstance(
            title: String,
            message: String
        ): LoadingDialog{
            val dialog = LoadingDialog()
            val bundle = Bundle()
            bundle.putString(KEY_TITLE, title)
            bundle.putString(KEY_MESSAGE, message)
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
        return inflater.inflate(R.layout.dialog_loading_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpView()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString(KEY_TITLE, title)
        outState.putString(KEY_MESSAGE, message)
    }

    private fun restoreInstanceState(bundle: Bundle){
        title = bundle.getString(KEY_TITLE)!!
        message = bundle.getString(KEY_MESSAGE)!!
    }

    private fun restoreArguments(bundle: Bundle){
        title = bundle.getString(KEY_TITLE)!!
        message = bundle.getString(KEY_MESSAGE)!!
    }

    private fun setUpView(){
        loading_title.text = title
        loading_message.text = message
    }

    class Builder{
        private lateinit var title: String
        private lateinit var message: String

        fun setTitle(title: String): Builder{
            this.title = title
            return this
        }

        fun setMessage(message: String): Builder{
            this.message = message
            return this
        }

        fun build(): LoadingDialog{
            return newInstance(title, message)
        }
    }
}