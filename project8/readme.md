# 웹뷰  
## xml  
### activity_main   
+ 버튼과 EditText를 ConstraintLayout으로 감싸줌  
+ 버튼의 DimensionRatio 속성을 통해 이미지 버튼의 크기를 비율에 맞게 조정  
+ EditText의 imeOptions속성을 actionDone으로 주고 해당 버튼이 눌렸을 때의 이벤트 처리를 MainActivity에서 해줌  
+ selectAllOnFocus속성을 True로 줌으로써 EditText가 선택되었을 때 모든 문자열이 선택될 수 있도록 해줌  
+ 웹을 아래로 당겼을 때 해당 내용이 refresh될 수 있도록 SwipeRefreshLayout으로 WebView를 감싸줌  
+ 다음 실행 될 웹뷰가 어느정도 진행되었나를 나타내주기 위해 ContentLoadingProgressBar를 배치  

## Activity  
### MainActivity  
+ webview를 연결하면 자동으로 외부 웹 브라우저로 이동하게 되기 때문에 이를 해결하기 위해 override를 해줘야 함  
+ 뒤로가기 버튼을 눌렀을 때 이전의 웹사이트로 이동되는 구조기 때문에 onBackPressed를 override해서 webView의 goBack 메소드를 실행해주고 이 구조는 웹사이트들을 하나씩 스택에 쌓아두고 뒤로가기를 할 때 스택에서 pop하여 다시 top을 보여주는 구조를 활용  
+  내부의 다른 class를 통해 상위 클래스에 접근하기 위해 inner class를 사용해주고 상속받은 상위클래스에 있는 필요한 기능들을 override하여 사용  

![1](./1.png)
