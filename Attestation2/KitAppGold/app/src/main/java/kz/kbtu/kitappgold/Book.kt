package kz.kbtu.kitappgold

/**
 * Created by abakh on 26-Mar-18.
 */
class Book(var title:String?, var subtitle : String?, var author : String?, var description : String?, var pagesCount : String?, var imageLink : String?) {

    override fun toString(): String {
        return "Book(title=$title, subtitle=$subtitle, author=$author, description=$description, pagesCount=$pagesCount, imageLink=$imageLink)"
    }
}
