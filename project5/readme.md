# 전자액자  
## xml  
### activity_main   
+ 이미지뷰를 알맞게 배치  
+ 이미지뷰는 동적으로 늘어나는 것이 아닌 정적으로 정해진 개수만큼만 만들어 둠  
### activity_photoframe  
+ 이미지가 지나가면서 보일 수 있도록 이미지뷰를 배치   

## Activity  
### MainActivity  
+ 이미지뷰를 리스트로 가지고 있기 위해 List에 ImageView를 넣어주고 각각의 id를 연결해줌  
+ 이미지의 정보를 가진 Uri를 가지고 있어야 하고 해당 Uri들은 이미지에 따라 변할 수 있기 때문에 MutableList형태로 Uri를 가지고 있음  
+ 갤러리에 접근하기 위해 ContextCompat.checkSelfPermission을 통해 권한 확인  
+ intent 의 type을 image/* 값으로 줌으로써 이미지형태의 모든 이미지들을 불러올 수 있도록 설정  
+ 해당 intent를 startActivityForResult를 통해 requestCode를 보내는 형태로 실행하여 선택된 이미지의 결과 Uri값을 가져올 수 있도록 onActivityResult를 통해 Uri를 받아옴  
### PhotoFrameActivity  
+ MainActivity로부터 넘어온 string형태의 uri값을 Uri형태로 변경해주기 위해 Uri.parse(it)를 사용  
+ 일정 주기마다 이미지가 변경되어 보여지게 하기 위해 timer를 사용하고 timer에서 UI를 변경시켜주기 위해 runOnUiThread내에서 이미지를 변경시켜줌     

![1](./1.png)
