package ac.th.kmutt.math.the14d_diary.ui.diary

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import ac.th.kmutt.math.the14d_diary.R
import ac.th.kmutt.math.the14d_diary.helper.AppbarHelper
import ac.th.kmutt.math.the14d_diary.model.DiaryModel
import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.provider.MediaStore
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.fragment_diary.*

class DiaryFragment : Fragment() {

    companion object {
        fun newInstance() = DiaryFragment()
    }

    private val viewmodel by viewModels<DiaryViewModel> ()
    private lateinit var appbarHelper: AppbarHelper
    private lateinit var diaryName: String
    private lateinit var diaryID: String
    private lateinit var diaryType: String
    private lateinit var diaryDetail: DiaryModel

    // camera part
    private val CAMERA_REQUEST = 101
    private lateinit var pictureForDiary: Bitmap

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_diary, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        this.diaryName = arguments?.getString("name")!!
        this.diaryID = arguments?.getString("id")!!
        this.diaryType = arguments?.getString("type")!!
        setupAppbar()

        viewmodel.getDiary(this.diaryID, this.diaryType).observe(viewLifecycleOwner, Observer {
            this.diaryDetail = it
            setupData(this.diaryDetail)
        })

        take_picture.setOnClickListener {
            val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            startActivityForResult(intent, CAMERA_REQUEST)
        }

        diary_submit.setOnClickListener {
            viewmodel.submitQuest(this.diaryDetail, this.pictureForDiary)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == CAMERA_REQUEST && resultCode == Activity.RESULT_OK) {
            data?.let {
                if (it.hasExtra("data")) {
                    pictureForDiary = data.getParcelableExtra("data") as Bitmap
                    diary_picture.setImageBitmap(pictureForDiary)
                }
            } ?: run {
                Toast.makeText(
                    activity?.applicationContext,
                    "Can not found picture.",
                    Toast.LENGTH_LONG
                ).show()
            }
        } else {
            Toast.makeText(
                activity?.applicationContext,
                "Can not found picture.",
                Toast.LENGTH_LONG
            ).show()
        }
    }

    private fun setupAppbar() {
        appbarHelper = AppbarHelper(view!!)
        appbarHelper.setMunuIcon(R.drawable.ic_chevron_left_white_24dp)
        appbarHelper.setTitle(this.diaryName)
        appbarHelper.setMenuClickListener(View.OnClickListener {
            activity?.onBackPressed()
        })
    }

    private fun setupData(data: DiaryModel){
        diary_name.text = data.diaryName

        if (data.diaryType == "quest"){
            diary_detail.isEnabled = false
            diary_detail.setText(data.diaryDetail)
        }

        if (data.imgUrl !== ""){
            Glide.with(this).load(data.imgUrl).centerInside().into(diary_picture)
        }

    }

}
