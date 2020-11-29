package com.ovs_corp.bstorm.ui.home.idea_create;

open class BaseResponse<T>(
    val code: String,
    val message: String,
    val payload : T
)
