package com.ovs_corp.bstorm.ui.home.idea_create;

import java.time.Instant

data class IdeaRsDto(
        val id: String,
        val title: String,
        val summary: String?,
        val author: String,
        val status: String,
        var votesYes: Int,
        var votesNo: Int,
        val benefits: SpecClass?,
        val dateCreated: String,
        val businessDirections: List<String>,
        val expenses: SpecClass?,
        val thematics: String?,
        val tags: List<String>?
)