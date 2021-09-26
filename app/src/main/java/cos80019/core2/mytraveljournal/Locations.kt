package cos80019.core2.mytraveljournal

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Locations(
    var image: Int,
    val titleTV: Int,
    var name: String,
    var cityAndState: String,
    var country: String,
    var lastVisitDate: String,
    var rating: Float
) : Parcelable {}
