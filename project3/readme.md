# 다이어리  
## xml  
### activity_main  
+ TextView의 글꼴을 변경해주기 위해 res폴더에 font폴더를 만들어주고 원하는 font파일을 집어넣고 fontFamily속성에 해당 위치를 넣어줌  
+ 비밀번호 기능을 활용하기 위해 NumberPicker를 3개 만들어 줌  
+ 비밀번호 변경과 비밀번호 입력을 구분하기 위해 버튼을 추가  
### activity_diary  
+ 다이어이를 입력할 EditText가 있으며 쓰여진 길이에 따라 높이와 넓이를 변경해주기 위해 0dp로 값을 설정   
## Activity  
### MainActivity  
+ 각 자리의 NumberPicker의 minValue와 maxValue를 1과 9로 설정  
+ 따로 직접 사용하지 않는 변수들에 대해서는 따로 호출만해서 lazy하게 초기화 해줘야 함  
  - lazy하게 초기화하는 이유는 View가 다 그려지는 시점이 아니기 때문에 lazy로 초기화를 해줌  
  - View가 다 그려지는시점은 onCreate    
+ 안드로이드에서 기본적으로 제공되는 SharedPreferences를 사용하여 비밀번호나 다이어리 내용인 간단한 값들을 편리하게 저장할 수 있도록 사용  
+ 비밀번호가 틀렸을 경우 AlertDialog를 사용  
### DiaryActivity  
+ editText의 수정된 내용을 DB에 수정하기 위해 addTextChangedListener를 사용하고 handler를 통해 작업을 처리  
+ 메인스레드와 UI스레드를 연결해주기 위해 핸들러를 사용  

![1](./1.png)
![1](./2.png) 
