package ac.duksung.cbm_android

import android.net.http.SslError
import android.webkit.*

class LogWebViewClient: WebViewClient() {
    override fun onReceivedHttpError(
        view: WebView?, request: WebResourceRequest?, errorResponse: WebResourceResponse? )
    { super.onReceivedHttpError(view, request, errorResponse) }
    override fun onReceivedError(
        view: WebView?, request: WebResourceRequest?, error: WebResourceError?
    ) { super.onReceivedError(view, request, error) }
    override fun onReceivedSslError(view: WebView?, handler: SslErrorHandler, error: SslError? ) {
        handler.proceed()
    }

}
