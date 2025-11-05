//Главный класс приложения
package com.example.hellokotlin //Объявление пакета

//Импорт необходимых классов
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.ListView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
//Объявление класса активности

    //Объявление свойств класса
    private lateinit var taskList: MutableList<String>
    private lateinit var adapter: ArrayAdapter<String>

    //Стандартный метод жизненного цикла активности, вызываемый при ее создании
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main) //Установка XML-макета

        //Инициализация свойства taskList и наделение пустым изменяемым списком (MutableList)
        taskList = mutableListOf()

        //Поиск виджетов из XML-макета по их ID и привязка их к переменным в коде
        val editText = findViewById<EditText>(R.id.editText)
        val buttonAdd = findViewById<Button>(R.id.buttonAdd)
        val buttonDeleteCompleted = findViewById<Button>(R.id.buttonDeleteCompleted) // Новая кнопка
        val listView = findViewById<ListView>(R.id.listView)

        //Создание и установка адаптера для ListView
        adapter = TodoAdapter(this, taskList)
        listView.adapter = adapter

        //Добавление задачи
        buttonAdd.setOnClickListener {
            val task = editText.text.toString().trim()
            if (task.isNotEmpty()) {
                taskList.add(task)
                adapter.notifyDataSetChanged()
                editText.text.clear()
            } else {
                Toast.makeText(this, "Введите задачу", Toast.LENGTH_SHORT).show()
            }
        }

        //Отметка о выполнении
        listView.setOnItemClickListener { _, _, position, _ ->
            val task = taskList[position]
            if (!task.startsWith("✓ ")) {
                taskList[position] = "✓ $task"
                adapter.notifyDataSetChanged()
                Toast.makeText(this, "Задача выполнена!", Toast.LENGTH_SHORT).show()
            }
        }

        //Удаление по долгому нажатию
        listView.setOnItemLongClickListener { _, _, position, _ ->
            val removedTask = taskList.removeAt(position)
            adapter.notifyDataSetChanged()
            Toast.makeText(this, "Удалено: $removedTask", Toast.LENGTH_SHORT).show()
            true
        }

        //Удаление ВСЕХ выполненных задач
        buttonDeleteCompleted.setOnClickListener {
            val completedTasks = taskList.filter { it.startsWith("✓ ") }

            if (completedTasks.isNotEmpty()) {
                taskList.removeAll { it.startsWith("✓ ") }
                adapter.notifyDataSetChanged()
                Toast.makeText(this, "Удалено выполненных: ${completedTasks.size}", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Нет выполненных задач", Toast.LENGTH_SHORT).show()
            }
        }
    }
}