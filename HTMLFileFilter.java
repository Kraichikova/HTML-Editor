package com.javarush.task.task32.task3209;

import javax.swing.filechooser.FileFilter;
import java.io.File;

/*
Для открытия или сохранения файла мы будем использовать JFileChooser из библиотеки swing.
Объекты этого типа поддерживают фильтры, унаследованные от FileFilter.
Сейчас мы напишем свой фильтр:
21.1. Создай публичный класс HTMLFileFilter унаследованный от FileFilter.
21.2. Мы хотим, чтобы окно выбора файла отображало только html/htm файлы или папки.
Переопредели метод accept(File file), чтобы он возвращал true,
если переданный файл директория или содержит в конце имени ".html" или ".htm" без учета регистра.
21.3. Чтобы в окне выбора файла в описании доступных типов файлов
отображался текст "HTML и HTM файлы" переопредели соответствующим образом метод getDescription().
 */

public class HTMLFileFilter extends FileFilter {
    @Override
    public boolean accept(File f) {
        return f.isDirectory()||f.getName().toLowerCase().endsWith(".html")||f.getName().toLowerCase().endsWith(".htm");
    }

    @Override
    public String getDescription() {
        return "HTML и HTM файлы";
    }
}
