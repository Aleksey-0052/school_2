package hogwarts.school_2.service;

public interface InfoService {


    int getPort();


    // Создаем эндпоинт с модифицированной логикой предложенного решения. В результате модификаций эндпоинт возвращает
    // значение за меньшее количество времени.

    void calculate(int limit);

}
