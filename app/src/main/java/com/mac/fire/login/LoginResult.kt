package com.mac.fire.login

import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.tasks.Task

/**
 * Wrapper class for data recieved in LoginActivity's onActivityResult()
 * function
 */
data class LoginResult(val requestCode: Int, val account: GoogleSignInAccount?)