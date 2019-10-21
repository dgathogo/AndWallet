package us.ait.andwallet.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.transaction_row.view.*
import us.ait.andwallet.MainActivity
import us.ait.andwallet.R
import us.ait.andwallet.data.Transaction
import kotlin.math.exp

class TransactionAdapter : RecyclerView.Adapter<TransactionAdapter.ViewHolder> {

    val context: Context
    val transactionList = mutableListOf<Transaction>()
    var incomeTotal:Int = 0
    var expensesTotal: Int = 0

    constructor(context: Context) {
        this.context = context
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val transactionRow = LayoutInflater.from(context).inflate(
            R.layout.transaction_row, parent, false
        )
        return ViewHolder(transactionRow)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        var transaction = transactionList[position]
        holder.tvTitle.text = transaction.title
        holder.tvAmount.text = transaction.amount.toString()
        holder.isIncome.text = transaction.isIncome.toString()
        if (transaction.isIncome) {
            holder.imageIcon.setImageResource(R.drawable.income)
        } else {
            holder.imageIcon.setImageResource(R.drawable.expense)
        }
        holder.btnDelete.setOnClickListener {
            deleteTransaction(holder.adapterPosition)
            (context as MainActivity).updateBalance()
        }
    }

    private fun deleteTransaction(position: Int) {
        var transaction = transactionList[position]
        if (transaction.isIncome) {
            incomeTotal -= transaction.amount
        } else {
            expensesTotal -= transaction.amount
        }
        transactionList.removeAt(position)
        notifyItemRemoved(position)
    }

    fun addTransaction(transaction : Transaction) {
        transactionList.add(transaction)
        if (transaction.isIncome) {
            incomeTotal += transaction.amount
        } else {
            expensesTotal += transaction.amount
        }
        notifyItemInserted(transactionList.lastIndex)
    }

    fun getBalance(): Int {
        return incomeTotal - expensesTotal
    }

    override fun getItemCount(): Int {
        return transactionList.size
    }

    fun clearAll() {
        transactionList.clear()
        notifyDataSetChanged()
        incomeTotal = 0
        expensesTotal = 0
    }

    inner class ViewHolder (itemView: View) : RecyclerView.ViewHolder(itemView){
        val tvTitle = itemView.tvTitle
        val tvAmount = itemView.tvAmount
        val isIncome = itemView.isIncome
        val imageIcon = itemView.itemIcon
        val btnDelete = itemView.btnDelete
    }
}
