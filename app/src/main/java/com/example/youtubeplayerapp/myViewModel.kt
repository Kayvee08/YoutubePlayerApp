package com.example.youtubeplayerapp

import android.net.Uri
import androidx.lifecycle.ViewModel

class myViewModel:ViewModel() {
    private lateinit var name:String
    private lateinit var email:String
    private lateinit var uri: Uri
    private var img:Boolean=false
    fun imageClicked(b:Boolean){
        img=b
    }
    fun checkimgClicked():Boolean{
        return img
    }
    fun saveName(n:String){
        name=n
    }
    fun getName():String{
        return name
    }
    fun saveEmail(e:String){
        email=e
    }
    fun getEmail():String{
        return email
    }
    fun setUri(inUri:Uri){
        uri=inUri
    }
    fun getUri():Uri{
        return uri
    }
}