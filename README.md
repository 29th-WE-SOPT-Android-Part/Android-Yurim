## 2차 과제

### 실행 GIF
<img src="https://user-images.githubusercontent.com/52950523/137860770-9138a554-04ef-46f3-8e20-01120cdc962a.gif" width="30%">
<hr/>

### Logic 설명 (Fragment 만들기)<br/><br/>

HomeActivity 하단에 버튼을 눌렀을 때 Fragment가 전환되게 만들기<br/><br/>
1. FollowerFragment.kt

        class FollowerFragment : Fragment() {
            private var _binding : FragmentFollowerBinding? = null
            private val binding get() = _binding ?: error("Binding이 초기화 되지 않았습니다.")

            override fun onCreateView(
                inflater: LayoutInflater, container: ViewGroup?,
                savedInstanceState: Bundle?
            ): View {
                _binding = FragmentFollowerBinding.inflate(layoutInflater, container,false)

                return binding.root
            }

            override fun onDestroyView() {
                super.onDestroyView()
                _binding= null
            }
        }
- Kotlin property를 활용해서 binding 변수의 getter 정의<br/>
- onDestroyView()에서 binding 객체 참조를 해제한다.<br/>
- RepositoryFragment.kt도 동일하게 만들어준다. <br/><br/>

2. HomeActivity.kt<br/><br/>

        private var position = FOLLOWER_POSITION

        private fun initTransactionEvent(){
            val followerFragment = FollowerFragment()
            val repositoryFragment = RepositoryFragment()

            supportFragmentManager.beginTransaction().add(R.id.container_List, followerFragment).commit()

            binding.btnRepository.setOnClickListener {
                val transaction = supportFragmentManager.beginTransaction()

                when (position){
                    FOLLOWER_POSITION -> {
                        transaction.replace(R.id.container_List,repositoryFragment)
                        position = REPOSITORY_POSITION
                    }
                }
                transaction.commit()
            }

            binding.btnFollower.setOnClickListener{
                val transaction = supportFragmentManager.beginTransaction()

                when (position){
                    REPOSITORY_POSITION -> {
                        transaction.replace(R.id.container_List, followerFragment)
                        position = FOLLOWER_POSITION
                    }
                }
                transaction.commit()
            }
        }

        companion object {
            const val FOLLOWER_POSITION = 1
            const val REPOSITORY_POSITION = 2
        }
- 가독성을 위해 companion object로 상수값을 선언해준다.<br/>
- 버튼을 눌렀을 때 트랜잭션을 교체하고 position을 바꿔준다.<br/>
- 마지막에 transaction.commt()을 통해 작업을 수행한다.
<br/><br/>

3. activity_home.xml

        <androidx.fragment.app.FragmentContainerView
            android:id="@+id/container_List"
            android:layout_width="match_parent"
            android:layout_height="0dp"
            android:layout_marginTop="3dp"
            android:layout_marginBottom="5dp"
            app:layout_constraintBottom_toBottomOf="parent"
            app:layout_constraintTop_toBottomOf="@+id/btn_follower"/>
        
- FragmentContainerView를 통해 Activity 내에서 Fragment를 띄울 수 있다.
<br/>
<hr/>

### Logic 설명 (RecyclerView 만들기)<br/>

순서<br/>
1. follower_list.xml 만들기<br/>
2. FollowerData.kt 만들기<br/>
3. ViewHolder와 Adapter 만들기 (FollowerAdapter.kt)<br/><br/>

        class FollowerAdapter : RecyclerView.Adapter<FollowerAdapter.FollowerViewHolder>() {

            val followerList = mutableListOf<FollowerData>()

            override fun onCreateViewHolder(
                parent: ViewGroup,
                viewType: Int
            ): FollowerViewHolder { //ViewHolder 객체 반환
                val binding = FollowerListBinding.inflate(
                    LayoutInflater.from(parent.context), //LayoutInflater.from을 통해 LayoutInflater를 생성
                    parent,false
                )
                return FollowerViewHolder(binding)
            }

            override fun onBindViewHolder(holder: FollowerViewHolder, position: Int) {
                holder.onBind(followerList[position]) //ViewHolder의 onBind함수를 호출해서 데이터를 넘겨줌
            }

            override fun getItemCount(): Int = followerList.size

            class FollowerViewHolder(private val binding: FollowerListBinding)
                : RecyclerView.ViewHolder(binding.root) { 
                fun onBind(data : FollowerData) {
                    binding.tvName.text = data.name
                    binding.tvIntro.text = data.introduction
                }
            }
        }
    <br/>
    *FollowerViewHolder 코드 분석:<br/>
    Adapter로부터 전달받은 데이터를 붙여주는 함수로, onBindViewHoler 호출 시 실행된다.<br/>

    *Adapter 코드 분석: 아래의 세 가지의 함수를 갖고 있어야함 <br/>

    - onCreateViewHolder 코드 분석:<br/>
     ViewHolder를 생성하고 ItemLayout의 Binding 객체를 만들어 ViewHolder의 생성자로 넘겨주는 함수<br/><br/>

    - onBindViewHolder 코드 분석:<br/>
    재활용되는 뷰를 호출하여 실행되는 함수, ViewHolder와 position의 데이터를 결합시키는 역할<br/><br/>

    - getItemCount() 코드 분석:<br/>
    Recyclerview로 보여줄 전체 데이터의 개수 반환
<br/><br/>
4. RecyclerView 배치하기 (fragment_follower.xml)<br/>

        <androidx.recyclerview.widget.RecyclerView
        android:id="@+id/rv_follower"
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        app:layoutManager="androidx.recyclerview.widget.LinearLayoutManager"
        tools:itemCount="4"
        tools:listitem="@layout/follower_list" />

    - follower리스트는 LinearLayoutManager로 선형적으로 item을 보여준다.<br/><br/>

5. RecyclerView에 Adapter 연결하기 (FollowerFragment.kt)<br/><br/>

        private lateinit var followerAdapter : FollowerAdapter 

        private fun initAdapter() {

            followerAdapter = FollowerAdapter()
            binding.rvFollower.adapter = followerAdapter
            followerAdapter.followerList.addAll(
                listOf(
                    FollowerData("최유림", "안드로이드"),
                    FollowerData("박정훈", "안드로이드"),
                    FollowerData("이준호", "IOS"),
                    FollowerData("김인우", "기획"),
                    FollowerData("박민우", "안드로이드"),
                    FollowerData("김우영", "서버")
                )
            )
            followerAdapter.notifyDataSetChanged()
        }
        
    - 어댑터를 초기화하고 binding.rvFollower.adapter = followerAdapter 이 코드로 Adapter와 RecyclerView를 연동시킨다.<br/>
    - 어댑터에 리스트로 보여줄 데이터를 넣고 followerAdapter.notifyDataSetChanged() 이 코드로 어댑터에 전체 리스트의 데이터가 갱신되었다고 알려준다.<br/><br/>

위의 순서를 한번 더 반복해서 Repository의 RecyclerView도 똑같은 방법으로 만들어준다.<br/>
다른 점 한 가지는 RecyclerView 배치하기 (fragment_repository.xml)

    <androidx.recyclerview.widget.RecyclerView
    android:id="@+id/rv_repository"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:layout_marginStart="10dp"
    android:layout_marginEnd="10dp"
    app:layoutManager="androidx.recyclerview.widget.GridLayoutManager"
    app:layout_constraintEnd_toEndOf="parent"
    app:layout_constraintStart_toStartOf="parent"
    app:spanCount="2"
    tools:itemCount="4"
    tools:listitem="@layout/repository_list" />

- GridLayoutManager로 격자식으로 item을 보여준다. app:spanCount="2" 로 가로로 2칸 보여주게 설정했다.<br/><br/>
<hr>

### 고찰 <br/>
1. repository_list.xml

        <TextView
        android:id="@+id/tv_subtitle"
        android:layout_width="0dp"
        android:layout_height="wrap_content"
        android:layout_marginTop="10dp"
        android:layout_marginBottom="10dp"
        android:ellipsize="end"
        android:maxLines="1"
        android:textAlignment="center"
        android:textSize="15sp"
        app:layout_constraintBottom_toBottomOf="parent"
        app:layout_constraintEnd_toEndOf="@+id/tv_title"
        app:layout_constraintStart_toStartOf="@+id/tv_title"
        app:layout_constraintTop_toBottomOf="@+id/tv_title"
        tools:text="subtitle" />
    - ellipsize 사용법:<br/>- ellipsize end로 제한된 글자 수를 넘어가면 끝에 ... 표시<br/>- maxLines로 최대 줄 수 지정 가능하다. <br/><br/>

2. border_pink.xml

        <shape
        xmlns:android="http://schemas.android.com/apk/res/android"
        android:shape="rectangle">

            <stroke
            android:width="3dp"
            android:color="#FF3399" />
            <corners android:radius="15dp"/>

        </shape>
        
    - android:background="@drawable/border_pink" 이 속성으로 테두리 설정 가능하다.
