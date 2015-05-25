package com.todolist.model.utils;

import com.todolist.model.Priority;
import com.todolist.model.Todo;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Utility class for common constants and methods.
 *
 */
public class TodoListUtils {

    private TodoListUtils() { }

    public static final String SESSION_USER = "user";

    public static final String DATE_FORMAT = "dd/MM/yyyy";

    /**
     * Return the icon name for the given priority.
     *
     * @param priority the priority of the todo
     * @return the icon name
     */
    public static String getPriorityIcon(Priority priority) {

        String priorityIcon = "";

        switch (priority) {
            case HIGH:
                priorityIcon = "up";
                break;
            case MEDIUM:
                priorityIcon = "right";
                break;
            case LOW:
                priorityIcon = "down";
        }

        return priorityIcon;
    }

    /**
     * Return the css style to apply for the given status.
     *
     * @param status the status of the todo
     * @return the css style of the status
     */
    public static String getStatusStyle(boolean status) {
        return status ? "label-success" : "";
    }

    /**
     * Return the label of the given status.
     *
     * @param status the status of the todo
     * @return the label of the status
     */
    public static String getStatusLabel(boolean status) {
        return status ? "DONE" : "TODO";
    }

    /**
     * Apply a search/replace of the pattern in the input text.
     *
     * @param input   text to which apply the style for each matched pattern
     * @param pattern the pattern to highlight
     * @return the transformed text
     */
    public static String highlight(final String input, final String pattern) {

        String cssClass = "label label-warning";
        String startSpanTag = "<span class=\"" + cssClass + "\">";
        String endSpanTag = "</span>";

        StringBuilder stringBuilder = new StringBuilder(startSpanTag);
        stringBuilder.append(pattern);
        stringBuilder.append(endSpanTag);

        Pattern p = Pattern.compile(pattern, Pattern.CASE_INSENSITIVE);
        Matcher matcher = p.matcher(input);

        return matcher.replaceAll(stringBuilder.toString());

    }

    /**
     * Count the number of todos that are done.
     * @param todoList the todos list
     * @return the number of todos done
     */
    public static int countTotalDone(List<Todo> todoList) {
        int count = 0;
        for (Todo todo : todoList) {
            if (todo.isDone()) {
                count ++;
            }
        }
        return count;
    }

}