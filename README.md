# viewlibrary
流式布局
可以往布局中添加任意view，view会从左到右从上到下排列。


适用案例用于热门标签，如图：
![image][https://github.com/corgi7donkey/viewlibrary/blob/master/src/main/res/drawable/img1.png]

代码：
```xml
<com.sjb.bupt.viewlibrary.view.FlowLayout
       android:id="@+id/id_fl"
       android:layout_width="wrap_content"
       android:layout_height="wrap_content"
       android:padding="5dp"
       android:background="#cccccc">
   </com.sjb.bupt.viewlibrary.view.FlowLayout>
```

引入方式：

Add it in your root build.gradle at the end of repositories:

	allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}
Step 2. Add the dependency

	dependencies {
	        compile 'com.github.corgi7donkey:viewlibrary:-SNAPSHOT'
	}
