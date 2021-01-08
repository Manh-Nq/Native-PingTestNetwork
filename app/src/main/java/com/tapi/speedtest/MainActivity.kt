package com.tapi.speedtest


import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.tapi.speedtest.`object`.IP
import com.tapi.speedtest.core.Terminal
import com.tapi.speedtest.manager.vpn.VPNServerChooser
import com.tapi.speedtest.util.Utils
import kotlinx.coroutines.*

class MainActivity : AppCompatActivity() {

    private lateinit var btPing: Button
    private lateinit var btPingIP: Button
    private lateinit var btGet: Button
    private lateinit var tvRs: TextView
    private lateinit var edtIP: EditText
    private lateinit var prg: ProgressBar
    private lateinit var btPermission: Button
    lateinit var vpnChooser: VPNServerChooser
    lateinit var terminal: Terminal
    val tmp = mutableListOf(
        "gooogle.com", "facebook.com", "youtube.com", "vlxx.com","1.1.1.1","8.8.8.8", "8.8.4.4"
    )
    val listIP = mutableListOf<IP>()
    val myScope = CoroutineScope(Dispatchers.Default + SupervisorJob())


    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        btPing = findViewById(R.id.bt_ping)
        btPingIP = findViewById(R.id.bt_ping_ip)
        btGet = findViewById(R.id.bt_get)
        tvRs = findViewById(R.id.tv_result)
        edtIP = findViewById(R.id.edt_ip)
        prg = findViewById(R.id.prg)
        btPermission = findViewById(R.id.bt_permission)
        vpnChooser = VPNServerChooser()
        terminal = Terminal()
        tmp.map {
            listIP.add(IP(it))
        }

        btPermission.setOnClickListener {
            if (ContextCompat.checkSelfPermission(
                    this,
                    Manifest.permission.ACCESS_NETWORK_STATE
                ) != PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(
                    this,
                    Manifest.permission.INTERNET
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                requestPermissions(
                    arrayOf(
                        Manifest.permission.ACCESS_NETWORK_STATE,
                        Manifest.permission.INTERNET
                    ), 100
                )
            }

        }

        btGet.setOnClickListener {
            myScope.launch {
                vpnChooser.deleteAll()
            }
        }
        btPing.setOnClickListener {
            tvRs.text = ""
            showProgress(true)
            myScope.launch {

                val rs = vpnChooser.choose(listIP)

                withContext(Dispatchers.Main) {
                    showProgress(false)
                    Log.d("TAG", "NManhhh: ${rs.address}")
                }
            }

        }


        btPingIP.setOnClickListener {
            myScope.launch {

                val rs = Utils.getIPAddress(true)
                Log.d("TAG", "NManhhh: $rs")
            }
        }


    }

    private fun showProgress(b: Boolean) {
        if (b) {
            prg.visibility = View.VISIBLE
        } else {
            prg.visibility = View.INVISIBLE
        }
    }

    private fun showToast(text: String) {
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show()
    }

}