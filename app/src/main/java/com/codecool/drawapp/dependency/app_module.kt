package com.codecool.drawapp.dependency

import com.codecool.drawapp.dependency.add_friend.AddFriendImplementation
import com.codecool.drawapp.dependency.add_friend.AddFriendService
import com.codecool.drawapp.dependency.basic_queries.BasicDatabaseQueries
import com.codecool.drawapp.dependency.basic_queries.BasicDatabaseQueryService
import com.codecool.drawapp.dependency.firend_invites.FriendInvitesImplementation
import com.codecool.drawapp.dependency.firend_invites.FriendInvitesService
import com.codecool.drawapp.dependency.friends.FriendsImplementation
import com.codecool.drawapp.dependency.friends.FriendsService
import com.codecool.drawapp.dependency.lobby.LobbyImplementation
import com.codecool.drawapp.dependency.lobby.LobbyService
import com.codecool.drawapp.dependency.lobby.lobby_listener.LobbyListenerImp
import com.codecool.drawapp.dependency.lobby.lobby_listener.LobbyListenerService
import com.codecool.drawapp.dependency.login.LoginImplementation
import com.codecool.drawapp.dependency.login.LoginService
import com.codecool.drawapp.dependency.random_word.RandomWordImplementation
import com.codecool.drawapp.dependency.random_word.RandomWordService
import com.codecool.drawapp.dependency.register.RegisterImplementation
import com.codecool.drawapp.dependency.register.RegisterService
import com.codecool.drawapp.dependency.upload_image.UploadService
import com.codecool.drawapp.dependency.upload_image.UploadServiceImplementation
import org.koin.dsl.module

val appModule = module{
    single<RegisterService> { RegisterImplementation() }
    single<LoginService> { LoginImplementation() }
    single<FriendsService> { FriendsImplementation() }
    single<AddFriendService>{ AddFriendImplementation()}
    single<BasicDatabaseQueryService>{ BasicDatabaseQueries()}
    single<FriendInvitesService>{ FriendInvitesImplementation()}
    single<LobbyService>{ LobbyImplementation() }
    single<RandomWordService>{RandomWordImplementation()}
    single<LobbyListenerService>{ LobbyListenerImp() }
    single<UploadService>{UploadServiceImplementation()}
}