package com.example.workwithretrofit2
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.workwithretrofit2.adapters.RecyclerAdapter
import com.example.workwithretrofit2.api.NewsApiJSON
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

const val BASE_URL = "https://newsapi.org"

class MainActivity : AppCompatActivity() {

    private var titleList = mutableListOf<String>()
    private var descriptionList = mutableListOf<String>()
    private var imagesList = mutableListOf<String>()
    private var linksList = mutableListOf<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        makeApiRequest()
    }

    private fun deleteBlackFon() {
        v_black_Screen.animate().apply {
            alpha(0f)
            duration = 3200
        }.start()
    }

    private fun setUpRecyclerView() {
        rv_recyclerView.layoutManager = LinearLayoutManager(applicationContext)
        rv_recyclerView.adapter = RecyclerAdapter(titleList, descriptionList, imagesList, linksList)
    }

    private fun addToList(title: String, description: String, image: String, link: String) {
        titleList.add(title)
        descriptionList.add(description)
        imagesList.add(image)
        linksList.add(link)
    }

    private fun makeApiRequest() {
        progressBar.visibility = View.VISIBLE
        val api = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(APIRequest::class.java)

        GlobalScope.launch(Dispatchers.IO) {
            try {
                val response: NewsApiJSON = api.getNews()
                for (article in response.articles) {
                    Log.i("MainActivity", "Results = $article")
                    addToList(article.title, article.description, article.urlToImage, article.url)
                }
                withContext(Dispatchers.Main) {
                    setUpRecyclerView()
                    deleteBlackFon()
                    progressBar.visibility = View.GONE
                }
            } catch (e: Exception) {
                Log.e("MainActivity", e.toString())
                withContext(Dispatchers.Main) {
                }
            }
        }
    }
}