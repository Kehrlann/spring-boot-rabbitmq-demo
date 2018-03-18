package wf.garnier.kotlinclient

fun String.containsDaniel() = this.toLowerCase().contains("daniel")

fun String?.isNullOrEmpty() = this == null || this.isEmpty()