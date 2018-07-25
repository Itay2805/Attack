package fuck.it.attack.googlePlayServices;

import android.app.AlertDialog;
import android.content.Intent;
import android.view.View;
import com.google.android.gms.auth.api.signin.*;
import com.google.android.gms.games.Games;
import com.google.android.gms.games.GamesClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import fuck.it.attack.MainActivity;
import fuck.it.attack.R;
import fuck.it.attack.core.Logger;

public class SignInHelper {

	public static final int RC_SIGN_IN = 9001;

	private static View popupsView;
	private static GamesClient games;
	private static GoogleSignInAccount account;

	public static void signIn() {
		account = GoogleSignIn.getLastSignedInAccount(MainActivity.getContext());
		if(account == null) {
			silentSignIn();
		}
	}

	public static void signInResult(GoogleSignInResult result) {
		if(result.isSuccess()) {
			account = result.getSignInAccount();
			signInSuccess();
		}else {
			String message = result.getStatus().getStatusMessage();
			if (message == null || message.isEmpty()) {
				message = "Unknown error while performing sign-in.";
			}
			new AlertDialog.Builder(MainActivity.getContext()).setMessage(message).setNeutralButton(android.R.string.ok, null).show();
		}
	}

	public static void silentSignIn() {
		GoogleSignInClient signInClient = GoogleSignIn.getClient(MainActivity.getContext(), GoogleSignInOptions.DEFAULT_GAMES_SIGN_IN);
		signInClient.silentSignIn().addOnCompleteListener(MainActivity.getContext(), new OnCompleteListener<GoogleSignInAccount>() {
			public void onComplete(Task<GoogleSignInAccount> task) {
			if (task.isSuccessful()) {
				account = task.getResult();
				signInSuccess();
			} else {
				explicitSignIn();
			}
			}
		});
	}

	private static void explicitSignIn() {
		GoogleSignInClient signInClient = GoogleSignIn.getClient(MainActivity.getContext(), GoogleSignInOptions.DEFAULT_GAMES_SIGN_IN);
		Intent intent = signInClient.getSignInIntent();
		MainActivity.getContext().startActivityForResult(intent, RC_SIGN_IN);
	}

	private static void signInSuccess() {
		Logger.info("Logged in successfully: ", account.getDisplayName());

		games = Games.getGamesClient(MainActivity.getContext(), account);
		if(popupsView != null) {
			games.setViewForPopups(popupsView);
		}
	}

	public static void setPopupsView(View view) {
		popupsView = view;
		if(games != null) {
			games.setViewForPopups(popupsView);
		}
	}

	public static GoogleSignInAccount getAccount() {
		return account;
	}

	public static GamesClient getGames() {
		return games;
	}
}
