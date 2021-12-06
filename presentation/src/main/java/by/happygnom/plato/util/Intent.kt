package by.happygnom.plato.util

import android.content.Intent
import android.net.Uri

fun build3dModelIntent(modelUrl: String): Intent {
    val sceneViewerIntent = Intent(Intent.ACTION_VIEW)

    val intentUri = Uri.parse("https://arvr.google.com/scene-viewer/1.0").buildUpon()
        .appendQueryParameter("file", modelUrl)
        .appendQueryParameter("mode", "3d_preferred")
        .appendQueryParameter("title", "Plato route")
        .build()

    sceneViewerIntent.data = intentUri
    sceneViewerIntent.setPackage("com.google.ar.core")

    return sceneViewerIntent
}
