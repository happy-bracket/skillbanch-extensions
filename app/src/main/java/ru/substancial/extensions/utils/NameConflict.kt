package ru.substancial.extensions.utils

data class Email(
    val identifier: String,
    val domain: String,
    val topDomain: String
)

fun Email.fullAddress(): String =
    "${this.identifier}@$domain.$topDomain"

val fullAddress: Email.() -> String = Email::fullAddress

fun sampleRec() {

    val email = Email("happybracket", "gmail", "com")

    email.fullAddress()
    fullAddress(email)
    email.fullAddress()

}