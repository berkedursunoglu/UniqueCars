package com.example.uniquecars

object CarsSingleton {

    var arraylist: Cars? =null

    @JvmName("setArraylist1")
    fun setArraylist(arraylist: Cars){
        this.arraylist = arraylist
    }

    @JvmName("getArraylist1")
    fun getArraylist(): Cars? {
        return this.arraylist
    }
}