package dev.bhavindesai.dogstagram.ui.fragments.dogimages

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.facebook.drawee.view.SimpleDraweeView
import dev.bhavindesai.dogstagram.R

class DogImageGridAdapter(
    private val images: List<String>
): RecyclerView.Adapter<DogImageGridAdapter.ViewHolder>() {

    class ViewHolder(val dogImage: SimpleDraweeView): RecyclerView.ViewHolder(dogImage)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder = ViewHolder(
        LayoutInflater.from(parent.context).inflate(
            R.layout.list_item_dog_image,
            parent,
            false
        ) as SimpleDraweeView
    )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.dogImage.setImageURI(images[position])
    }

    override fun getItemCount() = images.size

}