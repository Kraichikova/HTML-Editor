package com.javarush.task.task32.task3209;

import javax.swing.*;
import javax.swing.text.html.HTMLDocument;
import javax.swing.text.html.HTMLEditorKit;
import java.awt.event.ActionListener;
import java.io.*;

public class Controller {
    private View view;
    private HTMLDocument document;
    private File currentFile;

    public Controller(View view) {
        this.view = view;
    }

    public HTMLDocument getDocument() {
        return document;
    }

    /*  20.2. Реализуй метод инициализации init() контроллера.
    Он должен просто вызывать метод создания нового документа.*/
    public void init() {
        createNewDocument();
    }

    /*
    Добавь в контроллер метод resetDocument(), который будет сбрасывать текущий документ. Он
    должен:
    15.1.	Удалять у текущего документа document слушателя правок которые можно
    отменить/вернуть (найди подходящий для этого метод, унаследованный от
    AbstractDocument). Слушателя нужно запросить у представления (метод getUndoListener()).
    Не забудь проверить, что текущий документ существует (не null).
    15.2.	Создавать новый документ по умолчанию и присваивать его полю document.
    Подсказка: воспользуйся подходящим методом класса HTMLEditorKit.
    15.3.	Добавлять новому документу слушателя правок.
    15.4.	Вызывать у представления метод update().
    */
    public void resetDocument() {
        if (document != null)
            document.removeUndoableEditListener(view.getUndoListener());
        document = (HTMLDocument) new HTMLEditorKit().createDefaultDocument();
        document.addUndoableEditListener(view.getUndoListener());
        view.update();
    }

    /**
     * Добавь метод setPlainText(String text) в контроллер. Он будет записывать переданный текст с
     * html тегами в документ document. При его реализации:
     * 16.1.	Сбрось документ.
     * 16.2.	Создай новый реадер StringReader на базе переданного текста.
     * 16.3.	Вызови метод read() из класса HTMLEditorKit, который вычитает данные из реадера в
     * документ document.
     * 16.4.	Проследи, чтобы метод не кидал исключения. Их необходимо просто логировать.
     */
    public void setPlainText(String text) {
        resetDocument();
        StringReader stringReader = new StringReader(text);
        try {
            new HTMLEditorKit().read(stringReader, document, 0);
        } catch (Exception e) {
            ExceptionHandler.log(e);
        }
    }

    /*   Добавь метод String getPlainText() в контроллер. Он должен получать текст из документа со всеми html тегами.
         17.1. Создай объект StringWriter.
         17.2. Перепиши все содержимое из документа document в созданный объект с помощью метода write класса HTMLEditorKit.
         17.3. Как обычно, метод не должен кидать исключений.
    */
    public String getPlainText() {
        StringWriter stringWriter = new StringWriter();
        try {
            new HTMLEditorKit().write(stringWriter, document, 0, document.getLength());
        } catch (Exception e) {
            ExceptionHandler.log(e);
        }
        return stringWriter.toString();
    }

    public void exit() {
        System.exit(0);
    }


  /*    2.2.1. Создавать объект представления.
        2.2.2. Создавать контроллер, используя представление.
        2.2.3. Устанавливать у представления контроллер.
        2.2.4. Инициализировать представление.
        2.2.5. Инициализировать контроллер. Контроллер должен инициализироваться после представления.
   */
    public static void main(String[] args) {
        View view = new View();
        Controller controller = new Controller(view);
        view.setController(controller);
        view.init();
        controller.init();
    }

    /*
    20.1. Реализуй метод создания нового документа createNewDocument() в контроллере. Он должен:
    20.1.1. Выбирать html вкладку у представления.
    20.1.2. Сбрасывать текущий документ.
    20.1.3. Устанавливать новый заголовок окна, например: "HTML редактор". Воспользуйся методом setTitle(),
     который унаследован в нашем представлении.
    20.1.4. Сбрасывать правки в Undo менеджере. Используй метод resetUndo представления.
    20.1.5. Обнулить переменную currentFile.
    Проверь работу пункта меню Новый.
     */
    public void createNewDocument() {
        view.selectHtmlTab();
        resetDocument();
        view.setTitle("HTML редактор");
        view.resetUndo();
        currentFile = null;
    }
    /*
    23.2. Пришло время реализовать метод openDocument().
    Метод должен работать аналогично методу saveDocumentAs(), в той части, которая отвечает за выбор файла.
    Подсказка: Обрати внимание на имя метода для показа диалогового окна.
    Когда файл выбран, необходимо:
    23.2.1. Установить новое значение currentFile.
    23.2.2. Сбросить документ.
    23.2.3. Установить имя файла в заголовок у представления.
    23.2.4. Создать FileReader, используя currentFile.
    23.2.5. Вычитать данные из FileReader-а в документ document с помощью объекта класса HTMLEditorKit.
    23.2.6. Сбросить правки (вызвать метод resetUndo представления).
    23.2.7. Если возникнут исключения - залогируй их и не пробрасывай наружу.
    Проверь работу пунктов меню Сохранить и Открыть.
     */
    public void openDocument() {
        view.selectHtmlTab();
        JFileChooser fileChooser=new JFileChooser();
        fileChooser.setFileFilter(new HTMLFileFilter());
        int choise = fileChooser.showOpenDialog(view);
        if (choise == JFileChooser.APPROVE_OPTION) {
            currentFile = fileChooser.getSelectedFile();
            resetDocument();
            view.setTitle(currentFile.getName());

            try (FileReader fileReader = new FileReader(currentFile)) {
                new HTMLEditorKit().read(fileReader,document,0);
            } catch (Exception e) {
                ExceptionHandler.log(e);
            }
            view.resetUndo();
        }
    }

    /*
    23.1. Напишем метод для сохранения открытого файла saveDocument().
    Метод должен работать также, как saveDocumentAs(), но не запрашивать файл у пользователя, а использовать currentFile.
    Если currentFile равен null, то вызывать метод saveDocumentAs().
    */
    public void saveDocument() {
        view.selectHtmlTab();
        if (currentFile == null)
            saveDocumentAs();
        else {
            try (FileWriter fileWriter = new FileWriter(currentFile)) {
                new HTMLEditorKit().write(fileWriter, document, 0, document.getLength());
            } catch (Exception e) {
                ExceptionHandler.log(e);
            }
        }
    }

    /*
    Реализация должна:
    22.1. Переключать представление на html вкладку.
    22.2. Создавать новый объект для выбора файла JFileChooser.
    22.3. Устанавливать ему в качестве фильтра объект HTMLFileFilter.
    22.4. Показывать диалоговое окно "Save File" для выбора файла.
    22.5. Если пользователь подтвердит выбор файла:
    22.5.1. Сохранять выбранный файл в поле currentFile.
    22.5.2. Устанавливать имя файла в качестве заголовка окна представления.
    22.5.3. Создавать FileWriter на базе currentFile.
    22.5.4. Переписывать данные из документа document в объекта FileWriter-а аналогично тому,
    как мы это делали в методе getPlainText().
    22.6. Метод не должен кидать исключения.
    Проверь работу пункта меню Сохранить как...
     */

    public void saveDocumentAs() {
        view.selectHtmlTab();
        JFileChooser fileChooser=new JFileChooser();
        fileChooser.setFileFilter(new HTMLFileFilter());
        int choise = fileChooser.showSaveDialog(view);
        if (choise == JFileChooser.APPROVE_OPTION) {
            currentFile = fileChooser.getSelectedFile();
            view.setTitle(currentFile.getName());
            try (FileWriter fileWriter = new FileWriter(currentFile)) {
                new HTMLEditorKit().write(fileWriter, document, 0, document.getLength());
            } catch (Exception e) {
                ExceptionHandler.log(e);
            }
        }
    }
}
