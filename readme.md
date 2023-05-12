# MEditor

Miserable Editor, the miserable Java-based Emacs wannabe.

## Todo

- [-] Connect language interpreter
- [-] Define basic built-in's for editing the buffer
- [-] Add built-in for switching between command modes, i.e,
      simple command mode, lisp command mode and mlang mode
- [-] Infinitely spawneable resizeable panels (difficult)
- [-] Ability to choose the buffer type from available options,
      basic mode which uses the simple text editor and enhanced
      mode for the text pane view panel instead
- [-] Set a major mode per view panel, which dictates what is available
      like Emacs, these should be customiseable using the Editors languages
- [-] Like Emacs, multiple possible minor modes which customise
      the panels content
- [-] Adding custom key bindings using the built-in language, will require some 
      built-in function to communicate with the editor

Thinking of adding a virtual machine which acts as the basis for all languages that 
can be used in the editor. This way, I can run languages in an interpreted mode 
or compile them ahead of time AOT.

```
MEditorLang ---                    
               | -----> MEditor VM  ------> UI
MEditorLisp ---
```

The languages can then be optimised for the VM runtime without being aware of 
how the underlying UI functions, also enabling new language features to be more 
easily implemented. The VM becomes the gateway to the UI.

I want the UI to support different window types, such as loading a text buffer 
into a window or a form (such as for editor settings)... Question is, how to implement...

```
UI will have a grid of the form MeditorDisplay<MeditorWindow<?>>
- The MEditor VM won't directly know what type of MeditorWindow it is working with
  because of this, when it retrieves a window from the display via the UI that is
- Making the VM implement MeditorWindowVisitor could then handle the various 
  window types from the VM, could then visit windows like
  MeditorWindow<JTextPane>, MeditorWindow<TextPane>, MeditorWindow<CustomComponent>, etc...
- This will make it so the MeditorWindow doesn't actually know anything about its 
  content aside from that it has content, so the visitor is necessary for 
  implementing new functionality on the windows
```
      
## Editor commands

### Interpreter related

setInterpreter(type)

- Sets the interpreter to the specified type (SIMPLE or FOX)

### Buffer related

openBuffer(name, row, col):

- Opens a buffer with the given name at the specified 
row and column. If the buffer does not exist, it is 
created automatically and opened in a new window at 
the given row and col.

closeBuffer(name):

- Closes the given buffer but not the window it is 
displayed on.

getBufferContent(name)

- Returns the text content of a buffer

getBufferCharLength(name)

- Returns the number of characters in the given buffer

getNumberOfLines(name)

- Returns the number of lines in the given buffer

### MeditorWindow related

closeWindow(row, col):

- Closes and removes the Window at the given row and column,
this does not delete the buffer it is backed by. To close 
a buffer, use closeBuffer(name);

openWindow(row, col):

- Opens a new window, which may or may not be visible depending on 
whether the Editors show empty windows option is enabled. The given 
window will have no content by default as it is not backed by a buffer.

setActiveWindow(row, col):

- Sets the active window on the display to the window
at the given position on the grid, this will change focus to that window. 
If a window at the given position does not exist, it is created.

### Caret related

getCaretPosition()

- Returns the carets current position in the currently active window

getSelectedText()

- Returns the currently selected text. Empty string means 
no selected text.

getSelectionStart()

- Gets the start position of a selection.

getSelectionEnd()

- Gets the end position of a selection

moveCaretPosition(int amount)

- Moves the caret by the given amount

setCaretPosition(int pos)

- Sets the caret to the given position

moveCaretUp()/moveCaretDown()/moveCaretLeft()/moveCaretRight()

- Moves the caret up/right/down/left one line in the active window buffer
