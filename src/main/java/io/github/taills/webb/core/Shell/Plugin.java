package io.github.taills.webb.core.Shell;

import javax.swing.*;

public interface Plugin {
   void init(ShellEntity var1);

   JPanel getView();
}
