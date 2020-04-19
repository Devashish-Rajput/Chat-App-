package com.example.messengerapp.ModelClasses

class Users {
    private var uid:String=""
    private var username:String=""
    private var profile:String=""
    private var cover:String=""
    private var status:String=""
    private var search:String=""
    private var facebook:String=""
    private var instagram:String=""
    private var website:String=""

    constructor()


    constructor(
        uid: String,
        username: String,
        profile: String,
        cover: String,
        status: String,
        search: String,
        facebook: String,
        instagram: String,
        website: String
    ) {
        this.uid = uid
        this.username = username
        this.profile = profile
        this.cover = cover
        this.status = status
        this.search = search
        this.facebook = facebook
        this.instagram = instagram
        this.website = website
    }

    fun getUID():String?{
        return uid
    }
    fun setUID(uid: String){
        this.uid=uid
    }

    fun getUserName():String?{
        return username
    }
    fun setUserName(username: String){
        this.username=username
    }

    fun getProfile():String?{
        return profile
    }
    fun setProfile(profile: String){
        this.profile=profile
    }

    fun getCover():String?{
        return status
    }
    fun setCover(status: String){
        this.status=status
    }

    fun getSearch():String?{
        return search
    }
    fun setSearch(search: String){
        this.search=search
    }

    fun getInstagram():String?{
        return instagram
    }
    fun setInstagram(instagram: String){
        this.instagram=instagram
    }

    fun getWebsite():String?{
        return website
    }
    fun setWebsite(website: String){
        this.website=website
    }


}