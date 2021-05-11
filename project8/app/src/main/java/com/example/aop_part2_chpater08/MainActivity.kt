package com.example.aop_part2_chpater08

import android.graphics.Bitmap
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.inputmethod.EditorInfo
import android.webkit.URLUtil
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import androidx.core.widget.ContentLoadingProgressBar
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout


class MainActivity : AppCompatActivity() {

    private val goHomeButton: ImageButton by lazy{
        findViewById(R.id.goHomeButton)
    }
    private val goForwardButton: ImageButton by lazy{
        findViewById(R.id.goForwardButton)
    }
    private val goBackButton: ImageButton by lazy{
        findViewById(R.id.goBackButton)
    }

    private val addressBar: EditText by lazy {
        findViewById(R.id.addressBar)
    }
    private val webView: WebView by lazy {
        findViewById(R.id.webView)
    }
    private val refreshLayout: SwipeRefreshLayout by lazy{
        findViewById(R.id.refreshLayout)
    }
    private val progressBar: ContentLoadingProgressBar by lazy{
        findViewById(R.id.progressBar)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initViews()
        bindViews()
    }

    override fun onBackPressed() {
        if(webView.canGoBack()){
            webView.goBack()
        }
        else {
            super.onBackPressed()
        }
    }
    private fun initViews() {
        //TODO 구글을 열게되면 자동으로 외부 웹 브라우저로 이동하게 됨 이를 해결하기 위해 OVERRIDE 해야함
        webView.apply{
            webViewClient = WebViewClient()
            webChromeClient=WebChromeClient()
            settings.javaScriptEnabled = true
            loadUrl(DEFAULT_URL)
        }
    }

    private fun bindViews() {
        goHomeButton.setOnClickListener{
            webView.loadUrl(DEFAULT_URL)
        }
        addressBar.setOnEditorActionListener { v, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                val loadingUrl=v.text.toString()
                if(URLUtil.isNetworkUrl(loadingUrl)){
                    webView.loadUrl(loadingUrl)
                }
                else{
                    webView.loadUrl("http://$loadingUrl")
                }
            }

            return@setOnEditorActionListener false
        }
        goBackButton.setOnClickListener {
            webView.goBack()
        }
        goForwardButton.setOnClickListener {
            webView.goForward()
        }
        refreshLayout.setOnRefreshListener{
            webView.reload()
        }
    }
    //TODO inner를 붙임으로써 상위 클래스에 접근이 가능하도록 한다.
    inner class WebViewClient: android.webkit.WebViewClient(){
        override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
            super.onPageStarted(view, url, favicon)

            progressBar.show()
        }
        override fun onPageFinished(view: WebView?, url: String?) {
            super.onPageFinished(view, url)
            refreshLayout.isRefreshing = false
            //TODO 화면에 로딩이 돌아가는 것을 사라지게 하기 위해서

            progressBar.hide()
            goBackButton.isEnabled=webView.canGoBack()
            goForwardButton.isEnabled=webView.canGoForward()
            addressBar.setText(url)
        }
    }

    inner class WebChromeClient: android.webkit.WebChromeClient(){
        override fun onProgressChanged(view: WebView?, newProgress: Int) {
            super.onProgressChanged(view, newProgress)
            progressBar.progress=newProgress
        }
    }

    companion object{
        private const val DEFAULT_URL="http://www.google.com"
    }
}