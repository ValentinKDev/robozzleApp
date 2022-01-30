package com.mobilegame.robozzle.domain

enum class UserConnectionState {
   Created, Connected, NotConnected, NoUser, ServerNotReached
}


sealed class UserConnection(val state: String) {
   object Created: UserConnection("created_state")
   object CreatedAndVerified: UserConnection("created_and_verified_state")
   object Connected: UserConnection("connected_state")
   object NotConnected: UserConnection("not_connected_state")
   object NotCreated: UserConnection("not_created_state")
   object NoUser: UserConnection("no_user_state")
   object ServerNotReached: UserConnection("server_not_reached_state")
   //todo: differenciate user not connected to user not verified by server ?
}