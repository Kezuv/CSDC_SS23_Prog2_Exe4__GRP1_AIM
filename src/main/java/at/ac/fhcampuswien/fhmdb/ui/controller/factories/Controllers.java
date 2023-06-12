package at.ac.fhcampuswien.fhmdb.ui.controller.factories;

public enum Controllers {
        HOME ("/at/ac/fhcampuswien/fhmdb/content/home.fxml"),
        MAIN_VIEW ("/at/ac/fhcampuswien/fhmdb/content/mainview.fxml"),
        WATCHLIST ("/at/ac/fhcampuswien/fhmdb/content/watchlist.fxml"),
        LOGIN_VIEW ("/at/ac/fhcampuswien/fhmdb/content/loginview.fxml");

        private String path;

        Controllers(String path) {
            this.path = path;
        }

        public String getPath(){
                return path;
        }

}
