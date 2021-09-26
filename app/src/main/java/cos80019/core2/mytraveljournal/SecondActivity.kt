package cos80019.core2.mytraveljournal


import android.app.Activity
import android.app.DatePickerDialog
import android.os.Bundle
import android.os.Parcelable
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import java.time.format.DateTimeFormatter
import java.util.*


class SecondActivity : AppCompatActivity() {

    lateinit var updatedLoc: Locations
    var locNameText = ""
    var locCityAndStateText = ""
    var locCountryText = ""
    var locVisitedDateText = ""
    var locRatingFloat: Float = 0.0f


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second)

        val locName = findViewById<EditText>(R.id.location_ET)
        val locCityAndState = findViewById<EditText>(R.id.cityAndState_ET)
        val locCountry = findViewById<EditText>(R.id.country_ET)
        val locDateVisited = findViewById<EditText>(R.id.visitDate_ET)

        val result = intent.getParcelableExtra<Locations>("result")
        if (result != null) {
            updatedLoc = result
            locNameText = updatedLoc.name
            locCityAndStateText = updatedLoc.cityAndState
            locCountryText = updatedLoc.country
            locVisitedDateText = updatedLoc.lastVisitDate
            locRatingFloat = updatedLoc.rating
        }

        val locImage = findViewById<ImageView>(R.id.location_imageV)
        val locRating = findViewById<RatingBar>(R.id.ratingBar)
        val locRatingTV = findViewById<TextView>(R.id.rating_textV)
        val resDrawable = updatedLoc.image


        //populate EditTexts with texts from currLocation
        locImage.setImageDrawable(resDrawable?.let { getDrawable(it) })

        //populate EditTexts with texts from currLocation
        locName.setText(locNameText)
        locCityAndState.setText(locCityAndStateText)
        locCountry.setText(locCountryText)
        locDateVisited.setText(locVisitedDateText)

        //below is for RatingBar rating
        //get rating from the Result passed from MainActivity
        locRating.setRating(locRatingFloat)

        //update textview with the rating
        locRatingTV.setText("Rating: ".plus(locRatingFloat.toString()).plus("/5"))


        //get the new rating from user and update textview with the new rating
        //https://developer.android.com/reference/android/widget/RatingBar
        //https://kotlin-android.com/android-ratingbar-kotlin/
        locRating.setOnRatingBarChangeListener(object : RatingBar.OnRatingBarChangeListener {
            override fun onRatingChanged(
                locRating: RatingBar?,
                rating: Float,
                fromUser: Boolean
            ) {
                locRatingTV.setText("Rating: ".plus(rating.toString()).plus("/5"))
                locRatingFloat = rating
            }
        }
        )

        //Use the DatePickerDialog as method for Date input and validate no later than today date
        //https://www.tutorialkart.com/kotlin-android/android-datepicker-kotlin-example/
        //https://androidride.com/open-calendar-on-button-click-in-android-example-kotlin-java/
        val visitedDateIL = findViewById<TextInputLayout>(R.id.visitDate_IL)
        val mcalendar = Calendar.getInstance()
        val mday = mcalendar.get(Calendar.DAY_OF_MONTH)
        val mth = mcalendar.get(Calendar.MONTH)
        val myear = mcalendar.get(Calendar.YEAR)

        locDateVisited.setOnClickListener {
            val datePickerDialog = DatePickerDialog(
                this, DatePickerDialog.OnDateSetListener
                { view, year, monthOfYear, dayOfMonth ->
                    if (year > myear) {
                        visitedDateIL.error = getString(R.string.dateInputError)
                    } else if ((year == myear) && (monthOfYear > mth)) {
                        visitedDateIL.error = getString(R.string.dateInputError)
                    } else if ((year == myear) && (monthOfYear == mth) && (dayOfMonth > mday)) {
                        visitedDateIL.error = getString(R.string.dateInputError)
                    } else {
                        locDateVisited.setText("Last visited: " + dayOfMonth + "/" + (monthOfYear + 1) + "/" + year)
                        visitedDateIL.error = null
                        visitedDateIL.setErrorEnabled(false)
                    }
                }, myear, mth, mday
            )
            datePickerDialog.show()
        }

        //https://codereview.stackexchange.com/questions/191587/kotlin-text-validation
        //validate the input for Name in the EditText widget when the text changed
        val nameIL = findViewById<TextInputLayout>(R.id.location_IL)
        locName.addTextChangedListener { text ->
            val a = locName.text.toString().trim()
            if (isLetters(a)) {
                nameIL.error = null
                nameIL.setErrorEnabled(false)
            } else {
                nameIL.error = getString(R.string.nameInputError)
            }
        }

        //validate the input for City and State in the EditText widget when the text changed
        val cityAndStateIL = findViewById<TextInputLayout>(R.id.cityAndState_IL)
        locCityAndState.addTextChangedListener { text ->
            val a = locCityAndState.text.toString().trim()
            if (isLetters(a)) {
                cityAndStateIL.error = null
                cityAndStateIL.setErrorEnabled(false)
            } else {
                cityAndStateIL.error = getString(R.string.cityAndStateInputError)
            }
        }

        //validate the input for Country in the EditText widget when the text changed
        val countryIL = findViewById<TextInputLayout>(R.id.country_IL)
        locCountry.addTextChangedListener { text ->
            val a = locCountry.text.toString().trim()
            if (isLetters(a)) {
                countryIL.error = null
                countryIL.setErrorEnabled(false)
            } else {
                countryIL.error = getString(R.string.countryInputError)
            }
        }

    }


    //https://www.techiedelight.com/check-if-string-contains-only-alphabets-kotlin/
    private fun isLetters(str: String): Boolean {
        return str.matches("^[a-zA-Z ,-]{0,29}[a-zA-Z]$".toRegex())
    }


    private fun EditText.addTextChangedListener(testFunction: (text: String) -> Unit) {
        addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                testFunction(s.toString())
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })
    }


    //https://stackoverflow.com/questions/52883381/how-do-you-pass-data-from-current-activity-to-previous-activity
    override fun onBackPressed() {

        val locName = findViewById<EditText>(R.id.location_ET)
        val locCityAndState = findViewById<EditText>(R.id.cityAndState_ET)
        val locCountry = findViewById<EditText>(R.id.country_ET)
        val locDateVisited = findViewById<EditText>(R.id.visitDate_ET)

        updatedLoc.name = locName.text.toString().trim()
        updatedLoc.cityAndState = locCityAndState.text.toString().trim()
        updatedLoc.country = locCountry.text.toString().trim()
        updatedLoc.lastVisitDate = locDateVisited.text.toString()
        updatedLoc.rating = locRatingFloat


        val i = intent.apply {
            putExtra("returnedResult", updatedLoc)
        }
        setResult(Activity.RESULT_OK, i)
        super.onBackPressed()

    }

}



