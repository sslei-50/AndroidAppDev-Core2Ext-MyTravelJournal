package cos80019.core2.mytraveljournal

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.lifecycle.ViewModelProvider


private const val TAG = "MainActivity"

class MainActivity : AppCompatActivity() {

    private val locationBank = mutableListOf(
        Locations(R.drawable.butchart, R.id.butchartTitle_textV, "ButChart Gardens", "Victoria, British Columbia", "Canada", "Last visited: 15/05/2019", 4.5f),
        Locations(R.drawable.grandcanyon, R.id.grandcanyonTitle_textV, "Grand Canyon", "Arizona", "United States", "Last visited: 23/05/2019", 5.0f),
        Locations(R.drawable.creekqld, R.id.mossmanTitle_textV, "Mossman Gorge", "Cairns, Queensland", "Australia", "Last visited: 10/08/2018", 4.0f),
        Locations(R.drawable.harbinoperahouse, R.id.harbinoperahouseTitle_textV, "Harbin Opera House", "Harbin, Heilongjiang", "China", "Last visited: 23/07/2017", 3.5f)
    )


    private var imageIndex: Int = 0
    private lateinit var location: Locations

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "onStart()called")
        setContentView(R.layout.activity_main)

        val locationImage1 = findViewById<ImageView>(R.id.butchart_imageV)
        val locationImage2 = findViewById<ImageView>(R.id.grandcanyon_imageV)
        val locationImage3 = findViewById<ImageView>(R.id.creekqld_imageV)
        val locationImage4 = findViewById<ImageView>(R.id.harbinoperahouse_imageV)


        fun passIntent(){
            location = locationBank[imageIndex]
            val i = Intent(this, SecondActivity::class.java).apply {
                putExtra("result", location)
            }
            //don't use startActivity(i), use startForResult.launch(i) for result to be returned back from the second activity
            startForResult.launch(i)
        }


        locationImage1.setOnClickListener {
            imageIndex = 0
            Log.i("IMAGE1", "onClick")
            passIntent()
        }

        locationImage2.setOnClickListener {
            imageIndex = 1
            passIntent()
        }

        locationImage3.setOnClickListener {
            imageIndex = 2
            passIntent()
        }

        locationImage4.setOnClickListener {
            imageIndex = 3
            passIntent()
        }

    }


    val startForResult =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { returnedResult ->
            when (returnedResult.resultCode) {
                RESULT_OK -> {
                    val intent = returnedResult.data
                    val result = intent?.getParcelableExtra<Locations>("result")
                    if (result != null) {
                        val existingLocation: Locations = locationBank[imageIndex]
                        val updatedLocation: Locations = result
                        locationBank[imageIndex] = updatedLocation

                        //update Location's title TextView
                        val titleTVId = updatedLocation.titleTV
                        val ratingStr = updatedLocation.rating.toString()
                        val introTV = findViewById<TextView>(titleTVId)
                        val introStr = updatedLocation.name.plus("\n").plus(ratingStr).plus(" Stars")
                        introTV.text = introStr

                        //show a toast to advise if details of the location has been updated
                        if ((existingLocation.name != updatedLocation.name) ||
                            (existingLocation.cityAndState != updatedLocation.cityAndState) ||
                            (existingLocation.country != updatedLocation.country) ||
                            (existingLocation.lastVisitDate != updatedLocation.lastVisitDate) ||
                            (existingLocation.rating != updatedLocation.rating)) {
                                Toast.makeText(
                                    this,
                                    "${updatedLocation.name} updated!",
                                    Toast.LENGTH_LONG).show()
                        }
                    }
                }
            }
        }

    //override five more lifecycle functions in MainActivity
    override fun onStart() {
        super.onStart()
        Log.d(TAG, "onStart()called")
    }

    override fun onResume() {
        super.onResume()
        Log.d(TAG, "onResume()called")
    }

    override fun onPause() {
        super.onPause()
        Log.d(TAG, "onPause()called")
    }

    override fun onStop() {
        super.onStop()
        Log.d(TAG, "onStop()called")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "onDestroy()called")
    }

}