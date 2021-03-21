package dev.bhavindesai.dogstagram.ui.fragments.dogimages

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import com.stfalcon.imageviewer.StfalconImageViewer
import dev.bhavindesai.dogstagram.R
import dev.bhavindesai.dogstagram.ui.utils.screenRectPx

class DogImageGridAdapter(
    private val context: Context,
    spanCount: Int,
    private val images: List<String>,
): RecyclerView.Adapter<DogImageGridAdapter.ViewHolder>() {

    private val spacing = context.resources.getDimension(R.dimen.grid_dog_images_spacing).toInt()
    private lateinit var viewer: StfalconImageViewer<String>
    private val dimension = (screenRectPx.width()/spanCount) - spacing

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder = ViewHolder(
        LayoutInflater.from(parent.context).inflate(
            R.layout.grid_item_dog_image,
            parent,
            false
        ).apply {
            layoutParams.width = dimension
            layoutParams.height = dimension
        } as ImageView
    )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        Picasso.get()
            .load(images[position])
            .resize(dimension, dimension)
            .centerCrop()
            .placeholder(R.drawable.dog_placeholder)
            .into(holder.dogImage)

        holder.dogImage.setOnClickListener {
            openViewer(position, holder.dogImage, images)
        }
    }

    private fun openViewer(startPosition: Int, target: ImageView, images: List<String>) {
        viewer = StfalconImageViewer.Builder(context, images) { view, image ->
            Picasso.get().load(image).into(view)
        }
            .withStartPosition(startPosition)
            .withTransitionFrom(target)
            .withImageChangeListener {
                viewer.updateTransitionImage(target)
            }
            .show()
    }

    override fun getItemCount() = images.size

    class ViewHolder(val dogImage: ImageView): RecyclerView.ViewHolder(dogImage)
}