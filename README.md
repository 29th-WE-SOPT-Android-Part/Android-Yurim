## 3차 과제
<br/>

### 실행 GIF
<img src="https://user-images.githubusercontent.com/52950523/139587355-bd123aca-85b0-405f-98ca-9d9e40da8d61.gif" width="30%">
<br/><br/>

### Logic 설명<br/><br/>
    
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

- isSelected= true인 경우와 isSelected= false인 경우 두 가지에 적용되는 스타일이 다를 경우 selector를 이용한다.
<br/><br/>

### 고찰
- androidx.appcompat.widget.AppCompatButton을 사용할 경우 기본적인 Button 사용할 때는 디폴트 값으로 인해 적용되지 않는 부분을 커스텀 가능하다. <br/>
- 디자인 적용시키는 것이 굉장히 귀찮고 수고스러운 일임을 깨달았다,,ㅠ
