package htw.gma_sose22.metronomeui.util

import android.app.Dialog
import android.content.DialogInterface
import androidx.appcompat.app.AlertDialog
import androidx.core.widget.doOnTextChanged
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.textfield.TextInputLayout

fun MaterialAlertDialogBuilder.showInput(
    layout: Int,
    tilId: Int,
    hintRes: Int,
    counterMaxLength: Int = 0,
    prefilled: String = ""
): Dialog {
    this.setView(layout)
    val dialog = this.show()
    val til = dialog.findViewById<TextInputLayout>(tilId)
    til?.let {
        til.hint = context.getString(hintRes)
        if (counterMaxLength > 0) {
            til.counterMaxLength = counterMaxLength
            til.isCounterEnabled = true
        }
        til.editText?.doOnTextChanged { text, _, _, _ ->
            dialog.getButton(AlertDialog.BUTTON_POSITIVE).isEnabled =
                !text.isNullOrBlank() && (counterMaxLength == 0 || text.length <= counterMaxLength)
        }
        til.editText?.append(prefilled)
        dialog.getButton(AlertDialog.BUTTON_POSITIVE).isEnabled = prefilled.isNotBlank()
    }
    return dialog
}

fun DialogInterface.inputText(tilId: Int): String {
    return (this as AlertDialog).findViewById<TextInputLayout>(tilId)?.editText?.text?.toString()
        .orEmpty()
}