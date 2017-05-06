package com.sys1yagi.mastodon.android.data.model

import com.sys1yagi.mastodon4j.api.entity.Notification

class Notification(val entity: Notification) {
    val status: TimelineStatus? = entity.status?.let { TimelineStatus(it) }
}
