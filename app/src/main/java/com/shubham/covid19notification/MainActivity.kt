package com.shubham.covid19notification

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import kotlinx.android.synthetic.main.recycler_list_layout.*
import org.json.JSONException

class MainActivity : AppCompatActivity() {

    private var recyclerView: RecyclerView? = null
    private var requestQueue: RequestQueue? = null
    private lateinit var notificationData: Array<DataMem>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerView = findViewById(R.id.recycler1)
        recyclerView!!.setLayoutManager(LinearLayoutManager(this))
        requestQueue = Volley.newRequestQueue(this)

        parseJson()

    }

    private fun parseJson() {
        val url = "https://api.rootnet.in/covid19-in/notifications"
        val request = JsonObjectRequest(
            Request.Method.GET, url, null,
            Response.Listener { response ->
                try {
                    val array = response.getJSONObject("data").getJSONArray("notifications")
                    val gsonBuilder = GsonBuilder()
                    val gson: Gson = gsonBuilder.create()
                    notificationData = gson.fromJson(array.toString(), Array<DataMem>::class.java)

                    recyclerView?.adapter = NotificationAdapter(notificationData).apply {
                        onClickListener = {link->

                            val intent = Intent(Intent.ACTION_VIEW)
                            intent.data = Uri.parse(link)
                            startActivity(intent)

                        }
                    }
                } catch (e: JSONException) {
                    e.printStackTrace()

                }
            },
            Response.ErrorListener { error ->
                Toast.makeText(this@MainActivity, error.message, Toast.LENGTH_SHORT).show()
            }
        )
        requestQueue?.add(request)
    }
}
