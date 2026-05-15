package com.linguaceleris.testing

@Target(AnnotationTarget.CLASS, AnnotationTarget.FUNCTION, AnnotationTarget.FILE)
@Retention(AnnotationRetention.BINARY)
annotation class ExcludeFromKover
