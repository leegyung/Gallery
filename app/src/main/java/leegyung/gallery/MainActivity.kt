package leegyung.gallery

import android.database.Cursor
import android.media.ExifInterface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.provider.MediaStore
import androidx.core.app.ActivityCompat
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_main.*
import java.io.IOException

class MainActivity : AppCompatActivity() {
    lateinit var adapter: Adapter
    val data = mutableListOf<ProfileData>()
    val uriList = mutableListOf<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE),99)


        initRecycler()
        initScrollListener()
    }

    private fun initPictures(){
        val cursor : Cursor? = contentResolver.query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,null,null,null)
        if(cursor != null){
            while(cursor.moveToNext()){
                val uri = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA))
                uriList.add(uri)
            }
            cursor.close()
        }
    }


    private fun initRecycler(){

        initPictures()


        adapter = Adapter(this)
        recycle_view.adapter = adapter
        val range = (1..uriList.size)


        //리스트에 나오는 칸을 꾸미는 ItemDecorator 클래스를 만들어서
        //recycle_view 에 다가 적용시키는 것
        recycle_view.addItemDecoration(ItemDecorator(10))

        addData()
    }

    private fun initScrollListener(){
        recycle_view.addOnScrollListener(object:RecyclerView.OnScrollListener(){
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                if(!recycle_view.canScrollVertically(1)){

                    Handler().postDelayed({
                        addData()
                    }, 300)


                }

            }
        })
    }

    private fun addData(){
        val range = (1..uriList.size)
        if(data.size > 5){ data.removeAt(data.size - 1) }



        for (i : Int in 0..13){
            val rand = range.random()
            val exif = getExif(uriList[rand])
            if(i%2 == 0){
                data.add(ProfileData(image = uriList[rand], image2 = null, name = "날짜: " + exif?.getAttribute(ExifInterface.TAG_DATETIME),
                    date = "크기: " + exif?.getAttribute(ExifInterface.TAG_IMAGE_LENGTH) +
                            " * " + exif?.getAttribute(ExifInterface.TAG_IMAGE_WIDTH),
                    viewType = 1))
            }
            else{
                data.add(ProfileData(image = uriList[rand], image2 = uriList[range.random()], name = null, date = null, viewType = 2))
            }

        }

        data.add(ProfileData(image = null, image2 = null, name = null, date = null, viewType = 3))
        adapter.datas = data
        adapter.notifyDataSetChanged()


    }


    private fun getExif(file : String) : ExifInterface?{
        val exif : ExifInterface?
        try{
            exif = ExifInterface(file)
        }catch(e : IOException){
            return null
        }
        return exif
    }

}