package com.mobilegame.robozzle.domain.state

//enum class UserConnectionState {
//   Created, Connected, NotConnected, NoUser, ServerNotReached
//}

//todo a connection state sealed class for every type of request: Token , UltimateUser, Levels...
sealed class UserConnectionState(val str: String) {
   object CreatedAndNotVerified: UserConnectionState("created_state")
   object Verified: UserConnectionState("created_and_verified_state")
   object Connected: UserConnectionState("connected_state")
   object IssueWithServer: UserConnectionState("issue_with_server_state")
   object InvalidNameOrPassword: UserConnectionState("not_connected_state")
   object NotConnected: UserConnectionState("not_connected_state")
   object NotCreated: UserConnectionState("not_created_state")
   object NoUser: UserConnectionState("no_user_state")
   object ServerNotReached: UserConnectionState("server_not_reached_state")
   //todo: differenciate user not connected to user not verified by server ?
}