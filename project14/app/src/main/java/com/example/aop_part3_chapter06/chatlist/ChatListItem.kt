package com.example.aop_part3_chapter06.chatlist

data class ChatListItem(
    val buyerId: String,
    val sellerId: String,
    val itemTitle: String,
    val key: Long
) {
    //TODO 파이어베이스에서 사용하기 위해 빈생성자를 만들어줘야 함
    constructor() : this("", "", "", 0)
}
