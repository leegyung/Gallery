package leegyung.gallery

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.recycle_layout.view.*
import kotlinx.android.synthetic.main.recycle_layout.view.image
import kotlinx.android.synthetic.main.recycle_layout2.view.*
import kotlinx.android.synthetic.main.recycle_layout2_2.view.*

class Adapter(private val context: Context): RecyclerView.Adapter<RecyclerView.ViewHolder>(){
    //여기에 리스트에 보여질 모든 자료들이
    //ProfileDate 형식의 data class 로 저장
    var datas = mutableListOf<ProfileData>()



    //여기서 맨 처음 화면에 보이는 전체 리스트 목록이 딱 10개라면, 위아래 버퍼를 생각해서 13~15개 정도의 뷰 객체가 생성된다.
    //처음만들어졌을때 보여질 10개의 목록의 layout 과 ViewHolder 클래스를 지정하고
    //getItemViewType 를 통해 받은 뷰타입에 따라 다른 레이아웃을 설정
    //onBindViewHolder 를 호출
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view : View?
        return when(viewType){
            1 ->{
                view = LayoutInflater.from(parent.context).inflate(R.layout.recycle_layout, parent,false)
                ViewHolder1(view!!)
            }
            2 -> {
                view = LayoutInflater.from(parent.context).inflate(R.layout.recycle_layout2_2, parent,false)
                ViewHolder2(view!!)
            }
            else -> {
                view = LayoutInflater.from(parent.context).inflate(R.layout.progress_layout, parent,false)
                ProgressHolder(view!!)
            }
        }
    }

    
    //스크롤을 해서 아래나 위에서 새롭게온 하나의 칸의 
    //홀더 와 순번(position) 이 파람으로 들어온다
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        //지금 position 순번에 해당되는 데이터를 불러오고
        //데이터가 원하는 타입의 레이아웃에 해당되는 뷰홀더랑 정보를 바인드 해서
        //글자나 사진을 UI에 표시
        when(datas[position].viewType){
            1 -> {
                (holder as ViewHolder1).bind(datas[position])
                holder.setIsRecyclable(false)
            }
            2 -> {
                (holder as ViewHolder2).bind(datas[position])
                holder.setIsRecyclable(false)
            }
            else -> {
                (holder as ProgressHolder).bind(datas[position])
                holder.setIsRecyclable(false)
            }
        }

    }

    override fun getItemCount(): Int = datas.size

    //onCreateViewHolder 실행전 이게 실행돼서 viewType 을 넘겨준다
    override fun getItemViewType(position: Int): Int {
        return datas[position].viewType
    }




    //recycle_layout 에 있는 위젯들에 어떤정보를 넣을껀지 bind 함수에서 설정
    inner class ViewHolder1(view : View) : RecyclerView.ViewHolder(view){
        fun bind(item : ProfileData){
            itemView.name_txt.text = item.name
            itemView.date_txt.text = item.date
            Glide.with(itemView).load(item.image).into(itemView.image)

            //특정 칸을 눌렀을때의 실행함수
            itemView.setOnClickListener {
                val intent = Intent(context, PictureDetailActivity::class.java)
                intent.putExtra("uri", item.image)
                startActivity(context, intent, null)
            }
        }
    }


    //recycle_layout2 에 있는 위젯들에 어떤정보를 넣을껀지 bind 함수에서 설정
    inner class ViewHolder2(view : View) : RecyclerView.ViewHolder(view){
        fun bind(item : ProfileData){
            Glide.with(itemView).load(item.image).into(itemView.image1_1)
            Glide.with(itemView).load(item.image2).into(itemView.image2_2)

            //특정 칸을 눌렀을때의 실행함수
            itemView.setOnClickListener {
                val ii = 0
            }
        }
    }

    inner class ProgressHolder(view : View) : RecyclerView.ViewHolder(view){
        fun bind(item : ProfileData){

        }
    }


}