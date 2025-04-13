package GOL;

import java.util.ListResourceBundle;

public class AuthorsListsPL extends ListResourceBundle {
    @Override
    protected Object[][] getContents() {
        return new Object[][]{
                {"authors", "Autorzy"},
                {"author1", "Klaudia Łuczak"},
                {"author2", "Julia Rzeźniczak"},
        };
    }
}
