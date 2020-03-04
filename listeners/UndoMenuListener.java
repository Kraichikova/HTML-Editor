package com.javarush.task.task32.task3209.listeners;

import com.javarush.task.task32.task3209.View;

import javax.swing.*;
import javax.swing.event.MenuEvent;
import javax.swing.event.MenuListener;

/*Этот слушатель будет следить за меню, а если конкретнее, то за моментом,
  когда меню редактирования будет выбрано пользователем.
  Этот слушатель будет следить за меню, а если конкретнее, то за моментом,
  когда меню редактирования будет выбрано пользователем.
  В этот момент он будет запрашивать у представления можем ли мы сейчас отменить или вернуть какое-то действие,
  и в зависимости от этого делать доступными или не доступными пункты меню "Отменить" и "Вернуть".
*/

public class UndoMenuListener implements javax.swing.event.MenuListener {
    private View view;
    private JMenuItem undoMenuItem = new JMenuItem("Отменить");
    private JMenuItem redoMenuItem = new JMenuItem("Вернуть");

    public UndoMenuListener(View view, JMenuItem undoMenuItem, JMenuItem redoMenuItem) {
        this.view = view;
        this.undoMenuItem = undoMenuItem;
        this.redoMenuItem = redoMenuItem;
    }

    /*
    будет вызываться перед показом меню. Он должен:
    10.3.1. Спрашивать у представления можем ли мы отменить действие с помощью метода boolean canUndo().
    Пока у представления нет такого метода, поэтому добавь заглушку, которая всегда возвращает false.
    10.3.2. Делать доступным или не доступным пункт меню undoMenuItem в зависимости от того, что нам вернуло представление.

    Подсказка: используй метод setEnabled().

    10.3.3. Аналогично поступи и для пункта меню redoMenuItem, добавив метод-заглушку canRedo() в представление.
    Запусти программу и убедись, что пункты меню Отменить и Вернуть недоступны.
     */
    @Override
    public void menuSelected(MenuEvent e) {
        undoMenuItem.setEnabled(view.canUndo());
        redoMenuItem.setEnabled(view.canRedo());
    }

    @Override
    public void menuDeselected(MenuEvent e) {

    }

    @Override
    public void menuCanceled(MenuEvent e) {

    }
}
