# 타이머  
## xml  
### activity_main   
+ 분과 초를 시간이 지남에 따라 보여줄 TextView  
+ Seek바를 통해 남은 시간을 bar의 형태로 보여줌  

## Activity  
### MainActivity  
+ sound를 활용하기 위해 앱내에 있는 Sound를 재생할 때 쓰이는 Class인 SounPool활용  
+ SoundPool의 Builder를 이용해 객체 생성  
+ Load 메소드를 통해 사운드 리소스를 load해주어야 함  
+ 리소스를 사용한 이후에는 항상 release를 해주어야 함  
+ seekBar를 활용해 남은 시간의 값에 따라 화살표의 위치를 변경해 줌  
+ seekBar의 setOnSeekBarChangeListener를 사용해 SeekBar의 값이 변할 때마다 시간을 나타내는 TextView의 값을 업데이트  
+ seekBar의 Bar를 사용자가 임의로 끌어서 시간을 변경할 수도 있음  
+ 

![1](./1.png)
