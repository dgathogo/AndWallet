package us.ait.andwallet

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import us.ait.andwallet.adapter.TransactionAdapter
import us.ait.andwallet.data.Transaction

class MainActivity : AppCompatActivity() {

    companion object {
        const val REQUEST_DETAILS = 1001
        const val KEY_INCOME = "KEY_INCOME"
        const val KEY_EXPENSES = "KEY_EXPENSES"
    }

    lateinit var transactionAdapter: TransactionAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        transactionAdapter = TransactionAdapter(this)
        recyclerTransaction.adapter = transactionAdapter

        btnSave.setOnClickListener {
            try {
                if (etTitle.text.toString().isNullOrBlank()) {
                    etTitle.error = getString(R.string.error_message)
                }
                else if (etAmount.text.toString().isNullOrBlank()) {
                    etAmount.error = getString(R.string.error_message)
                } else {
                    transactionAdapter.addTransaction(
                        Transaction(
                            etTitle.text.toString(),
                            etAmount.text.toString().toInt(),
                            tbIncome.isChecked
                        )
                    )
                    updateBalance()
                }
            } catch (e:Exception){
                Log.e(getString(R.string.error_tag), e.message)
            }
        }

    }

    fun updateBalance() {
        tvBalance.text = getString(R.string.balance_text, transactionAdapter.getBalance())
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_toolbar, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_summary  -> {
                var intentDetails = Intent()
                intentDetails.setClass(this@MainActivity,
                    SummaryActivity::class.java)
                intentDetails.putExtra(KEY_INCOME, transactionAdapter.incomeTotal)
                intentDetails.putExtra(KEY_EXPENSES, transactionAdapter.expensesTotal)
                startActivityForResult(intentDetails, REQUEST_DETAILS)
            }
            R.id.action_delete_all -> {
                transactionAdapter.clearAll()
                updateBalance()
            }
        }
        return true
    }
}
