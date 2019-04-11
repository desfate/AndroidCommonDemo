package com.defate.mac.androidcommondemo.rxjavas

/**
 * {
"status": 1,
"content": {
"from": "en-EU",
"to": "zh-CN",
"vendor": "baidu",
"out": "你好世界",
"err_no": 0
}
}
 */


data class Translation(
    val content: Content,
    val status: Int
)

data class Content(
    val err_no: Int,
    val from: String,
    val `out`: String,
    val to: String,
    val vendor: String
)