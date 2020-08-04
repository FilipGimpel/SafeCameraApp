package com.gimpel.safecamera.ui.view

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.gimpel.safecamera.R
import com.gimpel.safecamera.storage.Photo
import com.squareup.picasso.Picasso
import java.io.File

class PhotoThumbnailAdapter(private var photos: List<Photo>,
                            private val picasso: Picasso,
                            private val clickListener: OnItemClickListener)
    : RecyclerView.Adapter<PhotoThumbnailAdapter.PhotoViewHolder>() {

    class PhotoViewHolder(private val cardView: CardView,
                          private val picasso: Picasso,
                          private val clickListener: OnItemClickListener)
        : RecyclerView.ViewHolder(cardView){

        private var textView: TextView? = null
        private var imageView: ImageView? = null

        init {
            imageView = cardView.findViewById(R.id.card_view_image)
            textView = cardView.findViewById(R.id.card_view_image_title)

        }

        fun bind(data: Photo) {
            textView?.text = data.fileName
            picasso
                .load(File(data.encryptedThumbnailPath))
                .placeholder(R.drawable.lock)
                /*.fit()*/
                .into(imageView);

            cardView.setOnClickListener {
                clickListener.onItemClick(data)
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup,
                                    viewType: Int): PhotoViewHolder {
        val cardView = LayoutInflater.from(parent.context)
            .inflate(R.layout.photo_thumbnail, parent, false) as CardView

        return PhotoViewHolder(
            cardView,
            picasso,
            clickListener
        )
    }

    override fun onBindViewHolder(holder: PhotoViewHolder, position: Int) {
        holder.bind(photos[position])
    }

    override fun getItemCount() = photos.size

    fun setPhotos(photos: List<Photo>) {
        this.photos = photos
        notifyDataSetChanged()
    }

    interface OnItemClickListener {
        fun onItemClick(photo: Photo)
    }
}
