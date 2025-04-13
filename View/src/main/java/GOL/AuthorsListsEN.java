package GOL;

import java.util.ListResourceBundle;

public class AuthorsListsEN extends ListResourceBundle {
    @Override
    protected Object[][] getContents() {
        return new Object[][]{
                {"authors", "Authors"},
                {"author1", "Klaudia Luczak"},
                {"author2", "Julia Rzezniczak"},
        };
    }
}
