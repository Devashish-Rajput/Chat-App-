package com.example.messengerapp

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_register.*

class RegisterActivity : AppCompatActivity() {

    private lateinit var mAuth:FirebaseAuth
    private lateinit var refUsers:DatabaseReference
    private var firebaseUserId: String =""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)


        val toolbar: Toolbar =findViewById(R.id.toolbar_register)
        setSupportActionBar(toolbar)
        supportActionBar!!.title="Register"
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        toolbar.setNavigationOnClickListener{
            val intent = Intent(this@RegisterActivity,WelcomeActivity::class.java)
            startActivity(intent)
            finish()
        }

        mAuth=FirebaseAuth.getInstance()


        register_btn.setOnClickListener{
            registerUser()
        }

    }

    private fun registerUser() {
        val username: String =username_register.text.toString()
        val email: String =email_register.text.toString()
        val password: String =password_register.text.toString()

        if(username=="")
        {
            Toast.makeText(this@RegisterActivity,"please write username", Toast.LENGTH_LONG).show()
        }
        else if(email=="")
        {
            Toast.makeText(this@RegisterActivity,"please write email", Toast.LENGTH_LONG).show()
        }
        else if(password=="")
        {
            Toast.makeText(this@RegisterActivity,"please write password", Toast.LENGTH_LONG).show()
        }
        else{
            mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener{
                task ->
                if(task.isSuccessful)
                {
                    firebaseUserId=mAuth.currentUser!!.uid
                    refUsers=FirebaseDatabase.getInstance().reference.child("Users").child(firebaseUserId)

                    val userHashMap=HashMap<String, Any>()
                    userHashMap["uid"]=firebaseUserId
                    userHashMap["username"]=username
                    userHashMap["profile"]="https://firebasestorage.googleapis.com/v0/b/messengerapp-e4672.appspot.com/o/profile.png?alt=media&token=5bb07407-df2f-4b51-a241-ad1227d870e0"
                    userHashMap["cover"]="https://firebasestorage.googleapis.com/v0/b/messengerapp-e4672.appspot.com/o/cover.jpg?alt=media&token=793d4154-42fa-46ca-beaf-2bbdcbd9c6ec"
                    userHashMap["status"]="offline"
                    userHashMap["search"]=username.toLowerCase()
                    userHashMap["facebook"]="https://m.facebook.com"
                    userHashMap["instagram"]="https://m.instagram.com"
                    userHashMap["website"]="https://www.google.com"

                    refUsers.updateChildren(userHashMap).addOnCompleteListener {
                        task->
                        if(task.isSuccessful)
                        {
                            val intent = Intent(this@RegisterActivity,MainActivity::class.java)
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
                            startActivity(intent)
                            finish()
                        }
                    }
                }
                else{
                    Toast.makeText(this@RegisterActivity,"Error Message: "+task.exception!!.message.toString(), Toast.LENGTH_LONG).show()
                }
            }
        }
    }

    override fun onBackPressed() {
        val intent = Intent(this@RegisterActivity,WelcomeActivity::class.java)
        startActivity(intent)
        finish()
    }
}
