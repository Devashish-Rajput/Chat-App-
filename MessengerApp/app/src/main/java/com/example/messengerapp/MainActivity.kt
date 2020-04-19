package com.example.messengerapp

import android.content.Intent
import android.os.Bundle
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.viewpager.widget.ViewPager
import com.example.messengerapp.Fragments.ChatsFragment
import com.example.messengerapp.Fragments.SearchFragment
import com.example.messengerapp.Fragments.SettingsFragment
import com.example.messengerapp.ModelClasses.Users
import com.google.android.material.tabs.TabLayout
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*
import com.squareup.picasso.Picasso

import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    var refUsers: DatabaseReference?=null
    var firebaseUser:FirebaseUser?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar_main)

        firebaseUser=FirebaseAuth.getInstance().currentUser
        refUsers=FirebaseDatabase.getInstance().reference.child("Users").child(firebaseUser!!.uid)
        

        val toolbar: Toolbar=findViewById(R.id.toolbar_main)
        setSupportActionBar(toolbar)
        supportActionBar!!.title=""

        val tabLayout: TabLayout=findViewById(R.id.tab_layout)
        val viewPager: ViewPager=findViewById(R.id.view_pager)
        val viewPagerAdapter= ViewPagerAdapter(supportFragmentManager)

        viewPagerAdapter.addFragment(ChatsFragment(),title="Chats")
        viewPagerAdapter.addFragment(SearchFragment(),title="Search")
        viewPagerAdapter.addFragment(SettingsFragment(),title="Settings")

        viewPager.adapter=viewPagerAdapter
        tabLayout.setupWithViewPager(viewPager)
        
        //display the username and profile 
        refUsers!!.addValueEventListener(object:ValueEventListener {
            override fun onDataChange(p0: DataSnapshot) {
                if (p0.exists()){
                    val user: Users?=p0.getValue(Users:: class.java)

                    user_name.text=user!!.getUserName()
                    Picasso.get().load(user.getProfile()).placeholder(R.drawable.profile).into(profile_image)
                }
            }

            override fun onCancelled(p0: DatabaseError) {

            }
        })


    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        when (item.itemId)
        {
            R.id.action_logout -> {
                FirebaseAuth.getInstance().signOut()
                val intent = Intent(this@MainActivity,WelcomeActivity::class.java)
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
                startActivity(intent)
                finish()

                return true
            }
        }
        return false
    }

    internal class ViewPagerAdapter(fragmentManager: FragmentManager) :
        FragmentPagerAdapter(fragmentManager, FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT)
    {
        private val fragments: ArrayList<Fragment>
        private val titles: ArrayList<String>

        init{
            fragments=ArrayList<Fragment>()
            titles=ArrayList<String>()
        }

        override fun getItem(position: Int): Fragment {
            return fragments[position]
        }

        override fun getCount(): Int {
            return fragments.size
        }

        fun addFragment(fragment: Fragment, title: String)
        {
            fragments.add(fragment)
            titles.add(title)
        }

        override fun getPageTitle(i: Int): CharSequence? {
            return titles[i]
        }

    }

    override fun onBackPressed() {
        val intent = Intent(this@MainActivity,MainActivity::class.java)
        startActivity(intent)
        finish()
    }
}
