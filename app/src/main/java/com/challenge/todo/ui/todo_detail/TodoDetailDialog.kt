package com.challenge.todo.ui.todo_detail

import android.app.Dialog
import android.view.View
import android.view.Window
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.challenge.todo.data.ToDoItem
import com.challenge.todo.databinding.ActivityTodoDialogBinding
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime

class TodoDetailDialog(private val context: AppCompatActivity) {

    private lateinit var binding : ActivityTodoDialogBinding
    // Dialog 에는 부모 context 넣어주기
    private val dlg = Dialog(context)

    fun show(item: ToDoItem?){
        binding = ActivityTodoDialogBinding.inflate(context.layoutInflater)

        // 타이틀 바 제거
        dlg.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dlg.setContentView(binding.root)

        // 다이얼로그 바깥을 클릭 했을 때 닫힐지 여부
        dlg.setCancelable(true)

        if(item != null)
        {
            binding.registBtn.visibility = View.GONE
            binding.cancleBtn1.visibility = View.GONE
            binding.updateBtn.visibility = View.VISIBLE
            binding.cancleBtn.visibility = View.VISIBLE

            binding.detailTitle.setText(item.title)
            binding.detailContent.setText(item.content)
        }

        //등록
        binding.registBtn.setOnClickListener{
            Toast.makeText(context,"등록",Toast.LENGTH_SHORT).show()
            val toDoItem : ToDoItem = ToDoItem(
                binding.detailTitle.text.toString(),
                binding.detailContent.text.toString(),
                LocalDateTime.now().toString(),
                null,
                false
            )
            onClickListner.onRegistClick(toDoItem)


            dlg.dismiss()
        }

        //등록 취소
        binding.cancleBtn1.setOnClickListener{
            dlg.dismiss()
        }

        //수정
        binding.updateBtn.setOnClickListener{
            Toast.makeText(context,"수정",Toast.LENGTH_SHORT).show()
            dlg.dismiss()
        }

        //수정 취소
        binding.cancleBtn.setOnClickListener{
            dlg.dismiss()
        }



        dlg.show()
    }

    private lateinit var onClickListner: ButtonClickListner
    interface ButtonClickListner {
        fun onRegistClick(toDoItem: ToDoItem)
    }

    fun setOnClickListner(listner: ButtonClickListner){
        onClickListner = listner
    }

}