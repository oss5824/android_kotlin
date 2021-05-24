package com.example.aop_part3_chapter05

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import com.facebook.CallbackManager
import com.facebook.FacebookCallback
import com.facebook.FacebookException
import com.facebook.login.LoginResult
import com.facebook.login.widget.LoginButton
import com.google.firebase.auth.FacebookAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class LoginActivity: AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var callbackManager: CallbackManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        auth = Firebase.auth
        callbackManager=CallbackManager.Factory.create()

        initLoginButton()
        initSignUpButton()
        initEmailAndPasswordEditText()
        initFacebookLoginButton()
    }

    private fun initFacebookLoginButton() {
        val facebookLoginButton=findViewById<LoginButton>(R.id.facebookLoginButton)
        facebookLoginButton.setPermissions("email","public_profile")
        facebookLoginButton.registerCallback(callbackManager,object : FacebookCallback<LoginResult>{
            override fun onSuccess(result: LoginResult) {
                //TODO 로그인 성공
                val credential = FacebookAuthProvider.getCredential((result.accessToken.token))
                auth.signInWithCredential(credential)
                    .addOnCompleteListener(this@LoginActivity){task->
                        if(task.isSuccessful){
                            handleSuccessLogin()
                        }
                        else{
                            Log.e("MAINACTIVITY",task.exception.toString())
                            Toast.makeText(this@LoginActivity,"페이스북 로그인이 실패했습니다.",Toast.LENGTH_SHORT).show()
                        }
                    }
            }

            override fun onCancel() {
            }

            override fun onError(error: FacebookException?) {
                Toast.makeText(this@LoginActivity,"페이스북 로그인이 실패했습니다.",Toast.LENGTH_SHORT).show()
            }

        })
    }

    private fun initEmailAndPasswordEditText() {
        val emailEditText=findViewById<EditText>(R.id.emailEditText)
        val passwordEditText=findViewById<EditText>(R.id.passwordEditText)

        val loginButton = findViewById<Button>(R.id.loginButton)
        val signUpButton = findViewById<Button>(R.id.signUpButton)

        emailEditText.addTextChangedListener{
            val enable = emailEditText.text.isNotEmpty()&&passwordEditText.text.isNotEmpty()
            loginButton.isEnabled=enable
            signUpButton.isEnabled=enable
        }
        passwordEditText.addTextChangedListener{
            val enable = emailEditText.text.isNotEmpty()&&passwordEditText.text.isNotEmpty()
            loginButton.isEnabled=enable
            signUpButton.isEnabled=enable
        }


    }

    private fun initLoginButton() {
        val loginButton = findViewById<Button>(R.id.loginButton)
        loginButton.setOnClickListener {
            val email = getInputEmail()
            val password = getInputPassword()

            auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this) { task ->
                    if(task.isSuccessful){
                        handleSuccessLogin()
                    }else {
                        Toast.makeText(this, "로그인에 실패했습니다. 이메일 또는 비밀번호를 확인해주세요", Toast.LENGTH_SHORT)
                            .show()
                    }
                }
        }
    }
    private fun initSignUpButton() {
        val signUpButton = findViewById<Button>(R.id.signUpButton)
        signUpButton.setOnClickListener {
            val email = getInputEmail()
            val password = getInputPassword()

            auth.createUserWithEmailAndPassword(email,password)
                .addOnCompleteListener(this){ task->
                    if(task.isSuccessful){
                        Toast.makeText(this,"회원가입에 성공했습니다. 로그인 해주세요.", Toast.LENGTH_SHORT).show()
                    }
                    else{
                        Log.e("MAINACTIVITY",task.exception.toString())
                        Toast.makeText(this,"이미 가입한 이메일이거나, 회원가입에 실패했습니다.", Toast.LENGTH_SHORT).show()
                    }
                }
        }
    }

    private fun getInputEmail(): String {
        return findViewById<EditText>(R.id.emailEditText).text.toString()
    }

    private fun getInputPassword(): String {
        return findViewById<EditText>(R.id.passwordEditText).text.toString()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        
        callbackManager.onActivityResult(requestCode,resultCode,data)
    }

    private fun handleSuccessLogin(){
        if(auth.currentUser==null){
            Toast.makeText(this,"로그인에 실패했습니다.",Toast.LENGTH_SHORT).show()
            return
        }
        val userId=auth.currentUser?.uid.orEmpty()//TODO 어차피 위에서 null처리해줘서 null이 될 일은 없음
        val currentUserDB = Firebase.database.reference.child("Users").child(userId)
        val user = mutableMapOf<String,Any>()
        user["userId"]=userId
        currentUserDB.updateChildren(user)

        finish()
    }

}