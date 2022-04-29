package com.example.uniquecars

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.PopupMenu
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class CarsRV(val context: Context, val cararraylist: ArrayList<Cars>) :
    RecyclerView.Adapter<CarsRV.RVHolder>() {


    class RVHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val carname: TextView = itemView.findViewById(R.id.car_name)
        val carmodel: TextView = itemView.findViewById(R.id.car_model)
        val caryear: TextView = itemView.findViewById(R.id.car_year)
        val carimage: ImageView = itemView.findViewById(R.id.car_image)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RVHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.cardview, parent, false)
        return RVHolder(view)
    }

    override fun onBindViewHolder(holder: RVHolder, position: Int) {
        holder.carname.text = cararraylist[position].car_name
        holder.carmodel.text = cararraylist[position].car_model
        holder.caryear.text = cararraylist[position].car_year
        val bitmap = cararraylist[position].image?.let { BitmapConverter.getImage(it) }
        holder.carimage.setImageBitmap(bitmap)
        holder.itemView.setOnClickListener {
            val intent = Intent(Intent(holder.itemView.context, SaveActivity::class.java))
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            intent.putExtra("key", 1)
            var singleton = CarsSingleton.setArraylist(cararraylist[position])
            context.startActivity(intent)
        }
       holder.itemView.setOnLongClickListener {
           menu(it,position)
       }


    }

    private fun menu(it: View,position: Int): Boolean {
        val popupMenu = PopupMenu(context, it)
        popupMenu.inflate(R.menu.delete)
        popupMenu.setOnMenuItemClickListener {
            when(it.itemId){
                R.id.action_delete -> delete(position)
                else -> {delete(position)}
            }
        }
        popupMenu.show()
        return true
    }

    private fun delete(position: Int): Boolean {
        val dao = CarsDAO(context)
        dao.deleteDB(cararraylist[position].id)
        cararraylist.removeAt(position)
        notifyItemRemoved(position)
        return true
    }


    override fun getItemCount(): Int {
        return cararraylist.size
    }
}




