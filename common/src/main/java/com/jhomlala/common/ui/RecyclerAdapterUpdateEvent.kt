package com.jhomlala.common.ui

data class RecyclerAdapterUpdateEvent(val type: RecyclerAdapterUpdateEventType, val position: Int = 0, val itemCount:Int = 0)