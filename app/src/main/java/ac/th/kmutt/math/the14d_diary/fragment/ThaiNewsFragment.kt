package ac.th.kmutt.math.the14d_diary.fragment

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import ac.th.kmutt.math.the14d_diary.R

class ThaiNewsFragment : Fragment() {

    companion object {
        fun newInstance() = ThaiNewsFragment()
    }

    private lateinit var viewModel: ThaiNewsViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_news_thai, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(ThaiNewsViewModel::class.java)
        // TODO: Use the ViewModel
    }

}
