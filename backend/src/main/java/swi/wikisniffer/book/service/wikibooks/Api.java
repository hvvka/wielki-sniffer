package swi.wikisniffer.book.service.wikibooks;

public final class Api {

    public static final String FORMAT = "format";
    public static final String ACTION = "action";

    public static final class Format {
        public static final String JSON = "json";
    }

    public static final class Query {
        public static final String TITLES = "titles";
        public static final String PROP = "prop";
        public static final String ACTION = "query";

        public static final class ImageInfo {
            public static final String II_PROP = "iiprop";
            public static final String PROP = "imageinfo";

            public static final class IIProp {
                public static final String URL = "url";
            }

            public static final class Response {
                public static final String URL = "url";
            }
        }

        public static final class Response {
            public static final String QUERY = "query";

            public static final class ResponseQuery {
                public static final String PAGES = "pages";

                public static final class ResponsePage {
                    public static final String TITLE = "title";
                    public static final String IMAGE_INFO = "imageinfo";
                }
            }
        }
    }
}
