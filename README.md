## 7차 과제

<br/>

### 실행 GIF
<img src="https://user-images.githubusercontent.com/52950523/147313298-36e230e1-7519-4dda-8765-cb293fbf9631.gif" width="30%">

<br/><br/>

### 1-1 온보딩<br/>

1. 라이브러리 추가

   ```kotlin
   //NavigationComponent
   implementation 'androidx.navigation:navigation-fragment-ktx:2.3.5'
   implementation 'androidx.navigation:navigation-ui-ktx:2.3.5'
   ```

   <br/>

2. Navigation Graph 만들기

   <br/>

3. activity_on_boarding.xml에 NavHostFragment 지정

   ```kotlin
   <androidx.fragment.app.FragmentContainerView
       android:id="@+id/container_List"
       android:name="androidx.navigation.fragment.NavHostFragment"
       android:layout_width="match_parent"
       android:layout_height="0dp"
       app:defaultNavHost="true"
       app:layout_constraintBottom_toBottomOf="parent"
       app:layout_constraintTop_toBottomOf="@+id/cl_top"
       app:navGraph="@navigation/nav_onboarding" />
   ```

   <br/>

4. OnBoarding1,2,3 Fragment 생성

   <br/>

5. Navigation Graph에서 fragment 교체 작업 작성

   ```kotlin
   <fragment
       android:id="@+id/onboarding1Fragment"
       android:name="kr.co.softcampus.sopt_assignment1.ui.fragment.Onboarding1Fragment"
       android:label="Onboarding1Fragment"
       tools:layout="@layout/fragment_onboarding1">
       <action
           android:id="@+id/action_onboarding1Fragment_to_onboarding2Fragment"
           app:destination="@id/onboarding2Fragment" />
   </fragment>
   ```

   - 온보딩1 fragment에서 온보딩2 fragment로 교체하는 action을 지정

   <br/>

6. findNavController().navigate로 fragment 전환

   ```kotlin
   override fun onCreateView(
       inflater: LayoutInflater, container: ViewGroup?,
       savedInstanceState: Bundle?
   ): View? {
       _binding = FragmentOnboarding1Binding.inflate(layoutInflater,container,false)
       binding.btnNext.setOnClickListener{
     findNavController().navigate(R.id.action_onboarding1Fragment_to_onboarding2Fragment)
       }
       return binding.root
   }
   ```

   - NavController란 NavHost내부에서 Fragment나 Activity의 전환을 담당하는 객체

<br/><br/>

### 1-2 자동 로그인/ 자동 로그인 해제<br/>

- SharedPreferences란?

  간단한 데이터를 key-value 형식으로 저장할 수 있게 해주는 로컬 저장<br/>

 1. 자동로그인을 위한 selector 생성

    ```kotlin
    <?xml version="1.0" encoding="utf-8"?>
    <selector xmlns:android="http://schemas.android.com/apk/res/android">
        <item android:drawable="@drawable/tv_rectangle_pink" android:state_selected="false"/>
        <item android:drawable="@drawable/btn_rectangle" android:state_selected="true"/>
    </selector>
    ```

    - select 되었을 경우와 select 되지 않았을 경우를 지정

    <br/>

 2. SharedPreferences 생성

    ```kotlin
    object SharedPreferences {
        private const val STORAGE_KEY = "USER_AUTH"
        private const val AUTO_LOGIN = "AUTO_LOGIN"
    
        fun getAutoLogin(context: Context): Boolean {
            val preferences = context.getSharedPreferences(STORAGE_KEY, Context.MODE_PRIVATE)
            return preferences.getBoolean(AUTO_LOGIN, false)
        }
    
        fun setAutoLogin(context: Context, value: Boolean) {
            val preferences = context.getSharedPreferences(STORAGE_KEY, Context.MODE_PRIVATE)
            preferences.edit()
                .putBoolean(AUTO_LOGIN, value)
                .apply()
        }
    
        fun removeAutoLogin(context: Context){
            val preferences = context.getSharedPreferences(STORAGE_KEY, Context.MODE_PRIVATE)
            preferences.edit()
                .remove(AUTO_LOGIN)
                .apply()
        }
    
        fun clearStorage(context: Context) {
            val preferences = context.getSharedPreferences(STORAGE_KEY, Context.MODE_PRIVATE)
            preferences.edit()
                .clear()
                .apply()
        }
    }
    ```

    - 앱 전역에서 계속해서 호출되므로 object 싱글톤으로 만들어줌
    - 파일을 작성할 때 edit() 메소드를 호출함
    - 작성이 끝나면 commit() 또는 apply() 호출

    <br/>

 3. 자동로그인 로직

    ```kotlin
    private fun initClickEvent() {
        binding.ibCheck.setOnClickListener {
            binding.ibCheck.isSelected = !binding.ibCheck.isSelected
            SharedPreferences.setAutoLogin(this, binding.ibCheck.isSelected)
        }
    }
    
    private fun isAutoLogin(){
        if(SharedPreferences.getAutoLogin(this)){
            Toast.makeText(this@SignInActivity, "자동로그인 되었습니다.", Toast.LENGTH_SHORT).show()
            startActivity(Intent(this@SignInActivity, HomeActivity::class.java))
            finish()
        }
    }
    ```

    - isAutoLogin에서 SharedPreferences에 true 값이 저장되어있다면 AutoLogin이 되게, 아니면 로그인화면이 뜨게 만듦
    - initClickEvent에서 자동 로그인을 클릭했을 때 SharedPreferences에 true값을 저장

    <br/>

 4. 자동로그인 해제

    ```kotlin
    private fun initcheckbox() {
        if (SharedPreferences.getAutoLogin(this)) {
            binding.checkbox.isChecked = false
        } else binding.checkbox.isChecked = true
    }
    
    private fun checkboxClickEvent() {
        binding.checkbox.setOnClickListener {
            Toast.makeText(this, "자동 로그인 해제", Toast.LENGTH_SHORT).show()
            SharedPreferences.removeAutoLogin(this)
        }
    }
    ```

    - initcheckbox에서 SharedPreferences에 true가 저장되어있으면 checkbox에 check 해제, false가 저장되어있으면 checkbox에 check 설정
    - checkboxClickEvent에서 checkbox를 눌렀을 때 "자동 로그인 해제"라는 toast 메시지 띄우기
    - `removeAutoLogin(this)`로 자동로그인 해제

<br/>

<br/>

### 1-3 패키징

![image](https://user-images.githubusercontent.com/52950523/147313173-6cd113df-6568-4124-9f66-636263954511.png)


 1. 크게 data, ui, util로 나눴다.

    - data
      - local - 로컬 데이터 로직과 관련된 클래스들을 모아둠
      - remote - 리모트 데이터 로직과 관련된 클래스들을 모아둠
    - ui
      - activity - activity 파일을 쉽게 구분할 수 있게 모아둠
      - adapter - adapter 파일을 쉽게 구분할 수 있게 모아둠
      - fragment - fragment 파일을 쉽게 구분할 수 있게 모아둠
    - util
      - Util 클래스나, 확장 함수 등을 모아둠

<br/>

 2. **Utility Class**

    프로젝트 진행 시 여러 클래스에서 공통적으로 사용되는 method가 있을 수 있다.<br/>

    이 때, 관련된 method 끼리 모아서 클래스로 만드는 경우에 중복된 코드가 발생하지 않고 효율적으로 관리할 수 있다. <br/>이를 Utility Class라고 한다.<br/>

    - NestedScrollableHost
    - SharedPreferences

    이 두 가지를 Util 클래스로 구분했다. 

<br/>

<br/>

### 배운 점

- SharedPreferences의 사용법 <br/>
- 패키징 방식<br/>
- 온보딩 화면 구성 시 NavigationComponent를 사용법
