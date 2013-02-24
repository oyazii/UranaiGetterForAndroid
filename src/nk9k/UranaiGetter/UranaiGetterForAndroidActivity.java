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

        // �F�؊m�F
        SharedPreferences pref = getSharedPreferences(PRKEY, MODE_PRIVATE);
        ugToken = pref.getString(ATKEY, "null");
        ugSecret = pref.getString(ASKEY, "null");
        if (ugToken.equals("null"))
        {
        	// �F�؂��ł��Ă��Ȃ���ΔF�؉�ʕ\��
        	CreateAuthView();
        }
    }

    private void CreateMainView()
    {
    	setContentView(R.layout.main);
        // �t�B�[���h�I�u�W�F�N�g�̎擾
        InitializeComponent();
        // �X�s�i�[�̌�␶��
        spnSeiza.setAdapter(EConstellations.GetAdapter(this));
        // �N���b�N���X�i�̓o�^
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
    	// �F�ؗpActivity�\��
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
    		// �ۑ�
    		SharedPreferences pref = getSharedPreferences(PRKEY, MODE_PRIVATE);
    		SharedPreferences.Editor prefEditor = pref.edit();
    		prefEditor.putString(ATKEY, ugToken);
    		prefEditor.putString(ASKEY, ugSecret);
    		prefEditor.commit();
    	}
    }

	public void onClick(View arg0) {
		// �肢�擾�{�^���������ꂽ��
		if (arg0.getId() == R.id.btnGetUranai)
		{
			final EConstellations selected = (EConstellations) spnSeiza.getSelectedItem();

			prgDialog = new ProgressDialog(this);
			prgDialog.setMessage("�肢�擾���c");
			prgDialog.setIndeterminate(true);
			prgDialog.show();

			final Handler handler = new Handler();
			// Uranai�Q�b�g
			// �ʐM�͕ʃX���b�h��
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
			// ���ʃe�L�X�g���͂Ɏg�p�����L�[�{�[�h�����
			InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
			imm.hideSoftInputFromWindow(txtResult.getWindowToken(), 0);

			// �i���_�C�A���O�̕\��
			prgDialog = new ProgressDialog(this);
			prgDialog.setMessage("�肢���e���c");
			prgDialog.setIndeterminate(true);
			prgDialog.show();

			// ���e�̊J�n
			Thread postAction = new Thread(this);
			postAction.start();

		}
	}

	public void run() {
		// �肢���e�{��
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
				Toast.makeText(getApplicationContext(), "���e���܂����B", Toast.LENGTH_LONG).show();
			}
		}
	};
}