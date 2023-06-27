package com.dewatwc.githubuser.view

import `in`.abhisheksaxena.toaster.Toaster
import android.content.Context
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dewatwc.githubuser.data.DatUser
import com.dewatwc.githubuser.data.DataUsers
import com.loopj.android.http.AsyncHttpClient
import com.loopj.android.http.AsyncHttpResponseHandler
import cz.msebera.android.httpclient.Header
import org.json.JSONArray
import org.json.JSONObject

class ViewModelUser : ViewModel() {
    private val listDataUser = MutableLiveData<ArrayList<DataUsers>>()
    val list = ArrayList<DataUsers>()

    fun getUserData(): MutableLiveData<ArrayList<DataUsers>> {
        return listDataUser
    }

    private val listDataDetail = MutableLiveData<DatUser>()
    fun getUserDetail(): MutableLiveData<DatUser> {
        return listDataDetail
    }

    private fun getDataDetail(id: String, context: Context) {
        val client = AsyncHttpClient()
        client.addHeader("Authorization", "token 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") //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
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
                        DataUsers(
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
                responseBody: ByteArray?,
                error: Throwable?
            ) {
                val errorMessage = when (statusCode) {
                    401 -> "$statusCode : Bad Request"
                    403 -> "$statusCode : Forbidden"
                    404 -> "$statusCode : Not Found"
                    else -> "$statusCode : ${error?.message}"
                }
                Toaster.popError(context, errorMessage, Toaster.LENGTH_LONG).show()
            }
        })
    }

    fun getDataSearch(id: String, context: Context) {
        val client = AsyncHttpClient()
        client.addHeader("Authorization", "token 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")
        client.addHeader("User-Agent", "request")
        val url = "https://api.github.com/search/users?q=$id"
        client.get(url, object : AsyncHttpResponseHandler() {
            override fun onSuccess(
                statusCode: Int,
                headers: Array<Header>,
                responseBody: ByteArray
            ) {
                val result = String(responseBody)
                try {
                    val jsonArray = JSONObject(result)
                    val item = jsonArray.getJSONArray("items")
                    for (i in 0 until item.length()) {
                        val jsonObject = item.getJSONObject(i)
                        val username: String = jsonObject.getString("login")
                        getDataDetail(username, context)
                    }
                    listDataUser.postValue(list)
                } catch (e: Exception) {
                    Toast.makeText(context, e.message, Toast.LENGTH_SHORT).show()
                    e.printStackTrace()
                }
            }

            override fun onFailure(
                statusCode: Int,
                headers: Array<Header>,
                responseBody: ByteArray,
                error: Throwable?
            ) {
                val errorMessage = when (statusCode) {
                    401 -> "$statusCode : Bad Request"
                    403 -> "$statusCode : Forbidden"
                    404 -> "$statusCode : Not Found"
                    else -> "$statusCode : ${error?.message + " GIT"}"
                }
                Toaster.popError(context, errorMessage, Toaster.LENGTH_LONG).show()
            }
        })
    }

    fun getDetail(user: String, context: Context) {
        val client = AsyncHttpClient()
        client.addHeader("Authorization", "token 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")
        client.addHeader("User-Agent", "request")
        val url = "https://api.github.com/users/$user"
        client.get(url, object : AsyncHttpResponseHandler() {
            override fun onSuccess(
                statusCode: Int,
                headers: Array<Header>?,
                responseBody: ByteArray
            ) {
                val result = String(responseBody)
                try {
                    val responseObject = JSONObject(result)
                    val userData = DatUser(
                        username = responseObject.getString("login"),
                        name = responseObject.getString("name"),
                        avatar = responseObject.getString("avatar_url"),
                        company = responseObject.getString("company"),
                        location = responseObject.getString("location"),
                        repositories = responseObject.getString("public_repos"),
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
                responseBody: ByteArray?,
                error: Throwable?
            ) {
                val errorMessage = when (statusCode) {
                    401 -> "$statusCode : Bad Request"
                    403 -> "$statusCode : Forbidden"
                    404 -> "$statusCode : Not Found"
                    else -> "$statusCode : ${error?.message}"
                }
                Toaster.popError(context, errorMessage, Toaster.LENGTH_LONG).show()
            }
        })
    }

    fun setFollowers(idLog: String, context: Context) {
        val client = AsyncHttpClient()
        client.addHeader("Authorization", "token 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")
        client.addHeader("User-Agent", "request")
        val url = "https://api.github.com/users/$idLog/followers"
        client.get(url, object : AsyncHttpResponseHandler() {
            override fun onSuccess(
                statusCode: Int,
                headers: Array<Header>?,
                responseBody: ByteArray
            ) {
                val result = String(responseBody)
                try {
                    val responseArray = JSONArray(result)
                    for (i in 0 until responseArray.length()) {
                        val item = responseArray.getJSONObject(i)
                        val username = item.getString("login")
                        getDataDetail(username, context)
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
                responseBody: ByteArray?,
                error: Throwable?
            ) {
                val errorMessage = when (statusCode) {
                    401 -> "$statusCode : Bad Request"
                    403 -> "$statusCode : Forbidden"
                    404 -> "$statusCode : Not Found"
                    else -> "$statusCode : ${error?.message}"
                }
                Toaster.popError(context, errorMessage, Toaster.LENGTH_LONG).show()
            }
        })
    }

    fun setFollowing(idLog: String, context: Context) {
        val client = AsyncHttpClient()
        client.addHeader("Authorization", "token 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")
        client.addHeader("User-Agent", "request")
        val url = "https://api.github.com/users/$idLog/following"
        client.get(url, object : AsyncHttpResponseHandler() {
            override fun onSuccess(
                statusCode: Int,
                headers: Array<Header>?,
                responseBody: ByteArray
            ) {
                val result = String(responseBody)
                try {
                    val responseArray = JSONArray(result)
                    for (i in 0 until responseArray.length()) {
                        val item = responseArray.getJSONObject(i)
                        val username = item.getString("login")
                        getDataDetail(username, context)
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
                responseBody: ByteArray?,
                error: Throwable?
            ) {
                val errorMessage = when (statusCode) {
                    401 -> "$statusCode : Bad Request"
                    403 -> "$statusCode : Forbidden"
                    404 -> "$statusCode : Not Found"
                    else -> "$statusCode : ${error?.message}"
                }
                Toaster.popError(context, errorMessage, Toaster.LENGTH_LONG).show()
            }
        })
    }
}




