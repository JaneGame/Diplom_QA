## Результат прогона тестов

1. Время прохождения тестов вручную около 15 часов, в то время как прогон тестов в автоматическом режиме занимает около 5минут.
2. При тестировании в автоматическом режиме и последующем анализе выявились следующие проблемы:

    * При создании задачи эпизодически возникает ошибочное создание задачи, в ходе которого задача без исполнителя создается в статусе "В работе", а задача с исполнителем в статусе "Открыто". Данная ошибка приводит к тому, что часть тестов, для которых необходимо создать задачу падает из-за того, что не находит нужный элемент (количество таких тестов колеблется от 0 до 4 в прогоне);
    * В связи с особенностью тестирования на реальном девайсе часть кейсов может падать из-за того, что не некорректно ввелось название, не нажалась кнопка или не подгрузился элемент. Для последнего случая где возможно были проставлены waitId или Thread.sleep. Количество упавших из-за этого тестов в прогоне может быть от 0 до 2);
    * Не получилось автоматизировать до конца кейсы по фильтрам из-за того, что часть категорий оказывалось под клавиатурой и не нажималось. В тест-кейсах приведен один такой кейс, для остальных таких кейсов автоматизация не была произведена.
