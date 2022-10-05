package leegyung.gallery

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowManager
import kotlinx.android.synthetic.main.picture_detail_activity.*

class PictureDetailActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.picture_detail_activity)

        window.addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN)

        
        motion.transitionToEnd()

    }
}