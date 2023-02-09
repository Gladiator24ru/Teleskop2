package com.example.teleskop2;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    // задание полей
    float teleskop = 14_000; // стоимость телескопа
    int account = 1_000; // счёт пользователя
    float wage = 2_500; // заработная плата в месяц
    int percentFree = 100; // доля заработной платы на любые траты
    float percentBank = 5; // годовая процентная ставка банка
    float[] monthlyPayments = new float[120]; // создание массива ежемесячных накоплений на 10 лет

    // метод подсчёта стоимости квартиры с учётом первоначального взноса
    private float apartmentPriceWithContribution() {
        return teleskop - account; // возврат подсчитанного значения
    }

    // метод подсчёта ежемесячных трат на ипотеку (зар.плата, процент своб.трат)
    public float mortgageCosts(float wage, int percent) {

        return (wage*percent)/100;
    }

    // метод подсчёта времени выплаты ипотеки (сумма долга, сумма платежа, годовой процент)
    // и заполнение массива monthlyPayments[] ежемесячными платежами
    public int countMonth(float total, float mortgageCosts, float percentBank) {

        float percentBankMonth = percentBank / 12; // подсчёт ежемесячного процента банка за ипотеку
        int count = 0; // счётчик месяцев накоплений

        // алгоритм расчёта платежа
        while (total>0) {
            count++; // добавление нового месяца платежа

            total = (total + (total*percentBankMonth)/100) - mortgageCosts; // вычисление долга с учётом выплаты и процента
            // заполнение массива ежемесячными платежами за ипотеку
            if(total < teleskop) { // если сумма долга больше ежемесячного платежа, то
                monthlyPayments[count-1] = mortgageCosts; // в массив добавляется целый платёж
            } else { // иначе
                break;
              /*  monthlyPayments[count-1] = total; // в массив добавляется платёж равный остатку долга */
            }
        }

        return count;
    }

    // создание дополнительных полей для вывода на экран полученных значений
    private TextView countOut; // поле вывода количества месяцев выплаты ипотеки
    private TextView manyMonthOut; // поле выписки по ежемесячным платежам

    // вывод на экран полученных значений
    @Override
    protected void onCreate(Bundle savedInstanceState) { // создание жизненного цикла активности
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main); // присваивание жизненному циклу активити представления activity_main

        // присваивание переменным активити элементов представления activity_main
        countOut = findViewById(R.id.countOut); // вывод информации количества месяцев выплаты ипотеки
        manyMonthOut = findViewById(R.id.manyMonthOut); // вывод информации выписки по ежемесячным платежам

        // запонение экрана
        // 1) вывод количества месяцев накоплений
        countOut.setText("Ипотека будет выплачиваться " + countMonth(apartmentPriceWithContribution(), mortgageCosts(wage, percentFree),percentBank) + " месяцев");
        // 2) подготовка выписки
        String monthlyPaymentsList = "";
        for(float list : monthlyPayments) {
            if (list > 0) {
                monthlyPaymentsList = monthlyPaymentsList + Float.toString(list) + " монет ";
            } else {
                break;
            }
        }
        // 3) вывод выписки ежемесячных выплат по ипотеке
        manyMonthOut.setText("Первоначальный взнос " + account + " монет, ежемесячные выплаты: " + monthlyPaymentsList);
    }
}