This is my first time doing web development in Java. I could have done this in Django since it is easier but since I wanted to learn how to use Java in a proper application I did in this. 

This uses a Model-View-Controller similar to Django and RoR, but Java Swing is used.

Remember to deploy from the mainframe view and not from others. Go to MainFrame.java and press the run option in the file. You will find at public static void main section.

There may be issues as I developed this on VScode. If so then configure .vscode to become .settings

The frontend for this is not so good.

Have to implement navigating backwards which till now was done by drop down menu on the left.

Steps for deploying.

1. Clone the repository given. ```git clone https://github.com/shreyasudaya/FMx```
2. Navigate to said repository.
3. Make sure you have java installed.
4. Now in terminal, copy paste following command:
   ```
   d:; cd 'd:{Location of the repo you cloned}'; & '{Location of your C:/Java}\jdk-21\bin\java.exe' '-agentlib:jdwp=transport=dt_socket,server=n,suspend=y,address=localhost:63640' '-XX:+ShowCodeDetailsInExceptionMessages' '-cp' '{Location you cloned}/bin' 'view.MainFrame'
   ```
5. Another way to do using VS Code:
    1. Install extensions on VS code for Java and Java Project Manager.
    2. Open New VS Code Window.
    3. Click Explorer at top left or to ```Ctrl+Shift+E```
    4. Select Create Java Project
    5. Copy the src folder, along with .classpath and .project files to the new Project.
    6. Go down to Java Projects and click on the Run and Debug option given.


