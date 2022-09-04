package com.example.rxjavausingretrofit2withmvvm.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.rxjavausingretrofit2withmvvm.databinding.RecyclerListBinding
import com.example.rxjavausingretrofit2withmvvm.network.VolumeInfo

class BookListAdapter : RecyclerView.Adapter<BookListAdapter.MyViewHolder>() {

    var bookListData = ArrayList<VolumeInfo>()

    class MyViewHolder(private val binding: RecyclerListBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(data: VolumeInfo) = with(binding) {
            title.text = data.volumeInfo.title ?: "No title"
            description.text = data.volumeInfo.description ?: "No description"
            publisher.text = data.volumeInfo.publisher ?: "No publisher"

            data.volumeInfo.imageLinks?.let {
                setImageView(ImageView, it.smallThumbnail)
            }
        }

        private fun setImageView(imageView: ImageView, url: String) {
            Glide.with(imageView)
                .load(url)
                .circleCrop()
                .into(imageView)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding =
            RecyclerListBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(bookListData[position])
    }

    override fun getItemCount(): Int {
        return bookListData.size
    }
}