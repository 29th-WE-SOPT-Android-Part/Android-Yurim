# Android-Yurim
![github_최유림_ver1-28](https://user-images.githubusercontent.com/70698151/135754615-ce39eada-b5f7-4298-9548-27e825f5fb93.png)
<br/>
## 1차 과제
<br/>

### 실행 GIF
<img src="https://user-images.githubusercontent.com/52950523/136426797-a77a9cb9-b815-49c4-a170-6dc78b6d6a47.gif" width="30%">
<br/><br/>

### Logic 설명<br/><br/>
    
1. SignInActivity.kt

        val intent = Intent(this, HomeActivity::class.java)
        binding.btnLogin.setOnClickListener {
            if(binding.etId.length()!=0 && binding.etPw.length()!=0) {
                Toast.makeText(this, binding.etId.text.toString()+"님 환영합니다", Toast.LENGTH_SHORT).show()
                startActivity(intent)
            }
            else{Toast.makeText(this, "로그인 실패", Toast.LENGTH_SHORT).show()}
        }

- startActivity(intent): intent를 사용한 화면 전환 

- if(binding.etId.length()!=0 && binding.etPw.length()!=0): 아이디, 비밀번호 입력이 모두 되어있는 경우

- binding.etId.text.toString(): 아이디 입력창의 내용으로 이름을 받아와서 OOO님 환영합니다 토스트 메시지 출력  
  


        val startForResult = registerForActivityResult(ActivityResultContracts.StartActivityForResult())       {
            result->
            if(result.resultCode == Activity.RESULT_OK){
                val id = result.data?.getStringExtra("id")
                val pw = result.data?.getStringExtra("pw")
                binding.etId.setText(id)
                binding.etPw.setText(pw)
            }
        }
        

- registerForActivityResult: Result를 받기 위해 액티비티를 실행하는 StartActivityForResult()함수를 넣어준다.

- if(result.resultCode == Activity.RESULT_OK): resultCode가 RESULT_OK인 경우

- result.data?.getStringExtra("id"): result로 받아온 값 중 key값이 "id"인 value

- binding.etId.setText(id): 그 아이디 값을 editText에 입력

        val intent2 = Intent(this,SignUpActivity::class.java)
        binding.btnReg.setOnClickListener{
            startForResult.launch(intent2)
        }


- startForResult.launch(intent2): 위에서 정의한 startForResult를 launch 함수로 시작시킴
<br/><br/><br/>

2. activity_sign_in.xml

- android:inputType="textPassword" : 비밀번호 입력 내용이 가려지게 함

- android:hint="비밀번호를 입력해주세요" : hint 속성으로 미리보기 글씨
<br/><br/><br/>

3. SignUpActivity.kt

        binding.btnReg.setOnClickListener {
            if(binding.etId.length()!=0 && binding.etPw.length()!=0 && binding.etName.length()!=0) {
                val nextIntent = Intent(this,SignInActivity::class.java)
                nextIntent.putExtra("id", binding.etId.text.toString())
                nextIntent.putExtra("pw", binding.etPw.text.toString())
                setResult(RESULT_OK, nextIntent)
                finish()
            }
            else{
                Toast.makeText(this, "입력되지 않은 정보가 있습니다", Toast.LENGTH_SHORT).show()
            }
        }
        
        
- if(binding.etId.length()!=0 && binding.etPw.length()!=0 && binding.etName.length()!=0): 이름, 아이디, 비밀번호 입력이 모두 되어있을 경우의 조건

- finish(): 액티비티 종료/ back 버튼을 누르는 동작과 동일, 스택에서 화면 삭제

- putExtra(): 액티비티 이동과 동시에 어떤 값을 넘기고 싶은 경우에 사용/ key 값과 value 값으로 이동하는 액티비티로 전달됨/ 여기서는 String값인 아이디와 비밀번호를 전달

- setResult(): 인자로 resultCode와 intent 데이터 넣어줌 
<br/><br/><br/>

4. HomeActivity.kt

        binding.git.setOnClickListener {
            val url = "https://github.com/ChoiYuLim"
            val webPage: Uri = Uri.parse(url)
            val intent = Intent(Intent.ACTION_VIEW, webPage)

            // 해당 intent를 성공적으로 수행할 수 있는지 체크
            if (intent.resolveActivity(packageManager) != null) {
                startActivity(intent)
            } else {
                Log.d("ImplicitIntents", "Can't handle this!")
            }
        }


- val intent = Intent(Intent.ACTION_VIEW, webPage:암시적 인텐트

- if (intent.resolveActivity(packageManager) != null): 해당 인텐트를 수행할 수 있는 액티비티가 있는 경우
<br/><br/><br/>

5. activity_home.xml

        <ImageView
        android:id="@+id/imageView"
        android:layout_width="0dp"
        android:layout_height="0dp"
        android:layout_marginTop="20dp"
        android:src="@drawable/lim"
        app:layout_constraintDimensionRatio="1"
        app:layout_constraintEnd_toEndOf="parent"
        app:layout_constraintStart_toStartOf="parent"
        app:layout_constraintTop_toBottomOf="@+id/title"
        app:layout_constraintWidth_default="percent"
        app:layout_constraintWidth_percent="0.35" />

- app:layout_constraintDimensionRatio="1": 사진의 비율을 1:1로 맞춤


        <ScrollView
        android:layout_width="match_parent"
        android:layout_height="0dp"
        app:layout_constraintBottom_toBottomOf="parent"
        android:layout_marginTop="20dp"
        android:layout_marginBottom="20dp"
        app:layout_constraintTop_toBottomOf="@+id/imageView">

        <androidx.constraintlayout.widget.ConstraintLayout
            android:layout_width="match_parent"
            android:layout_height="match_parent">
          
          ...
           
        </androidx.constraintlayout.widget.ConstraintLayout>
        </ScrollView>

- ScrollView를 사용
<br/><br/>

### 명시적 인텐트
인텐트에 클래스 객체나 컴포넌트 이름을 지정하여 호출할 대상을 확실히 알 수 있는 경우에 사용<br/><br/>
### 암시적 인텐트
인텐트의 액션과 데이터를 지정하긴 했지만, 호출할 대상이 달라질 수 있는 경우에 사용<br/>
정보를 처리할 수 있는 적절한 component를 찾아본 다음 사용자에게 그 대상과 처리 결과를 보여주는 과정을 거침<br/>
