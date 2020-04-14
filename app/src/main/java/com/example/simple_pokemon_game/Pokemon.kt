package com.example.simple_pokemon_game

class Pokemon{


    var name:String?=null
    var desc:String?=null
    var image:Int?=null
    var power:Double?=null
    var latitude:Double?=null
    var longitude:Double?=null
    var IsCatch:Boolean?=false

    constructor(image:Int, name:String, desc:String, power:Double, latitude:Double, longitude:Double) {
        this.name = name
        this.desc = desc
        this.image = image
        this.power = power
        this.latitude = latitude
        this.longitude = longitude
        this.IsCatch = false
    }

}