package com.abedalkareem.games_services

import android.app.Activity
import com.abedalkareem.games_services.util.PluginError
import com.abedalkareem.games_services.util.errorCode
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.games.Games
import io.flutter.plugin.common.MethodChannel

class Player {

  fun getPlayerID(activity: Activity?, result: MethodChannel.Result) {
    activity ?: return
    val lastSignedInAccount = GoogleSignIn.getLastSignedInAccount(activity) ?: return
    Games.getPlayersClient(activity, lastSignedInAccount)
      .currentPlayerId.addOnSuccessListener {
        result.success(it)
      }.addOnFailureListener {
        result.error(PluginError.FailedToGetPlayerId.errorCode(), it.localizedMessage, null)
      }
  }

  fun getPlayerToken(activity: Activity?, result: MethodChannel.Result) {
    activity ?: return
    val lastSignedInAccount = GoogleSignIn.getLastSignedInAccount(activity) ?: return
    if (lastSignedInAccount.idToken != null) {
      result.success(lastSignedInAccount.idToken)
    } else {
      result.error(PluginError.FailedToGetPlayerId.errorCode(), "Player token not found.", null)
    }
  }

  fun getPlayerName(activity: Activity?, result: MethodChannel.Result) {
    activity ?: return
    val lastSignedInAccount = GoogleSignIn.getLastSignedInAccount(activity) ?: return
    Games.getPlayersClient(activity, lastSignedInAccount)
      .currentPlayer
      .addOnSuccessListener { player ->
        result.success(player.displayName)
      }.addOnFailureListener {
        result.error(PluginError.FailedToGetPlayerName.errorCode(), it.localizedMessage, null)
      }
  }
}