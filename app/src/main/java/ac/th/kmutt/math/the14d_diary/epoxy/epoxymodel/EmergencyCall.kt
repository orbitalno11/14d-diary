package ac.th.kmutt.math.the14d_diary.epoxy.epoxymodel

import ac.th.kmutt.math.the14d_diary.R
import ac.th.kmutt.math.the14d_diary.epoxy.KotlinEpoxyHolder
import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import com.airbnb.epoxy.*

@EpoxyModelClass(layout = R.layout.layout_emergency)
abstract class EmergencyCall : EpoxyModelWithHolder<EmergencyCall.Holder>() {

    @EpoxyAttribute var onClick: View.OnClickListener? = null

    override fun bind(holder: Holder) {
        with(holder){
            this.emergencyButton.setOnClickListener(onClick)
        }
    }

    inner class Holder : KotlinEpoxyHolder(){
        val emergencyButton by bind<Button>(R.id.emergency_call)
    }

}