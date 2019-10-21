package us.ait.andwallet

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_summary.*

class SummaryActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_summary)

        var income: Int = intent.getIntExtra(MainActivity.KEY_INCOME, 0)
        var expenses: Int = intent.getIntExtra(MainActivity.KEY_EXPENSES, 0)
        var balance = income - expenses

        tvIncomeSummary.text = getString(R.string.income_text, income)
        tvExpensesSummary.text = getString(R.string.expenses_text, expenses)
        tvBalanceSummary.text = getString(R.string.balance_text, balance)


    }
}