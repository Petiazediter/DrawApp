package com.codecool.drawapp.dependency.firend_invites

interface FriendInvitesService {
    fun getUserFriendInvites( view : FriendInvitesImplementation.FriendInvitesCallback)
    fun declineFriendInvite(userId : String, view : FriendInvitesImplementation.FriendRequestActionCallback)
}