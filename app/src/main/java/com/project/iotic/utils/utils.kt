package com.project.iotic.utils

import android.app.Activity
import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.project.iotic.data.Repo
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.*

val timeFormatter = SimpleDateFormat("HH:mm", Locale.ENGLISH)
val dateTimeFormatter = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH)

fun Context.hideKeyboard(view: View) {
    val inputMethodManager = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
    inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
}

fun Activity.hideKeyboard() {
    hideKeyboard(currentFocus ?: View(this))
}

fun Fragment.hideKeyboard() {
    view?.let { activity?.hideKeyboard(it) }
}

fun resStr(resId: Int) = Repo.activity.getString(resId)

fun launchMain(block: suspend CoroutineScope.() -> Unit) = GlobalScope.launch (Dispatchers.Main, block = block)

fun Fragment.toast(msg: String) = Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()
fun Fragment.toastl(msg: String) = Toast.makeText(context, msg, Toast.LENGTH_LONG).show()


fun Response<*>.isOK() = this.code() in 200..299