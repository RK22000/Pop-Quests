object Compose{
    val version = "1.1.1"
}

object Dependencies {
    val implementations = listOf(
        "androidx.core:core-ktx:1.8.0",
        "androidx.compose.ui:ui:${Compose.version}",
        "androidx.compose.material:material:${Compose.version}",
        "androidx.compose.ui:ui-tooling-preview:${Compose.version}",
        "androidx.lifecycle:lifecycle-runtime-ktx:2.5.0",
        "androidx.activity:activity-compose:1.5.0",
    )
    val testImplementations = listOf(
        "junit:junit:4.13.2",
    )
    val androidTestImplementations = listOf(
        "androidx.test.ext:junit:1.1.3",
        "androidx.test.espresso:espresso-core:3.4.0",
        "androidx.compose.ui:ui-test-junit4:${Compose.version}",
    )
    val debugImplementations = listOf(
        "androidx.compose.ui:ui-tooling:${Compose.version}",
        "androidx.compose.ui:ui-test-manifest:${Compose.version}"
    )
}

object Modules {
    const val app = ":app"
    const val data = ":data"
    const val designSystems = ":design-systems"
}

object Moshi {
    val version = "1.13.0"
    val implementations = listOf(
        "com.squareup.moshi:moshi:$version",
        "com.squareup.moshi:moshi-kotlin:$version"
    )
}