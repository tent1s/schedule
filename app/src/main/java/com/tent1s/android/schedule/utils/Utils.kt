package com.tent1s.android.schedule.utils

import android.content.Context
import android.widget.Toast


fun Context.shortToast(message: String) = Toast.makeText(this, message, Toast.LENGTH_SHORT).show()