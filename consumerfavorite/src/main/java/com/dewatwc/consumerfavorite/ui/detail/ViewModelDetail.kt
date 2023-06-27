package com.dewatwc.consumerfavorite.ui.detail

import `in`.abhisheksaxena.toaster.Toaster
import android.content.Context
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dewatwc.consumerfavorite.data.DataUser
import com.loopj.android.http.AsyncHttpClient
import com.loopj.android.http.AsyncHttpResponseHandler
import cz.msebera.android.httpclient.Header
import org.json.JSONArray
import org.json.JSONObject

class ViewModelDetail : ViewModel() {
    private val listDataUser = MutableLiveData<ArrayList<DataUser>>()
    val list = ArrayList<DataUser>()

    fun getUserData(): MutableLiveData<ArrayList<DataUser>> {
        return listDataUser
    }

    private val listDataDetail = MutableLiveData<DataUser>()
    fun getUserDetail(): MutableLiveData<DataUser> {
        return listDataDetail
    }

    fun getDetail(user: String, context: Context) {
        val client = AsyncHttpClient()
        client.addHeader("Authorization", "token df42dc44969bd0f303b307905d3684bd7780ba8d")
        client.addHeader("User-Agent", "request")
        val url = "https://api.github.com/users/$user"
        client.get(url, object : AsyncHttpResponseHandler() {
            override fun onSuccess(
                statusCode: Int,
                headers: Array<Header>,
                responseBody: ByteArray
            ) {
                val result = String(responseBody)
                try {
                    val responseObject = JSONObject(result)
                    val userData = DataUser(
                        username = responseObject.getString("login"),
                        name = responseObject.getString("name"),
                        avatar = responseObject.getString("avatar_url"),
                        company = responseObject.getString("company"),
                        location = responseObject.getString("location"),
                        repository = responseObject.getString("public_repos"),
                        followers = responseObject.getString("followers"),
                        following = responseObject.getString("following"),
                        html_url = responseObject.getString("html_url")
                    )
                    listDataDetail.postValue(userData)
                } catch (e: Exception) {
                    Toast.makeText(context, e.message, Toast.LENGTH_SHORT).show()
                    e.printStackTrace()
                }
            }

            override fun onFailure(
                statusCode: Int,
                headers: Array<Header>?,
                responseBody: ByteArray,
                error: Throwable?
            ) {
                val errorMessage = when (statusCode) {
                    401 -> "$statusCode : Bad Request"
                    403 -> "$statusCode : Forbidden"
                    404 -> "$statusCode : Not Found"
                    else -> "$statusCode : ${error?.message}"
                }
                Toaster.popError(context, errorMessage, Toaster.LENGTH_SHORT).show()
            }
        })
    }

    fun setFollowers(idLog: String, context: Context) {
        val client = AsyncHttpClient()
        client.addHeader("Authorization", "token d62790ec720cd3b4d8fd25699e537c61df94dfbc")
        client.addHeader("User-Agent", "request")
        val url = "https://api.github.com/users/$idLog/followers"
        client.get(url, object : AsyncHttpResponseHandler() {
            override fun onSuccess(
                statusCode: Int,
                headers: Array<Header>,
                responseBody: ByteArray
            ) {
                val result = String(responseBody)
                try {
                    val responseArray = JSONArray(result)
                    for (i in 0 until responseArray.length()) {
                        val item = responseArray.getJSONObject(i)
                        val username = item.getString("login")
                        getData(username, context)
                    }
                    listDataUser.postValue(list)
                } catch (e: Exception) {
                    Toast.makeText(context, e.message, Toast.LENGTH_SHORT).show()
                    e.printStackTrace()
                }
            }

            override fun onFailure(
                statusCode: Int,
                headers: Array<Header>?,
                responseBody: ByteArray,
                error: Throwable?
            ) {
                val errorMessage = when (statusCode) {
                    401 -> "$statusCode : Bad Request"
                    403 -> "$statusCode : Forbidden"
                    404 -> "$statusCode : Not Found"
                    else -> "$statusCode : ${error?.message}"
                }
                Toaster.popError(context, errorMessage, Toaster.LENGTH_SHORT).show()
            }
        })
    }

    fun setFollowing(idLog: String, context: Context) {
        val client = AsyncHttpClient()
        client.addHeader("Authorization", "token 8a58b6424c8d59f7d36638dd44f30b7a57b3074d")
        client.addHeader("User-Agent", "request")
        val url = "https://api.github.com/users/$idLog/following"
        client.get(url, object : AsyncHttpResponseHandler() {
            override fun onSuccess(
                statusCode: Int,
                headers: Array<Header>,
                responseBody: ByteArray
            ) {
                val result = String(responseBody)
                try {
                    val responseArray = JSONArray(result)
                    for (i in 0 until responseArray.length()) {
                        val item = responseArray.getJSONObject(i)
                        val username = item.getString("login")
                        getData(username, context)
                    }
                    listDataUser.postValue(list)
                } catch (e: Exception) {
                    Toast.makeText(context, e.message, Toast.LENGTH_SHORT).show()
                    e.printStackTrace()
                }
            }

            override fun onFailure(
                statusCode: Int,
                headers: Array<Header>?,
                responseBody: ByteArray,
                error: Throwable?
            ) {
                val errorMessage = when (statusCode) {
                    401 -> "$statusCode : Bad Request"
                    403 -> "$statusCode : Forbidden"
                    404 -> "$statusCode : Not Found"
                    else -> "$statusCode : ${error?.message}"
                }
                Toaster.popError(context, errorMessage, Toaster.LENGTH_SHORT).show()
            }
        })
    }


    private fun getData(id: String, context: Context) {
        val client = AsyncHttpClient()
        client.addHeader("Authorization", "token 1679072e46d964f02534516a376cca85adcc3a53")
        client.addHeader("User-Agent", "request")
        val url = "https://api.github.com/users/$id"
        client.get(url, object : AsyncHttpResponseHandler() {
            override fun onSuccess(
                statusCode: Int,
                headers: Array<Header>,
                responseBody: ByteArray
            ) {
                val result = String(responseBody)
                try {
                    val jsonObject = JSONObject(result)
                    val username: String = jsonObject.getString("login").toString()
                    val name: String = jsonObject.getString("name").toString()
                    val avatar: String = jsonObject.getString("avatar_url").toString()
                    val company: String = jsonObject.getString("company").toString()
                    val location: String = jsonObject.getString("location").toString()
                    val repository: String = jsonObject.getString("public_repos")
                    val followers: String = jsonObject.getString("followers")
                    val following: String = jsonObject.getString("following")
                    val html: String = jsonObject.getString("html_url")
                    list.add(
                        DataUser(
                            username,
                            name,
                            avatar,
                            company,
                            location,
                            repository,
                            followers,
                            following,
                            html
                        )
                    )
                    listDataUser.postValue(list)
                } catch (e: Exception) {
                    Toast.makeText(context, e.message, Toast.LENGTH_SHORT).show()
                    e.printStackTrace()
                }
            }

            override fun onFailure(
                statusCode: Int,
                headers: Array<Header>?,
                responseBody: ByteArray,
                error: Throwable?
            ) {
                val errorMessage = when (statusCode) {
                    401 -> "$statusCode : Bad Request"
                    403 -> "$statusCode : Forbidden"
                    404 -> "$statusCode : Not Found"
                    else -> "$statusCode : ${error?.message}"
                }
                Toaster.popError(context, errorMessage, Toaster.LENGTH_SHORT).show()
            }
        })
    }
}

