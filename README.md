# 4차 과제

<br/>

### 실행 GIF

<img src="https://user-images.githubusercontent.com/52950523/141350754-e656733b-7426-4819-abbc-bcb060f017e8.gif" width="30%">
<br/><br/>


### 💖POSTMAN 테스트<br/><br/>

#### 1. 회원가입 완료

![image-20211112021412748](https://user-images.githubusercontent.com/52950523/141350921-ef2e127f-32aa-464a-bda0-2dc7de860e11.png)

 - 요청 헤더 넣어주기<br/><br/>
   ![image-20211112021209195](https://user-images.githubusercontent.com/52950523/141351067-3ba2a94f-e60f-44ba-ad8c-0dbb3c5ad45c.png)
- Body값 넣어주기<br/><br/>

> ##### 응답
![image-20211110234858004](https://user-images.githubusercontent.com/52950523/141351091-70bfb0c9-7b90-4585-bc85-9c1f23377404.png)<br/>
<br/>

#### 2. email로 user 찾기

![image-20211110234951783](https://user-images.githubusercontent.com/52950523/141351182-1e21b73f-e364-4c34-ad08-29065e461d6b.png)<br/>

- 요청 헤더 넣어주기 <br/>
- GET은 Body값을 갖지 않음!<br/><br/>

> ##### 응답
![image-20211110235038975](https://user-images.githubusercontent.com/52950523/141351212-040d3fd6-b300-4387-b75b-4743bb6e45c6.png)<br/>

<br/><br/>

#### 3. 로그인 완료

![image](https://user-images.githubusercontent.com/52950523/141411910-d2f03500-beeb-459c-aef1-cfff800e3e5d.png)<br/>
![image](https://user-images.githubusercontent.com/52950523/141411947-507a20af-db3b-4136-ad1c-41aaf404c25b.png)<br/><br/>
> ##### 응답
![image](https://user-images.githubusercontent.com/52950523/141411965-4e6c2a0d-3298-4ce7-9049-07b04f231b0c.png)
<br/><br/>

   

### 💖설정<br/>

1. build.gradle

```kotlin
//서버 연결을 위한 Retrofit2
implementation "com.squareup.retrofit2:retrofit:2.9.0"
//Retrofit2에서 gson 사용을 위한 컨버터
implementation "com.squareup.retrofit2:converter-gson:2.9.0"
//gson
implementation 'com.google.code.gson:gson:2.8.6'
```

- json이란❓: Key-Value 형태로 구성되어진 데이터 타입이며 사람이 읽을 수 있도록 텍스트 타입으로 지원되는 데이터 오브젝트<br/>
- gson이란❓: Java에서 Json을 파싱하고 생성하기 위해 사용되는 구글에서 개발한 오픈소스<br/>

- retrofit2는 HTTP API를 자바 인터페이스 형태로 사용하는 라이브러리<br/><br/>

2. AndroidManifest<br/>
   ![image-20211112033133097](https://user-images.githubusercontent.com/52950523/141351454-63140e72-dc95-4cab-b793-f560e2ef6d79.png)

  <br/>  <br/>

### 💖로그인 서버 통신 구현<br/>

1. RequestSigninData.kt

   ```kotlin
   data class RequestSigninData(
      @SerializedName("email") //"email"을 여기서는 id로 사용하겠다!
      val id: String,
      val password: String
   )
   ```

 - request시 필요한 id, password

   <br/>

2. ResponseSigninData.kt

   ```kotlin
   data class ResponseSigninData( //생성자 소괄호
       val status: Int,
       val success: Boolean,
       val message: String,
       val data: Data
   ) {
       data class Data(
           val id: Int,
           val name: String,
           val email: String
       )
   }
   ```

 - response 받을 때 필요한 status, success, message, Data(id, name, email)

   <br/>

3. SigninService.kt

   ```kotlin
   interface SigninService {
   
       @Headers("Content-Type: application/json")
       @POST("user/login")
       fun postLogin(
           @Body body: RequestSigninData
       ): Call<ResponseSigninData>
   }
   ```

   - 요청 헤더는 @Headers 사용<br/>
   - @POST 안에 base url 뒤의 경로 적어주기 (주의🧨: @POST("/user/login")으로 하면 안됨  맨 앞에 / 빼기)<br/>
   - fun postLogin(~): Call<ResponseSigninData>의 의미는 postLogin 함수는 ResponseSigninData.kt에서 정의한 status, success, message, Data(id, name, email)로 반환함<br/><br/>

4. ServiceCreator.kt

   ```kotlin
   object ServiceCreator {
       private const val BASE_URL = "https://asia-northeast3-we-sopt-29.cloudfunctions.net/api/"
       private val retrofit: Retrofit = Retrofit
           .Builder() //생성자 호출
           .baseUrl(BASE_URL) 
           .addConverterFactory(GsonConverterFactory.create()) //gson 컨버터 연동
           .build() //Retrofit 객체 반환
   
       //인터페이스 객체를 create에 넘겨 실제 구현체 생성
       val signinService: SigninService = retrofit.create(SigninService::class.java)
       val signupService: SignupService = retrofit.create(SignupService::class.java)
       val getUserByEmailService: GetUserByEmailService =
           retrofit.create(GetUserByEmailService::class.java)
   }
   ```

- 서버 호출이 필요할 때마다 Retrofit 객체를 만들어야한다면 너무 비효율적이기 때문에 Retrofit 객체의 경우 싱글톤(Object)으로 제작하는 것이 좋다!<br/>
- 싱글톤이란❓: 최초 한번만 메모리를 할당하고(Static) 그 메모리에 인스턴스를 만들어 사용하는 디자인패턴 (앱 통틀어서 하나만 생성되는 객체!)<br/><br/>

5. SignInActivity.kt

   ```kotlin
   private fun initNetwork() {
       val requestSigninData = RequestSigninData(
           id = binding.etId.text.toString(),
           password = binding.etPw.text.toString()
       )
       
   	//Call 객체를 받아온다
       val call: Call<ResponseSigninData> =
           ServiceCreator.signinService.postLogin(requestSigninData)
       
       //call 객체에 enqueue를 호출하여 실제 서버통신을 비동기적으로 요청
       call.enqueue(object : Callback<ResponseSigninData> { 
           override fun onResponse(
               call: Call<ResponseSigninData>,
               response: Response<ResponseSigninData>
           ) {
               if (response.isSuccessful) { 
                   //Status code가 200~300 사이일 때 true
                   successLogin(response.body()?.data?.name)
               } else {
                   Toast.makeText(this@SignInActivity, R.string.fail_login, Toast.LENGTH_LONG)
                       .show()
               }
           }
   
           override fun onFailure(call: Call<ResponseSigninData>, t: Throwable) {
               //에러 처리
               Toast.makeText(this@SignInActivity, "ERROR", Toast.LENGTH_LONG).show()
               Log.e("NetworkTest", "error:$t")
           }
       })
   }
   ```

 - Call<Type> : 동기적 또는 비동기적으로 Type을 받아오는 객체<br/>

 - Callbaxk<Type>: Type 객체를 받아왔을 때 프로그래머의 행동<br/>

 - response.body()는 null값(ex. 에러)이 올 수도 있기 떄문에 nullable한 타입 <br/>

 - Call 객체의 비동기 작업 이후 작업이 끝날 때 할 행동을 Callback 객체로 표현!!<br/>

   <br/>

### 💖회원가입 서버 통신 구현<br/>

1. RequestSignupData.kt

   ```kotlin
   data class RequestSignupData(
       @SerializedName("email")
       val id: String,
       val name: String,
       val password: String
   )
   ```

<br/>

2. ResponseSignupData.kt

   ```kotlin
   data class ResponseSignupData(
       val status: Int,
       val success: Boolean,
       val message: String,
       val data: Data
   ) {
       data class Data(
           val id: Int,
           val name: String,
           val email: String
       )
   }
   ```

<br/>

3. SignupService.kt

   ```kotlin
   interface SignupService {
       @Headers("Content-Type: application/json")
       @POST("user/signup")
       fun postSignup(
           @Body body: RequestSignupData
       ): Call<ResponseSignupData>
   }
   ```

<br/>

4. ServiceCreator.kt

   ```kotlin
   val signupService: SignupService = retrofit.create(SignupService::class.java)
   ```

<br/>

5. SignUpActivity.kt

   ```kotlin
   private fun initNetwork() {
       val requestSignupData = RequestSignupData(
           id = binding.etId.text.toString(),
           name = binding.etName.text.toString(),
           password = binding.etPw.text.toString()
       )
   
       val call: Call<ResponseSignupData> =
           ServiceCreator.signupService.postSignup(requestSignupData)
   
       call.enqueue(object : Callback<ResponseSignupData> {
           override fun onResponse(
               call: Call<ResponseSignupData>,
               response: Response<ResponseSignupData>
           ) {
               if (response.isSuccessful) {
                   val Intent_SignIn = Intent(this@SignUpActivity, SignInActivity::class.java)
                   Intent_SignIn.putExtra("id", binding.etId.text.toString())
                   Intent_SignIn.putExtra("pw", binding.etPw.text.toString())
                   Toast.makeText(
                       this@SignUpActivity,
                       "${response.body()?.data?.name}님 회원가입 완료",
                       Toast.LENGTH_LONG
                   ).show()
                   setResult(RESULT_OK, Intent_SignIn)
                   finish()
               } else {
                   Toast.makeText(this@SignUpActivity, "회원가입 실패", Toast.LENGTH_LONG).show()
               }
           }
   
           override fun onFailure(call: Call<ResponseSignupData>, t: Throwable) {
               //에러 처리
               Toast.makeText(this@SignUpActivity, "ERROR", Toast.LENGTH_LONG).show()
               Log.e("NetworkTest", "error:$t")
           }
       })
   }
   ```

   <br/><br/>

### 💖회원 이메일로 찾기 서버 통신 구현<br/>

- GET이라서 requestData는 따로 필요 없음<br/>

1. ResponseGetUserByEmailData.kt

   ```kotlin
   data class ResponseGetUserByEmailData(
       val status: Int,
       val sucess: Boolean,
       val message: String,
       val data: Data
   ) {
       data class Data(
           val id: Int,
           val name: String,
           val email: String
       )
   }
   ```

<br/>

2. GetUserByEmailService.kt

   ```kotlin
   interface GetUserByEmailService {
       @Headers("Content-Type: application/json")
       @GET("user")
       fun getgetUserByEmail(@Query("email") email: String): Call<ResponseGetUserByEmailData>
   }
   ```

 - GET은 Body값을 가지지 않음<br/>

 - GET은 URL에 데이터를 모두 담아서 전송<br/>

 - 요청 헤더 @Headers는 똑같이 넣어줘야함<br/>

   <br/>

3. ServiceCreator.kt

   ```kotlin
   val getUserByEmailService: GetUserByEmailService =
           retrofit.create(GetUserByEmailService::class.java)
   ```

<br/>

4. FindUserActivity.kt

   ```kotlin
   private fun initNetwork() {
   
           val call: Call<ResponseGetUserByEmailData> =
               ServiceCreator.getUserByEmailService.getgetUserByEmail(binding.etUseremail.text.toString())
   
           call.enqueue(object : Callback<ResponseGetUserByEmailData> {
               override fun onResponse(
                   call: Call<ResponseGetUserByEmailData>,
                   response: Response<ResponseGetUserByEmailData>
               ) {
                   if (response.isSuccessful) {
                       Log.d(
                           "success",
                           response.body()?.message + response.body()?.data?.email + response.body()?.data?.name
                       )
                       binding.tvResult.setText(response.body()?.message + "\n\nemail: " + response.body()?.data?.email + "\n\nname: " + response.body()?.data?.name)
                   } else {
                       Log.d("fail", response.body()?.message.toString())
                       binding.tvResult.setText("존재하지 않는 회원입니다.")
                   }
               }
   
               override fun onFailure(call: Call<ResponseGetUserByEmailData>, t: Throwable) {
                   //에러 처리
                   Toast.makeText(this@FindUserActivity, "ERROR", Toast.LENGTH_LONG).show()
                   Log.e("NetworkTest", "error:$t")
               }
           })
       }
   ```

   <br/><br/>

### 고찰📑

- ![image-20211112022535798](https://user-images.githubusercontent.com/52950523/141351502-e080b9cb-bd0e-42da-bda2-9c73d20f76b4.png)

  @POST("user/login")을 @POST("/user/login")으로 치고 @Body body 부분 오타 때문에 거의 2시간 정도 잡아먹은 것 같다,, 오타였다니 <br/><br/>

- user 이메일로 찾기 구현할 때 오류 때문에 거의 4-5시간?? 정말 많이 고생했다ㅠㅠ

  <br/>![image-20211112022943576](https://user-images.githubusercontent.com/52950523/141352198-2aacf78e-1ea8-4d49-825b-61b1c957bc91.png)

  <br/>@GET을 사용할 때 지금 여기는 /user?email=email 경로가 이거여서 계속 @GET("user?email={email}")로 작성을 했었다.<br/>

  계속 아래와 같은 오류가 떴다,, (진짜 5시간 동안 이 오류 봐서 질림)

  ![image-20211112024203540](https://user-images.githubusercontent.com/52950523/141351596-584d23d1-fcd7-48b3-b00a-a648b8d36471.png)

  > 해결방법<br/>

  참고자료) https://gwi02379.tistory.com/3 <br/>
  ![image-20211112022913688](https://user-images.githubusercontent.com/52950523/141351633-d729b76a-7890-485c-9884-11f751e98a96.png)

  <br/>여기서 쿼리 부분인 ?email= 이 부분을 넣으면 안된다는 것을 발견해서 @GET("user")로 변경했다.

  <br/>

  그리고 @Path를 사용할지 @Query를 사용할지 헷갈렸는데 ?email=email 이 부분이 쿼리문이므로 @Query를 사용했다. <br/>

  실제로 @Path를 사용하면 아래의 오류가 난다.<br/>
  ![image-20211112025006454](https://user-images.githubusercontent.com/52950523/141351671-02a4a166-c289-4627-a81a-64753a6fac11.png)
  <br/>

  그리고 FindUserActivity.kt에서 이렇게 사용한다.<br/><br/>
  ![image-20211112023005208](https://user-images.githubusercontent.com/52950523/141351690-79ae9aa8-06b2-4a69-a800-de71fe48c612.png)<br/><br/>
  GET은 body값이 없기 때문에 RequestGetUserByEmailData.kt는 필요 없이 바로 binding.etUseremail.text.toString()으로 받은 값을 인자로 준다.<br/>

  그럼 쿼리문에 email = *email*  ←이 부분에 들어가게 된다.<br/><br/>

- 한 가지 아직 이해가 안되는 부분 🙄 <br/>

  ```kotlin
  val call: Call<ResponseGetUserByEmailData> =
      ServiceCreator.getUserByEmailService.getgetUserByEmail(binding.etUseremail.text.toString())
  
  call.enqueue(object : Callback<ResponseGetUserByEmailData> {
      override fun onResponse(
          call: Call<ResponseGetUserByEmailData>,
          response: Response<ResponseGetUserByEmailData>
      ) {
          if (response.isSuccessful) {
              Log.d(
                  "success",
                  response.body()?.message + response.body()?.data?.email + response.body()?.data?.name
              )
              binding.tvResult.setText(response.body()?.message + "\n\nemail: " + response.body()?.data?.email + "\n\nname: " + response.body()?.data?.name)
          } else {
              Log.d("fail", response.body()?.message.toString())
              //binding.tvResult.setText(response.body()?.message + response.body()?.status + response.body()) //response 자체가 null값임
              binding.tvResult.setText("존재하지 않는 회원입니다.")
          }
      }
  
      override fun onFailure(call: Call<ResponseGetUserByEmailData>, t: Throwable) {
          //에러 처리
          Toast.makeText(this@FindUserActivity, "ERROR", Toast.LENGTH_LONG).show()
          Log.e("NetworkTest", "error:$t")
      }
  })
  ```

  여기서 주석 처리 한 부분!  만약에 존재하지 않는 회원일 때는response.body().message가 "존재하지 않는 회원입니다."라고 떠야되는데 <br/>

  response.body() 자체 부터 null값이고, 그 이후 message, status 모두 null값이 나온다.<br/>

  *왜 안되지?*<br/>

![image-20211112025841270](https://user-images.githubusercontent.com/52950523/141351970-b5899f19-ba53-4c6f-ac8e-dc44f49ad8fd.png)
