package com.example.noteapp.features.core.presentation

import android.content.Context
import android.widget.Toast

// toast to show msg
fun toastMsg(
    context: Context,
    message: String
) = Toast.makeText(
    context,
    message,
    Toast.LENGTH_SHORT
).show()