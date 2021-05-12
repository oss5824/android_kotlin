package com.example.aop_part2_chpater09

import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

class MyFirebaseMessagingService: FirebaseMessagingService() {

    //TODO 들어올 때마다 토큰이 바뀌어버리기 때문에 그것을 방지하기위해
    //TODO 바뀔 때마다 토큰을 서버로 보내줌
    override fun onNewToken(p0: String) {
        super.onNewToken(p0)
    }

    override fun onMessageReceived(message: RemoteMessage) {
        super.onMessageReceived(message)
    }
}