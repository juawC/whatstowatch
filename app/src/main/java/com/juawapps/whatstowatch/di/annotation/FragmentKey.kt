package com.juawapps.whatstowatch.di.annotation

import androidx.fragment.app.Fragment
import dagger.MapKey
import kotlin.reflect.KClass

@Retention(value = AnnotationRetention.BINARY)
@MapKey
internal annotation class FragmentKey(val value: KClass<out Fragment>)
