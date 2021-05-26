# xml  
## activity_main  
+ 복권과 같이 숫자를 선택하기 위한 NumberPicker    
+ 복권의 번호를 나열해주기 위해서 새로운 LinearLayout안에 TextView를 나열  
  - 처음에는 보여야하지 않기 때문에 visibility속성 값을 gone으로 설정  
  - 여기서 xml에서 미리보기를 하고 싶다면 tools의 visibility속성을 visible로 주면 됨   
 
# Activity  
## MainActivity  
+ 숫자들이 중복되지 않게 뽑혀야 하기 때문에 hashSetOf()활용  
  - 해당 변수의 contains method를 사용해 선택 된 수에 중복 된 수가 있는지 확인해 줌  
+ TextView를 여러개 담기 위해서 List의 형태로 TextView를 담아줌  
  - 이 리스트에 접근하기 위해서는 해당 변수에서 forEach형태로 값을 꺼내올 수 있음  
  - 인덱스에 접근해주기 위해서는 forEachIndexed형태로도 접근할 수 있음  
+ 복권의 번호처럼 numberPicker에서의 값이 1~45사이에서만 보여주게 하기 위해 numberPicker의 minValue 프로퍼티를 1로 maxValue 프로퍼티를 45로 설정해줌  
+ 출력되는 TextView가 복권 번호처럼 원의 모양처럼 출력이 되고 각각의 색을 바꿔주기 위해서 textView의 background프로퍼티를 ContextCompat.getDrawble(this,R.drawable.name)을 활용해 바꿔줌  
