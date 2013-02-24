package nk9k.UranaiGetter;

import java.util.Date;

import twitter4j.Twitter;
import twitter4j.TwitterFactory;
import twitter4j.TwitterException;
import twitter4j.conf.*;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class UranaiGetterForAndroidActivity extends Activity
	implements OnClickListener, Runnable{

	final private String PRKEY = "ugPrfKey";
	final private String ATKEY = "ugatKey";
	final private String ASKEY = "ugasKey";

	final private int OAUTHACTIVITY_CODE = 1;

    private String ugToken;
    private String ugSecret;

	private Spinner spnSeiza;
	private Button btnGetUranai;
	private EditText txtResult;
	private Button btnPost;

	private ProgressDialog prgDialog;

	private boolean isErr = false;
	private String errMessage;

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        CreateMainView();

        // 認証確認
        SharedPreferences pref = getSharedPreferences(PRKEY, MODE_PRIVATE);
        ugToken = pref.getString(ATKEY, "null");
        ugSecret = pref.getString(ASKEY, "null");
        if (ugToken.equals("null"))
        {
        	// 認証ができていなければ認証画面表示
        	CreateAuthView();
        }
    }

    private void CreateMainView()
    {
    	setContentView(R.layout.main);
        // フィールドオブジェクトの取得
        InitializeComponent();
        // スピナーの候補生成
        spnSeiza.setAdapter(EConstellations.GetAdapter(this));
        // クリックリスナの登録
        btnGetUranai.setOnClickListener(this);
        btnPost.setOnClickListener(this);

    }

    public void InitializeComponent()
    {
    	spnSeiza = (Spinner) this.findViewById(R.id.spnSeiza);
    	btnGetUranai = (Button) this.findViewById(R.id.btnGetUranai);
    	txtResult = (EditText)this.findViewById(R.id.txtResult);
    	btnPost = (Button) this.findViewById(R.id.btnPost);
    }

    private void CreateAuthView()
    {
    	// 認証用Activity表示
    	Intent newInt = new Intent(
    			this,
    			TwitterOAuthActivity.class);
    	newInt.setAction(Intent.ACTION_VIEW);
    	//this.startActivity(newInt);
    	this.startActivityForResult(newInt, OAUTHACTIVITY_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
    	if ((requestCode == OAUTHACTIVITY_CODE) && (resultCode == RESULT_OK))
    	{
    		ugToken = (String) data.getCharSequenceExtra(ActivityParamKeys.ACCESSTOKEN);
    		ugSecret = (String) data.getCharSequenceExtra(ActivityParamKeys.ACCESSTOKENSECRET);
    		// 保存
    		SharedPreferences pref = getSharedPreferences(PRKEY, MODE_PRIVATE);
    		SharedPreferences.Editor prefEditor = pref.edit();
    		prefEditor.putString(ATKEY, ugToken);
    		prefEditor.putString(ASKEY, ugSecret);
    		prefEditor.commit();
    	}
    }

	public void onClick(View arg0) {
		// 占い取得ボタンが押された時
		if (arg0.getId() == R.id.btnGetUranai)
		{
			final EConstellations selected = (EConstellations) spnSeiza.getSelectedItem();

			prgDialog = new ProgressDialog(this);
			prgDialog.setMessage("占い取得中…");
			prgDialog.setIndeterminate(true);
			prgDialog.show();

			final Handler handler = new Handler();
			// Uranaiゲット
			// 通信は別スレッドで
			Thread asyncAction = new Thread(
					new Runnable()
					{
						public void run()
						{
							final String uranaiResult = Communicator.GetUranai(selected, new Date());
							handler.post(
								new Runnable()
								{
									public void run()
									{
										txtResult.setText(uranaiResult);
										prgDialog.dismiss();
									}
								}) ;
						}
					});
			asyncAction.start();

		}
		else if (arg0.getId() == R.id.btnPost)
		{
			// 結果テキスト入力に使用したキーボードを閉じる
			InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
			imm.hideSoftInputFromWindow(txtResult.getWindowToken(), 0);

			// 進捗ダイアログの表示
			prgDialog = new ProgressDialog(this);
			prgDialog.setMessage("占い投稿中…");
			prgDialog.setIndeterminate(true);
			prgDialog.show();

			// 投稿の開始
			Thread postAction = new Thread(this);
			postAction.start();

		}
	}

	public void run() {
		// 占い投稿本体
		try
		{
			ConfigurationBuilder cb = TwitterOAuthActivity.GetBuilder();
			cb.setOAuthAccessToken(ugToken);
			cb.setOAuthAccessTokenSecret(ugSecret);
			TwitterFactory twf = new TwitterFactory(cb.build());
			Twitter tw = twf.getInstance();
			tw.updateStatus(txtResult.getText().toString());

			isErr = false;
			errMessage = "";
		}
		catch (TwitterException e)
		{
			isErr = true;
			errMessage = e.getErrorMessage();
		}

		onRunExitHandler.sendEmptyMessage(0);
	}

	private Handler onRunExitHandler = new Handler(){
		public void handleMessage(Message msg) {
			prgDialog.dismiss();
			if (isErr) {
				Toast.makeText(getApplicationContext(), errMessage, Toast.LENGTH_LONG).show();
			}
			else {
				Toast.makeText(getApplicationContext(), "投稿しました。", Toast.LENGTH_LONG).show();
			}
		}
	};
}