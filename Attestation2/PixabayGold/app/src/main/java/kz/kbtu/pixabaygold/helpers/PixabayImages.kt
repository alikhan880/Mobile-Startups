package kz.kbtu.pixabaysilver.helpers

import kz.kbtu.pixabaygold.helpers.Parent

open class PixabayImages(val tags : String, val webFormatURL : String, val favorites : Int, val likes : Int,
                    val comments : Int, val user : String) : Parent