package com.example.rxjavausingretrofit2withmvvm.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.rxjavausingretrofit2withmvvm.databinding.RecyclerListBinding
import com.example.rxjavausingretrofit2withmvvm.network.VolumeInfo

class BookListAdapter : RecyclerView.Adapter<BookListAdapter.MyViewHolder>() {

    var bookListData = ArrayList<VolumeInfo>()

    class MyViewHolder(private val binding :RecyclerListBinding) : RecyclerView.ViewHolder(binding.root){

        val title = binding.title
        val description = binding.description
        val publisher = binding.publisher
        val imageView = binding.ImageView


        fun bind(data : VolumeInfo){
            title.text = data.volumeInfo.title
            description.text = data.volumeInfo.description
            publisher.text = data.volumeInfo.publisher

            val url = data .volumeInfo?.imageLinks?.smallThumbnail

            Glide.with(imageView)
                .load(url)
                .circleCrop()
                .into(imageView)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding  = RecyclerListBinding.inflate(LayoutInflater.from(parent.context),parent,false)

        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
       holder.bind(bookListData[position])
    }

    override fun getItemCount(): Int {
       return  bookListData.size
    }
}