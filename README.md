## 3차 과제

<br/>

### 실행 GIF

<img src="https://user-images.githubusercontent.com/52950523/139587355-bd123aca-85b0-405f-98ca-9d9e40da8d61.gif" width="30%">
<img src="https://user-images.githubusercontent.com/52950523/147350190-b8d12b7f-e038-4989-8774-ec284adfccfa.gif" width="30%">
<br/><br/>



### Logic 설명<br/><br/>

#### Lv1 필수과제

1. HomeFragment.kt

       private fun initImage(){
       Glide.with(this)
           .load(R.drawable.img_github)
           .circleCrop()
           .into(binding.ivPhoto)
       }

- 코드에서 Glide 이용해서 동그란 이미지 만들기


      private fun initAdapter(){
      val fragmentList = listOf(TabFollowingFragment(), TabFollowerFragment())
      
      tabViewPagerAdapter = TabViewPagerAdapter(this)
      tabViewPagerAdapter.fragments.addAll(fragmentList)
      
      binding.vpFollow.adapter = tabViewPagerAdapter
      }

- ViewPager2와 Adapter 연동


        private fun initTabLayout(){
        val tabLabel = listOf("팔로잉","팔로워")
    
        TabLayoutMediator(binding.tlHome, binding.vpFollow) {tab, position ->
          tab.text = tabLabel[position]
        }.attach()
    
        }


- ViewPager2와 TabLayout 연동
  <br/><br/><br/>

2. HomeViewPagerAdapter.kt

       class HomeViewPagerAdapter (fragmentActivity: FragmentActivity) :
       FragmentStateAdapter(fragmentActivity){
       val fragments = mutableListOf<Fragment>()

 	override fun getItemCount(): Int = fragments.size
 	
 		override fun createFragment(position: Int): Fragment = fragments[position]
 	     }


​        

- FragmentStateAdapter를 상속받는 ViewPager2 어댑터
  <br/><br/><br/>

3. TabViewPagerAdapter.kt

       class TabViewPagerAdapter(fragment: Fragment) :
       FragmentStateAdapter(fragment){
       	val fragments = mutableListOf<Fragment>()
       
       override fun createFragment(position: Int): Fragment = fragments[position]
       
       override fun getItemCount(): Int = fragments.size
        }

- FragmentStateAdapter를 상속받는 ViewPager2 어댑터
  <br/><br/><br/>

4. selector_list_btn.xml


```kotlin
    <?xml version="1.0" encoding="utf-8"?>
    <selector xmlns:android="http://schemas.android.com/apk/res/android">
<item android:state_selected="false">
	<shape android:shape="rectangle">
		<corners android:radius="5dp"/>
		<solid android:color="#F2F2F2"/>
	</shape>
	</item>
	<item android:state_selected="true">
    		<shape android:shape="rectangle">
        			<corners android:radius="5dp"/>
        			<solid android:color="#FAB24C"/>
    		</shape>
	</item>
     </selector>
```

- isSelected= true인 경우와 isSelected= false인 경우 두 가지에 적용되는 스타일이 다를 경우 selector를 이용한다.
  <br/><br/>

#### Lv2 도전과제

- TabViewPagerAdapter.kt


```kotlin
class TabViewPagerAdapter(fragment: Fragment) :
      FragmentStateAdapter(fragment) {
      val fragments = mutableListOf<Fragment>()
  
      override fun createFragment(position: Int): Fragment = fragments[position]
  
      override fun getItemCount(): Int = fragments.size
  
  }
```

 <br/>

- NestedScrollableHost.kt

```kotlin
class NestedScrollableHost : FrameLayout {
    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)

    private var touchSlop = 0
    private var initialX = 0f
    private var initialY = 0f
    private val parentViewPager: ViewPager2?
        get() {
            var v: View? = parent as? View
            while (v != null && v !is ViewPager2) {
                v = v.parent as? View
            }
            return v as? ViewPager2
        }

    private val child: View? get() = if (childCount > 0) getChildAt(0) else null

    init {
        touchSlop = ViewConfiguration.get(context).scaledTouchSlop
    }

    private fun canChildScroll(orientation: Int, delta: Float): Boolean {
        val direction = -delta.sign.toInt()
        return when (orientation) {
            0 -> child?.canScrollHorizontally(direction) ?: false
            1 -> child?.canScrollVertically(direction) ?: false
            else -> throw IllegalArgumentException()
        }
    }

    //intercept 여부 판단
    //true일 때는 더 이상 자식뷰에게 터치 이벤트 전달하지 않음
    override fun onInterceptTouchEvent(e: MotionEvent): Boolean {
        handleInterceptTouchEvent(e)
        return super.onInterceptTouchEvent(e)
    }

    private fun handleInterceptTouchEvent(e: MotionEvent) {
        val orientation = parentViewPager?.orientation ?: return

        // child가 부모와 같은 방향으로 스크롤 할 수 없다면 조기 반환
        if (!canChildScroll(orientation, -1f) && !canChildScroll(orientation, 1f)) {
            return
        }

        if (e.action == MotionEvent.ACTION_DOWN) {
            initialX = e.x
            initialY = e.y
            parent.requestDisallowInterceptTouchEvent(true)
        } else if (e.action == MotionEvent.ACTION_MOVE) {
            val dx = e.x - initialX
            val dy = e.y - initialY
            val isVpHorizontal = orientation == ORIENTATION_HORIZONTAL

            // viewPager2 touch-slop이 child의 2배라고 가정
            // touch-slop이란 touch시 출렁거림의 정도
            val scaledDx = dx.absoluteValue * if (isVpHorizontal) .5f else 1f
            val scaledDy = dy.absoluteValue * if (isVpHorizontal) 1f else .5f

            if (scaledDx > touchSlop || scaledDy > touchSlop) { //touch로 판단이 될 경우
                if (isVpHorizontal == (scaledDy > scaledDx)) {
                    // 제스처가 수직, 모든 부모가 intercept 가능
                    parent.requestDisallowInterceptTouchEvent(false)
                } else {
                    // 제스처가 병렬, 해당 방향으로 이동할 수 있는 경우 하위 항목을 쿼리함
                    if (canChildScroll(orientation, if (isVpHorizontal) dx else dy)) {
                        // 스크롤 가능, 모든 부모가 intercept 불가능
                        parent.requestDisallowInterceptTouchEvent(true)
                    } else {
                        // 스크롤 불가능, 모든 부모가 intercept 가능
                        parent.requestDisallowInterceptTouchEvent(false)
                    }
                }
            }
        }
    }
}
```

<br/>

- HomeViewPagerAdapter.kt

  ```kotlin
  class HomeViewPagerAdapter(fragmentActivity: FragmentActivity) :
      FragmentStateAdapter(fragmentActivity) {
      val fragments = mutableListOf<Fragment>()
  
      override fun getItemCount(): Int = fragments.size
  
      override fun createFragment(position: Int): Fragment = fragments[position]
  }
  ```

<br/>

- BindingAdapter.kt

```kotlin
object BindingAdapter {
    @JvmStatic
    @BindingAdapter("imgUrl")
    fun setImage (imageview: ImageView, url: String){
        Glide.with(imageview.context)
            .load(url)
            .circleCrop()
            .into(imageview)
    }
}
```

<br/>

- follower_item.xml

```kotlin
<data>
    <variable
        name="followerlist"
        type="kr.co.softcampus.sopt_assignment1.FollowerData" />
</data>
```

<br/>



#### Lv3 심화과제

- FollowerAdapter

  ```kotlin
  class FollowerAdapter :
      RecyclerView.Adapter<FollowerAdapter.FollowerViewHolder>() { //<>안에 Adapter가 데이터를 전달할 ViewHolder 클래스 작성
  
      var followerList = mutableListOf<FollowerData>()
  
      /*ViewHolder를 생성하고 ItemLayout의 Binding 객체를 만들어 ViewHolder의 생성자로 넘겨주는 함수*/
      override fun onCreateViewHolder(
          parent: ViewGroup,
          viewType: Int
      ): FollowerViewHolder { //ViewHolder 객체 반환
          val binding = FollowerListBinding.inflate(
              LayoutInflater.from(parent.context), //LayoutInflater.from을 통해 LayoutInflater를 생성
              parent, false
          )
          return FollowerViewHolder(binding)
      }
  
      /*재활용되는 뷰를 호출하여 실행되는 함수, ViewHolder와 position의 데이터를 결합시키는 역할*/
      override fun onBindViewHolder(holder: FollowerViewHolder, position: Int) {
          holder.onBind(followerList[position]) //ViewHolder의 onBind함수를 호출해서 데이터를 넘겨줌
      }
  
      override fun getItemCount(): Int = followerList.size
  
      /* ViewHolder가 가진 View에 Adapter로부터 전달받은 데이터를 붙여주는 함수, onBindViewHolder 호출 시 실행됨 */
      class FollowerViewHolder(private val binding: FollowerListBinding) :
          RecyclerView.ViewHolder(binding.root) { //RecyclerView.ViewHolder 클래스 상속
          fun onBind(data: FollowerData) {
              binding.followerlist = data
          }
      }
  }
  ```

<br/>

- ImageFragment.kt

```kotlin
class ImageFragment : Fragment() {
    private var _binding: FragmentImageBinding? = null
    private val binding get() = _binding!!

    //onCreateView()의 반환값으로 정상적인 Fragment View 객체를 제공했을 때만 Fragment View의 Lifecycle 생성
    //layout inflate
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentImageBinding.inflate(inflater, container, false)
        return binding.root
    }

    //onCreateView()를 통해 반환된 View 객체는 onViewCreated()의 파라미터로 전달됨
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        openGallery()
    }

    //버튼 눌렀을 때
    private fun openGallery() {
        binding.btnAddImage.setOnClickListener {
            val intent = Intent(Intent.ACTION_GET_CONTENT)
            intent.type = "image/*"
            ActivityLauncher.launch(intent)
        }
    }

    private val ActivityLauncher: ActivityResultLauncher<Intent> =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            if (it.resultCode == RESULT_OK && it.data != null) {
                var currentImageUri = it.data?.data
                Glide.with(requireActivity()).load(currentImageUri).into(binding.ivImage)
            }
        }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
```



<br/>

- fragment_image.xml

```kotlin
<?xml version="1.0" encoding="utf-8"?>
<androidx.constraintlayout.widget.ConstraintLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    xmlns:tools="http://schemas.android.com/tools"
    android:id="@+id/frameLayout"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    tools:context=".ImageFragment">

    <ImageView
        android:id="@+id/iv_image"
        android:layout_width="250dp"
        android:layout_height="250dp"
        android:layout_marginBottom="30dp"
        app:layout_constraintBottom_toTopOf="@+id/textView2"
        app:layout_constraintEnd_toEndOf="parent"
        app:layout_constraintStart_toStartOf="parent"
        app:layout_constraintTop_toTopOf="parent"
        app:layout_constraintVertical_chainStyle="packed"
        tools:src="@tools:sample/avatars" />

    <TextView
        android:id="@+id/textView2"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:layout_marginBottom="15dp"
        android:fontFamily="@font/noto_sans_kr_bold"
        android:text="@string/add_pic"
        android:textColor="@color/black"
        android:textSize="20sp"
        app:layout_constraintBottom_toTopOf="@+id/btn_add_image"
        app:layout_constraintEnd_toEndOf="parent"
        app:layout_constraintStart_toStartOf="parent"
        app:layout_constraintTop_toBottomOf="@+id/iv_image" />

    <Button
        android:id="@+id/btn_add_image"
        android:layout_width="0dp"
        android:layout_height="wrap_content"
        android:layout_marginStart="20dp"
        android:layout_marginEnd="20dp"
        android:layout_marginBottom="50dp"
        android:fontFamily="@font/noto_sans_kr_bold"
        android:text="@string/attach"
        android:textSize="16sp"
        app:layout_constraintBottom_toBottomOf="parent"
        app:layout_constraintEnd_toEndOf="parent"
        app:layout_constraintStart_toStartOf="parent"
        app:layout_constraintTop_toBottomOf="@+id/textView2" />
</androidx.constraintlayout.widget.ConstraintLayout>
```

<br/><br/><br/>



### 고찰

- androidx.appcompat.widget.AppCompatButton을 사용할 경우 기본적인 Button 사용할 때는 디폴트 값으로 인해 적용되지 않는 부분을 커스텀 가능하다. <br/>
- 디자인 적용시키는 것이 굉장히 귀찮고 수고스러운 일임을 깨달았다,,ㅠ
