package swi.wikisniffer.book.service.wikibooks;

final public class Api {
    public static final String PATH = "/w/api.php";
    public static final String FORMAT = "format";
    public static final String ACTION = "action";


    public static final class Format {
        public static final String JSON = "json";
    }

    final public static class Query {
        public static final String TITLES = "titles";
        public static final String PROP = "prop";
        public static final String ACTION = "query";

        final public static class ImageInfo {
            public static final String II_PROP = "iiprop";
            public static final String PROP = "imageinfo";

            final public static class IIProp {
                public static final String URL = "url";
            }
            final public static class Response {
                public static final String URL = "url";
            }
        }

        final public static class Response {
            public static final String QUERY = "query";

            final public static class ResponseQuery {
                public static final String PAGES = "pages";

                final public static class ResponsePage {
                    public static final String TITLE = "title";
                    public static final String IMAGE_INFO = "imageinfo";
                }
            }
        }
    }
}
