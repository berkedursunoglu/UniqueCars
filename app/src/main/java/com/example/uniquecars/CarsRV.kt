package com.example.uniquecars

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class CarsRV(val context: Context,val cararraylist:ArrayList<Cars>) : RecyclerView.Adapter<CarsRV.RVHolder>() {

    class RVHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val carname:TextView = itemView.findViewById(R.id.car_name)
        val carmodel:TextView = itemView.findViewById(R.id.car_model)
        val caryear:TextView = itemView.findViewById(R.id.car_year)
        val carimage:ImageView = itemView.findViewById(R.id.car_image)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RVHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.cardview,parent,false)
        return RVHolder(view)
    }

    override fun onBindViewHolder(holder: RVHolder, position: Int) {
        holder.carname.text = cararraylist[position].car_name
        holder.carmodel.text = cararraylist[position].car_model
        holder.caryear.text = cararraylist[position].car_year
        val bitmap = BitmapConverter.getImage(cararraylist[position].image)
        holder.carimage.setImageBitmap(bitmap)
    }

    override fun getItemCount(): Int {
        return cararraylist.size
    }
}