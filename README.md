大家好,今天我们来温故一个 TextView 的demo

![](http://img.blog.csdn.net/20150908214127314)

这个文字闪亮的demo,相信大家都看过。

那我们就来分析一下它,回顾一下它。

1. 文字着色变化
2. 文字动画着色

其实他就这么简单，那我们就来了解这2个知识点。

## 第一：   文字着色 ##

Android 为我们提供了着色器 (shader类),Shader下并为我们提供了5个子类分别是:

1. BitmapShader   ----Bitmap着色
2. LinearGradient ----线性渐变着色
3. RadialGradient ----梯度着色
4. SweepGradient ----唤醒着色
5. ComposeShader ----混合着色


今天我们用 LinearGradient--线性渐变着色 为我们的TextView 着色。

LinearGradient类有2个构造方法

```java
public LinearGradient(float x0, float y0, float x1, float y1, int colors[], float positions[],TileMode tile)
```

```java
    public LinearGradient(float x0, float y0, float x1, float y1, int color0, int color1,
            TileMode tile)
```

这2分构造方法大体一样,只是上面的着色是一个数组,表现的也可以更丰富一些.让我们就使用第一种构造方法.

首先我们定义一个  LinearGradientTextView extends TextView  
然后构造我们的着色器,如下
```java
@Override
protected void onSizeChanged(int w, int h, int oldw, int oldh) {
if(mLinearGradient==null){
   mLinearGradient = new LinearGradient(
				0, // X0
				0, // Y0
				getWidth(), //X1 
				0,    //Y1
				new int[] { getCurrentTextColor(), //color 1
					        Color.WHITE,  //color 2
					        getCurrentTextColor()}, //color 3
				new float[] { 0, 0.5f, 1 },//和上面数组的个数要对应,值在[0-1]
				Shader.TileMode.CLAMP//模式有三种,CLAMP,MIRROR,REPEAT
				);
				
mMatrix = new Matrix(); // new 一个Matrix类
 }
}
```
因为我们要获取当前TextView 的宽度,这里我把代码放在 onSizeChanged() 方法中,保证准确的获取宽度
然后在onDraw()方法中,设置我们的着色器就可以了。
```
@Override
	protected void onDraw(Canvas canvas) {
		// TODO Auto-generated method stub
		super.onDraw(canvas);
		mPaint.setShader(mLinearGradient);
		mLinearGradient.setLocalMatrix(mMatrix);
	}
```
效果如下
![这里写图片描述](http://img.blog.csdn.net/20150908225825821)

这里我们的文字已经可以着色了,完成了第一步。

## 第二：  文字着色动起来 ##

动画我比较喜欢用属性动画, ObjectAnimator为我们才用的类。

```
public class ObjectAnimatorActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_object_animator);
		TextView mTextView = (TextView) findViewById(R.id.text1);
		
		ObjectAnimator animator = ObjectAnimator.ofFloat(mTextView, "alpha", 1f, 0f, 1f);  
		animator.setDuration(5000); 
		animator.setRepeatCount(ObjectAnimator.INFINITE);
		animator.start();  
	}
}
```
这样简单的设置一下我们就可以为TextView--的alpha设置一个动画了。效果图就不上了，太简单了。
ObjectAnimator 最常用的ofFloat()和ofInt()这两个方法了。
ObjectAnimator 是为Object 设置的,并不单单设置动画而已,就是为什么第二个参数可以设置任意值的原因，那我们就来更新一下任意值为何？

 当我第一次看到这个类的时候,也在思考为什么可以为任意值? 为什么叫属性动画?

其实这个问题,在第一个参数  为Object 类，就已经解答了。为何呢？
我们知道，当一个类的成员变量被我们称之为属性的时候，正常情况下都会为它添加 setXXX/getXXX 这2个方法，所谓的属性动画就是设置属性的值来驱动变化。（是不是动画就不重要了）

根据这个结论，我们就来试一试是否正确，这里我们就设置一个属性，使我们的TextView和计算器一样100豪秒+1(只是为了更好看效果)

```
public class ObjectAnimatorActivity extends Activity {
	
	private int count;
	private TextView mTextView;
	
	//为count 属性添加一个setXXX方法
	public void setCount(int count) {
		this.count = count;
		mTextView.setText(""+count);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_object_animator);
		mTextView = (TextView) findViewById(R.id.text1);
		ObjectAnimator animator = ObjectAnimator.ofInt(this, "count", 0, 100);  
		animator.setDuration(100000); //10000/100-->100毫秒设置+1 
		animator.setInterpolator(new LinearInterpolator()); // 线性累加
		animator.setRepeatCount(ObjectAnimator.INFINITE);
		animator.start();  
	}
	
}
```
效果如下
![这里写图片描述](http://img.blog.csdn.net/20150908233744952)

充分的说明，ObjectAnimator 设置思想就是这样的。至于我们设置,alpha,rotation,scaleY等等之类的是因为传入的对象有其方法,在用反射进行设置而已。

###好，这样2个问题我们都解决了,我们把2者结合起来,代码如下:###

```
public class GlowTextView extends TextView {

	private LinearGradient mLinearGradient;
	private Paint mPaint;
	private Matrix mMatrix;
	private float glow; //发光的大小位置
	private ObjectAnimator mAnimator;

	public GlowTextView(Context context) {
		super(context);
	}

	public GlowTextView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}
	
	public void setGlow(float glow) {
		this.glow = glow;
		invalidate();
	}

	private void init() {
		mLinearGradient = new LinearGradient(
				0, // X0
				0, // Y0
				getWidth(), //X1 这个TextView
				0,    //Y1
				new int[] { getCurrentTextColor(), //color 1
					        Color.WHITE,  //color 2
					        getCurrentTextColor()}, //color 3
				new float[] { 0, 0.5f, 1 },
				Shader.TileMode.REPEAT //模式
				);
	    mMatrix = new Matrix();
		mPaint = getPaint();
		
		// TODO Auto-generated method stub
		mAnimator = ObjectAnimator.ofFloat(this, "glow", 0, getWidth());
		mAnimator.setRepeatCount(ValueAnimator.INFINITE);
		mAnimator.setDuration(1000);
		mAnimator.setStartDelay(0);
		mAnimator.start();
	}

	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh) {
		// TODO Auto-generated method stub
		super.onSizeChanged(w, h, oldw, oldh);
		init();
	}

	@Override
	protected void onDraw(Canvas canvas) {
		// TODO Auto-generated method stub
		super.onDraw(canvas);
		mPaint.setShader(mLinearGradient);
	    mMatrix.setTranslate(glow, 0); //改变的区域
		mLinearGradient.setLocalMatrix(mMatrix);
	}

}
```
上面2个知识点结合一下,只需要添加一行  mMatrix.setTranslate(glow, 0); //改变的区域  就实现了我们需要的效果了。

![这里写图片描述](http://img.blog.csdn.net/20150909000142048)

到此我们就温故了一个DEMO，最后附上源码，补上可以控制一下发光。










