/**
 * @file PrintHtml.java
 * @author Aloïs Kernaonet (2022)
 * @author Maxime Lacourte (2022)
 * @version 1.00.00
 * @date 2022-09-06
 *
 * @copyright License Cecill-V2 \n
 * <http://www.cecill.info/licences/Licence_CeCILL_V2-en.html>
 *
 * (c) CNRS - Universite d'Orleans - INRAE (France)
 */
/*
 * This file is part of FullSWOF-transfer_UI software.
 * <https://sourcesup.renater.fr/projects/fullswof-ui/>
 *
 * FullSWOF-transfer_UI = Full Shallow-Water equations for Overland Flow, transfer equations.
 * User Interface.
 * This software designed to create input the file for the transfer equations of FullSWOF_1D.
 *
 * Contact: <fullswof.contact@listes.univ-orleans.fr>
 *
 * LICENSE
 *
 * This software is governed by the CeCILL license under French law and
 * abiding by the rules of distribution of free software. You can use,
 * modify and/ or redistribute the software under the terms of the CeCILL
 * license as circulated by CEA, CNRS and INRIA at the following URL
 * <http://www.cecill.info>.
 *
 * As a counterpart to the access to the source code and rights to copy,
 * modify and redistribute granted by the license, users are provided only
 * with a limited warranty and the software's author, the holder of the
 * economic rights, and the successive licensors have only limited
 * liability.
 *
 * In this respect, the user's attention is drawn to the risks associated
 * with loading, using, modifying and/or developing or reproducing the
 * software by the user in light of its specific status of free software,
 * that may mean that it is complicated to manipulate, and that also
 * therefore means that it is reserved for developers and experienced
 * professionals having in-depth computer knowledge. Users are therefore
 * encouraged to load and test the software's suitability as regards their
 * requirements in conditions enabling the security of their systems and/or
 * data to be ensured and, more generally, to use and operate it in the
 * same conditions as regards security.
 *
 * The fact that you are presently reading this means that you have had
 * knowledge of the CeCILL license and that you accept its terms.
 *
 ******************************************************************************/

package fr.univ.fullswofng.views.upperNavBar;

import javafx.geometry.Dimension2D;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;

/**
 * @brief
 * An instance of this class is a Stage used to display the content of any HTML
 * with basic style support.
 * @details
 * It is useful to display the user manual or the
 * application credits.
 */
public class PrintHtml extends Stage {

    /**
     * @details
     * Constructs a window displaying a HTML file.
     *
     * @param url
     *            the URL of the HTML file to display
     * @param title
     *            the title of the stage
     * @throws IOException
     *             if the file cannot be read
     */
    public PrintHtml(URL url, String title) throws IOException {
        super();
        setTitle(title);
        setOnCloseRequest(event -> close());
        setScene(createContent(url));

        centerOnScreen();
        show();

    }

    /**
     * Returns a scroll pane with a WebView to display the content of a HTML file
     *
     * @param url
     *            the URL of the HTML file to display
     * @return a scroll pane with a WebView to display the content of a HTML file
     * @throws IOException
     *             if the file cannot be read
     */
    private Scene createContent(URL url) throws IOException {
        WebView webView = new WebView();
        WebEngine webEngine = webView.getEngine();
        webEngine.load(url.toString());

        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setContent(webView);
        scrollPane.setFitToWidth(true);
        scrollPane.setFitToHeight(true);



        return new Scene(scrollPane);
    }


    public void setSize(Dimension2D dimension) {
    }

    public void setLocationRelativeTo(Object o) {
    }
}


