window.Mapping = {
    CREATE_ITEM: "/item",
    DELETE_ITEMS: "/item/id",
    DELETE_KEYS: "/key",
    DELETE_LABELS: "/label",
    GET_ITEMS: "/item",
    GET_KEY: "/key/*",
    GET_KEYS: "/key",
    GET_KEYS_FOR_LABELS: "/key/label",
    GET_LABEL: "/label/*",
    GET_LABELS: "/label",
    LOGOUT: "/logout",
    GET_OBJECT_FROM_STORE: "/stored-object/*",
    GET_RANDOM_ITEM: "/item/random",
    SAVE_TO_OBJECT_STORE: "/stored-object",
    SPEAK_VALUE: "/value/play",
    UPDATE_ITEM: "/item/*",
    UPDATE_KEY: "/key/*",
    UPDATE_LABEL: "/label/*",

    concat: function(path, id){
        return path.replace("*", id);
    }
}