package kz.kbtu.spydetectorgold

/**
 * Created by abakh on 15-Mar-18.
 */
data class Result(var name : String?, var correct : Int, var incorrect : Int) {
    constructor() : this("User", 0, 0)

}