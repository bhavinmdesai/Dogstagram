package dev.bhavindesai.dogstagram.ui.fragments.dogimages

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.facebook.drawee.view.SimpleDraweeView
import dev.bhavindesai.dogstagram.R
import dev.bhavindesai.dogstagram.ui.utils.screenRectPx

class DogImageGridAdapter(
    context: Context,
    private val spanCount: Int,
    private val images: List<String>
): RecyclerView.Adapter<DogImageGridAdapter.ViewHolder>() {

    private val spacing = context.resources.getDimension(R.dimen.grid_dog_images_spacing).toInt()

    class ViewHolder(val dogImage: SimpleDraweeView): RecyclerView.ViewHolder(dogImage)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder = ViewHolder(
        LayoutInflater.from(parent.context).inflate(
            R.layout.grid_item_dog_image,
            parent,
            false
        ).apply {
            layoutParams.width = (screenRectPx.width()/spanCount) - spacing
            layoutParams.height = (screenRectPx.width()/spanCount) - spacing
        } as SimpleDraweeView
    )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.dogImage.setImageURI(images[position])
    }

    override fun getItemCount() = images.size

}