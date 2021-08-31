package com.test.restaurantapp.helpers

import android.content.Context
import android.opengl.Visibility
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.test.restaurantapp.R
import com.test.restaurantapp.datamodel.ProductModel

class ProductListAdapter(
    private var context: Context,
    private val list: MutableList<ProductModel>,
) :
    RecyclerView.Adapter<ProductListAdapter.ViewHolder>() {


    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvProductName: TextView = itemView.findViewById(R.id.tvProductName)
        val tvProductDesc: TextView = itemView.findViewById(R.id.tvProductDesc)
        val tvProductOffer: TextView = itemView.findViewById(R.id.tvProductOffer)
        val ivProduct: ImageView = itemView.findViewById(R.id.ivProduct)
        val rbProduct: RatingBar = itemView.findViewById(R.id.rbProduct)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_products, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val productModel: ProductModel = list[position]
        holder.tvProductName.text = productModel.name
        holder.tvProductDesc.text = productModel.description
        holder.tvProductOffer.text = productModel.offer
        holder.ivProduct.setOnClickListener {
            holder.rbProduct.visibility=View.VISIBLE
        }
        Glide.with(context)
            .load(productModel.image_url)
//            .placeholder(shimmerDrawable)
//            .error(R.drawable.placeholder)
            .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
            .skipMemoryCache(false)
            .onlyRetrieveFromCache(false)
            .into(holder.ivProduct)
    }
}
