package com.github.myseries.data.model

import com.github.myseries.domain.model.Series

internal fun Show.toSeries(): Series {
    return Series(this.name ?: "", this.image?.medium ?: "")
}
