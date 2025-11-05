//Адаптер ListView-tasks
package com.example.hellokotlin //Пакет, к которому принадлежит этот класс

//Импорт необходимых классов
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView

//Главный класс адаптера
class TodoAdapter(context: Context, private val tasks: MutableList<String>) :
    ArrayAdapter<String>(context, 0, tasks) {

    //Главный метод адаптера
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        //Создание View для элемента списка.
        val view = convertView ?: LayoutInflater.from(context)
            .inflate(android.R.layout.simple_list_item_1, parent, false)

        //Находит TextView внутри макета элемента, берет задачу из списка на текущей позиции и устанавливает ее в качестве текста
        val taskText = view.findViewById<TextView>(android.R.id.text1)
        val task = getItem(position)
        taskText.text = task

        //Изменение цвета для выполненных задачи
        if (task?.startsWith("✓ ") == true) {
            taskText.setBackgroundColor(0xFFC8E6C9.toInt()) // Зеленый фон
        } else {
            taskText.setBackgroundColor(0xFFFFFFFF.toInt()) // Белый фон
        }

        return view
    }
}